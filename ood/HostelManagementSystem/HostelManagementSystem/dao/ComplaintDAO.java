package dao;

import model.Complaint;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    private Connection connection;
    
    public ComplaintDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public boolean addComplaint(Complaint complaint) {
        String query = "INSERT INTO Complaint (studentID, description, status, dateSubmitted) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, complaint.getStudentID());
            stmt.setString(2, complaint.getDescription());
            stmt.setString(3, complaint.getStatus());
            stmt.setTimestamp(4, new java.sql.Timestamp(complaint.getDateSubmitted().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    complaint.setComplaintID(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding complaint: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateComplaintStatus(int complaintID, String status) {
        String query = "UPDATE Complaint SET status = ?, dateResolved = ? WHERE complaintID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            
            if ("Resolved".equals(status)) {
                stmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            } else {
                stmt.setNull(2, Types.TIMESTAMP);
            }
            
            stmt.setInt(3, complaintID);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating complaint status: " + e.getMessage());
            return false;
        }
    }
    
    public Complaint getComplaintById(int complaintID) {
        String query = "SELECT * FROM Complaint WHERE complaintID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, complaintID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintID(rs.getInt("complaintID"));
                complaint.setStudentID(rs.getInt("studentID"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                complaint.setDateSubmitted(rs.getTimestamp("dateSubmitted"));
                
                Timestamp resolvedDate = rs.getTimestamp("dateResolved");
                if (resolvedDate != null) {
                    complaint.setDateResolved(resolvedDate);
                }
                
                return complaint;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving complaint: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Complaint> getComplaintsByStudentId(int studentID) {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM Complaint WHERE studentID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintID(rs.getInt("complaintID"));
                complaint.setStudentID(rs.getInt("studentID"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                complaint.setDateSubmitted(rs.getTimestamp("dateSubmitted"));
                
                Timestamp resolvedDate = rs.getTimestamp("dateResolved");
                if (resolvedDate != null) {
                    complaint.setDateResolved(resolvedDate);
                }
                
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving complaints: " + e.getMessage());
        }
        
        return complaints;
    }
    
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM Complaint";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintID(rs.getInt("complaintID"));
                complaint.setStudentID(rs.getInt("studentID"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                complaint.setDateSubmitted(rs.getTimestamp("dateSubmitted"));
                
                Timestamp resolvedDate = rs.getTimestamp("dateResolved");
                if (resolvedDate != null) {
                    complaint.setDateResolved(resolvedDate);
                }
                
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving complaints: " + e.getMessage());
        }
        
        return complaints;
    }
}
