#include <GL/glut.h>
#include <limits>

float angle = 315.0f;
float verticalAngle = 30.0f;
const int WIDTH = 600, HEIGHT = 600;
float zBuffer[WIDTH][HEIGHT];

void init() {
    glEnable(GL_DEPTH_TEST);
    glClearColor(0.0, 0.0, 0.0, 1.0);
}

void clearZBuffer() {
    for (int i = 0; i < WIDTH; i++) {
        for (int j = 0; j < HEIGHT; j++) {
            zBuffer[i][j] = std::numeric_limits<float>::infinity();
        }
    }
}

void drawFace(float vertices[4][3], float r, float g, float b) {
    glBegin(GL_QUADS);
    glColor3f(r, g, b);
    for (int i = 0; i < 4; i++) {
        glVertex3f(vertices[i][0], vertices[i][1], vertices[i][2]);
    }
    glEnd();
}

void display() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();
    clearZBuffer();
    
    glTranslatef(0.0f, 0.0f, -5.0f);
    glRotatef(angle, 0.0f, 1.0f, 0.0f);
    glRotatef(verticalAngle, 1.0f, 0.0f, 0.0f);
    
    float frontFace[4][3] = {{-1, -1, 1}, {1, -1, 1}, {1, 1, 1}, {-1, 1, 1}};
    float rightFace[4][3] = {{1, -1, 1}, {1, -1, -1}, {1, 1, -1}, {1, 1, 1}};
    float topFace[4][3] = {{-1, 1, 1}, {1, 1, 1}, {1, 1, -1}, {-1, 1, -1}};
    float backFace[4][3] = {{-1, -1, -1}, {1, -1, -1}, {1, 1, -1}, {-1, 1, -1}};
    float leftFace[4][3] = {{-1, -1, 1}, {-1, -1, -1}, {-1, 1, -1}, {-1, 1, 1}};
    float bottomFace[4][3] = {{-1, -1, 1}, {1, -1, 1}, {1, -1, -1}, {-1, -1, -1}};
    
    drawFace(frontFace, 1.0, 0.0, 0.0);
    drawFace(rightFace, 0.0, 1.0, 0.0);
    drawFace(topFace, 0.0, 0.0, 1.0);
    
    glColorMask(GL_FALSE, GL_FALSE, GL_FALSE, GL_FALSE);
    drawFace(backFace, 0.5, 0.5, 0.5);
    drawFace(leftFace, 0.5, 0.5, 0.5);
    drawFace(bottomFace, 0.5, 0.5, 0.5);
    glColorMask(GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE);
    
    glutSwapBuffers();
}

void reshape(int w, int h) {
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, (float)w/(float)h, 1.0, 10.0);
    glMatrixMode(GL_MODELVIEW);
}

void keyboard(unsigned char key, int x, int y) {
    if (key == 'a') angle -= 5.0f;
    if (key == 'd') angle += 5.0f;
    if (key == 'w') verticalAngle -= 5.0f;
    if (key == 's') verticalAngle += 5.0f;
    glutPostRedisplay();
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(WIDTH, HEIGHT);
    glutCreateWindow("Z-Buffer Hidden Surface Removal");
    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutMainLoop();
    return 0;
}