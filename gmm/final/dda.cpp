#include <GLUT/glut.h>
#include <iostream>

using namespace std;

int x0, y0, x1, y1;
void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_MODELVIEW);
    gluOrtho2D(-200, 200, -200, 200);
}

void drawAxis()
{
    glColor3f(1, 1, 1);
    glBegin(GL_LINES);
    glVertex2i(-200, 0);
    glVertex2i(200, 0);
    glVertex2i(0, 200);
    glVertex2i(0, -200);
    glEnd();
}

void dda()
{
    int dx = x1 - x0, dy = y1 - y0;
    int steps;
    float xinc, yinc, x = x0, y = y0;
    glBegin(GL_POINTS);
    if (abs(dx) > abs(dy))
        steps = abs(dx);
    else
        steps = abs(dy);
    xinc = float(dx) / float(steps);
    yinc = float(dy) / float(steps);
    glVertex2i(floor(x),floor(y));
    for(int i=0;i<steps;i++){
        x+=xinc,y+=yinc;
        glVertex2i(floor(x),floor(y));
    }
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxis();
    dda();
    glFlush();
}

int main(int argc, char **argv)
{
    cin >> x0 >> y0 >> x1 >> y1;
    glutInit(&argc, argv);
    glutCreateWindow("DDA");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
}