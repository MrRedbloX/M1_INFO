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
	
   int *nbL;
   if((nbL=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(5);
   }
   
   op.sem_num=1;op.sem_op=-1;op.sem_flg=0;
   semop(semid,&op,1);
   
   nbL = nbL+1;
   shmdt(nbL);
   
   if(nbL==(int*)1){
	   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
	   semop(semid,&op,1);
   }
   
   op.sem_num=1;op.sem_op=1;op.sem_flg=0;
   semop(semid,&op,1);
   printf("Début lecteur : \n");
   FILE *fic;
   if ((fic=fopen("/tmp/motdj","r"))!=NULL){
	   char str[4096];
	   while ( (fgets(str,4096,fic) != (char*) NULL)){
		   printf("%s", str);
		   sleep(5);
	   }
   }
   
   printf("Fin lecteur \n");
   
   if((nbL=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(5);
   }
   
   op.sem_num=1;op.sem_op=-1;op.sem_flg=0;
   semop(semid,&op,1);
   
   nbL= nbL-1;
   
   if(nbL==(int*)0){
	   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
	   semop(semid,&op,1);
   }
   
   op.sem_num=1;op.sem_op=1;op.sem_flg=0;
   semop(semid,&op,1);
   
}
