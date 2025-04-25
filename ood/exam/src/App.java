import java.util.*;


class Shape{
    String color;
}

class Rectangle extends Shape{

}

class Student{
    String name;
    //non parameterized constructor
    Student(){
        System.out.println("kuch bhi");
    }

    //parameterized constructor
    Student(String nameRecv){
        this.name=nameRecv;
    }

    //copy constructor
    Student(Student s1){
        this.name=s1.name;
    }


}
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String name="sap";
        // primitive: byte,short,int,long,float,double,char,bool
        // non-primitive: string(immutable declared with new),arrays
        //final keyword
        //Math.max(0, 0)
        int[] m=new int[3];
        Arrays.sort(m);
        System.out.println(name);
        Scanner sc=new Scanner(System.in);
        int inp=sc.nextInt();
        String nameA=sc.next();
        String sent=sc.nextLine();
        // &&(and) ||(or)
        //switch
        try{

        }catch(Exception e){
            System.out.println(e);
        }

        // abstraction encapsulation inheritance polymorphism
    }
}

// polymorphism
// function overloading = compile time poly
// function overriding = runtime poly


//inheritance
/*
 * 4 types in java 5 types in cpp
 * multiple inheritance not there in java but we have interfaces
 * 1. single level inheritance
 * 2. multi level inheritance
 * 3.hierarchial inheritance
 * 4. hybrid inheritance
 */

/*Access Modifiers (4 Java 3 cpp)
 * 1.public (sab jagah access in this or another package)
 * 2.private (only in that class ; use getters and setters )
 * 3.protected (yeh package mai sab jagah dusre package mai only sub classes mai)
 * 4.default (only in this package everywhere not accessible outside package)
 * 
 */

 //encapsulation=data + methods in one entity ( enables data hiding )

 interface Animal{
    void walk();}
class Cow implements Animal{
    public void walk(){

    }
}
 /*
  * Abstract class declared with abstract keyword
  Abstract class ka object nhi bna skte (instatiation error ayega)

  abstraction using interfaces( interfaces mai sif abstract rahega no implementation)
  interfaces= all fields are by default public,static and final
  methods are public and abstract by default

  */

  //static= sare objects ke liye same for a class (memory assigned only once)