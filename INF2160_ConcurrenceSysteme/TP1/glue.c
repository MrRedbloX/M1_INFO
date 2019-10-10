#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sched.h>
#include <stdio.h>
#include <sys/resource.h>

int main(){
	pid_t pid;
	int count = 0;
	int status;
	
	pid = fork();
	if(pid == 0){
		setsid();
		setpriority(PRIO_PROCESS, 0, 10);
		pid_t pidtmp;
		while(1){
			count++;
			printf("Processus : %d\n", count);
			pidtmp = fork();
			if(pidtmp < 0){
				printf("Machine gluee");
				exit(1);
			}
			else if(pidtmp == 0){
				int j,k,x;
				for (j = 1; j <= 100; j++){
					for (k = 0; k < 1000000; k++) x=1./j;
				}
			}
		}
	}
	getchar();
	kill(-pid, 9);
	exit(0);
}
