package dao;

import model.Student;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;
    
    public StudentDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public boolean addStudent(Student student) {
        String query = "INSERT INTO Student (name, age, contactNumber) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getContactNumber());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setStudentID(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }
    
    public Student getStudentById(int studentId) {
        String query = "SELECT s.*, u.name, u.contactNumber, u.userID FROM Student s " +
                       "JOIN User u ON s.userID = u.userID " +
                       "WHERE s.studentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Student student = new Student();
                student.setStudentID(rs.getInt("studentID"));
                student.setUserID(rs.getInt("userID"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setContactNumber(rs.getString("contactNumber"));
                student.setRoomNumber(rs.getInt("roomNumber"));
                student.setPaymentStatus(rs.getBoolean("paymentStatus"));
                return student;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        return null;
    }
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, u.name, u.contactNumber, u.userID FROM Student s " +
                       "JOIN User u ON s.userID = u.userID";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentID(rs.getInt("studentID"));
                student.setUserID(rs.getInt("userID"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setContactNumber(rs.getString("contactNumber"));
                student.setRoomNumber(rs.getInt("roomNumber"));
                student.setPaymentStatus(rs.getBoolean("paymentStatus"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
        
        return students;
    }
    
    public boolean updateStudent(Student student) {
        String query = "UPDATE Student SET name = ?, age = ?, contactNumber = ?, roomNumber = ? WHERE studentID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getContactNumber());
            stmt.setInt(4, student.getRoomNumber());
            stmt.setInt(5, student.getStudentID());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteStudent(int studentId) {
        String query = "DELETE FROM Student WHERE studentID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
    
    public boolean assignRoomToStudent(int studentId, int roomNumber) {
        String query = "UPDATE Student SET roomNumber = ? WHERE studentID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomNumber);
            stmt.setInt(2, studentId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error assigning room to student: " + e.getMessage());
            return false;
        }
    }
}
