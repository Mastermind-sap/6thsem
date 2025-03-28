package model;

public class Warden extends User {
    private int wardenID;
    private int hostelID;
    
    public Warden() {
        super();
        setRole("WARDEN");
    }
    
    public Warden(int userID, String username, String password, String name, String contactNumber,
                 int wardenID, int hostelID) {
        super(userID, username, password, "WARDEN", name, contactNumber);
        this.wardenID = wardenID;
        this.hostelID = hostelID;
    }
    
    // Getters and setters
    public int getWardenID() { return wardenID; }
    public void setWardenID(int wardenID) { this.wardenID = wardenID; }
    
    public int getHostelID() { return hostelID; }
    public void setHostelID(int hostelID) { this.hostelID = hostelID; }
    
    // Methods from class diagram
    public void assignRoom(Student student, Room room) {
        System.out.println("Warden " + getName() + " assigned room " + room.getRoomNumber() + " to student " + student.getName());
        room.allocateRoom(student);
        student.setRoomNumber(room.getRoomNumber());
    }
    
    public void manageStudent(Student student, String action) {
        System.out.println("Warden " + getName() + " performing action: " + action + " for student " + student.getName());
    }
    
    public boolean collectFees(Student student, double amount) {
        System.out.println("Warden " + getName() + " collected $" + amount + " from student " + student.getName());
        return true;
    }
    
    public boolean approveComplaint(Complaint complaint) {
        System.out.println("Warden " + getName() + " approved complaint: " + complaint.getDescription());
        complaint.setStatus("Approved");
        return true;
    }
    
    public void checkClearance(Student student) {
        System.out.println("Checking clearance for student: " + student.getName());
    }
    
    public void overseePolicies() {
        System.out.println("Warden " + getName() + " is overseeing hostel policies");
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Approve Complaints",
            "2. Allocate Rooms",
            "3. Check Hostel Clearance",
            "4. Manage Students",
            "5. Publish Notice",
            "6. View All Complaints",
            "7. Oversee Policies",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "APPROVE_COMPLAINTS":
            case "ALLOCATE_ROOMS":
            case "CHECK_CLEARANCE":
            case "MANAGE_STUDENTS":
            case "PUBLISH_NOTICE":
            case "VIEW_COMPLAINTS":
            case "OVERSEE_POLICIES":
                return true;
            default:
                return false;
        }
    }
}
