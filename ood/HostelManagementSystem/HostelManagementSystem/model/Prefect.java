package model;

public class Prefect extends Student {
    private int prefectID;
    private String responsibilityArea;
    
    public Prefect() {
        super();
        setRole("PREFECT");
    }
    
    public Prefect(int userID, String username, String password, String name, String contactNumber, 
                  int studentID, int age, int prefectID, String responsibilityArea) {
        super(userID, username, password, name, contactNumber, studentID, age);
        this.prefectID = prefectID;
        this.responsibilityArea = responsibilityArea;
        setRole("PREFECT");
    }
    
    // Getters and setters
    public int getPrefectID() { return prefectID; }
    public void setPrefectID(int prefectID) { this.prefectID = prefectID; }
    
    public String getResponsibilityArea() { return responsibilityArea; }
    public void setResponsibilityArea(String responsibilityArea) { this.responsibilityArea = responsibilityArea; }
    
    // Additional methods for prefects
    public void viewAllComplaints() {
        System.out.println("Prefect " + getName() + " is viewing all complaints");
    }
    
    public void raiseCleaningRequest(String description) {
        System.out.println("Prefect " + getName() + " raised cleaning request: " + description);
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Pay Fees",
            "2. View Notices",
            "3. View Room Details",
            "4. Raise Complaint",
            "5. View All Complaints",
            "6. Raise Cleaning Request",
            "7. View Profile",
            "8. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        // First check base student permissions but exclude specific ones
        if (permission.equals("PAY_FEES") || 
            permission.equals("VIEW_NOTICES") ||
            permission.equals("VIEW_ROOM_DETAILS") ||
            permission.equals("VIEW_PROFILE") ||
            permission.equals("UPDATE_PROFILE")) {
            return true;
        }
        
        switch (permission) {
            case "RAISE_COMPLAINT": // Add this back for Prefects
            case "VIEW_ALL_COMPLAINTS":
            case "RAISE_CLEANING_REQUEST":
                return true;
            default:
                return false;
        }
    }
}
