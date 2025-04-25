import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecordManager {
    private Record[] entryRecords;
    private Record[] exitRecords;
    private int currentEntryCount;
    private int currentExitCount;
    
    public RecordManager(int capacity) {
        this.entryRecords = new Record[capacity];
        this.exitRecords = new Record[capacity];
        this.currentEntryCount = 0;
        this.currentExitCount = 0;
    }
    
    public String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    
    public void addEntryRecord(String personName) {
        String timestamp = getCurrentTime();
        entryRecords[currentEntryCount] = new Record(personName, timestamp);
        currentEntryCount++;
        System.out.println("Entry recorded at: " + timestamp);
    }
    
    public void addExitRecord(String personName) {
        String timestamp = getCurrentTime();
        exitRecords[currentExitCount] = new Record(personName, timestamp);
        currentExitCount++;
        System.out.println("Exit recorded at: " + timestamp);
    }
    
    public void displayEntryRecords() {
        System.out.println("\n--- ENTRY RECORDS ---");
        System.out.println("No.\tName\t\tTimestamp");
        System.out.println("-------------------------------");
        
        if (currentEntryCount == 0) {
            System.out.println("No entries recorded yet.");
        } else {
            for (int i = 0; i < currentEntryCount; i++) {
                System.out.println((i+1) + ".\t" + entryRecords[i].getPersonName() + "\t\t" + entryRecords[i].getTimestamp());
            }
        }
        System.out.println("-------------------------------\n");
    }
    
    public void displayExitRecords() {
        System.out.println("\n--- EXIT RECORDS ---");
        System.out.println("No.\tName\t\tTimestamp");
        System.out.println("-------------------------------");
        
        if (currentExitCount == 0) {
            System.out.println("No exits recorded yet.");
        } else {
            for (int i = 0; i < currentExitCount; i++) {
                System.out.println((i+1) + ".\t" + exitRecords[i].getPersonName() + "\t\t" + exitRecords[i].getTimestamp());
            }
        }
        System.out.println("-------------------------------\n");
    }
    
    public int getCurrentEntryCount() {
        return currentEntryCount;
    }
    
    public int getCurrentExitCount() {
        return currentExitCount;
    }
}
