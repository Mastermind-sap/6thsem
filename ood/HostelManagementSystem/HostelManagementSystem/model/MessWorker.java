package model;

public class MessWorker extends User {
    private int workerID;
    private String shift;
    
    public MessWorker() {
        super();
        setRole("MESS_WORKER");
    }
    
    public MessWorker(int userID, String username, String password, String name, String contactNumber,
                     int workerID, String shift) {
        super(userID, username, password, "MESS_WORKER", name, contactNumber);
        this.workerID = workerID;
        this.shift = shift;
    }
    
    // Getters and setters
    public int getWorkerID() { return workerID; }
    public void setWorkerID(int workerID) { this.workerID = workerID; }
    
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    
    // Mess worker methods
    public void viewMessMenu() {
        System.out.println("Mess worker " + getName() + " is viewing mess menu");
    }
    
    public void recordFoodService() {
        System.out.println("Mess worker " + getName() + " recorded food service");
    }
    
    // Add method to view mess feedback
    public void viewMessFeedback() {
        System.out.println("Mess worker " + getName() + " is viewing mess feedback");
        // The actual feedback viewing is now handled by MessFeedbackDAO in Main.java
    }

    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. View Mess Menu",
            "2. Record Food Service",
            "3. View Mess Feedback", // New option added
            "4. View Profile",
            "5. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "VIEW_MESS_MENU":
            case "RECORD_FOOD_SERVICE":
            case "VIEW_MESS_FEEDBACK": // New permission added
            case "VIEW_PROFILE":
            case "UPDATE_PROFILE":
                return true;
            default:
                return false;
        }
    }
}
