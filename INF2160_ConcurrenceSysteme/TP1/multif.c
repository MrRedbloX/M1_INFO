#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sched.h>


int main (int argc,char **argv) {

  int pid;
  int x;


  if (argc <= 1) {
    printf("Erreur dans les parametres, il faut une liste de fichier\n");
  }
  else {
     fcntl(1,F_SETFL,O_NONBLOCK); // pas de cache
     for (x=1;x<argc;x++) {  /* traitement de la liste des noms */
         if ((pid = fork()) == 0) {
            /* processus fils */
            int fic,c;

            if ((fic=open(argv[x],O_RDONLY)) < 0) {
               printf("Le fichier %s n'existe pas\n",argv[x]);
 	    }
	    else { /* lecture du fichier */
               while (read(fic,&c,1) > 0) {
                  write(1,&c,1);
                  sched_yield();
               }

	    }
	    exit(0); /* sortie et synchro avec le pere */
	 }
     }
     /* attende des fils */
     for (x=1;x<argc;x++) {
         int message;
         pid =wait(&message);
     }
  }
}
