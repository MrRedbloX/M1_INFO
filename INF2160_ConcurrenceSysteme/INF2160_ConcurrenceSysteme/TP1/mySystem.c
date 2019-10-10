#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sched.h>

int mySystem(char* string){
	int pid;
	int ret;
	if ((pid = fork()) == 0) {
		ret = execl("/bin/sh","sh","-c",string, NULL);
	}
	wait(NULL);
	return ret;
}

int main(){
	mySystem("ls -la");
}
