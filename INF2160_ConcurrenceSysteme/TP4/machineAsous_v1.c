#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <sched.h>  // sche_yield
#include <sys/types.h>
#include <sys/sem.h> // semaphore IPC
#include <sys/ipc.h> // services IPC
#include <sys/wait.h> // wait
#include <string.h>
#include <sys/shm.h>
#include <sys/time.h>
#include <signal.h>

void handler(int sig){
	if(sig == SIGUSR1){		
		fdatasync(0);
		//printf("Exit %d\n", getpid());
		exit(1);
	}
} 

int main (int argc, char ** argv) {
	
	if (argc > 2) {
		fprintf(stderr,"Usage : machineAsous N\n");
		exit(1);
	}

  int semid;
  int shmid;
  int tabPid[4096];

  ushort init_sem[1]={1};
  //Mutex
   
   if ((semid=semget(IPC_PRIVATE,1,IPC_CREAT|IPC_EXCL|0666))==-1) {
	fprintf(stderr,"Probleme sur semget\n");
	exit(3);
   }

   // initialise l'ensenble
   if (semctl(semid,1,SETALL,init_sem)==-1) {
	fprintf(stderr,"Probleme sur semctl SETALL\n");
	exit(4);
   }

   if ((shmid=shmget(IPC_PRIVATE,4096,IPC_CREAT|0644))==-1) {
	fprintf(stderr,"Probleme sur shmget\n");
	exit(5);
   }
   
   int *sem;
   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
	   fprintf(stderr, "Probleme sur shmat\n");
	   exit(6);
   }
   
   int init;
   int i;
   time_t t;
   pid_t pid;
   sigset_t set;
   struct sigaction actions;
   int rc;
   
   srandom(time(&t));

   for(i=0; i<atoi(argv[1]); i++) *(sem+i) = random()%10;
   struct sembuf op;
   for(i=0; i<atoi(argv[1]); i++){
	   pid = fork();
	   *(tabPid+i) = pid;
	   if(pid == 0){
		   //printf("PID f : %d\n",getpid());
		   sigemptyset(&actions.sa_mask);
		   actions.sa_flags = 0;
		   actions.sa_handler = handler;
		   rc = sigaction(SIGUSR1,&actions,NULL);
		   while(1){
			   sigemptyset(&set);
			   sigaddset(&set,SIGUSR1);
			   sigprocmask(SIG_SETMASK, &set, NULL);
			   
			   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
			   semop(semid,&op,1);
			   
			   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
				   fprintf(stderr, "Probleme sur shmat\n");
				   exit(7);
			   }
			   
			   if(*(sem+i) == 9) *(sem+i) = 0;
			   else *(sem+i) = *(sem+i) + 1;
			   
			   usleep(100);		   
			   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
			   semop(semid,&op,1);
			   
			   sigemptyset(&set);
			   sigprocmask(SIG_SETMASK, &set, NULL);
			   
		   }
		}
   }
   pid = fork();
   *(tabPid+atoi(argv[1])) = pid;
   if(pid == 0){
	   sigemptyset(&actions.sa_mask);
	   actions.sa_flags = 0;
	   actions.sa_handler = handler;
	   rc = sigaction(SIGUSR1,&actions,NULL);
	   while(1){
		   //printf("PID f : %d\n",getpid());
		   sigemptyset(&set);
		   sigaddset(&set,SIGUSR1);
		   sigprocmask(SIG_SETMASK, &set, NULL);
		   
		   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
		   semop(semid,&op,1);
		   
		   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
			   fprintf(stderr, "Probleme sur shmatIci\n");
			   exit(8);
		   }
		   
		   for(i=0; i<atoi(argv[1]);i++) printf("%d ", *(sem+i));
		   printf("\r");
		   
		   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
		   semop(semid,&op,1);
		   
		   sigemptyset(&set);
		   sigprocmask(SIG_SETMASK, &set, NULL);
	   }
	}
	
	getchar();

	//printf("\r");
	for(i=0; i<atoi(argv[1])+1;i++){
		printf("Kill %d\n",tabPid[i]);
		kill(tabPid[i],SIGUSR1);
		wait(NULL);
		sleep(1);
	}
	/*kill(tabPid[i+1],SIGUSR1);
	wait(NULL);*/
	
	op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
	semop(semid,&op,1);
	
	if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
		 fprintf(stderr, "Probleme sur shmat\n");
		 exit(10);
	 }
	 
	 //Blabla
	 
	 op.sem_num=0;op.sem_op=1;op.sem_flg=0;
	 semop(semid,&op,1);
	 
	 printf("Destruction sémaphore\n");
	 semctl(semid,0,IPC_RMID,0);
	 
    exit(0);
}
