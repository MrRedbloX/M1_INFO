//
//  tp1_squelette.cpp
//  
//
//  Created by Caroline Larboulette on 25/09/2017.
//
//

//Those includes are the good ones for Mac OSX to use OpenGL and GLUT (The OpenGL Utility Toolkit) -- to make windows among other things.

#ifdef __gnu_linux__
    #include <GL/freeglut.h>
    #include <GL/gl.h>
#else
    #include <OpenGL/gl.h>
    #include <GLUT/glut.h>
#endif

void drawLine(int x1, int x2, int y1, int y2){
	int dx = x2-x1;
	int dy = y2-y1;
	int d = 2*dy-dx;
	int x = x1;
	int y = y1;
	glColor3f(1,1,1);
	glBegin(GL_POINTS);
	glVertex3f(x,y,0);
	if(d<=1){
		while (x < x2) { 
			x++; 
			if (d <= 0) 
				d+= 2*dy; 
			else{ 
				d += 2*(dy - dx); 
				y++; 
			} 
			glVertex3f(x,y,0);
		} 
	}
	else{
		while (x < x2) { 
			x++; 
			if (d <= 0) 
				d+= 2*(dy - dx); 
			else{ 
				d += 2*dy; 
				y++; 
			} 
			glVertex3f(x,y,0);
		} 
	}
	glEnd();
}

void drawGrid(){
	glColor3f(1,0,0);
	glPointSize(4);
    glBegin(GL_LINES);
    for(int i =-4; i<5; i++){
		glVertex3f(-5,i,0);
		glVertex3f(5,i,0);
		glVertex3f(i,-5,0);
		glVertex3f(i,5,0);
	}
    glEnd();
}

/**
 void display(void)
 Draw function to render the scene
 **/
void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);
    
    //glColor3f(1.0, 1.0, 1.0); //White color
    //glColor3f(1, 0, 0);//Red color
   // glColor3f(0, 1, 0);//Green color
   //glColor3f(0, 0, 1);//Blue color
    //glPointSize(10);
	//glLineWidth(20);
    
    //glBegin(GL_POINTS); //Draws points
    //glBegin(GL_LINES);
    //glBegin(GL_LINE_STRIP);
    //glBegin(GL_LINE_LOOP);
    //glBegin(GL_TRIANGLES);
    //glPointSize(10);
    //glLineWidth(10);
    /*glLineStipple(1, 0x3F07);
	glEnable(GL_LINE_STIPPLE);
    glBegin(GL_LINE_STRIP);
    glVertex3f(1,1,0);
    glVertex3f(1,2,0);
    glColor3f(0, 0, 1);
    glVertex3f(2,2,0);
    glVertex3f(2,1,0);
    glVertex3f(3,3,0);
    glColor3f(0, 1, 1);
    glVertex3f(3,4,0);
    glVertex3f(4,4,0);
    glColor3f(1, 0, 1);
    glVertex3f(4,3,0);
    glVertex3f(-2,-3,0);
    glEnd();*/
    //glVertex3f(-2,-3,0); 
    //glVertex3f(-1,-2,0);
    int x1 = -1, y1 = -2, x2 = 4, y2 = 1; 
	drawGrid();
	drawLine(x1,x2,y1,y2);

    glFlush();
}

/**
 void init(void)
 Init function where you load your scene or create procedural objects
 **/
void init(void)
{
    glClearColor(0.0, 0.0, 0.0, 0.0); //Background color
    glMatrixMode(GL_PROJECTION); //Projection matrix
    glLoadIdentity();
    //left, right, bottom, top, near, far
    glOrtho(-5.0, 5.0, -5.0, 5.0, -1.0, 1.0);
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
    glutMainLoop(); //Calls the rendering loop
    return 0;
}

