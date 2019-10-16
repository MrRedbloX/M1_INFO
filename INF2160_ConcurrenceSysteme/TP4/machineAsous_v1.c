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
	usleep(5000);
	exit(1);

} 

int main (int argc, char ** argv) {
	
	if (argc > 2) {
		fprintf(stderr,"Usage : machineAsous N\n");
		exit(1);
	}

  int semid;
  int shmid;

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
   srandom(time(&t));

   for(i=0; i<atoi(argv[1]); i++) *(sem+i) = random()%10;
   shmdt(sem);
   struct sembuf op;
   for(i=0; i<atoi(argv[1]); i++){
	   if((pid = fork() == 0)){
		   
		   struct sigaction actions;
		   int rc;
		   sigset_t set;
		   
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
			   
			   shmdt(sem);
			   usleep(50);		   
			   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
			   semop(semid,&op,1);
			   
			   sigemptyset(&set);
			   sigprocmask(SIG_SETMASK, &set, NULL);
			   
		   }
		}
   }
   
   if((pid = fork() == 0)){
	   struct sigaction actions;
	   int rc;
	   sigset_t set;
	   
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
   
   for(i=0; i<atoi(argv[1]);i++) kill(pid,SIGUSR1);
   
   printf("Debug\n");
   
   for(i=0; i<atoi(argv[1]); i++){
	   op.sem_num=0;op.sem_op=-1;op.sem_flg=0;
	   semop(semid,&op,1);
	   
	   if((sem=(int *)shmat(shmid,0,0)) == (int*)-1){
		   fprintf(stderr, "Probleme sur shmat\n");
		   exit(10);
	   }
	   
	   printf("%d ",*(sem+i));
	   
	   op.sem_num=0;op.sem_op=1;op.sem_flg=0;
	   semop(semid,&op,1);
   }
   printf("\n");
   
   semctl(semid,0,IPC_RMID,0);
   exit(0);
}
