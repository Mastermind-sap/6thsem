package model;

import java.util.Date;

public class Fees {
    private int feesID;
    private int studentID;
    private double amount;
    private Date dueDate;
    private boolean isPaid;
    
    public Fees() {}
    
    public Fees(int feesID, int studentID, double amount, Date dueDate) {
        this.feesID = feesID;
        this.studentID = studentID;
        this.amount = amount;
        this.dueDate = dueDate;
        this.isPaid = false;
    }
    
    // Getters and setters
    public int getFeesID() { return feesID; }
    public void setFeesID(int feesID) { this.feesID = feesID; }
    
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
    
    // Methods from class diagram
    public void payFees() {
        System.out.println("Processing payment of $" + amount + " for student ID: " + studentID);
        this.isPaid = true;
    }
    
    public void generateReceipt() {
        System.out.println("--------------- RECEIPT ---------------");
        System.out.println("Fee ID: " + feesID);
        System.out.println("Student ID: " + studentID);
        System.out.println("Amount: $" + amount);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Payment Status: " + (isPaid ? "Paid" : "Unpaid"));
        System.out.println("---------------------------------------");
    }
    
    public double checkDueAmount() {
        if (isPaid) return 0.0;
        return amount;
    }
}
