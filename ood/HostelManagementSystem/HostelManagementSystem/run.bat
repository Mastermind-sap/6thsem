@echo off
echo === Hostel Management System Build ^& Run Script ===

rem Set variables
set MYSQL_CONNECTOR=lib\mysql-connector-java-8.0.23.jar
set MAIN_CLASS=Main
set OUTPUT_DIR=bin

rem Ensure output directory exists
if not exist %OUTPUT_DIR% mkdir %OUTPUT_DIR%

rem Check if MySQL connector exists
if not exist %MYSQL_CONNECTOR% (
  echo ERROR: MySQL connector JAR not found at %MYSQL_CONNECTOR%
  echo Please make sure the JAR file is in the correct location.
  exit /b 1
)

echo Compiling Java files...
javac -d %OUTPUT_DIR% -cp ".;%MYSQL_CONNECTOR%" src\model\*.java src\dao\*.java src\util\*.java *.java

rem Check if compilation was successful
if %ERRORLEVEL% neq 0 (
  echo Compilation failed. Please fix any errors and try again.
  exit /b 1
)

echo Compilation successful!
echo Running application...
java -cp "%OUTPUT_DIR%;%MYSQL_CONNECTOR%" %MAIN_CLASS%

echo Application terminated.
