#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sched.h>

int add(int size, char **arr){
	int res = 0;
	if(size > 0){
		res = addRec(size, arr, 1);	
	}
	return res;
}

int addRec(int size, char** arr, int pos){
	int n;
	pid_t pid;
	if((pid = fork()) == 0){
		printf("Lancement d'un processus pour calcul d'une addition\n");
		if(pos == size-1) exit(atoi(arr[pos]));
		else exit(atoi(arr[pos])+addRec(size,arr,pos+1));
	}
	waitpid(pid, &n, 0);
	return WEXITSTATUS(n);
}

int main(int argc, char **argv){
	printf("Res : %d\n", add(argc, argv));
}
