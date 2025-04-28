#include <GLUT/glut.h>
#include <cmath>
#include <iostream>
using namespace std;

// 3D cube vertices (8 corners)
const int nVertices = 8;
float originalCube[nVertices][3] = {
    {-1.0, -1.0, -1.0}, // 0: bottom-left-back
    {1.0, -1.0, -1.0},  // 1: bottom-right-back
    {1.0, 1.0, -1.0},   // 2: top-right-back
    {-1.0, 1.0, -1.0},  // 3: top-left-back
    {-1.0, -1.0, 1.0},  // 4: bottom-left-front
    {1.0, -1.0, 1.0},   // 5: bottom-right-front
    {1.0, 1.0, 1.0},    // 6: top-right-front
    {-1.0, 1.0, 1.0}    // 7: top-left-front
};

float transformedCube[nVertices][3];

// Transformation parameters
float tx = 0.0, ty = 0.0, tz = 0.0;             // Translation
float angleX = 0.0, angleY = 0.0, angleZ = 0.0; // Rotation angles
float sx = 1.0, sy = 1.0, sz = 1.0;             // Scaling

// Camera parameters
float cameraDistance = 10.0;
float cameraAngleX = 30.0;
float cameraAngleY = 45.0;

// Copy original cube to transformed cube
void resetCube()
{
    for (int i = 0; i < nVertices; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            transformedCube[i][j] = originalCube[i][j];
        }
    }
}

// Apply 3D transformations
void applyTransformations()
{
    resetCube();

    // Scale
    for (int i = 0; i < nVertices; i++)
    {
        transformedCube[i][0] *= sx;
        transformedCube[i][1] *= sy;
        transformedCube[i][2] *= sz;
    }

    // Rotate around X axis
    if (angleX != 0.0)
    {
        float rad = angleX * M_PI / 180.0;
        float cosTheta = cos(rad);
        float sinTheta = sin(rad);

        for (int i = 0; i < nVertices; i++)
        {
            float y = transformedCube[i][1];
            float z = transformedCube[i][2];
            transformedCube[i][1] = y * cosTheta - z * sinTheta;
            transformedCube[i][2] = y * sinTheta + z * cosTheta;
        }
    }

    // Rotate around Y axis
    if (angleY != 0.0)
    {
        float rad = angleY * M_PI / 180.0;
        float cosTheta = cos(rad);
        float sinTheta = sin(rad);

        for (int i = 0; i < nVertices; i++)
        {
            float x = transformedCube[i][0];
            float z = transformedCube[i][2];
            transformedCube[i][0] = x * cosTheta + z * sinTheta;
            transformedCube[i][2] = -x * sinTheta + z * cosTheta;
        }
    }

    // Rotate around Z axis
    if (angleZ != 0.0)
    {
        float rad = angleZ * M_PI / 180.0;
        float cosTheta = cos(rad);
        float sinTheta = sin(rad);

        for (int i = 0; i < nVertices; i++)
        {
            float x = transformedCube[i][0];
            float y = transformedCube[i][1];
            transformedCube[i][0] = x * cosTheta - y * sinTheta;
            transformedCube[i][1] = x * sinTheta + y * cosTheta;
        }
    }

    // Translate
    for (int i = 0; i < nVertices; i++)
    {
        transformedCube[i][0] += tx;
        transformedCube[i][1] += ty;
        transformedCube[i][2] += tz;
    }
}

void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, 1.0, 0.1, 100.0);

    glEnable(GL_DEPTH_TEST);

    resetCube();

    // Get initial transformation values from user
    cout << "Enter translation values (tx ty tz): ";
    cin >> tx >> ty >> tz;

    cout << "Enter rotation angles in degrees (angleX angleY angleZ): ";
    cin >> angleX >> angleY >> angleZ;

    cout << "Enter scaling factors (sx sy sz): ";
    cin >> sx >> sy >> sz;

    applyTransformations();
}

void drawAxes()
{
    glBegin(GL_LINES);
    // X axis (red)
    glColor3f(1.0, 0.0, 0.0);
    glVertex3f(0.0, 0.0, 0.0);
    glVertex3f(5.0, 0.0, 0.0);

    // Y axis (green)
    glColor3f(0.0, 1.0, 0.0);
    glVertex3f(0.0, 0.0, 0.0);
    glVertex3f(0.0, 5.0, 0.0);

    // Z axis (blue)
    glColor3f(0.0, 0.0, 1.0);
    glVertex3f(0.0, 0.0, 0.0);
    glVertex3f(0.0, 0.0, 5.0);
    glEnd();
}

// Draw a wireframe cube
void drawCube(float cube[nVertices][3])
{
    // Define edges of cube - each edge connects 2 vertices
    const int nEdges = 12;
    int edges[nEdges][2] = {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, // bottom face
        {4, 5},
        {5, 6},
        {6, 7},
        {7, 4}, // top face
        {0, 4},
        {1, 5},
        {2, 6},
        {3, 7} // connecting edges
    };

    glBegin(GL_LINES);
    for (int i = 0; i < nEdges; i++)
    {
        int v1 = edges[i][0];
        int v2 = edges[i][1];
        glVertex3fv(cube[v1]);
        glVertex3fv(cube[v2]);
    }
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // Position camera
    float camX = cameraDistance * sin(cameraAngleY * M_PI / 180.0) * cos(cameraAngleX * M_PI / 180.0);
    float camY = cameraDistance * sin(cameraAngleX * M_PI / 180.0);
    float camZ = cameraDistance * cos(cameraAngleY * M_PI / 180.0) * cos(cameraAngleX * M_PI / 180.0);

    gluLookAt(camX, camY, camZ, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    // Draw coordinate axes
    drawAxes();

    // Draw original cube in green wireframe
    glColor3f(0.0, 0.8, 0.0);
    drawCube(originalCube);

    // Draw transformed cube in red wireframe
    glColor3f(1.0, 0.0, 0.0);
    drawCube(transformedCube);

    glutSwapBuffers();
}

void keyboard(unsigned char key, int x, int y)
{
    switch (key)
    {
    case 27: // ESC key
        exit(0);
        break;

    // Camera controls
    case 'w':
        cameraAngleX += 5.0;
        break;
    case 's':
        cameraAngleX -= 5.0;
        break;
    case 'a':
        cameraAngleY += 5.0;
        break;
    case 'd':
        cameraAngleY -= 5.0;
        break;
    case '+':
        cameraDistance -= 0.5;
        break;
    case '-':
        cameraDistance += 0.5;
        break;
    }

    glutPostRedisplay();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(800, 800);
    glutCreateWindow("3D Transformations");
    init();
    glutDisplayFunc(display);
    glutKeyboardFunc(keyboard);

    cout << "\nCamera Controls:" << endl;
    cout << "W/S: Rotate camera up/down" << endl;
    cout << "A/D: Rotate camera left/right" << endl;
    cout << "+/-: Zoom in/out" << endl;
    cout << "ESC: Exit" << endl;

    glutMainLoop();
    return 0;
}
