//
//  tp5_squelette.cpp
//  
//
//  Created by Caroline Larboulette on 12/11/2017.
//
//  g++ tp5_squelette.cpp -framework OpenGL -framework GLUT

#include <stdio.h>
#include <stdlib.h>
#include <math.h> 

#include </usr/include/GL/gl.h>
#include </usr/include/GL/glut.h>

using namespace std;


double viewingMatrix[16] = {
	1.0, 	0.0, 	0.0, 	0.0,
	0.0, 	0.0, 	1.0, 	0.0,
	0.0, 	-100.0, 0.0, 	0.0,
	0.0, 	0.0, 	0.0, 	1.0
};

double frustrumMatrix[16] = {
	1.0,	0.0,	0.0,	0.0,
	0.0,	1.0,	0.0,	0.0,
	0.0,	0.0,	1.0,	1.0,
	0.0,	0.0,	0.0,	0.0
	
};

double perspectiveMatrix[16] = {
	1.0,	0.0,	0.0,	0.0,
	0.0,	1.0,	0.0,	0.0,
	0.0,	0.0,	1.0,	2.0,
	0.0,	0.0,	0.0,	0.0
	
};

double orthoMatrix[16] = {
	1.0,	0.0,	0.0,	0.0,
	0.0,	1.0,	0.0,	0.0,
	0.0,	0.0,	1.0,	1.0,
	0.0,	0.0,	0.0,	0.0
	
};

double angleRadian = 45*(M_PI/180);


double R[16] = {
	cos(angleRadian), 	0.0, 	sin(angleRadian), 	0.0,
			0.0, 		1.0, 		0.0,			0.0,
	-sin(angleRadian), 	0.0, 	cos(angleRadian),	0.0,
			0.0,		0.0,		0.0,			1.0
};

double roll[] = {0.0,0.0,0.0};
double pitch = 1.0;
double heading[] = {8.0,0.0,42.0};
double pos[] = {0.0,0.0,0.0};

void drawHouse()
{
    //Add your code here !
    
    //gluLookAt(/* Eye */8.0,0.0,84.0, /* Point que l'on regarde */ 8.0,0.0,42.0 /* Vecteur direction haut de la tête */,0.0,1.0,0.0); // 1 Point
    //gluLookAt(32.0,8.0,84.0, 0.0,0.0,0.0 ,0.0,1.0,0.0); // 2 Point
    //gluLookAt(32.0,32.0,84.0, 8.0,0.0,42.0 ,0.0,1.0,0.0); // 3 Point
    
	glMatrixMode(GL_PROJECTION);
	
	/* Exercise 2 Frustum */
	//glFrustum(-50.0,50.0, -50.0,50.0, 84.0,20.0);
   
	/* Exercise 2 Perspective */
	//gluPerspective(90.0,1.0,84.0,30.0);
   
	/* Exercise 2 Ortho */
	glOrtho(-50.0,50.0,-50.0,50.0,84.0,20.0);
    
    glColor3f(1.0, 1.0, 0.0);
    glLineWidth(1.0);
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glBegin(GL_POLYGON); //Draws x-axis
		glVertex3f(0,0,30);
		glVertex3f(16,0,30);
		glVertex3f(16,10,30);
		glVertex3f(8,16,30);
		glVertex3f(0,10,30);
		glVertex3f(0,0,30);
		glVertex3f(0,0,54);
		glVertex3f(16,0,54);
		glVertex3f(16,10,54);
		glVertex3f(8,16,54);
		glVertex3f(0,10,54);
		glVertex3f(0,0,54);
		glVertex3f(0,10,54);
		glVertex3f(0,10,30);
		glVertex3f(8,16,30);
		glVertex3f(8,16,54);
		glVertex3f(16,10,54);
		glVertex3f(16,10,30);
		glVertex3f(16,0,30);
		glVertex3f(16,0,54);
    glEnd();
    
}

/*void flightSimulator(double* roll, double pitch, double* heading){
	
	double headingx = heading[0];
	//double headingy = heading[1];
	double headingz = heading[1];
	
	double rollx = roll[0];
	double rollz = roll[1];
	
	glLoadIdentity();
	* */
	//gluLookAt(/* Eye */8.0,0.0,84.0, /* Point que l'on regarde */ headingx,pitch,headingz /* Vecteur direction haut de la tête */,rollx,1.0,rollz);
	/*
}
*/
void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);
    
    glLoadIdentity();
    //gluLookAt(0,0,0,0,0,-100,0,1,0);// Add parameters here that are not default ones
    gluLookAt(pos[0],pos[1],pos[2],heading[0],heading[1],heading[2],roll[0],pitch,roll[2]);// Add parameters here that are not default ones
    
    //glMultMatrixd(viewingMatrix);
	//glMultMatrixd(frustrumMatrix);
	//glMultMatrixd(R);
   
    //Draw axes
    glColor3f(1.0, 0.0, 0.0);
    glLineWidth(2.0);
    glBegin(GL_LINES); //Draws x-axis
    glVertex3f(-50,0,0);
    glVertex3f(50,0,0);
    glEnd();
    
    glColor3f(0.0, 1.0, 0.0);
    glBegin(GL_LINES); //Draws y-axis
    glVertex3f(0,-50,0);
    glVertex3f(0,50,0);
    glEnd();
    
    glColor3f(0.0, 0.0, 1.0);
    glBegin(GL_LINES); //Draws z-axis
    glVertex3f(0,0,-50);
    glVertex3f(0,0,50);
    glEnd();
    //coucou c'est moi :)
    drawHouse();
    
    glFlush();
}

void reshape(int w, int h)
{

    glViewport(0, 0, (GLsizei) w, (GLsizei) h);
  
    glMatrixMode(GL_MODELVIEW); //Ready to transform the object matrix
}

void init(void)
{
    glClearColor(0.0, 0.0, 0.0, 0.0); //Background color
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    //perspective projection left right bottom top near far
    //Change parameters to see your object
    glFrustum(-50,50.0,-50.0,50.0,0,10);
    glMatrixMode(GL_MODELVIEW);
    
}

/**
 void keyboard(unsigned char key, int x, int y)
 key returns the character hit by the user
 **/
void keyboard(unsigned char key, int x, int y)
{
    switch (key)
    {
        case '7':{ 
			roll[0] = roll[0]-0.1;
			display();
			break;
			}
		case '9':{ 
			roll[0] = roll[0]+0.1;
			display();
			break;
			}
		case '4':{ 
			heading[0] = heading[0]-1;
			display();
			break;
			}
		case '6':{ 
			heading[0] = heading[0]+1;
			display();
			break;
			}
		case '8':{ display();
			heading[1] = heading[1]+1;
			display();
			break;
			}
		case '2':{
			heading[1] = heading[1]-1;
			display();
			break;
			}
		case '1':{
			roll[2] = roll[2]-0.1;
			display();
			break;
			}
		case '3':{
			roll[2] = roll[2]+0.1;
			display();
			break;
			}
		case '5':{ 
			roll[0] = 0.0;
			roll[1] = 0.0;
			roll[2] = 0.0;
			pitch = 1.0;
			heading[0] = 8.0;
			heading[1] = 0.0;
			heading[2] = 42.0;
			pos[0] = 0;
			pos[1] = 0;
			pos[2] = 0;
			display();
			break;
			}
		case 'z':{ 
			pos[2] = pos[2] + 1;
			display();
			break;
			}
		case 's':{ 
			pos[2] = pos[2] - 1;
			display();
			break;
			}
		case 'q': {
			pos[0] = pos[0] + 1;
			display();
			break;
			}
		case 'd': {
			pos[0] = pos[0] - 1;
			display();
			break;
			}
		case 'a': {
			if(pitch == 1){
				pitch = 0.5;
			}
			display();
			break;
			}
		case 'e': {
			if(pitch == 0.5){
				pitch = 1;
			}
			display();
			break;
			}
    }
}

int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize (250, 250); //Window size in pixels
    glutInitWindowPosition (100, 100); //Window position on screen in pixels
    glutCreateWindow ("Bonjour les amis !"); //Creates a window with the title of your choice^^
    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutMainLoop(); //Calls the rendering loop
    return 0;
}



