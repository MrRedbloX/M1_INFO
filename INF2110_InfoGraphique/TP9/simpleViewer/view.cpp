#include "view.h"
using namespace qglviewer;

View::View(){
}
void View::draw() {

    glClear(GL_COLOR_BUFFER_BIT);

    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, texture);

    if(this->fill){
        glPolygonMode(GL_FRONT, GL_FILL);
    }
    else{
        glPolygonMode(GL_FRONT_AND_BACK,   GL_LINE);
    }



    glColor3f(1,1,1);
    unsigned int i;
    if(this->points){
        glBegin(GL_POINTS);
        for(i=0;i<allVertices.size();i++){
            glVertex3fv(allVertices[i]->position);
        }
        glEnd();
    }
    else{
       if(this->smooth){
            glBegin(GL_TRIANGLES);
            for(i=0;i<allFaces.size();i++){

                   glNormal3fv(allFaces[i]->p1->normal);
                   glTexCoord2f(allFaces[i]->U1, allFaces[i]->V1);
                   glVertex3fv(allFaces[i]->p1->position);

                   glNormal3fv(allFaces[i]->p2->normal);
                   glTexCoord2f(allFaces[i]->U2, allFaces[i]->V2);
                   glVertex3fv(allFaces[i]->p2->position);

                   glNormal3fv(allFaces[i]->p3->normal);
                   glTexCoord2f(allFaces[i]->U3, allFaces[i]->V3);
                   glVertex3fv(allFaces[i]->p3->position);
            }
            glEnd();
        }
        else{
            glBegin(GL_TRIANGLES);
            for(i=0;i<allFaces.size();i++){
                    glNormal3fv(allFaces[i]->normal);
                    glTexCoord2f(allFaces[i]->U1, allFaces[i]->V1);
                    glVertex3fv(allFaces[i]->p1->position);
                    glTexCoord2f(allFaces[i]->U2, allFaces[i]->V2);
                    glVertex3fv(allFaces[i]->p2->position);
                    glTexCoord2f(allFaces[i]->U3, allFaces[i]->V3);
                    glVertex3fv(allFaces[i]->p3->position);

            }
            glEnd();
        }
    }
    glDisable(GL_TEXTURE_2D);
}


void View::init() {
  // Restore previous viewer state.
  restoreStateFromFile();

 /* parser = ObjParser("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/bear-obj.obj");

  //parser = ObjParser("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/giraffe-obj.obj");

  //parser.readFile();

  parser.parser();*/
  restoreStateFromFile();
  this->model = glmReadOBJ("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/giraffe-obj.obj");

  this->allFaces=std::vector<Face*>();
  this->allVertices=std::vector<Vertex*>();
  unsigned int i;
  for (i=0;i<=this->model->numvertices*3;i+=3){
          Vertex* v=new Vertex(this->model->vertices[i],this->model->vertices[i+1],this->model->vertices[i+2]);
          this->allVertices.push_back(v);
  }

  for(i=0;i<this->model->numtriangles;i++){

          Vertex* v1=this->allVertices[this->model->triangles[i].vindices[0]];
          Vertex* v2=this->allVertices[this->model->triangles[i].vindices[1]];
          Vertex* v3=this->allVertices[this->model->triangles[i].vindices[2]];

          Face* face=new Face(v1,v2,v3);

          face->U1=this->model->texcoords[this->model->triangles[i].tindices[0]*2];
          face->V1=this->model->texcoords[this->model->triangles[i].tindices[0]*2 +1];

          face->U2=this->model->texcoords[this->model->triangles[i].tindices[1]*2];
          face->V2=this->model->texcoords[this->model->triangles[i].tindices[1]*2 +1];

          face->U3=this->model->texcoords[this->model->triangles[i].tindices[2]*2];
          face->V3=this->model->texcoords[this->model->triangles[i].tindices[2]*2 +1];

          v1->normal+=face->normal;
          v2->normal+=face->normal;
          v3->normal+=face->normal;

          face->normal=face->normal/face->normal.norm();
          this->allFaces.push_back(face);
  }
  for(i=0;i<this->allVertices.size();i++){
          this->allVertices[i]->normal.normalize();
  }

  QImage img("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP9/girafe.png");

  glGenTextures(1, &texture);
  glEnable(GL_TEXTURE_2D);
  glBindTexture(GL_TEXTURE_2D, texture);

  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

  glHint( GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST );

 // glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP );
  //glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP );
  glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
  QImage glImg = QGLWidget::convertToGLFormat(img);
  glTexImage2D(GL_TEXTURE_2D, 0, 3, glImg.width(), glImg.height(), 0,
               GL_RGBA, GL_UNSIGNED_BYTE, glImg.bits());

  setSceneRadius(20);
  showEntireScene();

  // Opens help window
  //help();
}

void View::keyPressEvent(QKeyEvent * e){
      const Qt::KeyboardModifiers modifiers = e->modifiers();

      bool handled = false;

      if (!handled)
        QGLViewer::keyPressEvent(e);

}
