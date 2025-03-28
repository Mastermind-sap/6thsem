package dao;

import model.Notice;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {
    private Connection connection;
    
    public NoticeDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public boolean addNotice(Notice notice) {
        String query = "INSERT INTO Notice (content, datePublished, publishedByUserID) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, notice.getContent());
            stmt.setTimestamp(2, new java.sql.Timestamp(notice.getDatePublished().getTime()));
            stmt.setInt(3, notice.getPublishedByUserID());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notice.setNoticeID(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding notice: " + e.getMessage());
            return false;
        }
    }
    
    public List<Notice> getAllNotices() {
        List<Notice> notices = new ArrayList<>();
        String query = "SELECT * FROM Notice ORDER BY datePublished DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Notice notice = new Notice();
                notice.setNoticeID(rs.getInt("noticeID"));
                notice.setContent(rs.getString("content"));
                notice.setDatePublished(rs.getTimestamp("datePublished"));
                notice.setPublishedByUserID(rs.getInt("publishedByUserID"));
                
                notices.add(notice);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving notices: " + e.getMessage());
        }
        
        return notices;
    }
}
