package service;

import java.util.List;
import java.util.Scanner;

import dao.ComplaintDAO;
import dao.NoticeDAO;
import dao.StudentDAO;
import model.*;

public class ViewService {
    private Scanner scanner;
    private ComplaintDAO complaintDAO;
    private NoticeDAO noticeDAO;
    private StudentDAO studentDAO;
    
    public ViewService(Scanner scanner) {
        this.scanner = scanner;
        this.complaintDAO = new ComplaintDAO();
        this.noticeDAO = new NoticeDAO();
        this.studentDAO = new StudentDAO();
    }
    
    public void viewUserProfile(User currentUser) {
        System.out.println("\n----- YOUR PROFILE -----");
        System.out.println("User ID: " + currentUser.getUserID());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Contact: " + currentUser.getContactNumber());
        System.out.println("Role: " + currentUser.getRole());
        
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Student ID: " + student.getStudentID());
            System.out.println("Age: " + student.getAge());
            System.out.println("Room Number: " + (student.getRoomNumber() > 0 ? student.getRoomNumber() : "Not assigned"));
            System.out.println("Payment Status: " + (student.isPaymentStatus() ? "Paid" : "Pending"));
            
            if (currentUser instanceof Prefect) {
                Prefect prefect = (Prefect) currentUser;
                System.out.println("Prefect ID: " + prefect.getPrefectID());
                System.out.println("Responsibility Area: " + prefect.getResponsibilityArea());
            } else if (currentUser instanceof MMC) {
                MMC mmc = (MMC) currentUser;
                System.out.println("MMC ID: " + mmc.getMmcID());
                System.out.println("Position: " + mmc.getPosition());
            }
        } else if (currentUser instanceof Warden) {
            Warden warden = (Warden) currentUser;
            System.out.println("Warden ID: " + warden.getWardenID());
            System.out.println("Hostel ID: " + warden.getHostelID());
        } else if (currentUser instanceof AdminStaff) {
            AdminStaff staff = (AdminStaff) currentUser;
            System.out.println("Staff ID: " + staff.getStaffID());
            System.out.println("Department: " + staff.getDepartment());
        } else if (currentUser instanceof Cleaner) {
            Cleaner cleaner = (Cleaner) currentUser;
            System.out.println("Cleaner ID: " + cleaner.getCleanerID());
            System.out.println("Area: " + cleaner.getArea());
        } else if (currentUser instanceof MessWorker) {
            MessWorker worker = (MessWorker) currentUser;
            System.out.println("Worker ID: " + worker.getWorkerID());
            System.out.println("Shift: " + worker.getShift());
        } else if (currentUser instanceof Security) {
            Security security = (Security) currentUser;
            System.out.println("Security ID: " + security.getSecurityID());
            System.out.println("Shift: " + security.getShift());
        } else if (currentUser instanceof Supervisor) {
            Supervisor supervisor = (Supervisor) currentUser;
            System.out.println("Supervisor ID: " + supervisor.getSupervisorID());
        } else if (currentUser instanceof DeanSW) {
            DeanSW dean = (DeanSW) currentUser;
            System.out.println("Dean ID: " + dean.getDeanID());
        }
    }
    
    public void viewAllComplaints() {
        System.out.println("\n----- ALL COMPLAINTS -----");
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        
        if (complaints.isEmpty()) {
            System.out.println("No complaints found.");
        } else {
            System.out.println("ID\tStudent ID\tStatus\tSubmitted Date\tDescription");
            for (Complaint complaint : complaints) {
                System.out.println(complaint.getComplaintID() + "\t" + 
                                   complaint.getStudentID() + "\t" + 
                                   complaint.getStatus() + "\t" + 
                                   complaint.getDateSubmitted() + "\t" + 
                                   complaint.getDescription());
            }
        }
    }
    
    public void viewNotices() {
        System.out.println("\n----- NOTICES -----");
        List<Notice> notices = noticeDAO.getAllNotices();
        
        if (notices.isEmpty()) {
            System.out.println("No notices to display.");
        } else {
            for (Notice notice : notices) {
                notice.displayNotice();
            }
        }
    }
    
    public void viewRoomDetails(Student student) {
        System.out.println("\n----- ROOM DETAILS -----");
        if (student.getRoomNumber() > 0) {
            System.out.println("Current Room Number: " + student.getRoomNumber());
            // Could fetch more room details from database if needed
        } else {
            System.out.println("You have not been assigned a room yet.");
        }
    }
    
    public void viewAllStudents() {
        System.out.println("\n----- ALL STUDENTS -----");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("ID\tName\tAge\tContact\tRoom");
            for (Student student : students) {
                System.out.println(student.getStudentID() + "\t" + 
                                   student.getName() + "\t" + 
                                   student.getAge() + "\t" + 
                                   student.getContactNumber() + "\t" + 
                                   (student.getRoomNumber() > 0 ? student.getRoomNumber() : "Not assigned"));
            }
        }
    }
}
