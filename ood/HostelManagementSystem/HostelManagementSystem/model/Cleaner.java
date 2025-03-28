package model;

public class Cleaner extends User {
    private int cleanerID;
    private String area;
    
    public Cleaner() {
        super();
        setRole("CLEANER");
    }
    
    public Cleaner(int userID, String username, String password, String name, String contactNumber,
                  int cleanerID, String area) {
        super(userID, username, password, "CLEANER", name, contactNumber);
        this.cleanerID = cleanerID;
        this.area = area;
    }
    
    // Getters and setters
    public int getCleanerID() { return cleanerID; }
    public void setCleanerID(int cleanerID) { this.cleanerID = cleanerID; }
    
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    
    // Cleaner methods
    public void viewCleaningRequests() {
        System.out.println("Cleaner " + getName() + " is viewing cleaning requests");
    }
    
    public void markRequestComplete(int requestID) {
        System.out.println("Cleaner " + getName() + " marked request #" + requestID + " as complete");
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. View Cleaning Requests",
            "2. Mark Request Complete",
            "3. View Profile",
            "4. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "VIEW_CLEANING_REQUESTS":
            case "MARK_REQUEST_COMPLETE":
            case "VIEW_PROFILE":
            case "UPDATE_PROFILE":
                return true;
            default:
                return false;
        }
    }
}
