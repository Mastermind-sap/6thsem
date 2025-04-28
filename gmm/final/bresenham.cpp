#include <GLUT/glut.h>
// #include <bits/stdc++.h>
#include <iostream>

using namespace std;

float x0, y0, x1, y1;

void drawAxes()
{
    glBegin(GL_LINES);
    glColor3f(1, 1, 1);
    glVertex2i(-200, 0);
    glVertex2i(200, 0);
    glVertex2i(0, -200);
    glVertex2i(0, 200);
    glEnd();
}

void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-200, 200, -200, 200);
}

void bressenham()
{
    int dx = abs(x1 - x0), dy = abs(y1 - y0);
    int a, b, p;
    int x = x0, y = y0;
    int sx = (x0 < x1) ? 1 : -1;
    int sy = (y0 < y1) ? 1 : -1;
    glBegin(GL_POINTS);
    glVertex2i(x, y);
    if (dx > dy)
    {
        p = 2 * dy - dx;
        a = 2 * dy, b = 2 * (dy - dx);
        while (x != x1)
        {
            if (p < 0)
            {
                p += a;
            }
            else
            {
                p += b;
                y += sy;
            }
            x += sx;
            glVertex2i(x, y);
        }
    }
    else
    {
        p = 2 * dx - dy;
        a = 2 * dx, b = 2 * (dx - dy);
        while (y != y1)
        {
            if (p < 0)
            {
                p += a;
            }
            else
            {
                p += b;
                x += sx;
            }
            y += sy;
            glVertex2i(x, y);
        }
    }
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    bressenham();
    glFlush();
}

int main(int argc, char **argv)
{
    cin >> x0 >> y0 >> x1 >> y1;
    glutInit(&argc, argv);
    glutCreateWindow("bressenham");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
}
