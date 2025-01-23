#include <GLFW/glfw3.h>
#include <iostream>

void drawScene() {
    glClear(GL_COLOR_BUFFER_BIT);

    // Outer rectangle (window boundary)
    glColor3f(0.0, 0.0, 1.0); // Blue
    glBegin(GL_LINE_LOOP);
    glVertex2f(-0.9f, -0.9f);
    glVertex2f(0.9f, -0.9f);
    glVertex2f(0.9f, 0.9f);
    glVertex2f(-0.9f, 0.9f);
    glEnd();

    // Inner rectangle (red edges)
    glColor3f(1.0, 0.0, 0.0); // Red
    glBegin(GL_POLYGON);
    glColor3f(1, 0, 0);
    glVertex2f(-0.5f, -0.5f);
    glColor3f(0, 0, 1);
    glVertex2f(0.5f, -0.5f);
    glColor3f(1, 0, 0);
    glVertex2f(0.5f, 0.5f);
    glColor3f(1, 0, 0);
    glVertex2f(-0.5f, 0.5f);
    glEnd();
}

int main() {
    if (!glfwInit()) {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow* window = glfwCreateWindow(500, 500, "Rectangular Window with Inner Rectangle", nullptr, nullptr);
    if (!window) {
        std::cerr << "Failed to create GLFW window\n";
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);

    while (!glfwWindowShouldClose(window)) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // White background
        drawScene();
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}
