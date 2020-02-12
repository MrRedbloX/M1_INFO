#include "view.h"
#include <GL/gl.h>
#include <GL/glut.h>
using namespace qglviewer;

View::View(){
}

void View::draw() {

    glColor3f(1,0,0);
    GLUquadric *quad;
    quad = gluNewQuadric();
    glTranslatef(2,2,2);
    gluSphere(quad,25,100,20);
}


void View::init() {
  // Restore previous viewer state.
  restoreStateFromFile();
}

void View::keyPressEvent(QKeyEvent * e){
      //const Qt::KeyboardModifiers modifiers = e->modifiers();

      bool handled = false;

      if (!handled)
        QGLViewer::keyPressEvent(e);

}
