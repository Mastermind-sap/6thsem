package service;

import java.util.Scanner;
import model.User;
import util.Authentication;

public class LoginService {
    private Scanner scanner;
    
    public LoginService(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public User login() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User currentUser = Authentication.login(username, password);
        
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
        
        return currentUser;
    }
}
