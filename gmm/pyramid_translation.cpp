#include <GLFW/glfw3.h>
#ifdef __APPLE__
#include <OpenGL/glu.h>
#else
#include <GL/glu.h>
#endif
#include <cmath>
#include <iostream>

// Global variables for camera angles (in degrees)
float camAngleX = 30.0f;
float camAngleY = 45.0f;
const float PI = 3.14159265358979323846f;

// Pyramid vertices (base + apex)
float pyramid[5][3] = {
    {-1.0f, 0.0f, -1.0f}, // Base vertex 0
    {1.0f, 0.0f, -1.0f},  // Base vertex 1
    {1.0f, 0.0f, 1.0f},   // Base vertex 2
    {-1.0f, 0.0f, 1.0f},  // Base vertex 3
    {0.0f, 2.0f, 0.0f}    // Apex
};

float translated[5][3];

// Key callback to update camera angles using arrow keys
void key_callback(GLFWwindow *window, int key, int scancode, int action, int mods)
{
    const float angleStep = 5.0f;
    if (action == GLFW_PRESS || action == GLFW_REPEAT)
    {
        switch (key)
        {
        case GLFW_KEY_LEFT:
            camAngleY -= angleStep;
            break;
        case GLFW_KEY_RIGHT:
            camAngleY += angleStep;
            break;
        case GLFW_KEY_UP:
            camAngleX -= angleStep;
            break;
        case GLFW_KEY_DOWN:
            camAngleX += angleStep;
            break;
        }
    }
}

// Draw axes
void drawAxes()
{
    glLineWidth(2.0f);

    // X axis (red)
    glColor3f(1.0f, 0.0f, 0.0f);
    glBegin(GL_LINES);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(3.0f, 0.0f, 0.0f);
    glEnd();

    // Y axis (green)
    glColor3f(0.0f, 1.0f, 0.0f);
    glBegin(GL_LINES);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 3.0f, 0.0f);
    glEnd();

    // Z axis (blue)
    glColor3f(0.0f, 0.0f, 1.0f);
    glBegin(GL_LINES);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 0.0f, 3.0f);
    glEnd();
}

// Draw a pyramid
void drawPyramid(float vertices[5][3], float r, float g, float b)
{
    glColor3f(r, g, b);

    // Draw base (quad)
    glBegin(GL_QUADS);
    glVertex3fv(vertices[0]);
    glVertex3fv(vertices[1]);
    glVertex3fv(vertices[2]);
    glVertex3fv(vertices[3]);
    glEnd();

    // Draw 4 triangular faces
    glBegin(GL_TRIANGLES);

    // Front face
    glColor3f(r * 0.8f, g * 0.8f, b * 0.8f);
    glVertex3fv(vertices[0]);
    glVertex3fv(vertices[3]);
    glVertex3fv(vertices[4]);

    // Right face
    glColor3f(r * 0.6f, g * 0.6f, b * 0.6f);
    glVertex3fv(vertices[3]);
    glVertex3fv(vertices[2]);
    glVertex3fv(vertices[4]);

    // Back face
    glColor3f(r * 0.4f, g * 0.4f, b * 0.4f);
    glVertex3fv(vertices[2]);
    glVertex3fv(vertices[1]);
    glVertex3fv(vertices[4]);

    // Left face
    glColor3f(r * 0.2f, g * 0.2f, b * 0.2f);
    glVertex3fv(vertices[1]);
    glVertex3fv(vertices[0]);
    glVertex3fv(vertices[4]);

    glEnd();
}

// Apply translation
void translate(float tx, float ty, float tz)
{
    for (int i = 0; i < 5; ++i)
    {
        translated[i][0] = pyramid[i][0] + tx;
        translated[i][1] = pyramid[i][1] + ty;
        translated[i][2] = pyramid[i][2] + tz;
    }
}

void renderScene()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    int width, height;
    glfwGetFramebufferSize(glfwGetCurrentContext(), &width, &height);
    float ratio = width / (float)height;

    // Set up the projection matrix
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, ratio, 1.0, 100.0);

    // Compute camera position using spherical coordinates
    float camDistance = 10.0f;
    float radAngleX = camAngleX * PI / 180.0f;
    float radAngleY = camAngleY * PI / 180.0f;
    float camX = camDistance * sin(radAngleY) * cos(radAngleX);
    float camY = camDistance * sin(radAngleX);
    float camZ = camDistance * cos(radAngleY) * cos(radAngleX);

    // Set up the model-view matrix
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    gluLookAt(camX, camY, camZ, // eye position
              0.0, 0.0, 0.0,    // look-at point
              0.0, 1.0, 0.0);   // up vector

    drawAxes();

    // Draw original pyramid (red)
    drawPyramid(pyramid, 1.0f, 0.0f, 0.0f);

    // Draw translated pyramid (green)
    drawPyramid(translated, 0.0f, 1.0f, 0.0f);

    glfwSwapBuffers(glfwGetCurrentContext());
}

int main()
{
    if (!glfwInit())
    {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow *window = glfwCreateWindow(800, 600, "3D Pyramid Translation", nullptr, nullptr);
    if (!window)
    {
        std::cerr << "Failed to create GLFW window\n";
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);
    glfwSetKeyCallback(window, key_callback);

    // Enable depth testing
    glEnable(GL_DEPTH_TEST);

    // Get translation values from user
    float tx, ty, tz;
    std::cout << "Enter translation values (Tx Ty Tz): ";
    std::cin >> tx >> ty >> tz;
    translate(tx, ty, tz);

    // Print the original and translated coordinates
    std::cout << "\nOriginal Pyramid Coordinates:" << std::endl;
    for (int i = 0; i < 5; ++i)
    {
        std::cout << "Vertex " << i << ": (" << pyramid[i][0] << ", "
                  << pyramid[i][1] << ", " << pyramid[i][2] << ")" << std::endl;
    }

    std::cout << "\nTranslated Pyramid Coordinates:" << std::endl;
    for (int i = 0; i < 5; ++i)
    {
        std::cout << "Vertex " << i << ": (" << translated[i][0] << ", "
                  << translated[i][1] << ", " << translated[i][2] << ")" << std::endl;
    }

    // Render loop
    while (!glfwWindowShouldClose(window))
    {
        renderScene();
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}
