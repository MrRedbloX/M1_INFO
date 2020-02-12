#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <stdio.h> // librairie standard C
#include <stdlib.h>
#include <sys/shm.h>

void main(){
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

   // demande un ensemble de semaphore (ici un seul mutex)
   if ((shmid=shmget(cle,4096,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(4);
   }
	
	/* liberation du s\'emaphore */
	printf("Destruction des sémaphores\n");
	semctl(semid,0,IPC_RMID,0);
	printf("Destruction du segment partagé\n");
	shmctl(shmid,IPC_RMID,0);
	
	system("rm /tmp/motdj");
}
