package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dao.UserDAO;
import model.*;
import util.DatabaseConnection;

public class UserRegistrationService {
    private Scanner scanner;
    private UserDAO userDAO;
    
    public UserRegistrationService(Scanner scanner) {
        this.scanner = scanner;
        this.userDAO = new UserDAO();
    }
    
    public void showSignUpMenu() {
        System.out.println("\n===== USER REGISTRATION =====");
        System.out.println("Select user type:");
        System.out.println("1. Student");
        System.out.println("2. Prefect");
        System.out.println("3. MMC Member");
        System.out.println("4. Warden");
        System.out.println("5. Admin Staff");
        System.out.println("6. Cleaner");
        System.out.println("7. Mess Worker");
        System.out.println("8. Security");
        System.out.println("9. Supervisor");
        System.out.println("10. Dean SW");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();  
        
        if (choice == 0) return;
        
        // Common details for all user types
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        
        switch (choice) {
            case 1:
                registerStudent(username, password, name, contactNumber);
                break;
            case 2:
                registerPrefect(username, password, name, contactNumber);
                break;
            case 3:
                registerMMC(username, password, name, contactNumber);
                break;
            case 4:
                registerWarden(username, password, name, contactNumber);
                break;
            case 5:
                registerAdminStaff(username, password, name, contactNumber);
                break;
            case 6:
                registerCleaner(username, password, name, contactNumber);
                break;
            case 7:
                registerMessWorker(username, password, name, contactNumber);
                break;
            case 8:
                registerSecurity(username, password, name, contactNumber);
                break;
            case 9:
                registerSupervisor(username, password, name, contactNumber);
                break;
            case 10:
                registerDeanSW(username, password, name, contactNumber);
                break;
            default:
                System.out.println("Invalid choice. Registration canceled.");
        }
    }
    
    private void registerStudent(String username, String password, String name, String contactNumber) {
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  
        
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);
        student.setName(name);
        student.setContactNumber(contactNumber);
        student.setAge(age);
        
        if (userDAO.addUser(student)) {
            System.out.println("Student registered successfully!");
            System.out.println("Your student ID is: " + student.getStudentID());
        } else {
            System.out.println("Failed to register student. Username may already exist.");
        }
    }
    
    private void registerPrefect(String username, String password, String name, String contactNumber) {
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Enter responsibility area: ");
        String responsibilityArea = scanner.nextLine();
        
        Prefect prefect = new Prefect();
        prefect.setUsername(username);
        prefect.setPassword(password);
        prefect.setName(name);
        prefect.setContactNumber(contactNumber);
        prefect.setAge(age);
        prefect.setResponsibilityArea(responsibilityArea);
        
        if (userDAO.addUser(prefect)) {
            System.out.println("Prefect registered successfully!");
            System.out.println("Your student ID is: " + prefect.getStudentID());
            System.out.println("Your prefect ID is: " + prefect.getPrefectID());
        } else {
            System.out.println("Failed to register prefect. Username may already exist.");
        }
    }

    private void registerMMC(String username, String password, String name, String contactNumber) {
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Enter position in MMC: ");
        String position = scanner.nextLine();
        
        MMC mmc = new MMC();
        mmc.setUsername(username);
        mmc.setPassword(password);
        mmc.setName(name);
        mmc.setContactNumber(contactNumber);
        mmc.setAge(age);
        mmc.setPosition(position);
        
        if (userDAO.addUser(mmc)) {
            System.out.println("MMC member registered successfully!");
            System.out.println("Your student ID is: " + mmc.getStudentID());
            System.out.println("Your MMC ID is: " + mmc.getMmcID());
        } else {
            System.out.println("Failed to register MMC member. Username may already exist.");
        }
    }

    private void registerWarden(String username, String password, String name, String contactNumber) {
        System.out.println("\n----- AVAILABLE HOSTELS -----");
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT hostelID, name, location FROM Hostel");
            
            if (!rs.isBeforeFirst()) {
                System.out.println("No hostels found in the database. Please add hostels first.");
                return;
            }
            
            System.out.println("ID\tName\tLocation");
            while (rs.next()) {
                System.out.println(rs.getInt("hostelID") + "\t" + 
                                   rs.getString("name") + "\t" + 
                                   rs.getString("location"));
            }
            
            System.out.print("\nEnter hostel ID: ");
            int hostelID = scanner.nextInt();
            scanner.nextLine();  
            
            // Check if the hostelID is valid
            PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM Hostel WHERE hostelID = ?");
            checkStmt.setInt(1, hostelID);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getInt(1) == 0) {
                System.out.println("Invalid hostel ID. Please try again.");
                return;
            }
            
            Warden warden = new Warden();
            warden.setUsername(username);
            warden.setPassword(password);
            warden.setName(name);
            warden.setContactNumber(contactNumber);
            warden.setHostelID(hostelID);
            
            if (userDAO.addUser(warden)) {
                System.out.println("Warden registered successfully!");
                System.out.println("Your warden ID is: " + warden.getWardenID());
            } else {
                System.out.println("Failed to register warden. Username may already exist.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading hostels: " + e.getMessage());
        }
    }

    private void registerAdminStaff(String username, String password, String name, String contactNumber) {
        System.out.print("Enter department (leave blank for 'Administration'): ");
        String department = scanner.nextLine();
        if (department.isEmpty()) {
            department = "Administration";
        }
        
        AdminStaff adminStaff = new AdminStaff();
        adminStaff.setUsername(username);
        adminStaff.setPassword(password);
        adminStaff.setName(name);
        adminStaff.setContactNumber(contactNumber);
        adminStaff.setDepartment(department);
        
        if (userDAO.addUser(adminStaff)) {
            System.out.println("Admin Staff registered successfully!");
            System.out.println("Your admin staff ID is: " + adminStaff.getStaffID());
        } else {
            System.out.println("Failed to register admin staff. Username may already exist.");
        }
    }

    private void registerCleaner(String username, String password, String name, String contactNumber) {
        System.out.print("Enter area of responsibility: ");
        String area = scanner.nextLine();
        
        Cleaner cleaner = new Cleaner();
        cleaner.setUsername(username);
        cleaner.setPassword(password);
        cleaner.setName(name);
        cleaner.setContactNumber(contactNumber);
        cleaner.setArea(area);
        
        if (userDAO.addUser(cleaner)) {
            System.out.println("Cleaner registered successfully!");
            System.out.println("Your cleaner ID is: " + cleaner.getCleanerID());
        } else {
            System.out.println("Failed to register cleaner. Username may already exist.");
        }
    }

    private void registerMessWorker(String username, String password, String name, String contactNumber) {
        System.out.print("Enter shift (Morning/Evening/Night): ");
        String shift = scanner.nextLine();
        
        MessWorker messWorker = new MessWorker();
        messWorker.setUsername(username);
        messWorker.setPassword(password);
        messWorker.setName(name);
        messWorker.setContactNumber(contactNumber);
        messWorker.setShift(shift);
        
        if (userDAO.addUser(messWorker)) {
            System.out.println("Mess Worker registered successfully!");
            System.out.println("Your worker ID is: " + messWorker.getWorkerID());
        } else {
            System.out.println("Failed to register mess worker. Username may already exist.");
        }
    }

    private void registerSecurity(String username, String password, String name, String contactNumber) {
        System.out.print("Enter shift (Morning/Evening/Night): ");
        String shift = scanner.nextLine();
        
        Security security = new Security();
        security.setUsername(username);
        security.setPassword(password);
        security.setName(name);
        security.setContactNumber(contactNumber);
        security.setShift(shift);
        
        if (userDAO.addUser(security)) {
            System.out.println("Security registered successfully!");
            System.out.println("Your security ID is: " + security.getSecurityID());
        } else {
            System.out.println("Failed to register security. Username may already exist.");
        }
    }

    private void registerSupervisor(String username, String password, String name, String contactNumber) {
        Supervisor supervisor = new Supervisor();
        supervisor.setUsername(username);
        supervisor.setPassword(password);
        supervisor.setName(name);
        supervisor.setContactNumber(contactNumber);
        
        if (userDAO.addUser(supervisor)) {
            System.out.println("Supervisor registered successfully!");
            System.out.println("Your supervisor ID is: " + supervisor.getSupervisorID());
        } else {
            System.out.println("Failed to register supervisor. Username may already exist.");
        }
    }

    private void registerDeanSW(String username, String password, String name, String contactNumber) {
        DeanSW deanSW = new DeanSW();
        deanSW.setUsername(username);
        deanSW.setPassword(password);
        deanSW.setName(name);
        deanSW.setContactNumber(contactNumber);
        
        if (userDAO.addUser(deanSW)) {
            System.out.println("Dean SW registered successfully!");
            System.out.println("Your dean ID is: " + deanSW.getDeanID());
        } else {
            System.out.println("Failed to register Dean SW. Username may already exist.");
        }
    }
}
