package model;

public class AdminStaff extends User {
    private int staffID;
    private String department;
    
    public AdminStaff() {
        super();
        setRole("ADMIN_STAFF");
    }
    
    public AdminStaff(int userID, String username, String password, String name, String contactNumber,
                     int staffID, String department) {
        super(userID, username, password, "ADMIN_STAFF", name, contactNumber);
        this.staffID = staffID;
        this.department = department;
    }
    
    // Getters and setters
    public int getStaffID() { return staffID; }
    public void setStaffID(int staffID) { this.staffID = staffID; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    // Add this method to fix the error
    public int getAdminStaffID() { return staffID; }
    
    // Admin staff methods
    public void publishNotice(String content) {
        System.out.println("Admin staff " + getName() + " published notice: " + content);
    }
    
    public void checkPayments() {
        System.out.println("Admin staff " + getName() + " is checking payments");
    }
    
    public void generateReport() {
        System.out.println("Admin staff " + getName() + " is generating reports");
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Publish Notice",
            "2. Check Payments",
            "3. Generate Reports",
            "4. Manage Room Allocations",
            "5. View Complaints",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "PUBLISH_NOTICE":
            case "CHECK_PAYMENTS":
            case "GENERATE_REPORTS":
            case "MANAGE_ROOM_ALLOCATIONS":
            case "VIEW_COMPLAINTS":
                return true;
            default:
                return false;
        }
    }
}
