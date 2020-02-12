#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include "tab2fic.c"
#include <stdlib.h> // exit
#include <string.h>


int main (int argc , char **argv){
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
   
   struct sembuf op;
   int j = 0;
   int buf[11];
   int N = 10;
   short finish = 0; //Condition d'arret pour le consommateur
	  //Processus fils consommateur
	  while(1){ 
		op.sem_num=1;op.sem_op=-1;op.sem_flg=0;
		semop(semid,&op,1);
		op.sem_num=2;op.sem_op=-1;op.sem_flg=0;
		semop(semid,&op,1);
		
		fic2tab(argv[1],buf,N);
		if(buf[0] > 0){
			int conso = buf[1];
			buf[0] = buf[0]-1;
			memmove(buf+1,buf+2,buf[0]*sizeof(int));
			printf("Consomateur lit : %d \n", conso);
			tab2fic(argv[1],buf,N);
			if(conso == 49) finish = 1; //Il doit finir
		}
		op.sem_num=2;op.sem_op=1;op.sem_flg=0;
		semop(semid,&op,1);
		op.sem_num=0;op.sem_op=1;op.sem_flg=0;
		semop(semid,&op,1);
		
		if(finish == 1) { //On vérifie s'il a fini
			printf("Le consommateur a fini\n");
			break;
		}
	  }
	 exit(0);
}
