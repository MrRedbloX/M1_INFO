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

#ifdef __gnu_linux__
    #include <GL/freeglut.h>
    #include <GL/gl.h>
#else
    #include <OpenGL/gl.h>
    #include <GLUT/glut.h>
#endif

using namespace std;


double posX,posY,posZ,lookAtY,roll1,roll2 = 0;
double lookAtX = 8;
double lookAtZ = 42;
double pitch = 1;

void drawHouse()
{
    
     //gluLookAt(/* Eye */8,0,84, /* Point que l'on regarde */ 8,0,42 /* Vecteur direction haut de la tête */,0,1,0); // 1 Point
     //gluLookAt(32,8,84, 0,0,0,0,1,0); // 2 Point
     //gluLookAt(32,32,86, 8,0,42 ,0,1,0); // 3 Point
    
	glMatrixMode(GL_PROJECTION);
	
	//glFrustum(-50.0,50.0, -50.0,50.0, 84.0,20.0);

	gluPerspective(90.0,1.0,1.0,100.0);
   
	//glOrtho(-50.0,50.0,-50.0,50.0,84.0,20.0);
    
    glColor3f(0, 1, 1);
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

void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);
    
    glLoadIdentity();
    gluLookAt(posX,posY,posZ,lookAtX,lookAtY,lookAtZ,roll1,pitch,roll2);
   
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
    //Change puarameters to see your object
   // glFrustum(-50,50.0,-50.0,50.0,0,10);
    glMatrixMode(GL_MODELVIEW);
    
}

/**
 void keyboard(unsigned char key, int x, int y)
 key returns the character hit by the user
 **/
void keyboard(unsigned char key, int x, int y)
{
    switch (key){
		case 'z' : posZ--; lookAtZ--; break;
		case 's' : posZ++; lookAtZ++; break;
		case 'a' : posX--; lookAtX--; break;
		case 'e' : posX++; lookAtX++; break;
		case 'u' : posY++; lookAtY++; break;
		case 'd' : posY--; lookAtY--; break;
		case 'p' : pitch++; break;
		case 'h' : pitch--; break;
		case 'r' : roll1++; break;
		case 'f' : roll1--; break;
		case 't' : roll2++; break;
		case 'g' : roll2--; break;
		case 'q' : exit(0);
    }
    display();
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



