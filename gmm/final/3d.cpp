#include <GL/glut.h>

static float angle = 0.0f;

// draw & rotate the pyramid
void display() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();
    // camera: eye at (3,3,3), look at origin, up = +Y
    gluLookAt(3,3,3,   0,0,0,   0,1,0);
    // rotate around Y
    glRotatef(angle, 0,1,0);

    // four triangular side-faces
    glBegin(GL_TRIANGLES);
      glColor3f(1,0,0); // front = red
      glVertex3f( 0,1, 0); glVertex3f(-1,0, 1); glVertex3f(1,0, 1);

      glColor3f(0,1,0); // right = green
      glVertex3f( 0,1, 0); glVertex3f(1,0, 1); glVertex3f(1,0,-1);

      glColor3f(0,0,1); // back = blue
      glVertex3f( 0,1, 0); glVertex3f(1,0,-1); glVertex3f(-1,0,-1);

      glColor3f(1,1,0); // left = yellow
      glVertex3f( 0,1, 0); glVertex3f(-1,0,-1); glVertex3f(-1,0, 1);
    glEnd();

    // square base
    glBegin(GL_QUADS);
      glColor3f(0.5f,0.5f,0.5f);
      glVertex3f(-1,0, 1); glVertex3f( 1,0, 1);
      glVertex3f( 1,0,-1); glVertex3f(-1,0,-1);
    glEnd();

    glutSwapBuffers();
}

// called when idle: increment angle and request redraw
void idle() {
    angle += 0.1f;
    if (angle > 360) angle -= 360;
    glutPostRedisplay();
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    // double-buffer, RGB, with depth buffer
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(600,600);
    glutCreateWindow("Rotating Pyramid");

    glEnable(GL_DEPTH_TEST);            // enable Z-buffer
    // set up a fixed perspective projection
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, 1.0, 1.0, 20.0);
    glMatrixMode(GL_MODELVIEW);

    glutDisplayFunc(display);
    glutIdleFunc(idle);
    glutMainLoop();
    return 0;
}