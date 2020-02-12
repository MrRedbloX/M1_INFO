#ifndef VIEW_H
#define VIEW_H

#include <QGLViewer/qglviewer.h>
#include <vertex.h>
#include <face.h>
#include <stdlib.h>
#include <stdio.h>
#include <objparser.h>

class View : public QGLViewer
{
public:
    View();

    virtual void draw();
    virtual void init();
    void keyPressEvent(QKeyEvent*);

    ObjParser parser;
};

#endif // VIEW_H
