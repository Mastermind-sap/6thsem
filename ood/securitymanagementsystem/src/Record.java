public class Record {
    private String personName;
    private String timestamp;

    public Record(String personName, String timestamp) {
        this.personName = personName;
        this.timestamp = timestamp;
    }

    public String getPersonName() {
        return personName;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
