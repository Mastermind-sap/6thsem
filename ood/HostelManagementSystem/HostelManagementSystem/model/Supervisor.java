package model;

public class Supervisor extends User {
    private int supervisorID;
    
    public Supervisor() {
        super();
        setRole("SUPERVISOR");
    }
    
    public Supervisor(int userID, String username, String password, String name, String contactNumber,
                     int supervisorID) {
        super(userID, username, password, "SUPERVISOR", name, contactNumber);
        this.supervisorID = supervisorID;
    }
    
    // Getters and setters
    public int getSupervisorID() { return supervisorID; }
    public void setSupervisorID(int supervisorID) { this.supervisorID = supervisorID; }
    
    // Supervisor methods
    public void resolveIssue(int complaintID) {
        System.out.println("Supervisor " + getName() + " resolved issue #" + complaintID);
    }
    
    public void viewAllComplaints() {
        System.out.println("Supervisor " + getName() + " is viewing all complaints");
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. View All Complaints",
            "2. Resolve Issue",
            "3. View Profile",
            "4. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "VIEW_ALL_COMPLAINTS":
            case "RESOLVE_ISSUE":
            case "VIEW_PROFILE":
            case "UPDATE_PROFILE":
                return true;
            default:
                return false;
        }
    }
}
