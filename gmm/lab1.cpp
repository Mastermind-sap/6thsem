#include <GLFW/glfw3.h>
#include <iostream>

void drawScn()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glLineWidth(5); 
    glColor3f(0, 0, 1);
    glBegin(GL_LINE_LOOP);
    glVertex2f(-0.9f, -0.9f);
    glVertex2f(0.9f, -0.9f);
    glVertex2f(0.9f, 0.9f);
    glVertex2f(-0.9f, 0.9f);
    glEnd();
    glColor3f(1, 0, 0);
    glBegin(GL_LINE_LOOP);
    glVertex2f(-0.5f, -0.5f);
    glVertex2f(0.5f, -0.5f);
    glVertex2f(0.5f, 0.5f);
    glVertex2f(-0.5f, 0.5f);
    glEnd();
}

int main()
{
    glfwInit();
    GLFWwindow *win = glfwCreateWindow(500, 500, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    glfwMakeContextCurrent(win);
    glClearColor(1, 1, 1, 1);
    while (!glfwWindowShouldClose(win))
    {
        drawScn();
        glfwSwapBuffers(win);
        glfwPollEvents();
    }
    glfwDestroyWindow(win);
    glfwTerminate();
    return 0;
}