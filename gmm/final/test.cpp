#include <GLUT/glut.h>

//#include <GL/glut.h> in linux

// #include <bits/stdc++.h>
#include <cmath>

using namespace std;


// vector<pair<float,float>>v;
void init()
{
    glClearColor(1, 1, 1, 0);// color r g b a for window
    glMatrixMode(GL_PROJECTION);
    // GL_MODELVIEW = transformations ke liye bola hai
    gluOrtho2D(-5,5,-5,5);
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(1, 0, 0);
    // glBegin(GL_LINES);
    // glVertex2i(0,50);
    // glVertex2i(100,150);
    // glEnd();

    //concave polygon nhi bna skte concave wala part bhi cover ho jata hai
    // glBegin(GL_POLYGON);
    // glVertex2i(-1,-1);
    // // glVertex2i(0,0); // yeh na rehna bhi barabar hai
    // glVertex2i(1,-1);
    // glVertex2i(0,1);    
    // glEnd();
    
    //concave possible
    // glBegin(GL_POLYGON);
    // glVertex2i(0 ,0);
    // glVertex2i(-1,-1);
    // glVertex2i(0,1);
    // glVertex2i(1,-1);
    // glEnd();


    //Circle
    // int r=1;
    // glBegin(GL_POINTS);
    // glPointSize(10);
    // for(float x=-r;x<=r;x+=0.0001){
    //     float y=sqrt(r*r-x*x);
    //     glVertex2f(x,y);
    //     glVertex2f(x,-y);
    // }
    // glEnd();
    
    //Ellipse
    // int a=4,b=3;
    // for(float x=-a;x<=a;x+=0.01){
    //     float y=sqrt((1-((x*x)/(a*a)))*b*b);
    //     glVertex2f(x,y);
    //     glVertex2f(x,-y);
    // }
    // glEnd();

    // GL_LINES= 2 - 2 ko connect krega
    // GL_LINE_STRIP = continuous hoga
    // GL_LINE_LOOP = continuos aur end aur first join


    // random gyaan
    // glBegin(GL_POINTS);
    // GLint pt[] ={1,1};
    // glVertex2iv(pt);
    // glEnd();

    // glRecti(0,2,0,2);
    
    // input based points display
    // glBegin(GL_POINTS);
    // for(auto i:v){
    //     glVertex2f(i.first,i.second);
    // }
    // glEnd();

    //transformations uske baad bhi figure plot krna prega with initial coordinates only
    // glTranslatef(1,1,1);
    // glRotatef(90,0,0,1);


    //colored quadrants circle
    // glBegin(GL_TRIANGLE_FAN);
    // int r=1;
    // glVertex2f(0,0);
    // glColor3f(1,0,0);
    // float i=0;
    // for(;i<(M_PI/2);i+=0.001){
    //     glVertex2f(r*cos(i),r*sin(i));
    // }
    // glVertex2f(0,0);
    // glColor3f(0,1,0);
    // for(;i<(M_PI);i+=0.001){
    //     glVertex2f(r*cos(i),r*sin(i));
    // }
    // glVertex2f(0,0);
    // glEnd();


    //tringles
    glBegin(GL_TRIANGLES);
    glVertex2f(2,0);
    glVertex2f(0,0);
    glVertex2f(0,2);
    glEnd();
    glFlush(); //very imp 
}

// void idle(){
//     float x,y;
//     cout<<"Give x,y:";
//     cin>>x>>y;
//     v.push_back({x,y});

//     glutPostRedisplay();
// }

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    // glutInitWindowPosition(500,500); //intial position ke liye nhi bhi do toh sahi hai aur create win ke upr dena
    // glutInitWindowSize(1000,1000); //optional
    // glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutCreateWindow("Let's go");
    init();
    glutDisplayFunc(display);
    // glutIdleFunc(idle); //for input during display
    glutMainLoop();
}

// g++ -lGL -lGLU -lglut