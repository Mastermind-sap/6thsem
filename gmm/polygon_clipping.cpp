#include <GLFW/glfw3.h>
#include <bits/stdc++.h>

using namespace std;

struct Point
{
    float x, y;
};

const float xmin = -0.5f, ymin = -0.5f, xmax = 0.5f, ymax = 0.5f;

vector<Point> polygon;

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

bool inside(Point p, int edge)
{
    switch (edge)
    {
    case 0:
        return p.x >= xmin;
    case 1:
        return p.x <= xmax;
    case 2:
        return p.y >= ymin;
    case 3:
        return p.y <= ymax;
    }
    return false;
}

Point intersect(Point p1, Point p2, int edge)
{
    Point intersection;
    float m = (p2.y - p1.y) / (p2.x - p1.x);

    switch (edge)
    {
    case 0:
        intersection.x = xmin;
        intersection.y = p1.y + m * (xmin - p1.x);
        break;
    case 1:
        intersection.x = xmax;
        intersection.y = p1.y + m * (xmax - p1.x);
        break;
    case 2:
        intersection.y = ymin;
        intersection.x = p1.x + (ymin - p1.y) / m;
        break;
    case 3:
        intersection.y = ymax;
        intersection.x = p1.x + (ymax - p1.y) / m;
        break;
    }
    return intersection;
}

vector<Point> sutherlandHodgmanClip(const vector<Point> &inputPolygon)
{
    vector<Point> clippedPolygon = inputPolygon;

    for (int edge = 0; edge < 4; edge++)
    {
        vector<Point> newPolygon;
        int n = clippedPolygon.size();

        for (int i = 0; i < n; i++)
        {
            Point p1 = clippedPolygon[i];
            Point p2 = clippedPolygon[(i + 1) % n];

            bool insideP1 = inside(p1, edge);
            bool insideP2 = inside(p2, edge);

            if (insideP1 && insideP2)
            {
                newPolygon.push_back(p2);
            }
            else if (insideP1 && !insideP2)
            {
                newPolygon.push_back(intersect(p1, p2, edge));
            }
            else if (!insideP1 && insideP2)
            {
                newPolygon.push_back(intersect(p1, p2, edge));
                newPolygon.push_back(p2);
            }
        }
        clippedPolygon = newPolygon;
    }

    return clippedPolygon;
}

void drawPolygon(const vector<Point> &polygon, float r, float g, float b, float thickness)
{
    glLineWidth(thickness);
    glColor3f(r, g, b);
    glBegin(GL_LINE_LOOP);
    for (const auto &p : polygon)
        glVertex2f(p.x, p.y);
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);

    glLineWidth(3);
    glColor3f(0, 0, 0);
    glBegin(GL_LINE_LOOP);
    glVertex2f(xmin, ymin);
    glVertex2f(xmax, ymin);
    glVertex2f(xmax, ymax);
    glVertex2f(xmin, ymax);
    glEnd();

    drawPolygon(polygon, 1, 0, 0, 3);
    vector<Point> clippedPolygon = sutherlandHodgmanClip(polygon);
    drawPolygon(clippedPolygon, 0, 1, 0, 3);

    drawAxes();
    glFlush();
}

int main()
{
    glfwInit();
    GLFWwindow *window = glfwCreateWindow(600, 600, "Saptarshi Adhikari 2212072", NULL, NULL);
    glfwMakeContextCurrent(window);
    glClearColor(1, 1, 1, 1);
    glOrtho(-1, 1, -1, 1, -1, 1);

    int n;
    cin >> n;
    polygon.resize(n);
    for (int j = 0; j < n; j++)
    {
        float x, y;
        cin >> x >> y;
        polygon[j].x = x / 100;
        polygon[j].y = y / 100;
    }

    while (!glfwWindowShouldClose(window))
    {
        display();
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glfwDestroyWindow(window);
    glfwTerminate();
    return 0;
}