package model;

public class DeanSW extends User {
    private int deanID;
    
    public DeanSW() {
        super();
        setRole("DEAN_SW");
    }
    
    public DeanSW(int userID, String username, String password, String name, String contactNumber,
                 int deanID) {
        super(userID, username, password, "DEAN_SW", name, contactNumber);
        this.deanID = deanID;
    }
    
    // Getters and setters
    public int getDeanID() { return deanID; }
    public void setDeanID(int deanID) { this.deanID = deanID; }
    
    // Dean SW methods
    public void processRefund(int studentID, double amount) {
        System.out.println("Dean SW " + getName() + " processed refund of $" + amount + " for student ID: " + studentID);
    }
    
    public void allocateRoom(Student student, Room room) {
        System.out.println("Dean SW " + getName() + " allocated room " + room.getRoomNumber() + " to student " + student.getName());
    }
    
    public void addressEscalatedComplaint(int complaintID) {
        System.out.println("Dean SW " + getName() + " addressed escalated complaint #" + complaintID);
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Allocate Rooms",
            "2. View All Complaints",
            "3. Address Escalated Complaints",
            "4. Process BHM Refunds",
            "5. View Profile",
            "6. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "ALLOCATE_ROOMS":
            case "VIEW_ALL_COMPLAINTS":
            case "ADDRESS_ESCALATED_COMPLAINTS":
            case "PROCESS_REFUNDS":
            case "VIEW_PROFILE":
            case "UPDATE_PROFILE":
                return true;
            default:
                return false;
        }
    }
}
