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

#include <QGLViewer/qglviewer.h>
#include <GL/gl.h>
#include "windows.h"
#include <GL/glu.h>
#include <QDir>
#include <QDebug>

class Viewer : public QGLViewer {
protected:
  virtual void init();
  virtual void draw();
  virtual void animate();
  virtual QString helpString() const;
  float rotateSun;
  float rotateEarth;
  float rotateEarthFromSun;
  float rotateMoon;
  float rotateMoonFromEarth;
};
