#ifndef FACE_H
#define FACE_H
#include <QGLViewer/qglviewer.h>
#include <vertex.h>

class Face
{
public:
    Face();
    Face(Vertex* p1_, Vertex* p2_, Vertex* p3_, qglviewer::Vec normal_);
    Face(Vertex* p1_, Vertex* p2_, Vertex* p3_);
    Vertex* p1;
    Vertex* p2;
    Vertex* p3;
    float U1, V1 = 0;
    float U2, V2 = 0;
    float U3, V3 = 0;
    qglviewer::Vec normal;
};

#endif // FACE_H
