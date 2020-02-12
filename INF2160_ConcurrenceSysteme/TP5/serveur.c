#include <unistd.h>
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <fcntl.h>
#include <time.h>
#include <string.h>

int main(){
	int t1,t2;
	char c;
	if((t1=open("tubeCommun",O_RDONLY)) < 0){
		fprintf(stderr,"Failed to open tubeCommun in serveur\n");
		exit(1);
	}
	char ch[2];
	read(t1,&c,1);
	ch[0] = c;
	ch[1] = '\0';
	
	if((t2=open(ch,O_WRONLY)) < 0){
		exit(1);
	}
	
	time_t tps;
	char date[24];
	tps = time(0);
	strcpy(date,ctime(&tps));
	
	write(t2, date, 24);
	close(t2);
	close(t1);
	exit(0);
}
