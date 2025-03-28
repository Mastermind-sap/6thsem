CREATE DATABASE IF NOT EXISTS hostel_management;
USE hostel_management;

-- User table for authentication
CREATE TABLE IF NOT EXISTS User (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,  -- STUDENT, WARDEN, ADMIN_STAFF, etc.
    name VARCHAR(100) NOT NULL,
    contactNumber VARCHAR(20) NOT NULL
);

-- Hostel table
CREATE TABLE IF NOT EXISTS Hostel (
    hostelID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(200) NOT NULL,
    totalRooms INT NOT NULL
);

-- Room table
CREATE TABLE IF NOT EXISTS Room (
    roomNumber INT PRIMARY KEY,
    roomType VARCHAR(50) NOT NULL,
    occupancyStatus VARCHAR(20) NOT NULL,
    hostelID INT,
    FOREIGN KEY (hostelID) REFERENCES Hostel(hostelID)
);

-- Student table with reference to User
CREATE TABLE IF NOT EXISTS Student (
    studentID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    age INT NOT NULL,
    roomNumber INT NULL,
    paymentStatus BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (roomNumber) REFERENCES Room(roomNumber) ON DELETE SET NULL
);

-- Warden table with reference to User
CREATE TABLE IF NOT EXISTS Warden (
    wardenID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    hostelID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (hostelID) REFERENCES Hostel(hostelID)
);

-- Admin Staff table with reference to User
CREATE TABLE IF NOT EXISTS AdminStaff (
    staffID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    department VARCHAR(100) DEFAULT 'Administration',
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Fees table
CREATE TABLE IF NOT EXISTS Fees (
    feesID INT PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    dueDate DATE NOT NULL,
    isPaid BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

-- Complaint table
CREATE TABLE IF NOT EXISTS Complaint (
    complaintID INT PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,  -- Pending, Approved, Resolved, Rejected
    dateSubmitted TIMESTAMP NOT NULL,
    dateResolved TIMESTAMP NULL,
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

-- Notice table
CREATE TABLE IF NOT EXISTS Notice (
    noticeID INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    datePublished TIMESTAMP NOT NULL,
    publishedByUserID INT NOT NULL,
    FOREIGN KEY (publishedByUserID) REFERENCES User(userID)
);

-- Prefect table with reference to Student
CREATE TABLE IF NOT EXISTS Prefect (
    prefectID INT PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    responsibilityArea VARCHAR(100) NOT NULL,
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

-- MMC table with reference to Student
CREATE TABLE IF NOT EXISTS MMC (
    mmcID INT PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    position VARCHAR(50) NOT NULL,
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

-- Cleaner table with reference to User
CREATE TABLE IF NOT EXISTS Cleaner (
    cleanerID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    area VARCHAR(100) NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Mess Worker table with reference to User
CREATE TABLE IF NOT EXISTS MessWorker (
    workerID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    shift VARCHAR(20) NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Security table with reference to User
CREATE TABLE IF NOT EXISTS Security (
    securityID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    shift VARCHAR(20) NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Supervisor table with reference to User
CREATE TABLE IF NOT EXISTS Supervisor (
    supervisorID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Dean SW table with reference to User
CREATE TABLE IF NOT EXISTS DeanSW (
    deanID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Cleaning Request table
CREATE TABLE IF NOT EXISTS CleaningRequest (
    requestID INT PRIMARY KEY AUTO_INCREMENT,
    prefectID INT NOT NULL,
    area VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    dateSubmitted TIMESTAMP NOT NULL,
    dateResolved TIMESTAMP NULL,
    FOREIGN KEY (prefectID) REFERENCES Prefect(prefectID)
);

-- Mess Menu table
CREATE TABLE IF NOT EXISTS MessMenu (
    menuID INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    weekStarting DATE NOT NULL,
    mmcID INT NOT NULL,
    FOREIGN KEY (mmcID) REFERENCES MMC(mmcID)
);

-- Mess Feedback table
CREATE TABLE IF NOT EXISTS MessFeedback (
    feedbackID INT PRIMARY KEY AUTO_INCREMENT,
    studentID INT NOT NULL,
    studentName VARCHAR(100) NOT NULL,
    rating INT NOT NULL,
    comment TEXT NOT NULL,
    dateSubmitted TIMESTAMP NOT NULL,
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

-- Insert sample hostel data (IMPORTANT: needed for Warden registration)
INSERT INTO Hostel (name, location, totalRooms) 
SELECT 'Boys Hostel', 'North Campus', 100 FROM dual 
WHERE NOT EXISTS (SELECT 1 FROM Hostel WHERE name = 'Boys Hostel');

INSERT INTO Hostel (name, location, totalRooms) 
SELECT 'Girls Hostel', 'South Campus', 80 FROM dual 
WHERE NOT EXISTS (SELECT 1 FROM Hostel WHERE name = 'Girls Hostel');

-- Insert sample rooms
INSERT INTO Room (roomNumber, roomType, occupancyStatus, hostelID)
SELECT 101, 'Single', 'Vacant', 1 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM Room WHERE roomNumber = 101);

INSERT INTO Room (roomNumber, roomType, occupancyStatus, hostelID)
SELECT 102, 'Double', 'Vacant', 1 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM Room WHERE roomNumber = 102);

INSERT INTO Room (roomNumber, roomType, occupancyStatus, hostelID)
SELECT 103, 'Triple', 'Vacant', 1 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM Room WHERE roomNumber = 103);

INSERT INTO Room (roomNumber, roomType, occupancyStatus, hostelID)
SELECT 201, 'Single', 'Vacant', 2 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM Room WHERE roomNumber = 201);

INSERT INTO Room (roomNumber, roomType, occupancyStatus, hostelID)
SELECT 202, 'Double', 'Vacant', 2 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM Room WHERE roomNumber = 202);
