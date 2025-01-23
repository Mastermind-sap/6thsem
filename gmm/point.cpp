#include <GLFW/glfw3.h>
#include <iostream>

// Window dimensions
const int WIDTH = 500;
const int HEIGHT = 500;

// Function to render points
void render()
{
    glClear(GL_COLOR_BUFFER_BIT); // Clear the color buffer

    glColor3f(1.0f, 0.0f, 0.0f); // Set point color to red
    glPointSize(1005.0f);           // Set point size

    glBegin(GL_POINTS);
    glVertex2f(10.0f / WIDTH * 2 - 1, 10.0f / HEIGHT * 2 - 1); // Normalized device coordinates
    glVertex2f(150.0f / WIDTH * 2 - 1, 80.0f / HEIGHT * 2 - 1);
    glVertex2f(100.0f / WIDTH * 2 - 1, 20.0f / HEIGHT * 2 - 1);
    glVertex2f(200.0f / WIDTH * 2 - 1, 100.0f / HEIGHT * 2 - 1);
    glEnd();
}

int main()
{
    // Initialize GLFW
    if (!glfwInit())
    {
        std::cerr << "Failed to initialize GLFW" << std::endl;
        return -1;
    }

    // Create a GLFW window
    GLFWwindow *window = glfwCreateWindow(WIDTH, HEIGHT, "Points", nullptr, nullptr);
    if (!window)
    {
        std::cerr << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);

    // Set clear color (white background)
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    // Main loop
    while (!glfwWindowShouldClose(window))
    {
        render();

        // Swap buffers and poll for events
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // Cleanup
    glfwDestroyWindow(window);
    glfwTerminate();

    return 0;
}
