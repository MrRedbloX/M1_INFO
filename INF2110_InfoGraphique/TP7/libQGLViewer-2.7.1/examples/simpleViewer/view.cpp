#include "view.h"

using namespace qglviewer;

View::View(){
}

bool light = true;
void View::draw() {

    glClear(GL_COLOR_BUFFER_BIT);

    for(std::vector<Vertex*>::iterator vit = parser.vertexArray.begin(); vit != parser.vertexArray.end(); vit++){
       Vec P1 = (*vit)->position;

       glBegin(GL_POINTS);
        glNormal3dv((*vit)->normal);
        glVertex3d(P1.x,P1.y,P1.z);
       glEnd();
    }


    glPolygonMode(GL_FRONT, GL_FILL);
    for(std::vector<Face*>::iterator fit = parser.faceArray.begin();
                                fit != parser.faceArray.end(); fit++)
    {

        Vec P1 = (*fit)->p1->position;
        Vec P2 = (*fit)->p2->position;
        Vec P3 = (*fit)->p3->position;

        // Smooth shading
        glBegin(GL_TRIANGLES);
         glNormal3dv((*fit)->p1->normal);
         glVertex3dv(P1);

         glNormal3dv((*fit)->p2->normal);
         glVertex3dv(P2);

         glNormal3dv((*fit)->p3->normal);
         glVertex3dv(P3);

        glEnd();
   }

    if(light){
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_LIGHT1);
        glEnable(GL_DEPTH_TEST);
    }
    else{
        glDisable(GL_LIGHTING);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHT1);
        glDisable(GL_DEPTH_TEST);
    }
    GLfloat paramsAmbient[] = {0,1,1,1};
    GLfloat paramsDiffuse[] = {0,0,1,1};
    glLightfv(GL_LIGHT0, GL_AMBIENT, paramsAmbient);
    glLightfv(GL_LIGHT1, GL_DIFFUSE, paramsDiffuse);
    /*glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_DEPTH_TEST);

    GLfloat no_mat[] = {0.0f, 0.0f, 0.0f, 1.0f};
    GLfloat mat_ambient[] = {0.7f, 0.7f, 0.7f, 1.0f};
    GLfloat mat_ambient_color[] = {0.8f, 0.8f, 0.2f, 1.0f};
    GLfloat mat_diffuse[] = {1.0f, 0.0f, 1.0f, 1.0f};
    GLfloat mat_specular[] = {1.0f, 0.0f, 0.0f, 1.0f};
    float no_shininess = 0.0f;
    float low_shininess = 5.0f;
    float high_shininess = 100.0f;
    GLfloat mat_emission[] = {0.3f, 0.2f, 0.2f, 0.0f};
    glPushMatrix();
    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, high_shininess);
    glMaterialfv(GL_FRONT, GL_EMISSION, mat_emission);
    glPopMatrix();

    glLightModelfv(GL_LIGHT_MODEL_AMBIENT, paramsAmbient);
}


void View::init() {
  // Restore previous viewer state.
  restoreStateFromFile();

  parser = ObjParser("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/bear-obj.obj");

  //parser = ObjParser("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/giraffe-obj.obj");

  //parser.readFile();

  parser.parser();

  setSceneRadius(500.0);

  // Opens help window
  //help();
}

void View::keyPressEvent(QKeyEvent * e){
      const Qt::KeyboardModifiers modifiers = e->modifiers();

      bool handled = false;
      if ((e->key() == Qt::Key_N) && (modifiers == Qt::NoButton)) {
          light = true;
          handled = true;
      }
      if ((e->key() == Qt::Key_F) && (modifiers == Qt::NoButton)) {
          light = false;
          handled = true;
      }
      if (!handled)
        QGLViewer::keyPressEvent(e);
      draw();
}
