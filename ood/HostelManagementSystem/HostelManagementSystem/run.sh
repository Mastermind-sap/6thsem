#!/bin/bash

echo "=== Hostel Management System Build & Run Script ==="

# Set variables
MYSQL_CONNECTOR="lib/mysql-connector-java-8.0.23.jar"
MAIN_CLASS="Main"
OUTPUT_DIR="bin"
MYSQL_USER="root"
MYSQL_PASSWORD=""
SQL_SCRIPT="sql/create_tables.sql"

# Ensure output directory exists
mkdir -p $OUTPUT_DIR

# Check if MySQL connector exists
if [ ! -f "$MYSQL_CONNECTOR" ]; then
  echo "ERROR: MySQL connector JAR not found at $MYSQL_CONNECTOR"
  echo "Please make sure the JAR file is in the correct location."
  exit 1
fi

# Initialize database
echo "Setting up MySQL database..."
if [ ! -f "$SQL_SCRIPT" ]; then
  echo "ERROR: SQL script not found at $SQL_SCRIPT"
  exit 1
fi

# Create database and tables
mysql -u $MYSQL_USER --password="$MYSQL_PASSWORD" < $SQL_SCRIPT
if [ $? -ne 0 ]; then
  echo "WARNING: There was an issue with the database setup."
  echo "The application may still run if the database already exists."
else
  echo "Database setup completed successfully."
fi

echo "Compiling Java files..."
javac -d $OUTPUT_DIR -cp ".:$MYSQL_CONNECTOR" $(find . -name "*.java") 

# Check if compilation was successful
if [ $? -ne 0 ]; then
  echo "Compilation failed. Please fix any errors and try again."
  exit 1
fi

echo "Compilation successful!"
echo "Running application..."
java -cp "$OUTPUT_DIR:$MYSQL_CONNECTOR" $MAIN_CLASS

echo "Application terminated."
