#include <GLUT/glut.h>
#include <limits>

float rotationY = 315.0f;
float rotationX = 30.0f;
const int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 600;
float zBuffer[WINDOW_WIDTH][WINDOW_HEIGHT];

void initializeGL()
{
    glEnable(GL_DEPTH_TEST);
    glClearColor(0.0, 0.0, 0.0, 1.0);
}

void initZBuffer()
{
    for (int i = 0; i < WINDOW_WIDTH; i++)
    {
        for (int j = 0; j < WINDOW_HEIGHT; j++)
        {
            zBuffer[i][j] = std::numeric_limits<float>::infinity();
        }
    }
}

void renderFace(float vertices[4][3], float r, float g, float b)
{
    glBegin(GL_QUADS);
    glColor3f(r, g, b);
    for (int i = 0; i < 4; i++)
    {
        glVertex3fv(vertices[i]);
    }
    glEnd();
}

void renderScene()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();
    initZBuffer();
    glTranslatef(0.0f, 0.0f, -5.0f);
    glRotatef(rotationY, 0.0f, 1.0f, 0.0f);
    glRotatef(rotationX, 1.0f, 0.0f, 0.0f);

    float frontFace[4][3] = {{-1, -1, 1}, {1, -1, 1}, {1, 1, 1}, {-1, 1, 1}};
    float rightFace[4][3] = {{1, -1, 1}, {1, -1, -1}, {1, 1, -1}, {1, 1, 1}};
    float topFace[4][3] = {{-1, 1, 1}, {1, 1, 1}, {1, 1, -1}, {-1, 1, -1}};
    float backFace[4][3] = {{-1, -1, -1}, {1, -1, -1}, {1, 1, -1}, {-1, 1, -1}};
    float leftFace[4][3] = {{-1, -1, 1}, {-1, -1, -1}, {-1, 1, -1}, {-1, 1, 1}};
    float bottomFace[4][3] = {{-1, -1, 1}, {1, -1, 1}, {1, -1, -1}, {-1, -1, -1}};

    renderFace(frontFace, 0.8, 0.2, 0.8);
    renderFace(rightFace, 1.0, 1.0, 0.0);
    renderFace(topFace, 0.0, 0.8, 1.0);
    glColorMask(GL_FALSE, GL_FALSE, GL_FALSE, GL_FALSE);
    renderFace(backFace, 0.3, 0.3, 0.3);
    renderFace(leftFace, 0.3, 0.3, 0.3);
    renderFace(bottomFace, 0.3, 0.3, 0.3);
    glColorMask(GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE);
    glutSwapBuffers();
}

void handleResize(int w, int h)
{
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, (float)w / (float)h, 1.0, 10.0);
    glMatrixMode(GL_MODELVIEW);
}

void handleKeypress(unsigned char key, int x, int y)
{
    switch (key)
    {
    case 'a':
        rotationY -= 5.0f;
        break;
    case 'd':
        rotationY += 5.0f;
        break;
    case 'w':
        rotationX -= 5.0f;
        break;
    case 's':
        rotationX += 5.0f;
        break;
    }
    glutPostRedisplay();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    glutCreateWindow("Saptarshi Adhikari 2212072");
    initializeGL();
    glutDisplayFunc(renderScene);
    glutReshapeFunc(handleResize);
    glutKeyboardFunc(handleKeypress);
    glutMainLoop();
    return 0;
}