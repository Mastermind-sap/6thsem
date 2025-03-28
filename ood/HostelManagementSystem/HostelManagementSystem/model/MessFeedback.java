package model;

import java.util.Date;

public class MessFeedback {
    private int feedbackID;
    private int studentID;
    private String studentName;  // Stored for easy display
    private int rating;
    private String comment;
    private Date dateSubmitted;
    
    public MessFeedback() {
        this.dateSubmitted = new Date();
    }
    
    public MessFeedback(int feedbackID, int studentID, String studentName, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.studentID = studentID;
        this.studentName = studentName;
        this.rating = rating;
        this.comment = comment;
        this.dateSubmitted = new Date();
    }
    
    // Getters and setters
    public int getFeedbackID() { return feedbackID; }
    public void setFeedbackID(int feedbackID) { this.feedbackID = feedbackID; }
    
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public Date getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(Date dateSubmitted) { this.dateSubmitted = dateSubmitted; }
    
    @Override
    public String toString() {
        return "Student: " + studentName + " (" + studentID + "), Rating: " + rating + " stars, Comment: " + comment;
    }
}
