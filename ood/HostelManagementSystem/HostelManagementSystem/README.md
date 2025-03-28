# Hostel Management System

A Java application for managing student accommodation, room allocation, and fee records.

## Overview

The Hostel Management System helps hostel wardens and administrators efficiently manage hostel operations with these key features:
- Student registration and management
- Room allocation and tracking
- Fee payment processing
- Report generation

## Project Structure

- `src`: Source files
  - `model`: Contains domain classes (Student, Room, Hostel, Warden, Fees)
  - `dao`: Data Access Objects for database operations
  - `util`: Utility classes including database connection
- `lib`: JDBC drivers and other dependencies
- `sql`: Database creation scripts
- `bin`: Compiled output

## Setup Instructions

### Prerequisites
- JDK 8 or later
- MySQL Server
- MySQL Connector/J (JDBC driver)

### Database Setup
1. Open MySQL command line or a GUI client
2. Run the SQL script from `sql/create_tables.sql`:
   ```
   mysql -u root -p < sql/create_tables.sql
   ```
3. Update database connection settings in `util/DatabaseConnection.java` if necessary

### Compiling and Running
You can compile and run the application in two ways:

#### Option 1: Using the automated scripts
1. For Unix-like systems (macOS, Linux):
   ```
   chmod +x run.sh  # Make the script executable (first time only)
   ./run.sh
   ```

2. For Windows:
   ```
   run.bat
   ```

#### Option 2: Manual compilation
1. Compile the source code:
   ```
   javac -d bin -cp ".;lib/mysql-connector-java-8.0.23.jar" src/**/*.java
   ```
   (On Unix-like systems, use `:` instead of `;` in the classpath)

2. Run the application:
   ```
   java -cp "bin;lib/mysql-connector-java-8.0.23.jar" Main
   ```
   (On Unix-like systems, use `:` instead of `;` in the classpath)

## Using the System

Follow the on-screen menu to:
1. Add new students
2. View student records
3. Assign rooms to students
4. Process fee payments
5. Generate receipts
