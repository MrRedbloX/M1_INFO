#include <unistd.h>
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <fcntl.h>

int main() {
	if(fork() > 0){
		if(fork() > 0){
			int t;
			char c;
			int nb;
			if((t=open("tubeCompress",O_WRONLY)) < 0){
				fprintf(stderr,"Failed to open in tubeCompress in P1\n");
				exit(1);
			}
			while((nb=read(0,&c,1)) > 0){
				write(t, &c,1);
			}
			close(t);
			exit(0);
		}
		if(fork() > 0){
			int t;
			char c;
			int nb;
			if((t=open("tubeResult",O_RDONLY)) < 0){
				fprintf(stderr,"Failed to open in tubeResult in P1\n");
				exit(1);
			}
			while((nb=read(t,&c,1)) > 0){
				write(1,&c,1);
			}
			close(t);
			exit(0);
		}
		exit(0);
	}
	if(fork() > 0){
		int t1,t2;
		char c;
		int nb;
		if((t1=open("tubeCompress",O_RDONLY)) < 0){
			fprintf(stderr,"Failed to open in tubeResult in P2\n");
			exit(1);
		}
		if((t2=open("tubeMajuscule",O_WRONLY)) < 0){
			fprintf(stderr,"Failed to open in tubeMajuscule in P2\n");
			exit(1);
		}
		while((nb=read(t1,&c,1)) > 0){
			if(c != ' ') write(t2,&c,1);
		}
		close(t1);
		close(t2);
		exit(0);
	}
	
	if(fork() > 0){
		int t1,t2;
		char c;
		int nb;
		if((t1=open("tubeMajuscule",O_RDONLY)) < 0){
			fprintf(stderr,"Failed to open in tubeMajuscule in P3\n");
			exit(1);
		}
		if((t2=open("tubeResult",O_WRONLY)) < 0){
			fprintf(stderr,"Failed to open in tubeResult in P3\n");
			exit(1);
		}
		while((nb=read(t1,&c,1)) > 0){
			char tmp = toupper(c);
			write(t2,&tmp,1);
		}
		close(t1);
		close(t2);
		exit(0);
	}
	
}
