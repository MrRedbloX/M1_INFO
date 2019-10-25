/****************************************************************************

 Copyright (C) 2002-2014 Gilles Debunne. All rights reserved.

 This file is part of the QGLViewer library version 2.7.1.

 http://www.libqglviewer.com - contact@libqglviewer.com

 This file may be used under the terms of the GNU General Public License 
 versions 2.0 or 3.0 as published by the Free Software Foundation and
 appearing in the LICENSE file included in the packaging of this file.
 In addition, as a special exception, Gilles Debunne gives you certain 
 additional rights, described in the file GPL_EXCEPTION in this package.

 libQGLViewer uses dual licensing. Commercial/proprietary software must
 purchase a libQGLViewer Commercial License.

 This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.

*****************************************************************************/

#include "simpleViewer.h"
#include "glm.h"
#include <stdio.h>
#include <stdlib.h>

using namespace std;
using namespace qglviewer;

int mode = 1;
int drawMode = 1;
GLMmodel *modelBear;
GLMmodel *modelGiraffe;

void vertex(GLMmodel *model){
    GLfloat * vertices = model->vertices;
    glBegin(GL_POINTS);
    for(int i=0; i<model->numvertices*3; i=i+3){
        glVertex3f(model->vertices[i],model->vertices[i+1],model->vertices[i+2]);
    }
    glEnd();
}

void triangle(GLMmodel *model){
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glBegin(GL_TRIANGLES);

    for(int i = 0; i < model->numtriangles; i++)
    {

      glVertex3fv(&model->vertices[3*model->triangles[i].vindices[0]]);
      glVertex3fv(&model->vertices[3*model->triangles[i].vindices[1]]);
      glVertex3fv(&model->vertices[3*model->triangles[i].vindices[2]]);
    }

    glEnd();
}

void bear(){
    if(mode == 0) vertex(modelBear);
    else triangle(modelBear);
}
void giraffe(){
    if(mode == 0)vertex(modelGiraffe);
    else triangle(modelGiraffe);
}

void Viewer::draw() {
    if(drawMode == 0) bear();
    else giraffe();
}

void Viewer::init() {
  // Restore previous viewer state.
  restoreStateFromFile();
  setSceneRadius(10);
  showEntireScene();
  modelBear = glmReadOBJ("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/bear-obj.obj");
  modelGiraffe = glmReadOBJ("/ubs/home/etud/2016/e1602617/Documents/M1_INFO/INF2110_InfoGraphique/TP6/giraffe-obj.obj");

  // Opens help window
  //help();
}

void Viewer::keyPressEvent(QKeyEvent *e) {
  // Get event modifiers key
  const Qt::KeyboardModifiers modifiers = e->modifiers();

  bool handled = false;
  if(mode ==0) mode = 1;
  else mode = 0;
  if ((e->key() == Qt::Key_B) && (modifiers == Qt::NoButton)) {
      drawMode = 0;
      handled = true;
  }
  if ((e->key() == Qt::Key_G) && (modifiers == Qt::NoButton)) {
      drawMode = 1;
      handled = true;
  }
  if (!handled)
    QGLViewer::keyPressEvent(e);
  draw();
}

QString Viewer::helpString() const {
  QString text("<h2>S i m p l e V i e w e r</h2>");
  text += "Use the mouse to move the camera around the object. ";
  text += "You can respectively revolve around, zoom and translate with the "
          "three mouse buttons. ";
  text += "Left and middle buttons pressed together rotate around the camera "
          "view direction axis<br><br>";
  text += "Pressing <b>Alt</b> and one of the function keys "
          "(<b>F1</b>..<b>F12</b>) defines a camera keyFrame. ";
  text += "Simply press the function key again to restore it. Several "
          "keyFrames define a ";
  text += "camera path. Paths are saved when you quit the application and "
          "restored at next start.<br><br>";
  text +=
      "Press <b>F</b> to display the frame rate, <b>A</b> for the world axis, ";
  text += "<b>Alt+Return</b> for full screen mode and <b>Control+S</b> to save "
          "a snapshot. ";
  text += "See the <b>Keyboard</b> tab in this window for a complete shortcut "
          "list.<br><br>";
  text += "Double clicks automates single click actions: A left button double "
          "click aligns the closer axis with the camera (if close enough). ";
  text += "A middle button double click fits the zoom of the camera and the "
          "right button re-centers the scene.<br><br>";
  text += "A left button double click while holding right button pressed "
          "defines the camera <i>Revolve Around Point</i>. ";
  text += "See the <b>Mouse</b> tab and the documentation web pages for "
          "details.<br><br>";
  text += "Press <b>Escape</b> to exit the viewer.";
  return text;
}

