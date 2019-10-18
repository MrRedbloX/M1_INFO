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

int main(){
  key_t cle;
  int semid;
  int shmid;
  int *sem;
  struct sembuf op;
  int tab[4096];
  int i;

  getchar();

   if ((cle=ftok("./key",'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(1);
   }

   if ((shmid=shmget(cle,4096,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(4);
   }

   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
   semop(semid,&op,1);

   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
     fprintf(stderr, "Probleme sur shmat\n");
     exit(5);
   }

   pid_t pidRedac = *(sem+4);
   pid_t pidLect = *(sem+5);
   int size = *(sem+6);

   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
   semop(semid,&op,1);

   kill(pidRedac, SIGUSR1);
   kill(pidLect, SIGUSR1);

   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
   semop(semid,&op,1);


   if((sem=(int *)shmat(shmid,0,0)) != (int*)-1){
     for(i=0; i<size;i++) tab[i] = *(sem+6+i);
   }

   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
   semop(semid,&op,1);

  int win = winner(size,tab);
  if(win) printf("Bravo vous avez gagné !\n");
  else printf("C'est perdu retentez votre chance.\n");
}
