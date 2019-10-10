#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/resource.h>
#include <sys/wait.h>
int main (int argc,char **argv) {

    if(argc > 1){
      int chargeCPU = atoi(argv[1]);
      unsigned long u1,n1,sys1,idle1;
      unsigned long u2,n2,sys2,idle2;

      printf("Charge voulue :  %d\n",chargeCPU);

      while(1){
          FILE * stat;
          stat = fopen("/proc/stat","rw");
          fscanf(stat,"cpu %lu%lu%lu%lu",&u1,&n1,&sys1,&idle1);
          fclose(stat);
          sleep(200000);
          stat = fopen("/proc/stat","rw");
          fscanf(stat,"cpu %lu%lu%lu%lu",&u2,&n2,&sys2,&idle2);
          fclose(stat);

          int chargeReel = ((u2-u1)+(n2-n1)+(sys2-sys1))*100/((u2-u1)+(n2-n1)+(sys2-sys1)+(idle2-idle1));
          printf("La charge du CPU est de : %d\%\n",chargeReel);

          if(chargeReel<chargeCPU-3){
              if (fork() == 0) {
                  int v;
                  for(int i=0; i<100;i++){
                      for(int j=0; j<1000000; j++) {
                          v=1.0/i;
                          sleep(10);
                      }
                  }
              }
          }
          sleep(200000);
      }
    }
    else{
      printf("Usage : charge N%\n");
    }
}
