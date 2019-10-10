#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sched.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <stdio.h>

int main(int argc, char** argv){	
	if(argc == 2){
		int i = 0;
		for(i=0; i<atoi(argv[1]); i++){
				if(fork() == 0){
					if(i%2) setpriority(PRIO_PROCESS, 0, 19);
					int j,k,x;
					clock_t start, end;
					struct timeval temps_avant, temps_apres;
					
					start = clock();
					gettimeofday (&temps_avant, NULL);
					
					for (j = 1; j <= 100; j++){
						for (k = 0; k < 1000000; k++) x=1./i;
					}
					
					end = clock();
					gettimeofday (&temps_apres, NULL);
					
					double totalVirtuel = (double) (end - start) / 100000;
					double totalReel = (((temps_apres.tv_sec - temps_avant.tv_sec) * 1000000 + temps_apres.tv_usec) - temps_avant.tv_usec)/100000;

					
					printf("\nProcessus %d -> temps virtuel : %f ms, temps reel : %f ms ", i+1, totalVirtuel, totalReel);
					if(i%2) printf("(priorite faible).\n");
					else printf(".\n");
					exit(0);
				}
			}
		for(i=0; i<atoi(argv[1]); i++){
			wait(NULL);
		}
	}
	exit(0);
}
