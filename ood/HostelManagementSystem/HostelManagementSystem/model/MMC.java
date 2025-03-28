package model;

import dao.MessMenuDAO;

public class MMC extends Student {
    private int mmcID;
    private String position;
    private static MessMenuDAO menuDAO = new MessMenuDAO();
    
    public MMC() {
        super();
        setRole("MMC");
    }
    
    public MMC(int userID, String username, String password, String name, String contactNumber, 
              int studentID, int age, int mmcID, String position) {
        super(userID, username, password, name, contactNumber, studentID, age);
        this.mmcID = mmcID;
        this.position = position;
        setRole("MMC");
    }
    
    // Getters and setters
    public int getMmcID() { return mmcID; }
    public void setMmcID(int mmcID) { this.mmcID = mmcID; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    // Additional methods for MMC
    public void setMessMenu(String menu) {
        System.out.println("MMC member " + getName() + " set mess menu:");
        menuDAO.addMenu(this.mmcID, menu);
    }
    
    public void viewFeedback() {
        System.out.println("MMC member " + getName() + " is viewing mess feedback");
        // The actual feedback viewing is now handled by MessFeedbackDAO in Main.java
    }
    
    // Add a new method for submitting mess feedback
    public void submitMessFeedback(String feedback) {
        System.out.println("MMC member " + getName() + " submitted mess feedback: " + feedback);
        // In a real system, this would save to database
    }

    // Additional method for MMC to view the menu they set
    public void viewMenu() {
        System.out.println("\n----- CURRENT MESS MENU -----");
        String menu = menuDAO.getCurrentMenu();
        
        if (menu == null) {
            System.out.println("No menu has been set for this week.");
            System.out.println("Please use the 'Set Mess Menu' option to create a menu.");
        } else {
            System.out.println(menu);
        }
    }
    
    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Pay Fees",
            "2. View Notices",
            "3. View Room Details",
            "4. Set Mess Menu",
            "5. View Mess Menu", // Added option to view the menu they set
            "6. Submit Mess Feedback",
            "7. View Mess Feedback",
            "8. View Profile",
            "9. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        // First check base student permissions but exclude specific ones
        if (permission.equals("PAY_FEES") || 
            permission.equals("VIEW_NOTICES") ||
            permission.equals("VIEW_ROOM_DETAILS") ||
            permission.equals("SUBMIT_MESS_FEEDBACK") || // Added permission
            permission.equals("VIEW_PROFILE") ||
            permission.equals("UPDATE_PROFILE")) {
            return true;
        }
        
        switch (permission) {
            case "SET_MESS_MENU":
            case "VIEW_MESS_MENU": // Added permission
            case "VIEW_MESS_FEEDBACK":
                return true;
            default:
                return false;
        }
    }
}
