#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <stdio.h> // librairie standard C
#include <stdlib.h>
#include "tab2fic.c"

void main(int argc , char **argv){
	if(argc < 2){
	  fprintf(stderr, "Usage : initialisation fileName");
	  exit(1);
   }
    key_t cle;
	int semid;
	
	if ((cle=ftok(argv[1],'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(2);
   }
   
   if ((semid=semget(cle,3,0))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(3);
   }
	
	/* liberation du s\'emaphore */
	printf("Destruction des sémaphores\n");
	semctl(semid,0,IPC_RMID,0);
}
