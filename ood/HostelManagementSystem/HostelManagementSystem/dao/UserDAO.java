package dao;

import model.User;
import model.Student;
import model.Warden;
import model.AdminStaff;
import model.Prefect;
import model.MMC;
import model.Cleaner;
import model.MessWorker;
import model.Security;
import model.Supervisor;
import model.DeanSW;
import util.DatabaseConnection;

import java.sql.*;

public class UserDAO {
    private Connection connection;
    
    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public boolean addUser(User user) {
        // First check if username already exists
        if (usernameExists(user.getUsername())) {
            System.err.println("Username already exists: " + user.getUsername());
            return false;
        }
        
        String query = "INSERT INTO User (username, password, role, name, contactNumber) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getContactNumber());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserID(generatedKeys.getInt(1));
                    
                    // Add role-specific information
                    if (user instanceof Student) {
                        if (user instanceof Prefect) {
                            return addPrefect((Prefect) user);
                        } else if (user instanceof MMC) {
                            return addMMC((MMC) user);
                        } else {
                            return addStudent((Student) user);
                        }
                    } else if (user instanceof Warden) {
                        return addWarden((Warden) user);
                    } else if (user instanceof AdminStaff) {
                        return addAdminStaff((AdminStaff) user);
                    } else if (user instanceof Cleaner) {
                        return addCleaner((Cleaner) user);
                    } else if (user instanceof MessWorker) {
                        return addMessWorker((MessWorker) user);
                    } else if (user instanceof Security) {
                        return addSecurity((Security) user);
                    } else if (user instanceof Supervisor) {
                        return addSupervisor((Supervisor) user);
                    } else if (user instanceof DeanSW) {
                        return addDeanSW((DeanSW) user);
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }
    
    public boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM User WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }
        
        return false;
    }
    
    private boolean addStudent(Student student) {
        String query = "INSERT INTO Student (userID, age, paymentStatus) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, student.getUserID());
            stmt.setInt(2, student.getAge());
            stmt.setBoolean(3, student.isPaymentStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setStudentID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addWarden(Warden warden) {
        String query = "INSERT INTO Warden (userID, hostelID) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, warden.getUserID());
            stmt.setInt(2, warden.getHostelID());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        warden.setWardenID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding warden: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addAdminStaff(AdminStaff staff) {
        String query = "INSERT INTO AdminStaff (userID, department) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, staff.getUserID());
            stmt.setString(2, staff.getDepartment());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        staff.setStaffID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding admin staff: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addPrefect(Prefect prefect) {
        // First add as a student
        if (!addStudent(prefect)) {
            return false;
        }
        
        // Then add prefect specific information
        String query = "INSERT INTO Prefect (studentID, responsibilityArea) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, prefect.getStudentID());
            stmt.setString(2, prefect.getResponsibilityArea());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prefect.setPrefectID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding prefect: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addMMC(MMC mmc) {
        // First add as a student
        if (!addStudent(mmc)) {
            return false;
        }
        
        // Then add MMC specific information
        String query = "INSERT INTO MMC (studentID, position) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, mmc.getStudentID());
            stmt.setString(2, mmc.getPosition());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mmc.setMmcID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding MMC member: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addCleaner(Cleaner cleaner) {
        String query = "INSERT INTO Cleaner (userID, area) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cleaner.getUserID());
            stmt.setString(2, cleaner.getArea());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cleaner.setCleanerID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding cleaner: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addMessWorker(MessWorker messWorker) {
        String query = "INSERT INTO MessWorker (userID, shift) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, messWorker.getUserID());
            stmt.setString(2, messWorker.getShift());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        messWorker.setWorkerID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding mess worker: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addSecurity(Security security) {
        String query = "INSERT INTO Security (userID, shift) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, security.getUserID());
            stmt.setString(2, security.getShift());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        security.setSecurityID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding security: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addSupervisor(Supervisor supervisor) {
        String query = "INSERT INTO Supervisor (userID) VALUES (?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, supervisor.getUserID());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        supervisor.setSupervisorID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding supervisor: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addDeanSW(DeanSW deanSW) {
        String query = "INSERT INTO DeanSW (userID) VALUES (?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, deanSW.getUserID());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        deanSW.setDeanID(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding Dean SW: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateUser(User user) {
        // ...existing code...
        return true;
    }
    
    public boolean deleteUser(int userID) {
        // ...existing code...
        return true;
    }
}
