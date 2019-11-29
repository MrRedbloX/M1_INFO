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
    bool smooth = false;
    bool points = false;
    bool fill = true;
    virtual void draw();
    virtual void init();
    void keyPressEvent(QKeyEvent*);
    GLuint texture;
    std::vector<Face*> allFaces;
    std::vector<Vertex*> allVertices;
    GLMmodel* model;
    ObjParser parser;
};

#endif // VIEW_H
