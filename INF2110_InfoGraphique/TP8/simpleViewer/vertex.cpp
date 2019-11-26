#include "vertex.h"

using namespace qglviewer;

Vertex::Vertex(){}

Vertex::Vertex(double x_, double y_, double z_, Vec normal_)
{
    this->position = Vec(x_,y_,z_);
    this->normal = normal_;
}

Vertex::Vertex(double x_, double y_, double z_)
{
    this->position = Vec(x_,y_,z_);
}

void Vertex::setNormal(Vec normal_){
    this->normal = normal_;
}
