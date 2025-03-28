package model;

public class Student extends User {
    private int studentID;
    private int age;
    private int roomNumber;
    private boolean paymentStatus;
    
    public Student() {
        super();
        setRole("STUDENT");
    }
    
    public Student(int userID, String username, String password, String name, String contactNumber,
                  int studentID, int age) {
        super(userID, username, password, "STUDENT", name, contactNumber);
        this.studentID = studentID;
        this.age = age;
    }
    
    // Getters and setters
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    
    public boolean isPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(boolean paymentStatus) { this.paymentStatus = paymentStatus; }
    
    // Methods from class diagram
    public boolean payFees(double amount) {
        System.out.println("Processing fee payment of $" + amount + " for student " + getName());
        this.paymentStatus = true;
        return true;
    }
    
    public void viewDetails() {
        System.out.println("Student Details:");
        System.out.println("ID: " + studentID);
        System.out.println("Name: " + getName());
        System.out.println("Age: " + age);
        System.out.println("Contact: " + getContactNumber());
        System.out.println("Room Number: " + roomNumber);
    }
    
    // Add method to submit mess feedback
    public void submitMessFeedback(String feedback) {
        System.out.println("Student " + getName() + " submitted mess feedback: " + feedback);
        // In a real system, this would save to database
    }

    @Override
    public String[] getDashboardOptions() {
        return new String[] {
            "1. Pay Fees",
            "2. View Notices",
            "3. View Room Details",
            "4. Submit Mess Feedback", // Added new option
            "5. View Profile",
            "6. Update Profile",
            "0. Logout"
        };
    }
    
    @Override
    public boolean hasPermission(String permission) {
        switch (permission) {
            case "PAY_FEES":
            case "VIEW_NOTICES":
            case "VIEW_ROOM_DETAILS":
            case "SUBMIT_MESS_FEEDBACK": // Added new permission
            case "VIEW_PROFILE":
            case "UPDATE_PROFILE":
                return true;
            default:
                return false;
        }
    }
}
