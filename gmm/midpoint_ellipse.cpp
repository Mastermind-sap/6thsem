#include <GLFW/glfw3.h>
#include <cmath>
#include <iostream>

const unsigned int SCR_WIDTH = 800;
const unsigned int SCR_HEIGHT = 600;
int a = 200, b = 100;

void drawAxes()
{
    glColor3f(0.0f, 0.0f, 0.0f);
    glLineWidth(2.0f);
    glBegin(GL_LINES);
    glVertex2f(-1.0f, 0.0f);
    glVertex2f(1.0f, 0.0f); // X-axis
    glVertex2f(0.0f, -1.0f);
    glVertex2f(0.0f, 1.0f); // Y-axis
    glEnd();
}

void drawArc(float cx, float cy, bool flip)
{
    int x = 0, y = b;
    float a2 = a * a, b2 = b * b;
    float d1 = b2 - (a2 * b) + (0.25 * a2);

    while ((2 * b2 * x) <= (2 * a2 * y))
    {
        glVertex2f(cx + (flip ? -y : x) / 300.0f, cy + (flip ? -x : y) / 300.0f);
        if (d1 < 0)
            d1 += b2 * (2 * x + 3);
        else
        {
            d1 += b2 * (2 * x + 3) + a2 * (-2 * y + 2);
            y--;
        }
        x++;
    }

    float d2 = b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2;
    while (y > 0)
    {
        glVertex2f(cx + (flip ? -y : x) / 300.0f, cy + (flip ? -x : y) / 300.0f);
        if (d2 > 0)
            d2 += a2 * (-2 * y + 3);
        else
        {
            d2 += b2 * (2 * x + 2) + a2 * (-2 * y + 3);
            x++;
        }
        y--;
    }
}

void drawFilledArc(float cx, float cy)
{
    glColor3f(1.0f, 0.0f, 0.0f);
    glBegin(GL_TRIANGLE_FAN);
    glVertex2f(cx, cy);
    drawArc(cx, cy, false);
    glEnd();

    glColor3f(0.0f, 1.0f, 0.0f);
    glBegin(GL_TRIANGLE_FAN);
    glVertex2f(cx, cy);
    drawArc(cx, cy, true);
    glEnd();
}

void renderScene()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    drawFilledArc(0.0f, 0.0f);
    glfwSwapBuffers(glfwGetCurrentContext());
}

int main()
{
    if (!glfwInit())
    {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow *window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    if (!window)
    {
        std::cerr << "Failed to create GLFW window\n";
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);
    glClearColor(1, 1, 1, 1);

    while (!glfwWindowShouldClose(window))
    {
        renderScene();
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}