#ifndef OBJPARSER_H
#define OBJPARSER_H

#include <QGLViewer/qglviewer.h>
#include <vertex.h>
#include <face.h>
#include <stdlib.h>
#include <stdio.h>
#include <glm.h>

class ObjParser
{
public:
    ObjParser();
    ObjParser(char* fileName_);
    char* fileName;
    std::vector<Face*> faceArray;
    std::vector<Vertex*> vertexArray;
    GLMmodel* model;

    void readFile();

    void parser();
};

#endif // OBJPARSER_H
