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

#include "fastDraw.h"
using namespace std;
using namespace qglviewer;
QString sunTexPath = "../img/sunTex.jpg";
QString earthTexPath = "../img/earthTex.jpg";
QString moonTexPath = "../img/moonTex.jpg";
GLuint textures[3];
void Viewer::init() {
    qDebug() << QDir::currentPath();
    QImage img1(sunTexPath);
    QImage img2(earthTexPath);
    QImage img3(moonTexPath);
    glGenTextures(3, textures);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, textures[0]);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glHint( GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST );
    QImage glImg1 = QGLWidget::convertToGLFormat(img1);
    glTexImage2D(GL_TEXTURE_2D, 0, 3, glImg1.width(), glImg1.height(), 0,
                 GL_RGBA, GL_UNSIGNED_BYTE, glImg1.bits());

    glBindTexture(GL_TEXTURE_2D, textures[1]);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glHint( GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST );
    QImage glImg2 = QGLWidget::convertToGLFormat(img2);
    glTexImage2D(GL_TEXTURE_2D, 0, 3, glImg2.width(), glImg2.height(), 0,
                 GL_RGBA, GL_UNSIGNED_BYTE, glImg2.bits());

    glBindTexture(GL_TEXTURE_2D, textures[2]);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glHint( GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST );
    QImage glImg3 = QGLWidget::convertToGLFormat(img3);
    glTexImage2D(GL_TEXTURE_2D, 0, 3, glImg3.width(), glImg3.height(), 0,
                 GL_RGBA, GL_UNSIGNED_BYTE, glImg3.bits());
  this->rotateSun = 0;
  this->rotateEarthFromSun = 0;
  this->rotateMoonFromEarth = 0;
  this->rotateEarth = 0;
  this->rotateMoon = 0;
  restoreStateFromFile();
  setSceneRadius(500);
  showEntireScene();
  startAnimation();
}

void Viewer::draw() {
    glEnable(GL_TEXTURE_2D);
    GLUquadric *sun;
    GLUquadric *earth;
    GLUquadric *moon;
    sun = gluNewQuadric();
    earth = gluNewQuadric();
    moon = gluNewQuadric();
    gluQuadricTexture(sun,GL_TRUE);
    gluQuadricTexture(earth,GL_TRUE);
    gluQuadricTexture(moon,GL_TRUE);
    glPushMatrix();
    glBindTexture(GL_TEXTURE_2D, textures[0]);
    glPolygonMode(GL_FRONT, GL_FILL);
    glRotatef(this->rotateSun, 0, 0, 1);
    gluSphere(sun,75,100,20);
    glPopMatrix();
    glPushMatrix();
    glBindTexture(GL_TEXTURE_2D, textures[1]);
    glPolygonMode(GL_FRONT, GL_FILL);
    glRotatef(this->rotateEarthFromSun, 0, 0, 1);
    glTranslatef(200,200,0);
    glRotatef(this->rotateEarth, 0, 0, 1);
    gluSphere(earth,20,100,20);
    glBindTexture(GL_TEXTURE_2D, textures[2]);
    glPolygonMode(GL_FRONT, GL_FILL);
    glRotatef(this->rotateMoonFromEarth, 0, 0, 1);
    glTranslatef(40,40,0);
    glRotatef(this->rotateMoon, 0, 0, 1);
    gluSphere(moon,8,100,20);
    glPopMatrix();

}

void Viewer::animate(){
    this->rotateSun += 0.5;
    this->rotateEarthFromSun += 1.5;
    this->rotateMoonFromEarth += 2.5;
    this->rotateEarth += 0.5;
    this->rotateMoon += 0.5;
}

QString Viewer::helpString() const {
  QString text("<h2>F a s t D r a w</h2>");
  text += "The <code>fastDraw()</code> function is called instead of "
          "<code>draw()</code> when the camera ";
  text += "is manipulated. Providing such a simplified version of "
          "<code>draw()</code> allows for interactive ";
  text += "frame rates when the camera is moved, even for very complex scenes.";
  return text;
}
