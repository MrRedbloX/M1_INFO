#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include <sys/shm.h>
#include <errno.h>

int tabPid[4096];
int size = 0;

void handler(int sig){
	if(sig == SIGUSR1){
    int i;
    printf("Want to kill\n");
    for(i=0; i<size;i++){
  		printf("Kill %d\n",tabPid[i]);
  		kill(tabPid[i],SIGUSR2);
  		wait(NULL);
  		sleep(1);
  	}
	}
  else if(sig == SIGUSR2){
    //printf("Exit %d\n", getpid());
		exit(1);
  }
}

int main(int argc, char ** argv){

  if (argc > 2) {
   fprintf(stderr,"Usage : Init N\n");
   exit(1);
 }
 size = atoi(argv[1]);

  key_t cle;
  int semid;
  int shmid;

   if ((cle=ftok("./key",'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(1);
   }

   if ((semid=semget(cle,3,IPC_CREAT|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(2);
   }

   if ((shmid=shmget(cle,4096,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(4);
   }

 	//Rouleaux début
  int i;
  struct sembuf op;
  pid_t pid;
  sigset_t set;
  struct sigaction actions;
  int rc;
  int *sem;

  op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
  semop(semid,&op,1);

  if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
  fprintf(stderr, "Probleme sur shmat1\n");
  exit(5);
  }
  *(sem+4) = getpid();
  op.sem_num=0;op.sem_op=1;op.sem_flg=0;
  semop(semid,&op,1);
  for(i=0; i<atoi(argv[1]); i++){
    pid = fork();
    *(tabPid+i) = pid;
    if(pid == 0){
      //printf("PID f : %d\n",getpid());
      sigemptyset(&actions.sa_mask);
      actions.sa_flags = 0;
      actions.sa_handler = handler;
      rc = sigaction(SIGUSR2,&actions,NULL);
      while(1){
        //Empêche les signaux pendant l'utilisation des sémaphores
        sigemptyset(&set);
        sigaddset(&set,SIGUSR2);
        sigprocmask(SIG_SETMASK, &set, NULL);
        //Partie 1 rédacteur début

        op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
       	semop(semid,&op,1);
       	if(*sem || *(sem+2) || *(sem+3)){
       		*(sem+3) = *(sem+3)+1; //DemandeRedacteur++
       		op.sem_num=0;op.sem_op=1;op.sem_flg=0;
       		semop(semid,&op,1);
       		op.sem_num=2;op.sem_op=-1;op.sem_flg=0;
       		semop(semid,&op,1);

       		op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
       		semop(semid,&op,1);

       		*(sem+3) = *(sem+3)-1; //DemandeRedacteur--;
       	}

       	*(sem+2) = *(sem+2)+1; //Redacteur++
        op.sem_num=0;op.sem_op=1;op.sem_flg=0;
       	semop(semid,&op,1);
        //Partie 1 rédacteur fin

        op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
        semop(semid,&op,1);

        if(*(sem+5+i) == 9) *(sem+5+i) = 0;
        else *(sem+5+i) = *(sem+5+i) + 1;

        op.sem_num=0;op.sem_op=1;op.sem_flg=0;
        semop(semid,&op,1);
        usleep(200);

        //Partie 2 rédacteur début

       	op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
       	semop(semid,&op,1);

       	*(sem+2) = *(sem+2)-1; //Redacteur--

       	if(*(sem+3) != 0){
       		op.sem_num=2;op.sem_op=1;op.sem_flg=0;
       		semop(semid,&op,1);
       	}
       	else{
       		if(*(sem+1)){
       			int nb;
       			for(nb=0; nb<(*(sem+1)); nb++){
       				op.sem_num=1;op.sem_op=1;op.sem_flg=0;
       				semop(semid,&op,1);
       			}
       		}
       	}
       	op.sem_num=0;op.sem_op=1;op.sem_flg=0;
       	semop(semid,&op,1);
        //Partie 2 rédacteur fin

        sigemptyset(&set);
        sigprocmask(SIG_SETMASK, &set, NULL);
      }
   }
  //Rouleau fin
  exit(0);
  }
}
