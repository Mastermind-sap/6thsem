package model;

import java.util.Date;

public class Security extends User {
    private int securityID;
    private String shift;
    
    public Security() {
        super();
        setRole("SECURITY");
    }
    
    public Security(int userID, String username, String password, String name, String contactNumber,
                   int securityID, String shift) {
        super(userID, username, password, "SECURITY", name, contactNumber);
        this.securityID = securityID;
        this.shift = shift;
    }
    
    // Getters and setters
    public int getSecurityID() { return securityID; }
    public void setSecurityID(int securityID) { this.securityID = securityID; }
    
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    
    // Security methods
    public void recordEntry(int studentID) {
        System.out.println("Security " + getName() + " recorded entry for student ID: " + studentID);
    }
    
    public void recordExit(int studentID) {
        System.out.println("Security " + getName() + " recorded exit for student ID: " + studentID);
    }
    
    public void viewEntryLogs() {
        System.out.println("Security " + getName() + " is viewing entry logs");
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Record Entry",
            "2. Record Exit",
            "3. View Entry Logs",
            "4. View Profile",
            "5. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "RECORD_ENTRY":
            case "RECORD_EXIT":
            case "VIEW_ENTRY_LOGS":
            case "VIEW_PROFILE":
            case "UPDATE_PROFILE":
                return true;
            default:
                return false;
        }
    }
}
