package model;

public class Room {
    private int roomNumber;
    private String roomType;
    private String occupancyStatus;
    private int hostelID;
    
    public Room() {}
    
    public Room(int roomNumber, String roomType, String occupancyStatus, int hostelID) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.occupancyStatus = occupancyStatus;
        this.hostelID = hostelID;
    }
    
    // Getters and setters
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    
    public String getOccupancyStatus() { return occupancyStatus; }
    public void setOccupancyStatus(String occupancyStatus) { this.occupancyStatus = occupancyStatus; }
    
    public int getHostelID() { return hostelID; }
    public void setHostelID(int hostelID) { this.hostelID = hostelID; }
    
    // Methods from class diagram
    public void allocateRoom(Student student) {
        System.out.println("Allocating room " + roomNumber + " to student " + student.getName());
        this.occupancyStatus = "Occupied";
    }
    
    public void vacateRoom() {
        System.out.println("Vacating room " + roomNumber);
        this.occupancyStatus = "Vacant";
    }
    
    public void getRoomDetails() {
        System.out.println("Room Details:");
        System.out.println("Number: " + roomNumber);
        System.out.println("Type: " + roomType);
        System.out.println("Status: " + occupancyStatus);
        System.out.println("Hostel ID: " + hostelID);
    }
}
