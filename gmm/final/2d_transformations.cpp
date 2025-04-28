#include <GLUT/glut.h>
#include <cmath>
#include <iostream>

const int nVertices = 3;
float originalTriangle[nVertices][2] = {
    {0.0, 2.0},
    {-1.5, -1.0},
    {1.5, -1.0}};

float transformedTriangle[nVertices][2];

float tx = 0.0f, ty = 0.0f;
float angle = 0.0f;
float sx = 1.0f, sy = 1.0f;
bool reflectX = false;
bool reflectY = false;

void resetTriangle()
{
    for (int i = 0; i < nVertices; i++)
    {
        for (int j = 0; j < 2; j++)
        {
            transformedTriangle[i][j] = originalTriangle[i][j];
        }
    }
}

void translate(float tx, float ty)
{
    for (int i = 0; i < nVertices; i++)
    {
        transformedTriangle[i][0] += tx;
        transformedTriangle[i][1] += ty;
    }
}

void rotate(float angle)
{
    float rad = angle * M_PI / 180.0;
    float cosTheta = cos(rad);
    float sinTheta = sin(rad);

    for (int i = 0; i < nVertices; i++)
    {
        float x = transformedTriangle[i][0];
        float y = transformedTriangle[i][1];
        transformedTriangle[i][0] = x * cosTheta - y * sinTheta;
        transformedTriangle[i][1] = x * sinTheta + y * cosTheta;
    }
}

void scale(float sx, float sy)
{
    for (int i = 0; i < nVertices; i++)
    {
        transformedTriangle[i][0] *= sx;
        transformedTriangle[i][1] *= sy;
    }
}

void reflect()
{
    for (int i = 0; i < nVertices; i++)
    {
        if (reflectX)
            transformedTriangle[i][0] = -transformedTriangle[i][0];
        if (reflectY)
            transformedTriangle[i][1] = -transformedTriangle[i][1];
    }
}

void applyTransformations()
{
    resetTriangle();
    scale(sx, sy);
    rotate(angle);
    reflect();
    translate(tx, ty);
}

void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-5, 5, -5, 5);
    resetTriangle();
}

void drawAxes()
{
    glColor3f(0.5, 0.5, 0.5);
    glBegin(GL_LINES);

    glVertex2f(-5.0, 0.0);
    glVertex2f(5.0, 0.0);

    glVertex2f(0.0, -5.0);
    glVertex2f(0.0, 5.0);
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);

    drawAxes();

    glColor3f(0.0, 0.5, 0.0);
    glBegin(GL_LINE_LOOP);
    for (int i = 0; i < nVertices; i++)
    {
        glVertex2fv(originalTriangle[i]);
    }
    glEnd();

    glColor3f(1.0, 0.0, 0.0);
    glBegin(GL_TRIANGLES);
    for (int i = 0; i < nVertices; i++)
    {
        glVertex2fv(transformedTriangle[i]);
    }
    glEnd();

    glFlush();
}

void keyboard(unsigned char key, int x, int y)
{
    switch (key)
    {

    case 'w':
        ty += 0.1;
        break;
    case 's':
        ty -= 0.1;
        break;
    case 'a':
        tx -= 0.1;
        break;
    case 'd':
        tx += 0.1;
        break;

    case 'q':
        angle += 5.0;
        break;
    case 'e':
        angle -= 5.0;
        break;

    case 'z':
        sx *= 1.1;
        sy *= 1.1;
        break;
    case 'x':
        sx *= 0.9;
        sy *= 0.9;
        break;
    case 'c':
        sx *= 1.1;
        break;
    case 'v':
        sx *= 0.9;
        break;
    case 'b':
        sy *= 1.1;
        break;
    case 'n':
        sy *= 0.9;
        break;

    case 'h':
        reflectX = !reflectX;
        break;
    case 'j':
        reflectY = !reflectY;
        break;

    case 'r':
        tx = ty = 0.0f;
        angle = 0.0f;
        sx = sy = 1.0f;
        reflectX = reflectY = false;
        break;

    case 27:
        exit(0);
    }

    applyTransformations();
    glutPostRedisplay();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(600, 600);
    glutCreateWindow("2D Transformations");
    init();
    glutDisplayFunc(display);
    glutKeyboardFunc(keyboard);

    printf("2D Transformations Controls:\n");
    printf("----------------------------\n");
    printf("Translation: W (up), S (down), A (left), D (right)\n");
    printf("Rotation: Q (counter-clockwise), E (clockwise)\n");
    printf("Scaling: Z (up), X (down), C (x-up), V (x-down), B (y-up), N (y-down)\n");
    printf("Reflection: H (x-axis), J (y-axis)\n");
    printf("Reset: R\n");
    printf("Exit: ESC\n");

    glutMainLoop();
    return 0;
}
