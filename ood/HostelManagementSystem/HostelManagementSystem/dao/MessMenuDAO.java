package dao;

import java.sql.*;
import java.util.Date;
import util.DatabaseConnection;

public class MessMenuDAO {
    private Connection connection;
    
    public MessMenuDAO() {
        this.connection = DatabaseConnection.getConnection();
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS MessMenu (" +
                "menuID INT PRIMARY KEY AUTO_INCREMENT, " +
                "content TEXT NOT NULL, " +
                "weekStarting DATE NOT NULL, " +
                "mmcID INT NOT NULL, " +
                "FOREIGN KEY (mmcID) REFERENCES MMC(mmcID))";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating MessMenu table: " + e.getMessage());
        }
    }
    
    public boolean addMenu(int mmcID, String content) {
        // First check if menu already exists for current week
        if (getCurrentMenu() != null) {
            // Update existing menu instead of adding new one
            return updateCurrentMenu(content);
        }
        
        String query = "INSERT INTO MessMenu (content, weekStarting, mmcID) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, content);
            stmt.setDate(2, new java.sql.Date(new Date().getTime())); // Current date
            stmt.setInt(3, mmcID);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding mess menu: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateCurrentMenu(String content) {
        String query = "UPDATE MessMenu SET content = ? ORDER BY menuID DESC LIMIT 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, content);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating mess menu: " + e.getMessage());
            return false;
        }
    }
    
    public String getCurrentMenu() {
        String query = "SELECT content FROM MessMenu ORDER BY weekStarting DESC LIMIT 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getString("content");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving mess menu: " + e.getMessage());
        }
        
        return null;
    }
}
