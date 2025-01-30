#include <GLFW/glfw3.h>
#include <cmath>
#include <iostream>

void drawPix(float x, float y) {
    glBegin(GL_POINTS);
    glVertex2f(x / 300.0f, y / 300.0f);
    glEnd();
}

void drawLineDDA(float x1, float y1, float x2, float y2) {
    float dx = x2 - x1, dy = y2 - y1;
    int steps = std::max(std::abs(dx), std::abs(dy));
    float xInc = dx / steps, yInc = dy / steps;
    float x = x1, y = y1;
    for (int i = 0; i <= steps; ++i) {
        drawPix(x, y);
        x += xInc;
        y += yInc;
    }
}

void drawScn(float x1, float y1, float x2, float y2) {
    glClear(GL_COLOR_BUFFER_BIT);
    glLineWidth(2); glColor3f(0, 0, 1);
    glBegin(GL_LINES);
    glVertex2f(-1, 0); glVertex2f(1, 0);
    glVertex2f(0, -1); glVertex2f(0, 1);
    glEnd();
    glColor3f(1, 0, 0); glPointSize(15);
    drawLineDDA(x1, y1, x2, y2);
}

int main() {
    float x1, y1, x2, y2;
    std::cout << "Enter the initial end-point (x1, y1): ";
    std::cin >> x1 >> y1;
    std::cout << "Enter the final end-point (x2, y2): ";
    std::cin >> x2 >> y2;

    glfwInit();
    GLFWwindow *win = glfwCreateWindow(600, 600, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    glfwMakeContextCurrent(win);
    glClearColor(1, 1, 1, 1);
    while (!glfwWindowShouldClose(win)) {
        drawScn(x1, y1, x2, y2);
        glfwSwapBuffers(win);
        glfwPollEvents();
    }
    glfwDestroyWindow(win);
    glfwTerminate();
    return 0;
}
