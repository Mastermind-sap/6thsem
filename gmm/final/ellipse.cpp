#include <GLUT/glut.h>
#include <iostream>
#include <math.h>
using namespace std;

float a = 140, b = 80;

void drawAxes() {
    glBegin(GL_LINES);
    glVertex2i(-300, 0); glVertex2i(300, 0);
    glVertex2i(0, -300); glVertex2i(0, 300);
    glEnd();
}

void ellipse() {
    float p = b*b - a*a*b + a*a/4;
    float x = 0, y = b;
    glBegin(GL_POINTS);
    glColor3f(0.7, 0.2, 0);
    glVertex2i(0, 0);
    while(2*b*b*x < 2*a*a*y){
        glVertex2i(x, y);
        glVertex2i(-x, y);
        glVertex2i(-x, -y);
        glVertex2i(x, -y);
        if(p >= 0){
            p -= 2*a*a*y + 2*a*a;
            y = y - 1;
        }
        p += 2*b*b*x + 3*b*b;
        x = x + 1;
    }
    p = b*b*(x + 0.5)*(x + 0.5) + a*a*(y - 1)*(y - 1) - a*a*b*b;
    while(y >= 0){
        glVertex2i(x, y);
        glVertex2i(-x, y);
        glVertex2i(-x, -y);
        glVertex2i(x, -y);
        if(p < 0){
            p += 2*b*b*x + 2 * b*b;
            x = x + 1;
        }
        p += 3*a*a - 2*a*a*y; 
        y= y - 1;
    }
    glEnd();
}

void display(){
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    ellipse();
    glFlush();
}


void init() {
    glClearColor(1, 1, 1, 1);
    glColor3f(0, 0, 0);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-300, 300, -300, 300);
}

int main(int argc, char *argv[])
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB | GLUT_SINGLE);
    glutInitWindowSize(600, 600);
    glutCreateWindow("Ellipse Midpoint");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
    return 0;
}