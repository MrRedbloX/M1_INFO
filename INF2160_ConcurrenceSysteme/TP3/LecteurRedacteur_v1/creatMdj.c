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

   if ((cle=ftok("/tmp/motdj",'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(1);
   }
   
   if ((semid=semget(cle,2,IPC_CREAT|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(2);
   }

	struct sembuf op;
	
	op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
	semop(semid,&op,1);
	
	printf("Début rédacteur : \n");
	system("cat > /tmp/motdj");
	printf("Fin rédacteur\n");
	
	op.sem_num=0;op.sem_op=1;op.sem_flg=0;
	semop(semid,&op,1);
	
}
