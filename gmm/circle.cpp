#include <GLFW/glfw3.h>
#include <cmath>
#include <iostream> 

const float RADIUS = 0.4f;

void drawQuadrant(float startAngle, float endAngle, float r, float g, float b) {
    glBegin(GL_TRIANGLE_FAN);
    glColor3f(r, g, b);
    glVertex2f(0.0f, 0.0f); 

    for (int i = startAngle; i <= endAngle; i++) {
        float theta = i * M_PI / 180.0f; 
        glVertex2f(RADIUS * cos(theta), RADIUS * sin(theta)); 
    }
    glEnd();
}

void renderScene() {
    glClear(GL_COLOR_BUFFER_BIT);
    
    glPushMatrix();
    glTranslatef(0.0f, 0.0f, 0.0f);

    drawQuadrant(0, 90, 0.052, 0.235, 0.235);    
    drawQuadrant(90, 180, 0.023, 0.80, 0.231);   
    drawQuadrant(180, 270, 0.443, 0.236, .123);  
    drawQuadrant(270, 360, .123, .091, .021);  

    glPopMatrix();

    glfwSwapBuffers(glfwGetCurrentContext());
}

int main() {
    if (!glfwInit()) {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow* window = glfwCreateWindow(600, 600, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    if (!window) {
        std::cerr << "Failed to create GLFW window\n";
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);

    glClearColor(1, 1, 1, 1); 

    while (!glfwWindowShouldClose(window)) {
        renderScene();
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}
