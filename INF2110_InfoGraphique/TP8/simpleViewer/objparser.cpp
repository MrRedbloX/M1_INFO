#include "objparser.h"

using namespace qglviewer;

ObjParser::ObjParser(){
}

ObjParser::ObjParser(char* fileName_)
{
    this->fileName = fileName_;
}
void ObjParser::parser(){

    this->model = glmReadOBJ(this->fileName);



    for(int i = 3; i < (int)(this->model->numvertices+1)*3; i++){
        Vertex* p = new Vertex();
        p->position[0] = this->model->vertices[i];
        i++;
        p->position[1] = this->model->vertices[i];
        i++;
        p->position[2] = this->model->vertices[i];
        this->vertexArray.push_back(p);
    }
        std::cout<<(this->vertexArray[0])->position<<std::endl;
        std::cout<<(this->vertexArray[this->vertexArray.size()-1])->position<<std::endl;
        std::cout<<this->vertexArray.size()<<std::endl;

        std::cout<<this->model->numtriangles<<std::endl;
        this->faceArray.clear();
        std::cout<< "taille avant" << this->faceArray.size()<<std::endl;

    for(int j = 0; j < (int)this->model->numtriangles; j++){

        Vertex *P1 = this->vertexArray[this->model->triangles[j].vindices[0]-1];
        Vertex *P2 = this->vertexArray[this->model->triangles[j].vindices[1]-1];

        Vertex *P3 = this->vertexArray[this->model->triangles[j].vindices[2]-1];

        Vec normal = (P2->position-P1->position)^(P3->position-P1->position);

        P1->normal+=normal;
        P2->normal+=normal;
        P3->normal+=normal;

        Face *nface = new Face(P1,P2,P3,normal/normal.norm());
        this->faceArray.push_back(nface);
    }
    std::cout<< "taille apres" << this->faceArray.size()<<std::endl;

    for(uint k = 0; k < this->faceArray.size(); k++){
        faceArray[k]->p1->normal = faceArray[k]->p1->normal/faceArray[k]->p1->normal.norm();
        faceArray[k]->p2->normal = faceArray[k]->p2->normal/faceArray[k]->p2->normal.norm();
        faceArray[k]->p3->normal = faceArray[k]->p3->normal/faceArray[k]->p3->normal.norm();
    }

}



























