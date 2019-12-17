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

void Viewer::init() {
  this->rotate = 0;
  restoreStateFromFile();
  setSceneRadius(500);
  showEntireScene();
  startAnimation();
}
void Viewer::draw() {
    glColor3f(1,1,0);
    GLUquadric *sun;
    GLUquadric *earth;
    GLUquadric *moon;
    sun = gluNewQuadric();
    earth = gluNewQuadric();
    moon = gluNewQuadric();
    glPushMatrix();
    glRotatef(this->rotate, 0, 0, 1);
    gluSphere(sun,75,100,20);
    glPopMatrix();
    glColor3f(0,0,1);
    glRotatef(this->rotate, 0, 0, 1);
    glTranslatef(200,200,0);
    gluSphere(earth,20,100,20);
    glColor3f(0.5,0.5,0.5);
    glTranslatef(40,40,0);
    gluSphere(moon,5,100,20);
}

void Viewer::animate(){
    this->rotate += 1;
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
