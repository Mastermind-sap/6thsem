#include <GLFW/glfw3.h>
#include <iostream>
#include <vector>
using namespace std;

const float xmin = -0.5f, ymin = -0.5f, xmax = 0.5f, ymax = 0.5f;
const int INSIDE = 0, LEFT = 1, RIGHT = 2, BOTTOM = 4, TOP = 8;

int computeOutcode(float x, float y)
{
    int code = INSIDE;
    if (x < xmin)
        code |= LEFT;
    else if (x > xmax)
        code |= RIGHT;
    if (y < ymin)
        code |= BOTTOM;
    else if (y > ymax)
        code |= TOP;
    return code;
}

bool cohenSutherlandClip(float &x1, float &y1, float &x2, float &y2)
{
    int outcode1 = computeOutcode(x1, y1);
    int outcode2 = computeOutcode(x2, y2);
    bool accept = false;

    while (true)
    {
        if (!(outcode1 | outcode2))
        {
            accept = true;
            break;
        }
        else if (outcode1 & outcode2)
        {
            break;
        }
        else
        {
            float x, y;
            int outcodeOut = outcode1 ? outcode1 : outcode2;

            if (outcodeOut & TOP)
            {
                x = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
                y = ymax;
            }
            else if (outcodeOut & BOTTOM)
            {
                x = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
                y = ymin;
            }
            else if (outcodeOut & RIGHT)
            {
                y = y1 + (y2 - y1) * (xmax - x1) / (x2 - x1);
                x = xmax;
            }
            else if (outcodeOut & LEFT)
            {
                y = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
                x = xmin;
            }

            if (outcodeOut == outcode1)
            {
                x1 = x;
                y1 = y;
                outcode1 = computeOutcode(x1, y1);
            }
            else
            {
                x2 = x;
                y2 = y;
                outcode2 = computeOutcode(x2, y2);
            }
        }
    }
    return accept;
}

void drawLine(float x1, float y1, float x2, float y2, float r, float g, float b, float t)
{
    glLineWidth(t);
    glColor3f(r, g, b);
    glBegin(GL_LINES);
    glVertex2f(x1, y1);
    glVertex2f(x2, y2);
    glEnd();
}

void drawAxes()
{
    glBegin(GL_LINES);
    glColor3f(0.0f, 0.0f, 0.0f);
    glVertex2i(-300, 0);
    glVertex2i(300, 0);
    glVertex2i(0, -300);
    glVertex2i(0, 300);
    glEnd();
}

vector<vector<float>> lines;

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);

    drawAxes();

    drawLine(xmin, ymin, xmax, ymin, 0, 0, 0, 3);
    drawLine(xmax, ymin, xmax, ymax, 0, 0, 0, 3);
    drawLine(xmax, ymax, xmin, ymax, 0, 0, 0, 3);
    drawLine(xmin, ymax, xmin, ymin, 0, 0, 0, 3);

    for (const auto &line : lines)
    {
        drawLine(line[0], line[1], line[2], line[3], 1, 0, 0, 3);

        float x1 = line[0], y1 = line[1], x2 = line[2], y2 = line[3];
        if (cohenSutherlandClip(x1, y1, x2, y2))
        {
            drawLine(x1, y1, x2, y2, 0, 1, 0, 3);
        }
    }

    glFlush();
}

int main()
{
    glfwInit();
    GLFWwindow *w = glfwCreateWindow(600, 600, "Saptarshi Adhikari 2212072", NULL, NULL);
    glfwMakeContextCurrent(w);
    glClearColor(1, 1, 1, 1);
    glOrtho(-1, 1, -1, 1, -1, 1);
    for (int i = 0; i < 5; i++)
    {
        float x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        lines.push_back({x1 / 100, y1 / 100, x2 / 100, y2 / 100});
    }
    while (!glfwWindowShouldClose(w))
    {
        display();
        glfwSwapBuffers(w);
        glfwPollEvents();
    }
    glfwDestroyWindow(w);
    glfwTerminate();
    return 0;
}