//
//  tp2_squelette.cpp
//  
//
//  Created by Caroline Larboulette on 02/10/2017.
//
//

#include <stdio.h>
#include <stdlib.h>

#ifdef __gnu_linux__
    #include <GL/freeglut.h>
    #include <GL/gl.h>
#else
    #include <OpenGL/gl.h>
    #include <GLUT/glut.h>
#endif

//Letter F
GLubyte rasters[24] = {
    0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00,
    0xff, 0x00, 0xff, 0x00, 0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00,
    0xff, 0xc0, 0xff, 0xc0};
    
GLubyte cLetter[24] = {
	0xff, 0xc0, 0xff, 0xc0, 0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00,
	0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00, 0xc0, 0x00,
	0xff, 0xc0, 0xff, 0xc0};
	
GLubyte hLetter[24] = {
	0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 
	0xff, 0xc0, 0xff, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0,
	0xc0, 0xc0, 0xc0, 0xc0};
	
GLubyte aLetter[24] = {
	0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0,
	0xff, 0xc0, 0xff, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0, 0xc0,
	0xff, 0xc0, 0xff, 0xc0};

int * cohen(int xmax, int xmin, int ymax, int ymin, int x1, int y1, int x2, int y2){
	static int ret[4];
	
	short bit1_1, bit1_2, bit1_3, bit1_4;
	short bit2_1, bit2_2, bit2_3, bit2_4;

	if(ymax - y1 < 0) bit1_1 = 1;
	else bit1_1 = 0;
	if(y1 - ymin >= 0) bit1_2 = 0;
	else bit1_2 = 1;
	if(xmax - x1 < 0) bit1_3 = 1;
	else bit1_3 = 0;
	if(x1 - xmin >= 0) bit1_4 = 0;
	else bit1_4 = 1;

	if(ymax - y2 < 0) bit2_1 = 1;
	else bit2_1 = 0;
	if(y2 - ymin >= 0) bit2_2 = 0;
	else bit2_2 = 1;
	if(xmax - x2 < 0) bit2_3 = 1;
	else bit2_3 = 0;
	if(x2 - xmin >= 0) bit2_4 = 0;
	else bit2_4 = 1;

	if((bit1_1 == 0 && bit1_2 == 0 && bit1_3 == 0 && bit1_4 == 0) && (bit2_1 == 0 && bit2_2 == 0 && bit2_3 == 0 && bit2_4 == 0)){
		ret[0] = x1;
		ret[1] = y1;
		ret[2] = x2;
		ret[3] = y2;
	}
	else if((bit1_1*bit2_1 != 0) || (bit1_2*bit2_2 != 0) || (bit1_3*bit2_3 != 0) || (bit1_4*bit2_4 != 0)){
		return 0;
	}
	else{
		
		int x1R,y1R,x2R,y2R;
		
		if(bit1_1 == 1){
			x1R = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
			y1R = ymax;
		}
		else if(bit1_2 == 1){
			x1R = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
			y1R = ymin;
		}
		else if(bit1_3 == 1){
			y1R = y1 + (y2 - y2) * (xmax - x1) / (x2 - x1);
			x1R = xmax;
		}
		else if(bit1_4 == 1){
			y1R = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
			x1R = xmin;
		}
		
		if(bit2_1 == 1){
			x2R = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
			y2R = ymax;
		}
		else if(bit2_2 == 1){
			x2R = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
			y2R = ymin;
		}
		else if(bit2_3 == 1){
			y2R = y1 + (y2 - y2) * (xmax - x1) / (x2 - x1);
			x2R = xmax;
		}
		else if(bit2_4 == 1){
			y2R = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
			x2R = xmin;
		}
		
		ret[0] = x1R;
		ret[1] = y1R;
		ret[2] = x2R;
		ret[3] = y2R;
	}
	
	return ret;
}

void displayCohen(){
	int xmax = 200;
	int xmin = 100;
	int ymax = 200;
	int ymin = 100;
	
	int x1Inside = 125;
	int y1Inside = 110;
	int x2Inside = 180;
	int y2Inside = 120;
	
	int x1Outside = 50;
	int y1Outside = 50;
	int x2Outside = 150;
	int y2Outside = 60;
	
	int x1Both = 70;
	int y1Both = 60;
	int x2Both = 130;
	int y2Both = 150;
	
	glBegin(GL_QUADS);
	glVertex3f(xmin,ymin,0);
    glVertex3f(xmin,ymax,0);
    glVertex3f(xmax,ymax,0);
    glVertex3f(xmax,ymin,0);
    glEnd();
    
	int * draw1 = cohen(xmax, xmin, ymax, ymin, x1Inside, y1Inside, x2Inside, y2Inside);
	int x1RI = *draw1;
	int y1RI = *(draw1+1);
	int x2RI = *(draw1+2);
	int y2RI = *(draw1+3);
	
	glColor3f(1,0,1);
	glBegin(GL_LINES);
	glVertex3f(x1RI,y1RI,0);
    glVertex3f(x2RI,y2RI,0);
    glEnd();
    
    int * draw2 = cohen(xmax, xmin, ymax, ymin, x1Inside, y1Inside, x2Inside, y2Inside);
	if(draw2 !=0) printf("Il y a un problème");
	
	//To continue
    
    glFlush();
}

void init(void)
{
    glPixelStorei (GL_UNPACK_ALIGNMENT, 1);
    glClearColor (0.0, 0.0, 0.0, 0.0);
    
}

void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f (1.0, 0, 1.0);
    glRasterPos2i (200, 200);
    glBitmap (10, 12, 0.0, 0.0, 11.0, 0.0, cLetter);
    //On ne peut pas colorer plusieurs lettres d'une couleur différente
    glColor3f (1.0, 1, 0);
    glBitmap (10, 12, 0.0, 0.0, 11.0, 0.0, hLetter);
    glBitmap (10, 12, 0.0, 0.0, 11.0, 0.0, aLetter);
    glFlush();
}

void displayLines(){
	//Aucunce différence sous Linux car les fonctions ne sont pas implémentées
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(1.0, 1.0, 1.0);
	glEnable(GL_LINE_SMOOTH);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
	glBegin(GL_LINES);
	glVertex3f(124,348,0);
    glVertex3f(364,150,0);
    glEnd();
    glFlush();
}

void displayPoints(){
	//Maitenant on voit la différence
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(1.0, 1.0, 1.0);
	glPointSize(10);
	glEnable(GL_POINT_SMOOTH);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glHint(GL_POINT_SMOOTH_HINT, GL_DONT_CARE);
	glBegin(GL_POINTS);
	glVertex3f(124,348,0);
	glEnd();
    glFlush();
	
}

void reshape(int w, int h)
{
    glViewport(0, 0, (GLsizei) w, (GLsizei) h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho (0, w, 0, h, -1.0, 1.0);
    glMatrixMode(GL_MODELVIEW);
}

void keyboard(unsigned char key, int x, int y)
{
    switch (key) {
        case 'q':
        exit(0);
    }
}

int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(400, 400);
    glutInitWindowPosition(100, 100);
    glutCreateWindow(argv[0]);
    init();
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutDisplayFunc(displayCohen);
    glutMainLoop();
    displayCohen();
    
    return 0;
}
