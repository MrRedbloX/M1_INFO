#include <unistd.h>
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <fcntl.h>

int main(){
	int t1,t2;
	char c;
	if((t1=open("tubeCommun",O_WRONLY)) < 0){
		fprintf(stderr,"Failed to open tubeCommun in client\n");
		exit(1);
	}
	char ch = 'a';
	char * tube = "a"; 
	write(t1,&ch,1);
	
	mode_t mode=S_IRUSR|S_IWUSR|S_IRGRP;
	umask(0);
	if (mkfifo(tube,mode) == -1) {
		perror("a");
		exit(1);
	}
	if((t2=open(tube,O_RDONLY)) < 0){
		exit(1);
	}
	while(read(t2,&c,1) > 0){
		write(1,&c,1);
	}
	char rt = '\n';
	write(1,&rt,1);
	close(t2);
	if (unlink(tube)== -1) {
		fprintf(stderr,"Failed to delete tubeCommun in client\n");
		exit(1);
	}
	close(t1);
	exit(0);
}
