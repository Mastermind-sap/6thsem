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

// Cube vertices (8 corners)
float cube[8][3] = {
    {-1.0f, -1.0f, -1.0f}, // 0: bottom, left, back
    {1.0f, -1.0f, -1.0f},  // 1: bottom, right, back
    {1.0f, 1.0f, -1.0f},   // 2: top, right, back
    {-1.0f, 1.0f, -1.0f},  // 3: top, left, back
    {-1.0f, -1.0f, 1.0f},  // 4: bottom, left, front
    {1.0f, -1.0f, 1.0f},   // 5: bottom, right, front
    {1.0f, 1.0f, 1.0f},    // 6: top, right, front
    {-1.0f, 1.0f, 1.0f}    // 7: top, left, front
};

float rotated[8][3];

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

// Draw a cube
void drawCube(float vertices[8][3], float r, float g, float b)
{
    // Define the faces of the cube using vertex indices
    int faces[6][4] = {
        {0, 1, 2, 3}, // Back face
        {4, 5, 6, 7}, // Front face
        {0, 4, 7, 3}, // Left face
        {1, 5, 6, 2}, // Right face
        {3, 7, 6, 2}, // Top face
        {0, 1, 5, 4}  // Bottom face
    };

    float colors[6][3] = {
        {r * 0.8f, g * 0.8f, b * 0.8f},
        {r, g, b},
        {r * 0.6f, g * 0.6f, b * 0.6f},
        {r * 0.7f, g * 0.7f, b * 0.7f},
        {r * 0.5f, g * 0.5f, b * 0.5f},
        {r * 0.9f, g * 0.9f, b * 0.9f}};

    // Draw each face as a quad
    for (int i = 0; i < 6; ++i)
    {
        glColor3fv(colors[i]);
        glBegin(GL_QUADS);
        for (int j = 0; j < 4; ++j)
        {
            glVertex3fv(vertices[faces[i][j]]);
        }
        glEnd();
    }

    // Draw edges
    glColor3f(0.0f, 0.0f, 0.0f);
    glLineWidth(1.0f);

    // Define the edges of the cube using vertex indices
    int edges[12][2] = {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Back face edges
        {4, 5},
        {5, 6},
        {6, 7},
        {7, 4}, // Front face edges
        {0, 4},
        {1, 5},
        {2, 6},
        {3, 7} // Connecting edges
    };

    glBegin(GL_LINES);
    for (int i = 0; i < 12; ++i)
    {
        glVertex3fv(vertices[edges[i][0]]);
        glVertex3fv(vertices[edges[i][1]]);
    }
    glEnd();
}

// Apply X-axis rotation
void rotateX(float angle)
{
    float rad = angle * PI / 180.0f;
    float cosA = cos(rad);
    float sinA = sin(rad);

    for (int i = 0; i < 8; ++i)
    {
        // X coordinate doesn't change in X-axis rotation
        rotated[i][0] = cube[i][0];
        // Y and Z coordinates are transformed using the X-rotation matrix
        rotated[i][1] = cube[i][1] * cosA - cube[i][2] * sinA;
        rotated[i][2] = cube[i][1] * sinA + cube[i][2] * cosA;
    }
}

// Apply Y-axis rotation
void rotateY(float angle)
{
    float rad = angle * PI / 180.0f;
    float cosA = cos(rad);
    float sinA = sin(rad);

    for (int i = 0; i < 8; ++i)
    {
        // X and Z coordinates are transformed using the Y-rotation matrix
        rotated[i][0] = cube[i][0] * cosA + cube[i][2] * sinA;
        // Y coordinate doesn't change in Y-axis rotation
        rotated[i][1] = cube[i][1];
        rotated[i][2] = -cube[i][0] * sinA + cube[i][2] * cosA;
    }
}

// Apply Z-axis rotation
void rotateZ(float angle)
{
    float rad = angle * PI / 180.0f;
    float cosA = cos(rad);
    float sinA = sin(rad);

    for (int i = 0; i < 8; ++i)
    {
        // X and Y coordinates are transformed using the Z-rotation matrix
        rotated[i][0] = cube[i][0] * cosA - cube[i][1] * sinA;
        rotated[i][1] = cube[i][0] * sinA + cube[i][1] * cosA;
        // Z coordinate doesn't change in Z-axis rotation
        rotated[i][2] = cube[i][2];
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

    // Draw original cube (red)
    drawCube(cube, 1.0f, 0.0f, 0.0f);

    // Draw rotated cube (blue)
    drawCube(rotated, 0.0f, 0.0f, 1.0f);

    glfwSwapBuffers(glfwGetCurrentContext());
}

int main()
{
    if (!glfwInit())
    {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow *window = glfwCreateWindow(800, 600, "3D Object Rotation", nullptr, nullptr);
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

    // Get rotation information from user
    int axis;
    float angle;

    std::cout << "Select rotation axis (1=X, 2=Y, 3=Z): ";
    std::cin >> axis;

    std::cout << "Enter rotation angle in degrees: ";
    std::cin >> angle;

    // Apply the appropriate rotation
    switch (axis)
    {
    case 1:
        rotateX(angle);
        std::cout << "Applying X-axis rotation of " << angle << " degrees" << std::endl;
        break;
    case 2:
        rotateY(angle);
        std::cout << "Applying Y-axis rotation of " << angle << " degrees" << std::endl;
        break;
    case 3:
        rotateZ(angle);
        std::cout << "Applying Z-axis rotation of " << angle << " degrees" << std::endl;
        break;
    default:
        std::cout << "Invalid axis, applying Y-axis rotation" << std::endl;
        rotateY(angle);
        break;
    }

    // Print the original and rotated coordinates
    std::cout << "\nOriginal Cube Coordinates:" << std::endl;
    for (int i = 0; i < 8; ++i)
    {
        std::cout << "Vertex " << i << ": (" << cube[i][0] << ", "
                  << cube[i][1] << ", " << cube[i][2] << ")" << std::endl;
    }

    std::cout << "\nRotated Cube Coordinates:" << std::endl;
    for (int i = 0; i < 8; ++i)
    {
        std::cout << "Vertex " << i << ": (" << rotated[i][0] << ", "
                  << rotated[i][1] << ", " << rotated[i][2] << ")" << std::endl;
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
