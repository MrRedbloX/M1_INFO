// jeuStarWars.c
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include <pthread.h>

#include <curses.h>
#include <unistd.h>

int x1 = 0;
int y1 = 0;

void * star() {

	pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

	int x2  = rand() % COLS ;
	int y2  = rand() % LINES ;

	int dx=rand() % 2;
	if (dx ==0) dx=-1;
	int dy=rand() % 2;
	if (dy ==0) dy=-1;

	while(1) {

		while (x2 >0 && x2 < COLS && y2 >0 && y2 < LINES) {

			pthread_mutex_lock(&lock);

			mvaddch(y2,x2,'*');
			refresh();

			int sleepFor = (rand() % 100000) + 900000;
			usleep(sleepFor);
			mvaddch(y2,x2,' ');

			pthread_mutex_unlock(&lock);

			x2+=dx;
			y2+=dy;
		}

		// regarde si le jedi est pas plus fort
		if(x1 == x2 && y1 == y2) pthread_exit(1);

		if(x2 == 0) {
			x2 = x2 + 1;
			dx = 1;
		}
		else if(x2 == COLS) {
			x2 = x2 - 1;
			dx = -1;
		}
		else if(y2 == 0) {
			y2 = y2 + 1;
			dy = 1;
		}
		else if(y2 == LINES) {
			y2 = y2 - 1;
			dy = -1;
		}

		// créer une nouvelle étoile
		pthread_t starId1;

		if(pthread_create(&starId1, NULL, star, (void *)NULL)) {
			fprintf(stderr, "error creating thread\n");
		}
	}
}


// programme principal
int main(void){

	initscr();
	clear();

	srand(time(NULL));

	pthread_t starId1;
	pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

	if(pthread_create(&starId1, NULL, star, (void *)NULL)) {
		fprintf(stderr, "error creating thread\n");
	}

	noecho(); // pas de retour ecran  (saisie masquee)

	char c;

	while ( (c = getch()) != '\n') {

		pthread_mutex_lock(&lock);
		mvaddch(y1, x1, ' ');
		pthread_mutex_unlock(&lock);

		switch (c) {
			case 56 : y1 += - 1; break; // h
			case 54 : x1 += 1; 	break; // d
			case 52 : x1 += - 1; break; // g
			case 50 : y1 += + 1; break; // b
		}

		pthread_mutex_lock(&lock);
		mvaddch(y1, x1, 219);
		pthread_mutex_unlock(&lock);

		echo();  // retour ecran
		noecho();  // pas de retour ecran
	}


	// attente des threads
	pthread_join(starId1, NULL);


}

// Compilation et edition des liens
// gcc jeuStarWarModel.c   -lcurses -ltermcap
