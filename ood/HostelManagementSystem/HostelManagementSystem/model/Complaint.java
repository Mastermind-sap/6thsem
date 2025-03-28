package model;

import java.util.Date;

public class Complaint {
    private int complaintID;
    private int studentID;
    private String description;
    private String status; // Pending, Approved, Resolved, Rejected
    private Date dateSubmitted;
    private Date dateResolved;
    
    public Complaint() {}
    
    public Complaint(int complaintID, int studentID, String description) {
        this.complaintID = complaintID;
        this.studentID = studentID;
        this.description = description;
        this.status = "Pending";
        this.dateSubmitted = new Date();
    }
    
    // Getters and setters
    public int getComplaintID() { return complaintID; }
    public void setComplaintID(int complaintID) { this.complaintID = complaintID; }
    
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(Date dateSubmitted) { this.dateSubmitted = dateSubmitted; }
    
    public Date getDateResolved() { return dateResolved; }
    public void setDateResolved(Date dateResolved) { this.dateResolved = dateResolved; }
    
    // Methods
    public void submitComplaint() {
        System.out.println("Complaint submitted: " + description);
        this.status = "Pending";
    }
    
    public void trackComplaint() {
        System.out.println("Tracking complaint #" + complaintID + ": Status - " + status);
    }
    
    public void resolveComplaint() {
        System.out.println("Resolving complaint #" + complaintID);
        this.status = "Resolved";
        this.dateResolved = new Date();
    }
}
