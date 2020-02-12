#include <unistd.h> // appel systeme fork
#include <fcntl.h>  // appel system unix ES
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>

key_t cle;

int semid;

int fic2tab(char * pathname,int * tab,int size){
  int cible;
   // lecture  du fichier
   if ( (cible  = open(pathname,O_RDONLY)) < 0){
     fprintf(stderr,"probleme d'ouverture du fichier\n");
     return -1;
   }
 
   if (read(cible,tab,(size+1) * sizeof(int)) !=(size+1) * sizeof(int)) {    
     fprintf(stderr,"probleme de lecture du fichier\n");
     return -1;
   }
   close(cible);
   return 0;
}

int tab2fic(char * pathname,int * tab,int size){
  int cible;
  // creation du fichier
   if ( (cible  = open(pathname,O_WRONLY|O_CREAT|O_TRUNC,0666)) < 0){
     fprintf(stderr,"probleme d'ouverture du fichier\n");
     return -1;
   }
 
   if (write(cible,tab,(size+1) * sizeof(int)) !=(size+1) * sizeof(int)) {    
     fprintf(stderr,"probleme d'ecriture du fichier\n");
     return -1;
   }
   close(cible);
   return 0;
}

int main ( int argc , char **argv ) {

  int pidP,pidC; // numero des fils
  int buf[11];
  buf[0] = 0;
  int N = 10;
  int waitTime = 200000;
  printf("On fait attendre le consommateur de %d ms\n", waitTime/1000);

  ushort init_sem[3]={10,0,1}; //strucutre par initialise le semaphore mutex
   // Place, article, mutex

   // creation d'une cle IPC en fonction du nom du programme
   if ((cle=ftok(argv[0],'0')) == -1 ) {
	fprintf(stderr,"Probleme sur ftoks\n");
	exit(1);
   }

   // demande un ensemble de semaphore (ici un seul mutex)
   if ((semid=semget(cle,3,IPC_CREAT|IPC_EXCL|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(2);
   } 

   // initialise l'enseble
   if (semctl(semid,3,SETALL,init_sem)==-1) {
	fprintf(stderr,"Probleme sur semctl SETALL\n");
	exit(3);
   }
   tab2fic(argv[1], buf, N); //On inialise le fichier;
   
  struct sembuf op;
  if ((pidP =fork())==0) { // creation d'un processus fils
	// processus fils producteur
	int j = 0;
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
  usleep(waitTime); //On fait attendre le consommateur pour mettre en évidence la bonne gestion de la file
  if((pidC = fork())==0){
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

  // attend la terminaison des fils
  int nP,nC;
  waitpid(pidP, &nP, 0); 
  waitpid(pidC, &nC, 0);
  
  /* liberation du s\'emaphore */
  printf("Destruction des sémaphores\n");
  semctl(semid,0,IPC_RMID,0);
  
  exit(0);
}
