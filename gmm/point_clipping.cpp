#include <GLFW/glfw3.h>
#include <iostream>

float xwmin = -0.5f, xwmax = 0.5f;
float ywmin = -0.5f, ywmax = 0.5f;

float px = 0.3f, py = 0.2f; // Change these values to test different points

bool isInside(float x, float y) {
    return (x >= xwmin && x <= xwmax && y >= ywmin && y <= ywmax);
}

void drawWindow() {
    glColor3f(1.0f, 0.0f, 0.0f); // res color
    glLineWidth(3);
    glBegin(GL_LINE_LOOP);
    glVertex2f(xwmin, ywmin);
    glVertex2f(xwmax, ywmin);
    glVertex2f(xwmax, ywmax);
    glVertex2f(xwmin, ywmax);
    glEnd();
}
void drawAxes() {
    glLineWidth(1.0f);
    glBegin(GL_LINES);
    
    glColor3f(0, 0, 0); 

    glVertex2f(-1.0f, 0.0f);
    glVertex2f(1.0f, 0.0f);

    glVertex2f(0.0f, -1.0f);
    glVertex2f(0.0f, 1.0f);

    glEnd();
}

void drawPoint(float x, float y) {
    glColor3f(0.0f, 0.0f, 0.0f); // Red color for point
    glPointSize(6);
    glBegin(GL_POINTS);
    glVertex2f(x, y);
    glEnd();
}

int main() {
    if (!glfwInit()) {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow* window = glfwCreateWindow(800, 600, "Saptarshi Adhikari 2212072", NULL, NULL);
    if (!window) {
        std::cerr << "Failed to create window\n";
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);
    glOrtho(-1, 1, -1, 1, -1, 1);  // Set orthographic projection

    bool inside = isInside(px, py);
    std::cout << std::boolalpha << inside << std::endl;  // Print true/false
    glClearColor(1, 1, 1, 1);

    while (!glfwWindowShouldClose(window)) {
        glClear(GL_COLOR_BUFFER_BIT);
        
        drawAxes();
        drawWindow();
        drawPoint(px, py);

        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}