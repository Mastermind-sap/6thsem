package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.ComplaintDAO;
import dao.MessMenuDAO;
import dao.NoticeDAO;
import dao.StudentDAO;
import dao.UserDAO;
import model.*;

public class MenuHandler {
    private Scanner scanner;
    private ViewService viewService;
    private FeedbackService feedbackService;
    private StudentDAO studentDAO;
    private ComplaintDAO complaintDAO;
    private NoticeDAO noticeDAO;
    private UserDAO userDAO;
    private MessMenuDAO messMenuDAO;
    
    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
        this.viewService = new ViewService(scanner);
        this.feedbackService = new FeedbackService(scanner);
        this.studentDAO = new StudentDAO();
        this.complaintDAO = new ComplaintDAO();
        this.noticeDAO = new NoticeDAO();
        this.userDAO = new UserDAO();
        this.messMenuDAO = new MessMenuDAO();
    }
    
    public void showUserDashboard(User currentUser) {
        boolean logout = false;
        
        while (!logout) {
            System.out.println("\n===== " + currentUser.getRole() + " DASHBOARD =====");
            String[] options = currentUser.getDashboardOptions();
            for (String option : options) {
                System.out.println(option);
            }
            
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            
            if (choice == 0) {
                logout = true;
                System.out.println("Logged out successfully.");
            } else {
                handleUserChoice(choice, currentUser);
            }
        }
    }
    
    private void handleUserChoice(int choice, User currentUser) {
        if (currentUser instanceof Student) {
            if (currentUser instanceof Prefect) {
                handlePrefectChoice(choice, (Prefect) currentUser);
            } else if (currentUser instanceof MMC) {
                handleMMCChoice(choice, (MMC) currentUser);
            } else {
                handleStudentChoice(choice, (Student) currentUser);
            }
        } else if (currentUser instanceof Warden) {
            handleWardenChoice(choice, (Warden) currentUser);
        } else if (currentUser instanceof AdminStaff) {
            handleAdminStaffChoice(choice, (AdminStaff) currentUser);
        } else if (currentUser instanceof Cleaner) {
            handleCleanerChoice(choice, (Cleaner) currentUser);
        } else if (currentUser instanceof MessWorker) {
            handleMessWorkerChoice(choice, (MessWorker) currentUser);
        } else if (currentUser instanceof Security) {
            handleSecurityChoice(choice, (Security) currentUser);
        } else if (currentUser instanceof Supervisor) {
            handleSupervisorChoice(choice, (Supervisor) currentUser);
        } else if (currentUser instanceof DeanSW) {
            handleDeanSWChoice(choice, (DeanSW) currentUser);
        } else {
            System.out.println("Unknown user role. Cannot process request.");
        }
    }
    
    private void handleStudentChoice(int choice, Student student) {
        switch (choice) {
            case 1: // Pay Fees
                payFees(student);
                break;
            case 2: // View Notices
                viewService.viewNotices();
                break;
            case 3: // View Room Details
                viewService.viewRoomDetails(student);
                break;
            case 4: // Submit Mess Feedback
                feedbackService.submitMessFeedback(student);
                break;
            case 5: // View Profile
                viewService.viewUserProfile(student);
                break;
            case 6: // Update Profile
                updateUserProfile(student);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handlePrefectChoice(int choice, Prefect prefect) {
        switch (choice) {
            case 1: // Pay Fees
                payFees(prefect);
                break;
            case 2: // View Notices
                viewService.viewNotices();
                break;
            case 3: // View Room Details
                viewService.viewRoomDetails(prefect);
                break;
            case 4: // Raise Complaint
                raiseComplaint(prefect);
                break;
            case 5: // View All Complaints
                viewService.viewAllComplaints();
                break;
            case 6: // Raise Cleaning Request
                raiseCleaningRequest(prefect);
                break;
            case 7: // View Profile
                viewService.viewUserProfile(prefect);
                break;
            case 8: // Update Profile
                updateUserProfile(prefect);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleMMCChoice(int choice, MMC mmc) {
        switch (choice) {
            case 1: // Pay Fees
                payFees(mmc);
                break;
            case 2: // View Notices
                viewService.viewNotices();
                break;
            case 3: // View Room Details
                viewService.viewRoomDetails(mmc);
                break;
            case 4: // Set Mess Menu
                setMessMenu(mmc);
                break;
            case 5: // View Mess Menu
                mmc.viewMenu();
                break;
            case 6: // Submit Mess Feedback
                feedbackService.submitMessFeedback(mmc);
                break;
            case 7: // View Mess Feedback
                feedbackService.viewMessFeedback();
                break;
            case 8: // View Profile
                viewService.viewUserProfile(mmc);
                break;
            case 9: // Update Profile
                updateUserProfile(mmc);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleWardenChoice(int choice, Warden warden) {
        switch (choice) {
            case 1: // Approve Complaints
                approveComplaints(warden);
                break;
            case 2: // Allocate Rooms
                allocateRoom();
                break;
            case 3: // Check Hostel Clearance
                checkHostelClearance();
                break;
            case 4: // Manage Students
                manageStudents();
                break;
            case 5: // Publish Notice
                publishNotice(warden);
                break;
            case 6: // View All Complaints
                viewService.viewAllComplaints();
                break;
            case 7: // Oversee Policies
                warden.overseePolicies();
                break;
            case 8: // View Profile
                viewService.viewUserProfile(warden);
                break;
            case 9: // Update Profile
                updateUserProfile(warden);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleAdminStaffChoice(int choice, AdminStaff staff) {
        switch (choice) {
            case 1: // Publish Notice
                publishNotice(staff);
                break;
            case 2: // Check Payments
                staff.checkPayments();
                break;
            case 3: // Generate Reports
                staff.generateReport();
                break;
            case 4: // Manage Room Allocations
                allocateRoom();
                break;
            case 5: // View Complaints
                viewService.viewAllComplaints();
                break;
            case 6: // View Profile
                viewService.viewUserProfile(staff);
                break;
            case 7: // Update Profile
                updateUserProfile(staff);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleCleanerChoice(int choice, Cleaner cleaner) {
        switch (choice) {
            case 1: // View Cleaning Requests
                cleaner.viewCleaningRequests();
                break;
            case 2: // Mark Request Complete
                System.out.print("Enter request ID to mark as complete: ");
                int requestID = scanner.nextInt();
                scanner.nextLine();
                cleaner.markRequestComplete(requestID);
                break;
            case 3: // View Profile
                viewService.viewUserProfile(cleaner);
                break;
            case 4: // Update Profile
                updateUserProfile(cleaner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleMessWorkerChoice(int choice, MessWorker worker) {
        switch (choice) {
            case 1: // View Mess Menu
                worker.viewMessMenu();
                break;
            case 2: // Record Food Service
                worker.recordFoodService();
                break;
            case 3: // View Mess Feedback
                feedbackService.viewMessFeedback();
                break;
            case 4: // View Profile
                viewService.viewUserProfile(worker);
                break;
            case 5: // Update Profile
                updateUserProfile(worker);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleSecurityChoice(int choice, Security security) {
        switch (choice) {
            case 1: // Record Entry
                System.out.print("Enter student ID for entry: ");
                int entryID = scanner.nextInt();
                scanner.nextLine();
                security.recordEntry(entryID);
                break;
            case 2: // Record Exit
                System.out.print("Enter student ID for exit: ");
                int exitID = scanner.nextInt();
                scanner.nextLine();
                security.recordExit(exitID);
                break;
            case 3: // View Entry Logs
                security.viewEntryLogs();
                break;
            case 4: // View Profile
                viewService.viewUserProfile(security);
                break;
            case 5: // Update Profile
                updateUserProfile(security);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleSupervisorChoice(int choice, Supervisor supervisor) {
        switch (choice) {
            case 1: // View All Complaints
                viewService.viewAllComplaints();
                break;
            case 2: // Resolve Issue
                System.out.print("Enter complaint ID to resolve: ");
                int complaintID = scanner.nextInt();
                scanner.nextLine();
                supervisor.resolveIssue(complaintID);
                break;
            case 3: // View Profile
                viewService.viewUserProfile(supervisor);
                break;
            case 4: // Update Profile
                updateUserProfile(supervisor);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void handleDeanSWChoice(int choice, DeanSW dean) {
        switch (choice) {
            case 1: // Allocate Rooms
                allocateRoom();
                break;
            case 2: // View All Complaints
                viewService.viewAllComplaints();
                break;
            case 3: // Address Escalated Complaints
                System.out.print("Enter complaint ID to address: ");
                int complaintID = scanner.nextInt();
                scanner.nextLine();
                dean.addressEscalatedComplaint(complaintID);
                break;
            case 4: // Process BHM Refunds
                System.out.print("Enter student ID for refund: ");
                int studentID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter refund amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                dean.processRefund(studentID, amount);
                break;
            case 5: // View Profile
                viewService.viewUserProfile(dean);
                break;
            case 6: // Update Profile
                updateUserProfile(dean);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // Helper methods
    private void updateUserProfile(User user) {
        System.out.println("\n----- UPDATE PROFILE -----");
        System.out.print("Enter new name [" + user.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) user.setName(name);
        
        System.out.print("Enter new contact number [" + user.getContactNumber() + "]: ");
        String contactNumber = scanner.nextLine();
        if (!contactNumber.isEmpty()) user.setContactNumber(contactNumber);
        
        System.out.print("Enter new password (leave blank to keep current): ");
        String password = scanner.nextLine();
        if (!password.isEmpty()) user.setPassword(password);
        
        if (userDAO.updateUser(user)) {
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Failed to update profile.");
        }
    }
    
    private void payFees(Student student) {
        System.out.println("\n----- PAY FEES -----");
        System.out.print("Enter fee amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  
        
        System.out.print("Enter due date (yyyy-MM-dd): ");
        String dueDateStr = scanner.nextLine();
        
        try {
            Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
            Fees fees = new Fees(0, student.getStudentID(), amount, dueDate);
            fees.payFees();
            student.setPaymentStatus(true);
            System.out.println("Fee payment processed successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
        }
    }
    
    private void raiseComplaint(Student student) {
        System.out.println("\n----- RAISE COMPLAINT -----");
        System.out.print("Enter complaint description: ");
        String description = scanner.nextLine();
        
        Complaint complaint = new Complaint(0, student.getStudentID(), description);
        complaint.submitComplaint();
        
        if (complaintDAO.addComplaint(complaint)) {
            System.out.println("Complaint submitted successfully with ID: " + complaint.getComplaintID());
        } else {
            System.out.println("Failed to submit complaint.");
        }
    }
    
    private void raiseCleaningRequest(Prefect prefect) {
        System.out.println("\n----- RAISE CLEANING REQUEST -----");
        System.out.print("Enter area to be cleaned: ");
        String area = scanner.nextLine();
        
        System.out.print("Enter request details: ");
        String details = scanner.nextLine();
        
        prefect.raiseCleaningRequest(area + ": " + details);
        System.out.println("Cleaning request submitted successfully.");
    }
    
    private void setMessMenu(MMC mmc) {
        System.out.println("\n----- SET MESS MENU -----");
        System.out.println("Enter menu for each day:");
        
        System.out.print("Monday: ");
        String monday = scanner.nextLine();
        
        System.out.print("Tuesday: ");
        String tuesday = scanner.nextLine();
        
        System.out.print("Wednesday: ");
        String wednesday = scanner.nextLine();
        
        System.out.print("Thursday: ");
        String thursday = scanner.nextLine();
        
        System.out.print("Friday: ");
        String friday = scanner.nextLine();
        
        System.out.print("Saturday: ");
        String saturday = scanner.nextLine();
        
        System.out.print("Sunday: ");
        String sunday = scanner.nextLine();
        
        String menu = "Monday: " + monday + "\n" +
                      "Tuesday: " + tuesday + "\n" +
                      "Wednesday: " + wednesday + "\n" +
                      "Thursday: " + thursday + "\n" +
                      "Friday: " + friday + "\n" +
                      "Saturday: " + saturday + "\n" +
                      "Sunday: " + sunday;
        
        mmc.setMessMenu(menu);
        System.out.println("Mess menu set successfully.");
    }
    
    private void approveComplaints(Warden warden) {
        System.out.println("\n----- APPROVE COMPLAINTS -----");
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        if (complaints.isEmpty()) {
            System.out.println("No complaints to approve.");
            return;
        }
        
        System.out.println("Pending Complaints:");
        for (Complaint complaint : complaints) {
            if ("Pending".equals(complaint.getStatus())) {
                System.out.println(complaint.getComplaintID() + ": " + complaint.getDescription());
            }
        }
        
        System.out.print("Enter complaint ID to approve (0 to cancel): ");
        int complaintId = scanner.nextInt();
        scanner.nextLine();  
        
        if (complaintId == 0) return;
        
        Complaint complaint = complaintDAO.getComplaintById(complaintId);
        if (complaint != null && "Pending".equals(complaint.getStatus())) {
            warden.approveComplaint(complaint);
            complaintDAO.updateComplaintStatus(complaintId, "Approved");
            System.out.println("Complaint approved successfully.");
        } else {
            System.out.println("Invalid complaint ID or complaint not in pending state.");
        }
    }
    
    private void allocateRoom() {
        System.out.println("\n----- ALLOCATE ROOM -----");
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  
        
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();  
        
        if (studentDAO.assignRoomToStudent(studentId, roomNumber)) {
            System.out.println("Room allocated successfully.");
        } else {
            System.out.println("Failed to allocate room.");
        }
    }
    
    private void checkHostelClearance() {
        System.out.println("\n----- CHECK HOSTEL CLEARANCE -----");
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  
        
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Checking clearance for " + student.getName() + "...");
        System.out.println("Payment status: " + (student.isPaymentStatus() ? "Cleared" : "Pending"));
        System.out.println("Room clearance: " + (student.getRoomNumber() > 0 ? "Needs to be vacated" : "No room assigned"));
    }
    
    private void manageStudents() {
        System.out.println("\n----- MANAGE STUDENTS -----");
        System.out.println("1. View all students");
        System.out.println("2. Add new student");
        System.out.println("3. Update student details");
        System.out.println("4. Delete student");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();  
        
        switch (choice) {
            case 1:
                viewService.viewAllStudents();
                break;
            case 2:
                addStudent();
                break;
            case 3:
                updateStudent();
                break;
            case 4:
                deleteStudent();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void publishNotice(User publisher) {
        System.out.println("\n----- PUBLISH NOTICE -----");
        System.out.print("Enter notice content: ");
        String content = scanner.nextLine();
        
        Notice notice = new Notice(0, content, publisher.getUserID());
        notice.publishNotice();
        
        if (noticeDAO.addNotice(notice)) {
            System.out.println("Notice published successfully.");
        } else {
            System.out.println("Failed to publish notice.");
        }
    }
    
    private void addStudent() {
        System.out.println("\n----- ADD STUDENT -----");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setContactNumber(contactNumber);
        
        if (studentDAO.addStudent(student)) {
            System.out.println("Student added successfully with ID: " + student.getStudentID());
        } else {
            System.out.println("Failed to add student.");
        }
    }
    
    private void updateStudent() {
        System.out.println("\n----- UPDATE STUDENT -----");
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  
        
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current details:");
        student.viewDetails();
        
        System.out.println("\nEnter new details (leave blank to keep current value):");
        System.out.print("Enter name [" + student.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) student.setName(name);
        
        System.out.print("Enter age [" + student.getAge() + "]: ");
        String ageStr = scanner.nextLine();
        if (!ageStr.isEmpty()) student.setAge(Integer.parseInt(ageStr));
        
        System.out.print("Enter contact number [" + student.getContactNumber() + "]: ");
        String contact = scanner.nextLine();
        if (!contact.isEmpty()) student.setContactNumber(contact);
        
        if (studentDAO.updateStudent(student)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Failed to update student.");
        }
    }
    
    private void deleteStudent() {
        System.out.println("\n----- DELETE STUDENT -----");
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Are you sure you want to delete this student? (y/n): ");
        String confirm = scanner.nextLine();
        
        if ("y".equalsIgnoreCase(confirm)) {
            if (studentDAO.deleteStudent(studentId)) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
}
