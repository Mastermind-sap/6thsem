#include <GLFW/glfw3.h>
#include <cmath>
#include <iostream>

void drawPix(float x, float y) {
    glBegin(GL_POINTS);
    glVertex2f(x / 300.0f, y / 300.0f);
    glEnd();
}

void drawLineBres(int x1, int y1, int x2, int y2) {
    int dx = std::abs(x2 - x1), dy = std::abs(y2 - y1);
    int sx = (x2 > x1) ? 1 : -1, sy = (y2 > y1) ? 1 : -1;
    int err = dx - dy, e2;
    int x = x1, y = y1;
    while (true) {
        drawPix(x, y);
        if (x == x2 && y == y2) break;
        e2 = 2 * err;
        if (e2 > -dy) { err -= dy; x += sx; }
        if (e2 < dx) { err += dx; y += sy; }
    }
}

void drawScn(int x1, int y1, int x2, int y2) {
    glClear(GL_COLOR_BUFFER_BIT);
    glLineWidth(2); glColor3f(0, 0, 1);
    glBegin(GL_LINES);
    glVertex2f(-1, 0); glVertex2f(1, 0);
    glVertex2f(0, -1); glVertex2f(0, 1);
    glEnd();
    glColor3f(1, 0, 0); glPointSize(15);
    drawLineBres(x1, y1, x2, y2);
}

int main() {
    int x1, y1, x2, y2;
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
