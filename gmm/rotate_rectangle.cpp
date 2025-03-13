#include <GLFW/glfw3.h>
#include <iostream>
#include <cmath>

float rectangle[4][2] = {{10, 20}, {30, 20}, {30, 40}, {10, 40}};
float transformed[4][2];
float pivotPoint[2];

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

void drawRectangle(float vertices[4][2], float r, float g, float b)
{
    glColor3f(r, g, b);
    glBegin(GL_QUADS);
    for (int i = 0; i < 4; ++i)
        glVertex2f(vertices[i][0] / 50, vertices[i][1] / 50);
    glEnd();
}

void drawPivotPoint(float x, float y)
{
    glColor3f(0.0f, 0.0f, 1.0f);
    glPointSize(5.0f);
    glBegin(GL_POINTS);
    glVertex2f(x / 50, y / 50);
    glEnd();
}

void translateToOrigin(float pivotX, float pivotY)
{
    for (int i = 0; i < 4; ++i)
    {
        transformed[i][0] = rectangle[i][0] - pivotX;
        transformed[i][1] = rectangle[i][1] - pivotY;
    }
}

void rotateAboutOrigin(float angle)
{
    float rad = angle * M_PI / 180.0; // Convert angle to radians
    for (int i = 0; i < 4; ++i)
    {
        float x = transformed[i][0];
        
        float y = transformed[i][1];
        transformed[i][0] = x * cos(rad) - y * sin(rad);
        transformed[i][1] = x * sin(rad) + y * cos(rad);
    }
}

void translateBack(float pivotX, float pivotY)
{
    for (int i = 0; i < 4; ++i)
    {
        transformed[i][0] += pivotX;
        transformed[i][1] += pivotY;
    }
}

void rotateAroundPivot(float pivotX, float pivotY, float angle)
{
    translateToOrigin(pivotX, pivotY);
    rotateAboutOrigin(angle);
    translateBack(pivotX, pivotY);
}

void renderScene()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    drawRectangle(rectangle, 1.0f, 0.0f, 0.0f);   // Original rectangle
    drawRectangle(transformed, 0.0f, 1.0f, 0.0f); // Transformed rectangle
    drawPivotPoint(pivotPoint[0], pivotPoint[1]); // Pivot point
    glfwSwapBuffers(glfwGetCurrentContext());
}

void menu()
{
    float angle;
    std::cout << "Enter pivot point (px, py): ";
    std::cin >> pivotPoint[0] >> pivotPoint[1];
    std::cout << "Enter angle of rotation: ";
    std::cin >> angle;
    rotateAroundPivot(pivotPoint[0], pivotPoint[1], angle);
    renderScene();
}

int main()
{
    if (!glfwInit())
    {
        std::cerr << "Failed to initialize GLFW\n";
        return -1;
    }

    GLFWwindow *window = glfwCreateWindow(800, 800, "Saptarshi Adhikari 2212072", nullptr, nullptr);
    if (!window)
    {
        std::cerr << "Failed to create GLFW window\n";
        glfwTerminate();
        return -1;
    }

    glfwMakeContextCurrent(window);
    glClearColor(1, 1, 1, 1);

    menu();

    while (!glfwWindowShouldClose(window))
    {
        renderScene();
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}
