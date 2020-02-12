#include <unistd.h>
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit

int main() {
	pid_t pid;
	int tube[2];
	
	if(pipe(tube) == -1){
		perror("pipe");
	}
	
	if((pid=fork()) > 0){
		char c;
		int nb;
		close(tube[0]);
		while((nb=read(0,&c,1)) > 0){
			write(tube[1], &c,1);
		}
		close(tube[1]);
	}
	else{
		char c;
		int nb;
		close(tube[1]);
		while((nb=read(tube[0],&c,1)) > 0){
			if(c != ' ') write(1,&c,1);
		}
		close(tube[0]);
		exit(0);
	}
}
