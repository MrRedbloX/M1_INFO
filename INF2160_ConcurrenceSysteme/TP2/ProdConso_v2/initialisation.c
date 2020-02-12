#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include "tab2fic.c"

int main (int argc , char **argv) {

  key_t cle;
  int semid;
  int buf[11];
  buf[0] = 0;
  int N = 10;


  ushort init_sem[3]={10,0,1}; //strucutre par initialise le semaphore mutex
   // Place, article, mutex
   
   if(argc < 2){
	  fprintf(stderr, "Usage : initialisation fileName");
	  exit(1);
   }

   // creation d'une cle IPC en fonction du nom du programme
   if ((cle=ftok(argv[1],'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(2);
   }

   // demande un ensemble de semaphore (ici un seul mutex)
   if ((semid=semget(cle,3,IPC_CREAT|IPC_EXCL|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(3);
   }

   // initialise l'enseble
   if (semctl(semid,3,SETALL,init_sem)==-1) {
	fprintf(stderr,"Probleme sur semctl SETALL\n");
	exit(4);
   }
   tab2fic(argv[1], buf, N); //On inialise le fichier;
	printf("Inialisation ok\n");
   exit(0);
}
