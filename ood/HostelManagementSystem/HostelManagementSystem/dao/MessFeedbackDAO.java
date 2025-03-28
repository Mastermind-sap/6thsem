package dao;

import model.MessFeedback;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessFeedbackDAO {
    private Connection connection;
    
    public MessFeedbackDAO() {
        this.connection = DatabaseConnection.getConnection();
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS MessFeedback (" +
                "feedbackID INT PRIMARY KEY AUTO_INCREMENT, " +
                "studentID INT NOT NULL, " +
                "studentName VARCHAR(100) NOT NULL, " +
                "rating INT NOT NULL, " +
                "comment TEXT NOT NULL, " +
                "dateSubmitted TIMESTAMP NOT NULL, " +
                "FOREIGN KEY (studentID) REFERENCES Student(studentID))";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating MessFeedback table: " + e.getMessage());
        }
    }
    
    public boolean addFeedback(MessFeedback feedback) {
        String query = "INSERT INTO MessFeedback (studentID, studentName, rating, comment, dateSubmitted) " +
                       "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, feedback.getStudentID());
            stmt.setString(2, feedback.getStudentName());
            stmt.setInt(3, feedback.getRating());
            stmt.setString(4, feedback.getComment());
            stmt.setTimestamp(5, new Timestamp(feedback.getDateSubmitted().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    feedback.setFeedbackID(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding mess feedback: " + e.getMessage());
            return false;
        }
    }
    
    public List<MessFeedback> getAllFeedback() {
        List<MessFeedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM MessFeedback ORDER BY dateSubmitted DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                MessFeedback feedback = new MessFeedback();
                feedback.setFeedbackID(rs.getInt("feedbackID"));
                feedback.setStudentID(rs.getInt("studentID"));
                feedback.setStudentName(rs.getString("studentName"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                feedback.setDateSubmitted(rs.getTimestamp("dateSubmitted"));
                
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving mess feedback: " + e.getMessage());
        }
        
        return feedbackList;
    }
    
    public double getAverageRating() {
        String query = "SELECT AVG(rating) as averageRating FROM MessFeedback";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getDouble("averageRating");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating average rating: " + e.getMessage());
        }
        
        return 0.0;
    }
}
