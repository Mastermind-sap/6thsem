#include <GLFW/glfw3.h>
#include <iostream>

const int W = 500, H = 500;

void rend() {
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(1, 0, 0); glPointSize(1005);
    glBegin(GL_POINTS);
    glVertex2f(10.0f / W * 2 - 1, 10.0f / H * 2 - 1);
    glVertex2f(150.0f / W * 2 - 1, 80.0f / H * 2 - 1);
    glVertex2f(100.0f / W * 2 - 1, 20.0f / H * 2 - 1);
    glVertex2f(200.0f / W * 2 - 1, 100.0f / H * 2 - 1);
    glEnd();
}

int main() {
    glfwInit();
    GLFWwindow *win = glfwCreateWindow(W, H, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    glfwMakeContextCurrent(win);
    glClearColor(1, 1, 1, 1);
    while (!glfwWindowShouldClose(win)) {
        rend();
        glfwSwapBuffers(win);
        glfwPollEvents();
    }
    glfwDestroyWindow(win);
    glfwTerminate();
    return 0;
}
