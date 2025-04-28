#include <GLUT/glut.h>

void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-5, 5, -5, 5);
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glFlush();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutCreateWindow("template");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
}
