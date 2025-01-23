#include <GLFW/glfw3.h>

void display()
{

    glClear(GL_COLOR_BUFFER_BIT);

    glBegin(GL_POLYGON);
    glColor3f(1, 0, 0);
    glVertex3f(-0.6, -0.75, 0.5);
    glColor3f(0, 1, 0);
    glVertex3f(0.6, -0.75, 0);
    glColor3f(0, 0, 1);
    glVertex3f(0, 0.75, 0);
    glEnd();

    glFlush();
}

int main(int argc, char **argv)
{

    glfwInit();
    GLFWwindow *window = glfwCreateWindow(500, 500, "Polygon", nullptr, nullptr);

    glfwMakeContextCurrent(window);

    // Set clear color (white background)
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    // Main loop
    while (!glfwWindowShouldClose(window))
    {
        display();

        // Swap buffers and poll for events
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // Cleanup
    glfwDestroyWindow(window);
    glfwTerminate();

    return 0;
}
