package model;

public abstract class User {
    private int userID;
    private String username;
    private String password;
    private String role;
    private String name;
    private String contactNumber;
    
    public User() {}
    
    public User(int userID, String username, String password, String role, String name, String contactNumber) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.contactNumber = contactNumber;
    }
    
    // Getters and setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    // Abstract method for role-specific dashboard options
    public abstract String[] getDashboardOptions();
    
    // Method to check if user has a specific permission
    public abstract boolean hasPermission(String permission);
}
