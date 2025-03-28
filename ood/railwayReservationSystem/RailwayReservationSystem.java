import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Railway Reservation System
 * This system allows users to search for trains, book tickets, and cancel reservations.
 * Admin functionality is also included for managing trains and schedules.
 */
public class RailwayReservationSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static TrainManager trainManager = new TrainManager();
    private static ReservationManager reservationManager = new ReservationManager();
    
    public static void main(String[] args) {
        // Initialize sample data
        initializeData();
        
        System.out.println("===============================================");
        System.out.println("    WELCOME TO RAILWAY RESERVATION SYSTEM");
        System.out.println("===============================================");
        
        boolean running = true;
        while (running) {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Search Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Check Reservation Status");
            System.out.println("5. Admin Login");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        searchTrains();
                        break;
                    case 2:
                        bookTicket();
                        break;
                    case 3:
                        cancelReservation();
                        break;
                    case 4:
                        checkReservationStatus();
                        break;
                    case 5:
                        adminLogin();
                        break;
                    case 6:
                        running = false;
                        System.out.println("\nThank you for using Railway Reservation System!");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
    
    private static void initializeData() {
        // Add sample trains
        Train train1 = new Train(1001, "Rajdhani Express", "Delhi", "Mumbai", 1200.0, 100);
        Train train2 = new Train(1002, "Shatabdi Express", "Mumbai", "Bangalore", 800.0, 120);
        Train train3 = new Train(1003, "Duronto Express", "Chennai", "Kolkata", 1500.0, 80);
        
        trainManager.addTrain(train1);
        trainManager.addTrain(train2);
        trainManager.addTrain(train3);
    }
    
    private static void searchTrains() {
        System.out.println("\n=== SEARCH TRAINS ===");
        System.out.print("Enter source station: ");
        String source = scanner.nextLine().trim();
        System.out.print("Enter destination station: ");
        String destination = scanner.nextLine().trim();
        
        List<Train> trains = trainManager.searchTrains(source, destination);
        
        if (trains.isEmpty()) {
            System.out.println("\nNo trains found for the given route.");
        } else {
            System.out.println("\nAvailable Trains:");
            System.out.println("------------------------------------------------");
            for (Train train : trains) {
                System.out.println(train);
            }
            System.out.println("------------------------------------------------");
        }
    }
    
    private static void bookTicket() {
        System.out.println("\n=== BOOK TICKET ===");
        
        // Display all trains
        List<Train> allTrains = trainManager.getAllTrains();
        if (allTrains.isEmpty()) {
            System.out.println("No trains available in the system.");
            return;
        }
        
        System.out.println("Available Trains:");
        System.out.println("------------------------------------------------");
        for (Train train : allTrains) {
            System.out.println(train);
        }
        System.out.println("------------------------------------------------");
        
        // Get train selection
        System.out.print("Enter train number: ");
        try {
            int trainNumber = Integer.parseInt(scanner.nextLine().trim());
            Train selectedTrain = trainManager.getTrainByNumber(trainNumber);
            
            if (selectedTrain == null) {
                System.out.println("Invalid train number.");
                return;
            }
            
            // Get passenger details
            System.out.print("Enter number of seats to book: ");
            int numSeats = Integer.parseInt(scanner.nextLine().trim());
            
            if (numSeats <= 0) {
                System.out.println("Number of seats must be greater than zero.");
                return;
            }
            
            if (numSeats > selectedTrain.getAvailableSeats()) {
                System.out.println("Sorry, only " + selectedTrain.getAvailableSeats() + " seats available.");
                return;
            }
            
            List<Passenger> passengers = new ArrayList<>();
            
            for (int i = 0; i < numSeats; i++) {
                System.out.println("\nPassenger #" + (i + 1) + " details:");
                System.out.print("Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Age: ");
                int age = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Gender (M/F/O): ");
                String gender = scanner.nextLine().trim();
                System.out.print("Contact number: ");
                String contactNumber = scanner.nextLine().trim();
                
                passengers.add(new Passenger(name, age, gender, contactNumber));
            }
            
            // Process payment
            double totalFare = selectedTrain.getTicketPrice() * numSeats;
            System.out.println("\nTotal fare: Rs. " + totalFare);
            System.out.println("Select payment method:");
            System.out.println("1. Cash");
            System.out.println("2. Credit Card");
            System.out.print("Enter choice: ");
            int paymentChoice = Integer.parseInt(scanner.nextLine().trim());
            
            Payment payment;
            if (paymentChoice == 1) {
                payment = new CashPayment(totalFare);
            } else if (paymentChoice == 2) {
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine().trim();
                System.out.print("Enter card holder name: ");
                String cardHolderName = scanner.nextLine().trim();
                System.out.print("Enter expiry date (MM/YY): ");
                String expiryDate = scanner.nextLine().trim();
                payment = new CreditCardPayment(totalFare, cardNumber, cardHolderName, expiryDate);
            } else {
                System.out.println("Invalid payment option.");
                return;
            }
            
            // Create reservation
            Reservation reservation = reservationManager.createReservation(selectedTrain, passengers, numSeats);
            
            if (reservation != null && payment.processPayment()) {
                selectedTrain.bookSeats(numSeats);
                reservation.confirmReservation(payment);
                System.out.println("\nReservation successful!");
                System.out.println("Your PNR number is: " + reservation.getPnrNumber());
                System.out.println("\nReservation details:");
                System.out.println(reservation);
            } else {
                System.out.println("Reservation failed. Please try again.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
    
    private static void cancelReservation() {
        System.out.println("\n=== CANCEL RESERVATION ===");
        System.out.print("Enter PNR number: ");
        try {
            int pnrNumber = Integer.parseInt(scanner.nextLine().trim());
            Reservation reservation = reservationManager.getReservation(pnrNumber);
            
            if (reservation == null) {
                System.out.println("No reservation found with PNR: " + pnrNumber);
                return;
            }
            
            System.out.println("\nReservation details:");
            System.out.println(reservation);
            
            System.out.print("\nDo you want to cancel this reservation? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            
            if (confirm.equalsIgnoreCase("Y")) {
                if (reservationManager.cancelReservation(pnrNumber)) {
                    System.out.println("Reservation cancelled successfully.");
                    System.out.println("A refund of Rs. " + reservation.getTotalFare() * 0.9 + 
                                      " (10% cancellation fee applied) will be processed.");
                } else {
                    System.out.println("Failed to cancel reservation.");
                }
            } else {
                System.out.println("Cancellation abandoned.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid PNR number format.");
        }
    }
    
    private static void checkReservationStatus() {
        System.out.println("\n=== CHECK RESERVATION STATUS ===");
        System.out.print("Enter PNR number: ");
        try {
            int pnrNumber = Integer.parseInt(scanner.nextLine().trim());
            Reservation reservation = reservationManager.getReservation(pnrNumber);
            
            if (reservation == null) {
                System.out.println("No reservation found with PNR: " + pnrNumber);
            } else {
                System.out.println("\nReservation details:");
                System.out.println(reservation);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid PNR number format.");
        }
    }
    
    private static void adminLogin() {
        System.out.println("\n=== ADMIN LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        // Simple admin authentication (in real system, this would be more secure)
        if ("admin".equals(username) && "admin123".equals(password)) {
            adminMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }
    
    private static void adminMenu() {
        boolean adminSession = true;
        
        while (adminSession) {
            System.out.println("\nADMIN MENU");
            System.out.println("1. Add Train");
            System.out.println("2. Display All Trains");
            System.out.println("3. Update Train");
            System.out.println("4. Remove Train");
            System.out.println("5. View All Reservations");
            System.out.println("6. Logout");
            System.out.print("\nEnter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addTrain();
                        break;
                    case 2:
                        displayAllTrains();
                        break;
                    case 3:
                        updateTrain();
                        break;
                    case 4:
                        removeTrain();
                        break;
                    case 5:
                        viewAllReservations();
                        break;
                    case 6:
                        adminSession = false;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private static void addTrain() {
        System.out.println("\n=== ADD NEW TRAIN ===");
        try {
            System.out.print("Enter train number: ");
            int trainNumber = Integer.parseInt(scanner.nextLine().trim());
            
            // Check if train already exists
            if (trainManager.getTrainByNumber(trainNumber) != null) {
                System.out.println("Train with this number already exists.");
                return;
            }
            
            System.out.print("Enter train name: ");
            String trainName = scanner.nextLine().trim();
            System.out.print("Enter source station: ");
            String source = scanner.nextLine().trim();
            System.out.print("Enter destination station: ");
            String destination = scanner.nextLine().trim();
            System.out.print("Enter ticket price: ");
            double ticketPrice = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter total seats: ");
            int totalSeats = Integer.parseInt(scanner.nextLine().trim());
            
            Train train = new Train(trainNumber, trainName, source, destination, ticketPrice, totalSeats);
            trainManager.addTrain(train);
            
            System.out.println("Train added successfully.");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter valid numbers.");
        }
    }
    
    private static void displayAllTrains() {
        System.out.println("\n=== ALL TRAINS ===");
        List<Train> trains = trainManager.getAllTrains();
        
        if (trains.isEmpty()) {
            System.out.println("No trains available in the system.");
        } else {
            System.out.println("------------------------------------------------");
            for (Train train : trains) {
                System.out.println(train);
            }
            System.out.println("------------------------------------------------");
        }
    }
    
    private static void updateTrain() {
        System.out.println("\n=== UPDATE TRAIN ===");
        try {
            System.out.print("Enter train number to update: ");
            int trainNumber = Integer.parseInt(scanner.nextLine().trim());
            
            Train train = trainManager.getTrainByNumber(trainNumber);
            
            if (train == null) {
                System.out.println("Train not found.");
                return;
            }
            
            System.out.println("Current train details: " + train);
            
            System.out.println("\nWhat do you want to update?");
            System.out.println("1. Train name");
            System.out.println("2. Source station");
            System.out.println("3. Destination station");
            System.out.println("4. Ticket price");
            System.out.println("5. Total seats");
            System.out.print("\nEnter your choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    System.out.print("Enter new train name: ");
                    String newName = scanner.nextLine().trim();
                    trainManager.updateTrainName(trainNumber, newName);
                    break;
                case 2:
                    System.out.print("Enter new source station: ");
                    String newSource = scanner.nextLine().trim();
                    trainManager.updateTrainSource(trainNumber, newSource);
                    break;
                case 3:
                    System.out.print("Enter new destination station: ");
                    String newDestination = scanner.nextLine().trim();
                    trainManager.updateTrainDestination(trainNumber, newDestination);
                    break;
                case 4:
                    System.out.print("Enter new ticket price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine().trim());
                    trainManager.updateTrainPrice(trainNumber, newPrice);
                    break;
                case 5:
                    System.out.print("Enter new total seats: ");
                    int newSeats = Integer.parseInt(scanner.nextLine().trim());
                    trainManager.updateTrainSeats(trainNumber, newSeats);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            System.out.println("Train updated successfully.");
            System.out.println("New train details: " + trainManager.getTrainByNumber(trainNumber));
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter valid numbers.");
        }
    }
    
    private static void removeTrain() {
        System.out.println("\n=== REMOVE TRAIN ===");
        try {
            System.out.print("Enter train number to remove: ");
            int trainNumber = Integer.parseInt(scanner.nextLine().trim());
            
            Train train = trainManager.getTrainByNumber(trainNumber);
            
            if (train == null) {
                System.out.println("Train not found.");
                return;
            }
            
            System.out.println("Train to remove: " + train);
            System.out.print("Are you sure you want to remove this train? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            
            if (confirm.equalsIgnoreCase("Y")) {
                if (trainManager.removeTrain(trainNumber)) {
                    System.out.println("Train removed successfully.");
                } else {
                    System.out.println("Failed to remove train.");
                }
            } else {
                System.out.println("Train removal cancelled.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid train number format.");
        }
    }
    
    private static void viewAllReservations() {
        System.out.println("\n=== ALL RESERVATIONS ===");
        List<Reservation> reservations = reservationManager.getAllReservations();
        
        if (reservations.isEmpty()) {
            System.out.println("No reservations found in the system.");
        } else {
            for (Reservation res : reservations) {
                System.out.println("------------------------------------------------");
                System.out.println(res);
            }
            System.out.println("------------------------------------------------");
        }
    }
}

// Abstract Payment class and its implementations
abstract class Payment {
    protected double amount;
    protected Date paymentDate;
    
    public Payment(double amount) {
        this.amount = amount;
        this.paymentDate = new Date();
    }
    
    public abstract boolean processPayment();
    
    public double getAmount() {
        return amount;
    }
    
    public Date getPaymentDate() {
        return paymentDate;
    }
}

class CashPayment extends Payment {
    public CashPayment(double amount) {
        super(amount);
    }
    
    @Override
    public boolean processPayment() {
        System.out.println("Processing cash payment of Rs. " + amount);
        // In real system, this would interface with a cash handling system
        return true;
    }
}

class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    
    public CreditCardPayment(double amount, String cardNumber, String cardHolderName, String expiryDate) {
        super(amount);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public boolean processPayment() {
        // Mask card number for display
        String maskedCard = "XXXX-XXXX-XXXX-" + cardNumber.substring(Math.max(0, cardNumber.length() - 4));
        System.out.println("Processing credit card payment of Rs. " + amount);
        System.out.println("Card: " + maskedCard + " | Cardholder: " + cardHolderName);
        
        // In real system, this would integrate with payment gateway
        return true;
    }
}

// Train class (using the existing one with minor modifications)
class Train {
    private int trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private double ticketPrice;
    private int totalSeats;
    private int availableSeats;

    public Train(int trainNumber, String trainName, String source, String destination, 
                double ticketPrice, int totalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    // Getters and setters
    public int getTrainNumber() { return trainNumber; }
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { 
        this.totalSeats = totalSeats;
        // Update available seats if needed
        if (this.availableSeats > totalSeats) {
            this.availableSeats = totalSeats;
        }
    }
    public int getAvailableSeats() { return availableSeats; }
    
    public boolean bookSeats(int numberOfSeats) {
        if (availableSeats >= numberOfSeats) {
            availableSeats -= numberOfSeats;
            return true;
        }
        return false;
    }
    
    public void cancelSeats(int numberOfSeats) {
        availableSeats += numberOfSeats;
        if (availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Train %d: %s, From: %s, To: %s, Price: Rs. %.2f, Available Seats: %d/%d",
                trainNumber, trainName, source, destination, ticketPrice, availableSeats, totalSeats);
    }
}

// Passenger class (using the existing one)
class Passenger {
    private String name;
    private int age;
    private String gender;
    private String contactNumber;

    public Passenger(String name, int age, String gender, String contactNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getContactNumber() { return contactNumber; }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Gender: " + gender + ", Contact: " + contactNumber;
    }
}

// Reservation class
class Reservation {
    private static int nextPnr = 1001;
    
    private int pnrNumber;
    private Train train;
    private List<Passenger> passengers;
    private Date reservationDate;
    private int numberOfSeats;
    private double totalFare;
    private Payment payment;
    private boolean confirmed;
    
    public Reservation(Train train, List<Passenger> passengers, int numberOfSeats) {
        this.pnrNumber = nextPnr++;
        this.train = train;
        this.passengers = new ArrayList<>(passengers);
        this.reservationDate = new Date();
        this.numberOfSeats = numberOfSeats;
        this.totalFare = train.getTicketPrice() * numberOfSeats;
        this.confirmed = false;
    }
    
    public int getPnrNumber() { return pnrNumber; }
    public Train getTrain() { return train; }
    public List<Passenger> getPassengers() { return passengers; }
    public Date getReservationDate() { return reservationDate; }
    public int getNumberOfSeats() { return numberOfSeats; }
    public double getTotalFare() { return totalFare; }
    public boolean isConfirmed() { return confirmed; }
    
    public void confirmReservation(Payment payment) {
        this.payment = payment;
        this.confirmed = true;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("PNR: ").append(pnrNumber).append("\n");
        sb.append("Train: ").append(train.getTrainName()).append(" (").append(train.getTrainNumber()).append(")\n");
        sb.append("From: ").append(train.getSource()).append(" To: ").append(train.getDestination()).append("\n");
        sb.append("Reservation Date: ").append(dateFormat.format(reservationDate)).append("\n");
        sb.append("Number of Seats: ").append(numberOfSeats).append("\n");
        sb.append("Total Fare: Rs. ").append(String.format("%.2f", totalFare)).append("\n");
        sb.append("Status: ").append(confirmed ? "Confirmed" : "Not Confirmed").append("\n");
        
        sb.append("Passenger Details:\n");
        for (int i = 0; i < passengers.size(); i++) {
            sb.append("  ").append(i+1).append(". ").append(passengers.get(i)).append("\n");
        }
        
        if (payment != null) {
            sb.append("Payment Date: ").append(dateFormat.format(payment.getPaymentDate())).append("\n");
        }
        
        return sb.toString();
    }
}

// Train Manager class
class TrainManager {
    private Map<Integer, Train> trains;
    
    public TrainManager() {
        trains = new HashMap<>();
    }
    
    public void addTrain(Train train) {
        trains.put(train.getTrainNumber(), train);
    }
    
    public Train getTrainByNumber(int trainNumber) {
        return trains.get(trainNumber);
    }
    
    public List<Train> getAllTrains() {
        return new ArrayList<>(trains.values());
    }
    
    public List<Train> searchTrains(String source, String destination) {
        List<Train> results = new ArrayList<>();
        for (Train train : trains.values()) {
            if (train.getSource().equalsIgnoreCase(source) && 
                train.getDestination().equalsIgnoreCase(destination)) {
                results.add(train);
            }
        }
        return results;
    }
    
    public boolean removeTrain(int trainNumber) {
        return trains.remove(trainNumber) != null;
    }
    
    // Update methods
    public void updateTrainName(int trainNumber, String newName) {
        Train train = trains.get(trainNumber);
        if (train != null) {
            train.setTrainName(newName);
        }
    }
    
    public void updateTrainSource(int trainNumber, String newSource) {
        Train train = trains.get(trainNumber);
        if (train != null) {
            train.setSource(newSource);
        }
    }
    
    public void updateTrainDestination(int trainNumber, String newDestination) {
        Train train = trains.get(trainNumber);
        if (train != null) {
            train.setDestination(newDestination);
        }
    }
    
    public void updateTrainPrice(int trainNumber, double newPrice) {
        Train train = trains.get(trainNumber);
        if (train != null) {
            train.setTicketPrice(newPrice);
        }
    }
    
    public void updateTrainSeats(int trainNumber, int newTotalSeats) {
        Train train = trains.get(trainNumber);
        if (train != null) {
            train.setTotalSeats(newTotalSeats);
        }
    }
}

// Reservation Manager class
class ReservationManager {
    private Map<Integer, Reservation> reservations;
    
    public ReservationManager() {
        reservations = new HashMap<>();
    }
    
    public Reservation createReservation(Train train, List<Passenger> passengers, int numberOfSeats) {
        if (train.getAvailableSeats() < numberOfSeats) {
            return null;
        }
        
        Reservation reservation = new Reservation(train, passengers, numberOfSeats);
        reservations.put(reservation.getPnrNumber(), reservation);
        return reservation;
    }
    
    public Reservation getReservation(int pnrNumber) {
        return reservations.get(pnrNumber);
    }
    
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations.values());
    }
    
    public boolean cancelReservation(int pnrNumber) {
        Reservation reservation = reservations.get(pnrNumber);
        if (reservation != null && reservation.isConfirmed()) {
            Train train = reservation.getTrain();
            train.cancelSeats(reservation.getNumberOfSeats());
            reservations.remove(pnrNumber);
            return true;
        }
        return false;
    }
}
