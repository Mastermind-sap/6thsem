#include <GLFW/glfw3.h>
#include <cmath>
#include <iostream>

void drawPixel(float x, float y) {
    glBegin(GL_POINTS);
    glVertex2f(x / 300.0f, y / 300.0f); // Scale to normalized device coordinates
    glEnd();
}

void drawLineDDA(float x1, float y1, float x2, float y2) {
    float dx = x2 - x1, dy = y2 - y1;
    int steps = std::max(std::abs(dx), std::abs(dy));
    float xIncrement = dx / steps, yIncrement = dy / steps;

    float x = x1, y = y1;
    for (int i = 0; i <= steps; ++i) {
        drawPixel(x, y);
        x += xIncrement;
        y += yIncrement;
    }
}

void drawScene() {
    glClear(GL_COLOR_BUFFER_BIT);

    // Draw axes
    glLineWidth(2.0f); // Thicker axes
    glColor3f(0.0, 0.0, 1.0); // Blue
    glBegin(GL_LINES);
    glVertex2f(-1.0f, 0.0f); glVertex2f(1.0f, 0.0f); // X-axis
    glVertex2f(0.0f, -1.0f); glVertex2f(0.0f, 1.0f); // Y-axis
    glEnd();

    // Draw lines for testing
    glColor3f(1.0, 0.0, 0.0); // Red
    glPointSize(15.0f);        // Increase point size for DDA pixels
    drawLineDDA(-200, -100, 200, 100);  // m > 0
    drawLineDDA(-200, 0, 200, 0);       // m = 0
    drawLineDDA(200, 100, 200, -100);   // m < 0
}

int main() {
    if (!glfwInit()) {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow* window = glfwCreateWindow(600, 600, "DDA Line Drawing Algorithm", nullptr, nullptr);
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
