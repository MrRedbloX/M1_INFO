#include <unistd.h>
#include <stdio.h> // librairie standard C
#include <stdlib.h> // exit
#include <fcntl.h>

int main() {
	int t;
	char c;
	if((t=open("tubeNomme",O_RDONLY)) < 0){
		exit(1);
	}
	while(read(t,&c,1) > 0){
		char tmp = toupper(c);
		write(1,&tmp,1);
	}
	close(t);
}
