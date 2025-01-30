#include <GLFW/glfw3.h>

void disp() {
    glClear(GL_COLOR_BUFFER_BIT);
    glBegin(GL_POLYGON);
    glColor3f(1, 0, 0); glVertex3f(-0.6, -0.75, 0.5);
    glColor3f(0, 1, 0); glVertex3f(0.6, -0.75, 0);
    glColor3f(0, 0, 1); glVertex3f(0, 0.75, 0);
    glEnd();
    glFlush();
}

int main() {
    glfwInit();
    GLFWwindow *win = glfwCreateWindow(500, 500, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    glfwMakeContextCurrent(win);
    glClearColor(1, 1, 1, 1);
    while (!glfwWindowShouldClose(win)) {
        disp();
        glfwSwapBuffers(win);
        glfwPollEvents();
    }
    glfwDestroyWindow(win);
    glfwTerminate();
    return 0;
}
