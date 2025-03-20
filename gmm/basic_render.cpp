// simple3d_lighting_color_interactive.cpp
//
// Compile (on Linux/macOS):
//   g++ simple3d_lighting_color_interactive.cpp -lglfw -lGL -lGLU -lm -o simple3d_lighting_color_interactive
//
// On Windows, link against glfw3, OpenGL32, and glu32.

#include <GLFW/glfw3.h>
#ifdef __APPLE__
#include <OpenGL/glu.h>
#else
#include <GL/glu.h>
#endif
#include <cmath>

// Global variables for camera angles (in degrees)
float camAngleX = 0.0f;
float camAngleY = 0.0f;
const float PI = 3.14159265358979323846f;

// Key callback to update camera angles using arrow keys.
void key_callback(GLFWwindow* window, int key, int scancode, int action, int mods) {
    const float angleStep = 5.0f;
    if (action == GLFW_PRESS || action == GLFW_REPEAT) {
        switch(key) {
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

// Draw a cube centered at the origin with given size.
void drawCube(float size) {
    float half = size / 2.0f;
    glBegin(GL_QUADS);
    // Front face (red)
    glColor3f(1.0f, 0.0f, 0.0f);
    glNormal3f(0.0f, 0.0f, 1.0f);
    glVertex3f(-half, -half,  half);
    glVertex3f( half, -half,  half);
    glVertex3f( half,  half,  half);
    glVertex3f(-half,  half,  half);
    // Back face (green)
    glColor3f(0.0f, 1.0f, 0.0f);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(-half, -half, -half);
    glVertex3f(-half,  half, -half);
    glVertex3f( half,  half, -half);
    glVertex3f( half, -half, -half);
    // Left face (blue)
    glColor3f(0.0f, 0.0f, 1.0f);
    glNormal3f(-1.0f, 0.0f, 0.0f);
    glVertex3f(-half, -half, -half);
    glVertex3f(-half, -half,  half);
    glVertex3f(-half,  half,  half);
    glVertex3f(-half,  half, -half);
    // Right face (yellow)
    glColor3f(1.0f, 1.0f, 0.0f);
    glNormal3f(1.0f, 0.0f, 0.0f);
    glVertex3f(half, -half, -half);
    glVertex3f(half,  half, -half);
    glVertex3f(half,  half,  half);
    glVertex3f(half, -half,  half);
    // Top face (magenta)
    glColor3f(1.0f, 0.0f, 1.0f);
    glNormal3f(0.0f, 1.0f, 0.0f);
    glVertex3f(-half,  half, -half);
    glVertex3f(-half,  half,  half);
    glVertex3f( half,  half,  half);
    glVertex3f( half,  half, -half);
    // Bottom face (cyan)
    glColor3f(0.0f, 1.0f, 1.0f);
    glNormal3f(0.0f, -1.0f, 0.0f);
    glVertex3f(-half, -half, -half);
    glVertex3f( half, -half, -half);
    glVertex3f( half, -half,  half);
    glVertex3f(-half, -half,  half);
    glEnd();
}

// Draw a pyramid with a square base centered at the origin.
void drawPyramid(float size) {
    float half = size / 2.0f;
    glBegin(GL_TRIANGLES);
    // Front face (red)
    glColor3f(1.0f, 0.0f, 0.0f);
    glNormal3f(0.0f, 0.707f, 0.707f);
    glVertex3f(0.0f,  half, 0.0f);
    glVertex3f(-half, -half,  half);
    glVertex3f( half, -half,  half);
    // Right face (green)
    glColor3f(0.0f, 1.0f, 0.0f);
    glNormal3f(0.707f, 0.707f, 0.0f);
    glVertex3f(0.0f,  half, 0.0f);
    glVertex3f( half, -half,  half);
    glVertex3f( half, -half, -half);
    // Back face (blue)
    glColor3f(0.0f, 0.0f, 1.0f);
    glNormal3f(0.0f, 0.707f, -0.707f);
    glVertex3f(0.0f,  half, 0.0f);
    glVertex3f( half, -half, -half);
    glVertex3f(-half, -half, -half);
    // Left face (yellow)
    glColor3f(1.0f, 1.0f, 0.0f);
    glNormal3f(-0.707f, 0.707f, 0.0f);
    glVertex3f(0.0f,  half, 0.0f);
    glVertex3f(-half, -half, -half);
    glVertex3f(-half, -half,  half);
    glEnd();

    // Draw the square base (gray)
    glBegin(GL_QUADS);
    glColor3f(0.5f, 0.5f, 0.5f);
    glNormal3f(0.0f, -1.0f, 0.0f);
    glVertex3f(-half, -half,  half);
    glVertex3f( half, -half,  half);
    glVertex3f( half, -half, -half);
    glVertex3f(-half, -half, -half);
    glEnd();
}

// Updated drawSphere function with normals for proper lighting.
void drawSphere(float radius, int stacks, int slices) {
    for (int i = 0; i < stacks; i++) {
        float lat0 = PI * (float)i / stacks;
        float lat1 = PI * (float)(i + 1) / stacks;
        float sinLat0 = sin(lat0);
        float cosLat0 = cos(lat0);
        float sinLat1 = sin(lat1);
        float cosLat1 = cos(lat1);

        glBegin(GL_QUAD_STRIP);
        for (int j = 0; j <= slices; j++) {
            float lon = 2 * PI * (float)j / slices;
            float sinLon = sin(lon);
            float cosLon = cos(lon);

            // First vertex of the quad
            float x0 = sinLat0 * cosLon;
            float y0 = cosLat0;
            float z0 = sinLat0 * sinLon;
            glNormal3f(x0, y0, z0);
            glColor3f((x0 + 1.0f) / 2.0f, (y0 + 1.0f) / 2.0f, (z0 + 1.0f) / 2.0f);
            glVertex3f(radius * x0, radius * y0, radius * z0);

            // Second vertex of the quad
            float x1 = sinLat1 * cosLon;
            float y1 = cosLat1;
            float z1 = sinLat1 * sinLon;
            glNormal3f(x1, y1, z1);
            glColor3f((x1 + 1.0f) / 2.0f, (y1 + 1.0f) / 2.0f, (z1 + 1.0f) / 2.0f);
            glVertex3f(radius * x1, radius * y1, radius * z1);
        }
        glEnd();
    }
}

int main(void) {
    // Initialize GLFW.
    if (!glfwInit())
        return -1;

    // Create a windowed mode window and its OpenGL context.
    GLFWwindow* window = glfwCreateWindow(800, 600, "3D Scene with Interactive Camera", NULL, NULL);
    if (!window) {
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);

    // Set the key callback for camera interaction.
    glfwSetKeyCallback(window, key_callback);

    // Enable depth testing.
    glEnable(GL_DEPTH_TEST);
    
    // Enable lighting and set up a default light source.
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    GLfloat lightPos[]    = {5.0f, 5.0f, 5.0f, 1.0f};
    GLfloat lightAmbient[]= {0.2f, 0.2f, 0.2f, 1.0f};
    GLfloat lightDiffuse[]= {0.8f, 0.8f, 0.8f, 1.0f};
    glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
    glLightfv(GL_LIGHT0, GL_AMBIENT,  lightAmbient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE,  lightDiffuse);

    // Enable color tracking so glColor calls affect the material properties.
    glEnable(GL_COLOR_MATERIAL);
    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

    // Main render loop.
    while (!glfwWindowShouldClose(window)) {
        int width, height;
        glfwGetFramebufferSize(window, &width, &height);
        float ratio = width / (float) height;
        glViewport(0, 0, width, height);

        // Clear the color and depth buffers.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Set up the projection matrix.
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45.0, ratio, 1.0, 100.0);

        // Compute camera position using spherical coordinates.
        float camDistance = 10.0f;
        float radAngleX = camAngleX * PI / 180.0f;
        float radAngleY = camAngleY * PI / 180.0f;
        float camX = camDistance * sin(radAngleY) * cos(radAngleX);
        float camY = camDistance * sin(radAngleX);
        float camZ = camDistance * cos(radAngleY) * cos(radAngleX);

        // Set up the model-view matrix.
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        gluLookAt(camX, camY, camZ,  // eye position (camera)
                  0.0, 0.0, 0.0,     // look-at point
                  0.0, 1.0, 0.0);    // up vector

        // Render primitives.
        // Draw cube (translated left)
        glPushMatrix();
        glTranslatef(-3.0f, 0.0f, 0.0f);
        drawCube(2.0f);
        glPopMatrix();

        // Draw sphere (centered)
        glPushMatrix();
        glTranslatef(0.0f, 0.0f, 0.0f);
        drawSphere(1.0f, 20, 20);
        glPopMatrix();

        // Draw pyramid (translated right)
        glPushMatrix();
        glTranslatef(3.0f, 0.0f, 0.0f);
        drawPyramid(2.0f);
        glPopMatrix();

        // Swap buffers and poll events.
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glfwTerminate();
    return 0;
}
