#ifndef VERTEX_H
#define VERTEX_H

#include <QGLViewer/qglviewer.h>

class Vertex
{
public:
    Vertex();
    Vertex(double x_, double y_, double z_, qglviewer::Vec normal_);
    Vertex(double x_, double y_, double z_);
    void setNormal(qglviewer::Vec normal_);

    qglviewer::Vec position;
    qglviewer::Vec normal;

};

#endif // VERTEX_H
