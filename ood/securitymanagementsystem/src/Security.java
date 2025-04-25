import java.util.Scanner;

public class Security {
    int length;
    
    public Security() {
        this.length = App.currentSec;
    }
    
    public void login(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter username:");
        String username=sc.nextLine();
        System.out.println("Enter password:");
        String passwd=sc.nextLine();
        Boolean done=false;
        for(int i=0; i<App.currentSec; i++){
            if(App.securityNames[i].equals(username) && App.passwords[i].equals(passwd)){ 
                menu();
                done=true;
                break;
            }
        }
        if(!done){
            System.out.println("Invalid");   
        }
    }

    public void signup(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter username:");
        String username=sc.nextLine();
        System.out.println("Enter password:");
        String passwd=sc.nextLine();
        App.securityNames[App.currentSec] = username;
        App.passwords[App.currentSec] = passwd;
        App.currentSec++;
        this.length = App.currentSec;
        menu();
    }

    public void entry(){
        System.out.println("Enter entry person:");
        Scanner sc=new Scanner(System.in);
        String personName=sc.nextLine();
        App.recordManager.addEntryRecord(personName);
    }

    public void exits(){
        System.out.println("Enter exit person:");
        Scanner sc=new Scanner(System.in);
        String personName=sc.nextLine();
        App.recordManager.addExitRecord(personName);
    }
    
    public void viewEntries() {
        App.recordManager.displayEntryRecords();
    }
    
    public void viewExits() {
        App.recordManager.displayExitRecords();
    }

    public void menu(){
        Scanner sc=new Scanner(System.in);
        Boolean run=true;
        while(run){
        System.out.println("Enter option:");
        System.out.println("1. Record entry:");
        System.out.println("2. Record exit:");
        System.out.println("3. View entry records:");
        System.out.println("4. View exit records:");
        System.out.println("5. to exit:");
        int n=sc.nextInt();
        switch (n) {
            case 1:
                entry();
                break;
            case 2:
                exits();
                break;
            case 3:
                viewEntries();
                break;
            case 4:
                viewExits();
                break;
            default:
                run=false;
                break;
        }}
    }
}
