// #include <GL/freeglut.h>
#include<GLUT/glut.h>
#include <iostream>
#include <unistd.h>
using namespace std;
float tx = 0, ty = 0, tz = 0;
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

float angle = 0;
void idle(){
    angle += 0.1f;
    if(angle >= 360) angle -=360;
    glutPostRedisplay();
    usleep(3);
}

void display(){
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();
    gluLookAt(3.0, 3.0, 7.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
    glRotatef(angle, 0, 1, 0);
    glPushMatrix();
    drawAxes();
    glPopMatrix();
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glColor3f(0.5f, 0.5f, 0.5f);
    glPushMatrix();
    drawPyramid(pyramid, 1, 0, 0);
    glPopMatrix();
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glPushMatrix();
    drawPyramid(tran, 0, 1, 0);
    glPopMatrix();
    glutSwapBuffers();
}

void reshape(int w, int h){
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45, (GLfloat)w/(GLfloat)h, 0.1, 100);
    glMatrixMode(GL_MODELVIEW);
}

int main(int argc, char *argv[])
{
    cin >> tx >> ty >> tz;

    for(int i = 0; i < 5; i++) {
        tran[i][0] = pyramid[i][0] + tx;
        tran[i][1] = pyramid[i][1] + ty;
        tran[i][2] = pyramid[i][2] + tz;
    }
    
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
    glutInitWindowSize(600, 600);
    glutCreateWindow("3D transformation");
    glClearColor(0, 0, 0, 1);
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_COLOR_MATERIAL);
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(idle);
    glutMainLoop();
}