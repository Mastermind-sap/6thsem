import java.util.*;

public class App {
    public static String[] securityNames=new String[100];
    public static String[] passwords=new String[100];
    public static int currentSec=0;
    public static RecordManager recordManager = new RecordManager(100);

    public static void menu(){
        Boolean run=true;
        Scanner sc=new Scanner(System.in);
        while(run){
        System.out.println("Enter option:");
        System.out.println("1. Login:");
        System.out.println("2. Signup:");
        System.out.println("3. to exit:");
        int x=sc.nextInt();
        Security s=new Security();
        switch(x){
            case 1:
                s.login();
                break;
            case 2:
                s.signup();
                break;
            case 3:
                System.out.println("Thank you");
                run=false;
                break;

        }}
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to NITS Security management system");
        menu();
    }
}
