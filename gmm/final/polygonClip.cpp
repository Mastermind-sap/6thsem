#include <GLUT/glut.h>
#include <vector>
#include <utility>

using namespace std;

typedef pair<float, float> Point;
vector<Point> polygon = {{-4, -2}, {-2, 2}, {2, 2}, {4, -2}}; // Example polygon
float xmin = -3, ymin = -1, xmax = 3, ymax = 1;               // Clipping window

vector<Point> clipPolygon(vector<Point> &poly, float x1, float y1, float x2, float y2)
{
    vector<Point> clipped;
    int n = poly.size();

    for (int i = 0; i < n; i++)
    {
        Point curr = poly[i];
        Point prev = poly[(i - 1 + n) % n];

        // Compute positions relative to the clipping edge
        float currPos = (x2 - x1) * (curr.second - y1) - (y2 - y1) * (curr.first - x1);
        float prevPos = (x2 - x1) * (prev.second - y1) - (y2 - y1) * (prev.first - x1);

        // Case 1: Current point is inside
        if (currPos >= 0)
        {
            if (prevPos < 0)
            {
                // Add intersection point
                float t = prevPos / (prevPos - currPos);
                clipped.push_back({prev.first + t * (curr.first - prev.first), prev.second + t * (curr.second - prev.second)});
            }
            clipped.push_back(curr); // Add current point
        }
        // Case 2: Current point is outside
        else if (prevPos >= 0)
        {
            // Add intersection point
            float t = prevPos / (prevPos - currPos);
            clipped.push_back({prev.first + t * (curr.first - prev.first), prev.second + t * (curr.second - prev.second)});
        }
    }

    return clipped;
}

void sutherlandHodgman()
{
    vector<Point> clipped = polygon;

    // Clip against all edges of the clipping window
    clipped = clipPolygon(clipped, xmin, ymin, xmax, ymin); // Bottom edge
    clipped = clipPolygon(clipped, xmax, ymin, xmax, ymax); // Right edge
    clipped = clipPolygon(clipped, xmax, ymax, xmin, ymax); // Top edge
    clipped = clipPolygon(clipped, xmin, ymax, xmin, ymin); // Left edge

    polygon = clipped;
}

void init()
{
    glClearColor(0, 0, 0, 1);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-5, 5, -5, 5);
}

void drawPolygon(const vector<Point> &poly, float r, float g, float b)
{
    glColor3f(r, g, b);
    glBegin(GL_LINE_LOOP);
    for (const auto &p : poly)
    {
        glVertex2f(p.first, p.second);
    }
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);

    // Draw clipping window
    glColor3f(1, 0, 0);
    glBegin(GL_LINE_LOOP);
    glVertex2f(xmin, ymin);
    glVertex2f(xmax, ymin);
    glVertex2f(xmax, ymax);
    glVertex2f(xmin, ymax);
    glEnd();

    // Draw original polygon
    drawPolygon(polygon, 0, 1, 0);

    // Perform clipping
    sutherlandHodgman();

    // Draw clipped polygon
    drawPolygon(polygon, 0, 0, 1);

    glFlush();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutCreateWindow("Sutherland-Hodgman Polygon Clipping");
    init();
    glutDisplayFunc(display);
    glutMainLoop();
}
