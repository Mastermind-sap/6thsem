// #include <GL/freeglut.h>
#include <iostream>
#include <math.h>
#include <vector>
#include <unistd.h>
using namespace std;

float pyramid[5][3] = {
    {0.0, 1.0, 0.0},
    {1.0, 0.0, 1.0},
    {-1.0, 0.0, 1.0},
    {1.0, 0.0, -1.0},
    {-1.0, 0.0, -1.0}
};
float tran[5][3];
void drawPyramid(float pyramid[5][3], float r, float g, float b) {
    glPushMatrix();
    glBegin(GL_TRIANGLES);
    glColor3f(r, g, b);
    glVertex3fv(pyramid[0]); glVertex3fv(pyramid[1]); glVertex3fv(pyramid[2]);
    glVertex3fv(pyramid[0]); glVertex3fv(pyramid[2]); glVertex3fv(pyramid[4]);
    glVertex3fv(pyramid[0]); glVertex3fv(pyramid[1]); glVertex3fv(pyramid[3]);
    glVertex3fv(pyramid[0]); glVertex3fv(pyramid[3]); glVertex3fv(pyramid[4]);
    glEnd();
    glBegin(GL_QUADS);
    glColor3f(r, g, b); 
    glVertex3fv(pyramid[1]); glVertex3fv(pyramid[2]); glVertex3fv(pyramid[4]); glVertex3fv(pyramid[3]);
    glEnd();
    glPopMatrix();
}

void drawAxes(){
    glBegin(GL_LINES);
    glColor3f(1, 0, 0);
    glVertex3f(0, 0, 0); glVertex3f(2, 0, 0);
    glColor3f(0, 1, 0);
    glVertex3f(0, 0, 0); glVertex3f(0, 2, 0);
    glColor3f(0, 0, 1);
    glVertex3f(0, 0, 0); glVertex3f(0, 0, 2);
    glEnd();
}

vector<float> axisChange(int choice, float x, float y, float z, float angle){
    switch (choice)
    {
    case 1:
        {
            vector<float> changed(3);
            changed[0] = x;
            changed[1] = y*cos(angle) - z*sin(angle);
            changed[2] = y*sin(angle) + z*cos(angle);
            return changed; 
        }
        break;
    case 2:
        {
            vector<float> changed(3);
            changed[0] = x*cos(angle) + z*sin(angle);
            changed[1] = y;
            changed[2] = -x*sin(angle) + z*cos(angle);
            return changed;
        }
        break;
    case 3:
        {
            vector<float> changed(3);
            changed[2] = z;
            changed[0] = x*cos(angle) - y*sin(angle);
            changed[1] = x*sin(angle) + y*cos(angle);
            return changed; 
        }
        break;
    default:
        return {};
        break;
    }
}
float rot = 0;
void idle(){
    rot += 0.1f;
    if(rot >= 360) rot -= 360;
    glutPostRedisplay();
    usleep(10);
}

void display(){
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();
    gluLookAt(3, 3, 7, 0, 0, 0, 0, 1, 0);
    glRotatef(rot, 0, 1, 0);
    glPushMatrix();
    drawAxes();
    glPopMatrix();
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glPushMatrix();
    drawPyramid(pyramid, 1, 0, 0);
    glPopMatrix();
    glPushMatrix();
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    drawPyramid(tran, 0, 1, 0);
    glPopMatrix();
    glutSwapBuffers();
}

void reshape(int w, int h){
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(60, (GLfloat)w/(GLfloat)h, 1, 100);
    glMatrixMode(GL_MODELVIEW);
}

int main(int argc, char *argv[])
{
    int axis;
    float angle;
    cin >> axis >> angle;
    angle = 3.14159*angle/180;
    for(int i = 0; i < 5; i++){
        vector<float> changed = axisChange(axis, pyramid[i][0], pyramid[i][1], pyramid[i][2], angle);
        tran[i][0] = changed[0], tran[i][1] = changed[1], tran[i][2] = changed[2];
    }
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
    glutInitWindowSize(600, 600);
    glutCreateWindow("Pyramid Rotate");
    glClearColor(0, 0, 0, 1);
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_COLOR_MATERIAL);
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(idle);
    glutMainLoop();
}