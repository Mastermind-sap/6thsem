package model;

import java.util.ArrayList;
import java.util.List;

public class Hostel {
    private int hostelID;
    private String name;
    private String location;
    private int totalRooms;
    private List<Room> rooms;
    
    public Hostel() {
        this.rooms = new ArrayList<>();
    }
    
    public Hostel(int hostelID, String name, String location, int totalRooms) {
        this.hostelID = hostelID;
        this.name = name;
        this.location = location;
        this.totalRooms = totalRooms;
        this.rooms = new ArrayList<>();
    }
    
    // Getters and setters
    public int getHostelID() { return hostelID; }
    public void setHostelID(int hostelID) { this.hostelID = hostelID; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public int getTotalRooms() { return totalRooms; }
    public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }
    
    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
    
    // Methods from class diagram
    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room " + room.getRoomNumber() + " added to hostel " + name);
        totalRooms++;
    }
    
    public void removeRoom(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
        System.out.println("Room " + roomNumber + " removed from hostel " + name);
        totalRooms--;
    }
    
    public void getHostelDetails() {
        System.out.println("Hostel Details:");
        System.out.println("ID: " + hostelID);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Total Rooms: " + totalRooms);
    }
}
