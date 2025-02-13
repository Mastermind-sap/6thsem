#include <GLFW/glfw3.h>
#include <cmath>
#include <iostream>

float triangle[3][2] = {{-0.6, -0.75}, {0.6, -0.75}, {0, 0.75}};
float transformed[3][2];

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

void drawTriangle(float vertices[3][2], float r, float g, float b)
{
    glColor3f(r, g, b);
    glBegin(GL_TRIANGLES);
    for (int i = 0; i < 3; ++i)
        glVertex2f(vertices[i][0] / 5, vertices[i][1] / 5);
    glEnd();
}

void renderScene()
{
    glClear(GL_COLOR_BUFFER_BIT);
    drawAxes();
    drawTriangle(triangle, 1.0f, 0.0f, 0.0f);    // Original triangle
    drawTriangle(transformed, 0.0f, 1.0f, 0.0f); // Transformed triangle
    glfwSwapBuffers(glfwGetCurrentContext());
}

void translate(float tx, float ty)
{
    for (int i = 0; i < 3; ++i)
    {
        transformed[i][0] = triangle[i][0] + tx;
        transformed[i][1] = triangle[i][1] + ty;
    }
}

void scale(float sx, float sy)
{
    for (int i = 0; i < 3; ++i)
    {
        transformed[i][0] = triangle[i][0] * sx;
        transformed[i][1] = triangle[i][1] * sy;
    }
}

void shear(float shx, float shy)
{
    for (int i = 0; i < 3; ++i)
    {
        transformed[i][0] = triangle[i][0] + shx * triangle[i][1];
        transformed[i][1] = triangle[i][1] + shy * triangle[i][0];
    }
}

void reflect(bool xAxis)
{
    for (int i = 0; i < 3; ++i)
    {
        transformed[i][0] = xAxis ? triangle[i][0] : -triangle[i][0];
        transformed[i][1] = xAxis ? -triangle[i][1] : triangle[i][1];
    }
}

void rotate(float angle)
{
    float rad = angle * M_PI / 180.0;
    for (int i = 0; i < 3; ++i)
    {
        transformed[i][0] = triangle[i][0] * cos(rad) - triangle[i][1] * sin(rad);
        transformed[i][1] = triangle[i][0] * sin(rad) + triangle[i][1] * cos(rad);
    }
}

void menu()
{
    int choice;
    float param1, param2;
    while (true)
    {
        std::cout << "Choose transformation:\n1. Translation\n2. Scaling\n3. Shearing\n4. Reflection\n5. Rotation\n";
        std::cin >> choice;
        switch (choice)
        {
        case 1:
            std::cout << "Enter tx and ty: ";
            std::cin >> param1 >> param2;
            translate(param1, param2);
            return;
        case 2:
            std::cout << "Enter sx and sy: ";
            std::cin >> param1 >> param2;
            scale(param1, param2);
            return;
        case 3:
            std::cout << "Enter shx and shy: ";
            std::cin >> param1 >> param2;
            shear(param1, param2);
            return;
        case 4:
            std::cout << "Reflect on X-axis (1) or Y-axis (0): ";
            std::cin >> param1;
            reflect(param1);
            return;
        case 5:
            std::cout << "Enter angle: ";
            std::cin >> param1;
            rotate(param1);
            return;
        default:
            std::cout << "Invalid choice\n";
        }
        renderScene();
    }
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
