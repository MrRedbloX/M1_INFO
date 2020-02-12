#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include <sys/shm.h>

int winner(int size, int tab[]){
	int ret=0;
	int i,j;
	int count = 0;
	for(i=0; i<size; i++){
		for(j=0; j<size; j++){
			if(tab[i] == tab[j]) count++;
		}
		if(count >= size-1){
			ret = 1;
			break;
		}
		count = 0;
	}
	return ret;
}

void handler(int sig){
	if(sig == SIGUSR1){
    exit(1);
	}
}

int main(int argc, char ** argv){
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

   sigset_t set;
   struct sigaction actions;
   int rc;
   struct sembuf op;
   int *sem;
   int i;
   pid_t pid;

   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
   semop(semid,&op,1);

   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(5);
   }

   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
   semop(semid,&op,1);

   if((pid = fork()) == 0){
	   sigemptyset(&actions.sa_mask);
	   actions.sa_flags = 0;
	   actions.sa_handler = handler;
	   rc = sigaction(SIGUSR2,&actions,NULL);
	   while(1){
		   //printf("PID f : %d\n",getpid());
		   sigemptyset(&set);
		   sigaddset(&set,SIGUSR2);
		   sigprocmask(SIG_SETMASK, &set, NULL);

		   //Partie 1 lecteur début
		   if((sem=(int *)shmat(shmid,0,0)) != (int*)-1){
			   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
			   semop(semid,&op,1);

			   if(*(sem+2) || *(sem+3)){
				   *(sem+1) = *(sem+1)+1; //DemandeLecteur++
				   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
				   semop(semid,&op,1);
				   op.sem_num=1;op.sem_op=-1;op.sem_flg=0;
				   semop(semid,&op,1);

				   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
				   semop(semid,&op,1);

				   *(sem+1) = *(sem+1)-1; //DemandeLecteur--
			   }

			   *sem = *sem+1; //Lecteur++
			   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
			   semop(semid,&op,1);
			   //Partie 1 lecteur fin
			   
			   if((sem=(int *)shmat(shmid,0,0)) != (int*)-1){

				   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
				   semop(semid,&op,1);

				   for(i=0; i<atoi(argv[1]);i++) printf("%d ", *(sem+5+i));
				   printf("\r");

				   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
				   semop(semid,&op,1);
			   }

			   //Partie 2 lecteur début
			   
			   if((sem=(int *)shmat(shmid,0,0)) != (int*)-1){

				   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
				   semop(semid,&op,1);

				   *sem = *sem-1; //Lecteur--

				   if(*sem == 0 && *(sem+3)){
					   op.sem_num=2;op.sem_op=1;op.sem_flg=0;
					   semop(semid,&op,1);
				   }
				   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
				   semop(semid,&op,1);
			   }
			   //Partie 2 lecteur fin
		   }

		   sigemptyset(&set);
		   sigprocmask(SIG_SETMASK, &set, NULL);
		   usleep(500);
	   }
	}

  getchar();
  op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
  semop(semid,&op,1);

  if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
  fprintf(stderr, "Probleme sur shmat\n");
  exit(5);
  }

  op.sem_num=0;op.sem_op=1;op.sem_flg=0;
  semop(semid,&op,1);

  pid_t pidRedac = *(sem+4);

  kill(pidRedac,SIGUSR1);
  kill(pid,SIGUSR1);
  wait(NULL);

  int tab[atoi(argv[1])];

  for(i=0; i<atoi(argv[1]);i++) tab[i] = *(sem+5+i);

  int win = winner(atoi(argv[1]),tab);
  if(win) printf("Bravo vous avez gagné !\n");
  else printf("C'est perdu retentez votre chance.\n");
}
