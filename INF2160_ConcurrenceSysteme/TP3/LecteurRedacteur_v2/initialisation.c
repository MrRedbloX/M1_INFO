#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include <sys/shm.h>

int main () {

  key_t cle;
  int semid;
  int shmid;

  system("touch /tmp/motdj");

  ushort init_sem[3]={1,0,0};
  //Mutex, semLec, semRed

   // creation d'une cle IPC en fonction du nom du programme
   if ((cle=ftok("/tmp/motdj",'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(1);
   }
   
   if ((semid=semget(cle,3,IPC_CREAT|IPC_EXCL|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(2);
   }

   // initialise l'ensenble
   if (semctl(semid,3,SETALL,init_sem)==-1) {
	fprintf(stderr,"Probleme sur semctl SETALL\n");
	exit(3);
   }

   // demande un ensemble de semaphore (ici un seul mutex)
   if ((shmid=shmget(cle,4096,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(4);
   }
   
   int *sem;
   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(5);
   }
   
   *sem=0; //Lecteur
   *(sem+1)=0; //DemandeLecteur
   *(sem+2)=0; //Redacteur
   *(sem+3)=0; //DemandeRedacteur
   shmdt(sem);

   printf("Inialisation ok\n");
   exit(0);
}
