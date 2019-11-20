//COGOLUEGNES Charles
//Ce programme se base sur le modèle lecteur/rédacteur avec une priorité sur le rédacteur
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include <sys/shm.h>

int main(int argc, char ** argv){
  if(argc < 2){
	fprintf(stderr,"usage : poeme N\n");
	exit(1);
  }
  int N,i;
  N = atoi(argv[1]); //Le nombre de processus fils / le nombre de ligne
  int semid;
  int shmid;
  ushort init_sem[3]={1,0,0};
  
  //Mutex, semLec, semRed
   if ((semid=semget(IPC_PRIVATE,3,IPC_CREAT|IPC_EXCL|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(1);
   }
   // initialise l'ensemble
   if (semctl(semid,3,SETALL,init_sem)==-1) {
	fprintf(stderr,"Probleme sur semctl SETALL\n");
	exit(1);
   }
   // demande un ensemble de semaphore (ici un seul mutex)
   if ((shmid=shmget(IPC_PRIVATE,4400,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(1);
   } 
   int *sem;
   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(1);
   }
   *sem=0; //Lecteur
   *(sem+1)=0; //DemandeLecteur
   *(sem+2)=0; //Redacteur
   *(sem+3)=0; //DemandeRedacteur
   *(sem+4)=0; //Permet au rédacteur de dire qu'il a fini
   printf("Inialisation ok\n");
   
   struct sembuf op;
	for(i=0; i<N; i++){
		if(fork() == 0){
			if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
			   fprintf(stderr, "Probleme sur shmat\n");
			   exit(1);
			}
			while(*(sem+4) == 0){
			   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
			   semop(semid,&op,1);
			   if(*(sem+2) || *(sem+3)){
				   *(sem+1) = *(sem+1)+1; //DemandeLecteur++
				   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
				   semop(semid,&op,1);
				   op.sem_num=1;op.sem_op=-1;op.sem_flg=0;
				   semop(semid,&op,1);
				   
				   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
					   fprintf(stderr, "Probleme sur shmat\n");
					   exit(1);
				   }
				   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
				   semop(semid,&op,1);
				   
				   *(sem+1) = *(sem+1)-1; //DemandeLecteur--
			   }
			   *sem = *sem+1; //Lecteur++
			   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
			   semop(semid,&op,1);
			   
			   printf("Début lecteur %d\n",getpid());
			   int j= 0;
			   char c = *(sem+5+(i*128)); //On commence par lire un caractère au l'endroit correspondant au lecteur i
			   char str[128]; //Notre ligne
			   while(c != 0 && j < 127){
				   str[j] = c; //On lui rajoute le caractère
				   j = j+1;
				   c = *(sem+5+(i*128)+j); //On récupère un autre caractère
			   }
			   char *mot = strtok(str, " \n");
			   while(mot != (char *)NULL){
				   printf("%s\n", mot);
				   sleep(1);
				   mot = strtok(NULL, " \n"); 
			   }
			   printf("Fin lecteur %d\n",getpid());
			   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
			   semop(semid,&op,1);
			   *sem = *sem-1; //Lecteur--	   
			   if(*sem == 0 && *(sem+3)){
				   op.sem_num=2;op.sem_op=1;op.sem_flg=0;
				   semop(semid,&op,1);
			   }
			   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
			   semop(semid,&op,1);
			   /*if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
				   fprintf(stderr, "Probleme sur shmat\n");
				   exit(1);
			   }*/
			   //exit(0);
		   }
		   exit(0);
		}
	}
	int t;
	char c;
	char ligne[128];
	int k,m = 0; //k va servir à écrire au bon endroit dans la mémoire et l sert à écrire dans le tableau
	
	while((fgets(ligne,128,stdin) != NULL)){
		//printf("%s\n",ligne);
		op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
		semop(semid,&op,1);
		if(*sem || *(sem+2) || *(sem+3)){
			*(sem+3) = *(sem+3)+1; //DemandeRedacteur++
			op.sem_num=0;op.sem_op=1;op.sem_flg=0;
			semop(semid,&op,1);
			op.sem_num=2;op.sem_op=-1;op.sem_flg=0;
			semop(semid,&op,1);
			
			op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
			semop(semid,&op,1);
			*(sem+3) = *(sem+3)-1; //DemandeRedacteur--;
		}
		*(sem+2) = *(sem+2)+1; //Redacteur++
		op.sem_num=0;op.sem_op=1;op.sem_flg=0;
		semop(semid,&op,1);
		
		printf("Début rédacteur \n");
		m = 0;
		while(m < 128){
			*(sem+5+k+m) = ligne[m]; //On écrit le caractère au bon endroit dans la mémoire partagée
			m = m+1;
		}
		printf("Fin rédacteur\n");
		
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
		op.sem_num=0;op.sem_op=1;op.sem_flg=0;
		semop(semid,&op,1);
		k = k+128; //On incrémente k de 128 pour passer à la ligne suivante dans le mémoire partagée
	}
	*(sem+4) = 1; //On dit aux lecteurs que le rédacteur a fini
	//printf("On dit aux lecteurs de stop : %d\n", *(sem+4));
	for(i=0; i<N; i++) wait(NULL);
	printf("Destruction des sémaphores\n");
	semctl(semid,0,IPC_RMID,0);
	printf("Destruction du segment partagé\n");
	shmctl(shmid,IPC_RMID,0);
	exit(0);
}
