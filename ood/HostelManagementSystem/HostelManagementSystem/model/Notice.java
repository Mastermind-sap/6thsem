package model;

import java.util.Date;

public class Notice {
    private int noticeID;
    private String content;
    private Date datePublished;
    private int publishedByUserID;
    
    public Notice() {}
    
    public Notice(int noticeID, String content, int publishedByUserID) {
        this.noticeID = noticeID;
        this.content = content;
        this.datePublished = new Date();
        this.publishedByUserID = publishedByUserID;
    }
    
    // Getters and setters
    public int getNoticeID() { return noticeID; }
    public void setNoticeID(int noticeID) { this.noticeID = noticeID; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Date getDatePublished() { return datePublished; }
    public void setDatePublished(Date datePublished) { this.datePublished = datePublished; }
    
    public int getPublishedByUserID() { return publishedByUserID; }
    public void setPublishedByUserID(int publishedByUserID) { this.publishedByUserID = publishedByUserID; }
    
    // Methods
    public void publishNotice() {
        System.out.println("Publishing notice: " + content);
        this.datePublished = new Date();
    }
    
    public void displayNotice() {
        System.out.println("-------------- NOTICE --------------");
        System.out.println("Date: " + datePublished);
        System.out.println(content);
        System.out.println("-----------------------------------");
    }
}
