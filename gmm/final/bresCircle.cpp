#include <GLUT/glut.h>

float rad = 200;
float xc = 50, yc = 50;

void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-500, 500, -500, 500);
}

void drawAxes()
{
    glBegin(GL_LINES);
    glColor3f(0, 0, 0);
    glVertex2i(-300, 0);
    glVertex2i(300, 0);
    glVertex2i(0, -300);
    glVertex2i(0, 300);
    glEnd();
}

void bresCircle()
{
    float x = 0, y = rad;
    float p = 3 - 2 * rad;
    glPointSize(3);
    glBegin(GL_POINTS);
    glColor3f(0, 0, 1);
    glVertex2i(xc, yc);
    while (x <= y)
    {
        glVertex2i(xc + x, yc + y);
        glVertex2i(xc + y, yc + x);
        glVertex2i(xc + -x, yc + y);
        glVertex2i(xc + -y, yc + x);
        glVertex2i(xc + x, yc + -y);
        glVertex2i(xc + y, yc + -x);
        glVertex2i(xc + -x, yc + -y);
        glVertex2i(xc + -y, yc + -x);
        if (p < 0)
        {
            p += 4 * x + 6;
        }
        else
        {
            p += 4 * (x - y) + 10;
            y = y - 1;
        }
        x += 1;
    }
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    bresCircle();
    glFlush();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutCreateWindow("bressenham circle");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
}
