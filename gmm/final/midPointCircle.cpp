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

void midCircle(){
    float x = 0, y = rad;
    float p = 5/4 - rad;//
    glPointSize(3);
    glBegin(GL_POINTS);
    glColor3f(0, 0, 1);
    glVertex2i(xc, yc);
    while(x <= y) {
        glVertex2i(xc + x, xc + y); glVertex2i(xc + y, yc + x);
        glVertex2i(xc + -x, yc + y); glVertex2i(xc + -y, yc + x);
        glVertex2i(xc + x, yc + -y); glVertex2i(xc + y, yc + -x);
        glVertex2i(xc + -x, yc + -y); glVertex2i(xc + -y, yc + -x);
        x = x + 1;
        if(p < 0) {
            p += 2*x + 1;//
        }
        else {
            y = y - 1;
            p += 2*(x - y) + 1;//
        }
    }
    glEnd();
}


void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    midCircle();
    glFlush();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutCreateWindow("midpoint circle");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
}
