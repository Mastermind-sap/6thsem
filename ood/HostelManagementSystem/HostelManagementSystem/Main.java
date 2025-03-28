import java.util.Scanner;
import model.User;
import service.LoginService;
import service.MenuHandler;
import service.UserRegistrationService;
import util.DatabaseConnection;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Initialize database connection
        DatabaseConnection.getConnection();
        
        // Start with login screen
        showLoginScreen();
        
        // Close database connection
        DatabaseConnection.closeConnection();
        
        // Close scanner
        scanner.close();
    }
    
    private static void showLoginScreen() {
        // Initialize services
        LoginService loginService = new LoginService(scanner);
        UserRegistrationService registrationService = new UserRegistrationService(scanner);
        MenuHandler menuHandler = new MenuHandler(scanner);
        
        boolean exitSystem = false;
        
        while (!exitSystem) {
            System.out.println("\n===== HOSTEL MANAGEMENT SYSTEM LOGIN =====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  
            
            switch (choice) {
                case 1:
                    User currentUser = loginService.login();
                    if (currentUser != null) {
                        menuHandler.showUserDashboard(currentUser);
                    }
                    break;
                case 2:
                    registrationService.showSignUpMenu();
                    break;
                case 0:
                    exitSystem = true;
                    System.out.println("Thank you for using Hostel Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}