#ifndef SIMPLEVIEWER_H
#define SIMPLEVIEWER_H

#endif // SIMPLEVIEWER_H

#include <QGLViewer/qglviewer.h>

class Viewer : public QGLViewer {
protected:
  virtual void draw();
  virtual void init();
  virtual QString helpString() const;
};
