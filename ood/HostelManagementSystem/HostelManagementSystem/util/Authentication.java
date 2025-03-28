package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;

public class Authentication {
    
    public static User login(String username, String password) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("userID");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String contactNumber = rs.getString("contactNumber");
                
                // Create and return the appropriate user type based on role
                switch (role) {
                    case "STUDENT":
                        return loadStudent(userID, username, password, name, contactNumber);
                    case "PREFECT":
                        return loadPrefect(userID, username, password, name, contactNumber);
                    case "MMC":
                        return loadMMC(userID, username, password, name, contactNumber);
                    case "WARDEN":
                        return loadWarden(userID, username, password, name, contactNumber);
                    case "ADMIN_STAFF":
                        return loadAdminStaff(userID, username, password, name, contactNumber);
                    case "CLEANER":
                        return loadCleaner(userID, username, password, name, contactNumber);
                    case "MESS_WORKER":
                        return loadMessWorker(userID, username, password, name, contactNumber);
                    case "SECURITY":
                        return loadSecurity(userID, username, password, name, contactNumber);
                    case "SUPERVISOR":
                        return loadSupervisor(userID, username, password, name, contactNumber);
                    case "DEAN_SW":
                        return loadDeanSW(userID, username, password, name, contactNumber);
                    default:
                        System.out.println("Unknown role: " + role);
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        
        return null;
    }
    
    private static Student loadStudent(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Student WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Student student = new Student(userID, username, password, name, contactNumber,
                        rs.getInt("studentID"), rs.getInt("age"));
                student.setRoomNumber(rs.getInt("roomNumber"));
                student.setPaymentStatus(rs.getBoolean("paymentStatus"));
                return student;
            }
        } catch (SQLException e) {
            System.err.println("Error loading student: " + e.getMessage());
        }
        
        return null;
    }
    
    private static Prefect loadPrefect(int userID, String username, String password, String name, String contactNumber) {
        // First load as student
        Student student = loadStudent(userID, username, password, name, contactNumber);
        if (student == null) return null;
        
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Prefect WHERE studentID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, student.getStudentID());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Prefect prefect = new Prefect();
                prefect.setUserID(userID);
                prefect.setUsername(username);
                prefect.setPassword(password);
                prefect.setName(name);
                prefect.setContactNumber(contactNumber);
                prefect.setStudentID(student.getStudentID());
                prefect.setAge(student.getAge());
                prefect.setRoomNumber(student.getRoomNumber());
                prefect.setPaymentStatus(student.isPaymentStatus());
                prefect.setPrefectID(rs.getInt("prefectID"));
                prefect.setResponsibilityArea(rs.getString("responsibilityArea"));
                return prefect;
            }
        } catch (SQLException e) {
            System.err.println("Error loading prefect: " + e.getMessage());
        }
        
        return null;
    }
    
    private static MMC loadMMC(int userID, String username, String password, String name, String contactNumber) {
        // First load as student
        Student student = loadStudent(userID, username, password, name, contactNumber);
        if (student == null) return null;
        
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM MMC WHERE studentID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, student.getStudentID());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MMC mmc = new MMC();
                mmc.setUserID(userID);
                mmc.setUsername(username);
                mmc.setPassword(password);
                mmc.setName(name);
                mmc.setContactNumber(contactNumber);
                mmc.setStudentID(student.getStudentID());
                mmc.setAge(student.getAge());
                mmc.setRoomNumber(student.getRoomNumber());
                mmc.setPaymentStatus(student.isPaymentStatus());
                mmc.setMmcID(rs.getInt("mmcID"));
                mmc.setPosition(rs.getString("position"));
                return mmc;
            }
        } catch (SQLException e) {
            System.err.println("Error loading MMC: " + e.getMessage());
        }
        
        return null;
    }
    
    private static Warden loadWarden(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Warden WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Warden(userID, username, password, name, contactNumber,
                        rs.getInt("wardenID"), rs.getInt("hostelID"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading warden: " + e.getMessage());
        }
        
        return null;
    }
    
    private static AdminStaff loadAdminStaff(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM AdminStaff WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AdminStaff(userID, username, password, name, contactNumber,
                        rs.getInt("staffID"), rs.getString("department"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading admin staff: " + e.getMessage());
        }
        
        return null;
    }
    
    private static Cleaner loadCleaner(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Cleaner WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cleaner(userID, username, password, name, contactNumber,
                        rs.getInt("cleanerID"), rs.getString("area"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading cleaner: " + e.getMessage());
        }
        
        return null;
    }
    
    private static MessWorker loadMessWorker(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM MessWorker WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MessWorker(userID, username, password, name, contactNumber,
                        rs.getInt("workerID"), rs.getString("shift"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading mess worker: " + e.getMessage());
        }
        
        return null;
    }
    
    private static Security loadSecurity(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Security WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Security(userID, username, password, name, contactNumber,
                        rs.getInt("securityID"), rs.getString("shift"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading security: " + e.getMessage());
        }
        
        return null;
    }
    
    private static Supervisor loadSupervisor(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Supervisor WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Supervisor(userID, username, password, name, contactNumber,
                        rs.getInt("supervisorID"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading supervisor: " + e.getMessage());
        }
        
        return null;
    }
    
    private static DeanSW loadDeanSW(int userID, String username, String password, String name, String contactNumber) {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM DeanSW WHERE userID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DeanSW(userID, username, password, name, contactNumber,
                        rs.getInt("deanID"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading dean SW: " + e.getMessage());
        }
        
        return null;
    }
}
