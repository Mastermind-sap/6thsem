package service;

import java.util.List;
import java.util.Scanner;

import dao.MessFeedbackDAO;
import model.MessFeedback;
import model.Student;

public class FeedbackService {
    private Scanner scanner;
    private MessFeedbackDAO messFeedbackDAO;
    
    public FeedbackService(Scanner scanner) {
        this.scanner = scanner;
        this.messFeedbackDAO = new MessFeedbackDAO();
    }
    
    public void viewMessFeedback() {
        System.out.println("\n----- MESS FEEDBACK -----");
        List<MessFeedback> feedbackList = messFeedbackDAO.getAllFeedback();
        
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available at the moment.");
        } else {
            double averageRating = messFeedbackDAO.getAverageRating();
            System.out.printf("Average Rating: %.1f stars\n\n", averageRating);
            
            for (MessFeedback feedback : feedbackList) {
                System.out.println("Student: " + feedback.getStudentName() + 
                                 " | Rating: " + feedback.getRating() + " stars" +
                                 " | Date: " + feedback.getDateSubmitted());
                System.out.println("Comment: " + feedback.getComment());
                System.out.println("------------------------------");
            }
        }
    }
    
    public void submitMessFeedback(Student student) {
        System.out.println("\n----- SUBMIT MESS FEEDBACK -----");
        System.out.println("Please rate the mess food (1-5 stars):");
        System.out.print("Star rating: ");
        int rating = scanner.nextInt();
        scanner.nextLine();  
        
        // Validate rating
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please enter a number between 1 and 5.");
            return;
        }
        
        System.out.print("Enter detailed feedback: ");
        String comment = scanner.nextLine();
        
        // Create and save the feedback
        MessFeedback feedback = new MessFeedback();
        feedback.setStudentID(student.getStudentID());
        feedback.setStudentName(student.getName());
        feedback.setRating(rating);
        feedback.setComment(comment);
        
        if (messFeedbackDAO.addFeedback(feedback)) {
            System.out.println("Thank you for your feedback!");
        } else {
            System.out.println("Error saving feedback. Please try again later.");
        }
    }
}
