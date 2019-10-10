#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include <sys/shm.h>

int main(){
  key_t cle;
  int semid;
  int shmid;

   if ((cle=ftok("/tmp/motdj",'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(1);
   }
   
   if ((semid=semget(cle,2,IPC_CREAT|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(2);
   }
   
   if ((shmid=shmget(cle,4096,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(4);
   }

	struct sembuf op;
	
	int *sem;
    if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(5);
    }
	op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
	semop(semid,&op,1);
	if(*sem || *(sem+2) || *(sem+3)){
		*(sem+3) = *(sem+3)+1; //DemandeRedacteur++
		shmdt(sem);
		op.sem_num=0;op.sem_op=1;op.sem_flg=0;
		semop(semid,&op,1);
		op.sem_num=2;op.sem_op=-1;op.sem_flg=0;
		semop(semid,&op,1);
		
		if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
			fprintf(stderr, "Probleme sur shmat\n");
			exit(5);
		}
		
		op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
		semop(semid,&op,1);
		*(sem+3) = *(sem+3)-1; //DemandeRedacteur--;
	}
	
	*(sem+2) = *(sem+2)+1; //Redacteur++
	shmdt(sem);
	op.sem_num=0;op.sem_op=1;op.sem_flg=0;
	semop(semid,&op,1);
	
	printf("Début rédacteur : \n");
	system("cat > /tmp/motdj");
	printf("Fin rédacteur\n");
	
	if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(5);
    }
    
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
	shmdt(sem);
	op.sem_num=0;op.sem_op=1;op.sem_flg=0;
	semop(semid,&op,1);
}
