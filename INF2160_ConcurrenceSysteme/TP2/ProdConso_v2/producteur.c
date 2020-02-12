#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include "tab2fic.c"
#include <stdlib.h> // exit

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
	while(1){
		if(j < 50){
			op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
			semop(semid,&op,1);
			op.sem_num=2;op.sem_op=-1;op.sem_flg=0;
			semop(semid,&op,1);
			
			printf("Producteur ajoute de %d\n", j);
			
			fic2tab(argv[1],buf,N);
			buf[buf[0]+1] = j;
			buf[0] = buf[0]+1;
			tab2fic(argv[1],buf,N);
			
			op.sem_num=2;op.sem_op=1;op.sem_flg=0;
			semop(semid,&op,1);
			op.sem_num=1;op.sem_op=1;op.sem_flg=0;
			semop(semid,&op,1);
			j++;
		}
		else{
			printf("Le producteur a fini\n");
			break;
		}
	}
	
	exit(0);
}
