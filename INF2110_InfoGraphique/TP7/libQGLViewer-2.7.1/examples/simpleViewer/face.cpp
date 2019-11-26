#include "face.h"

using namespace qglviewer;

Face::Face(Vertex* p1_, Vertex* p2_, Vertex* p3_, Vec normal_)
{
    this->p1 = p1_;
    this->p2 = p2_;
    this->p3 = p3_;
    this->normal = normal_;
}

Face::Face(Vertex* p1_, Vertex* p2_, Vertex* p3_)
{
    this->p1 = p1_;
    this->p2 = p2_;
    this->p3 = p3_;
}
