import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class EcoPointsRecyclingTracker {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Household> households = new HashMap<>();
    public static void main(String[] args) {
        loadHouseholdsFromFile();
        boolean running = true;
        while (running) {
            System.out.println("\n=== Eco-Points Recycling Tracker ===");
            System.out.println("1. Register Household");
            System.out.println("2. Log Recycling Event");
            System.out.println("3. Display Households");
            System.out.println("4. Display Household Recycling Events");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            // You will handle the choice entered here using switch(case)
            switch (choice) {
                case "1":
                    registerHousehold();
                    break;
                case "2":
                    logRecyclingEvents();
                    break;
                case "3":
                    displayHouseholds();
                    break;
                case "4":
                    displayHouseholdRecyclingEvents();
                    break;
                case "5":
                    generateReports();
                    break;
                case "6":
                    saveHouseholdsToFile();
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
    }

    private static void registerHousehold() {
        // Prompt the user to enter a unique household Id
        System.out.println("\nEnter householdId: ");
        String id = scanner.nextLine().trim();

        //Check if a household with this ID already exists
        if (households.containsKey(id)) {
            System.out.println("Error: Household already exists.");
            return;
        }

        //Prompt the user to enter the household's name
        System.out.println("Enter household name: ");
        String name = scanner.nextLine().trim();

        // Prompt the user to enter the household's address
        System.out.println("Enter household address: ");
        String address = scanner.nextLine().trim();

        //  Create a new Household object using the provided details
        Household household = new Household(id, name, address);

        // Add the new household to the households map (using ID as the key)
        households.put(id, household);

        //Confirm to the user that the household was registered successfully
        System.out.println("Successfully registered household on " + household.getJoinDate());
    }

    private static void logRecyclingEvents() {
        System.out.println("\nEnter householdId: ");
        String id = scanner.nextLine().trim();

        Household household = households.get(id);

        if (household == null) {
            System.out.println("Error: Household not found.");
            return;
        }

        // Ask the user for the material type they recycled
        System.out.println("Enter material type (plastic/ glass/ metal/ paper): ");
        String materialType = scanner.nextLine().trim();

        double weight = 0.0;

        // Loop until a valid weight is entered
        while (true) {
            try {
                System.out.print("Enter weight in kilograms: ");
                weight = Double.parseDouble(scanner.nextLine());  // Convert input to double
                // Check that weight is a positive number
                if (weight <= 0) throw new IllegalArgumentException();
                break;  // Exit loop if input is valid
            }  catch (NumberFormatException e) {
                System.out.println("Invalid weight. Must be a number.");
            }  catch (IllegalArgumentException e) {
                System.out.println("Invalid weight. Must be a positive number.");
            }
        }

        // Create a new RecyclingEvent using the material and weight
        RecyclingEvent event = new RecyclingEvent(materialType, weight);

        // Add the new event to the household and update points
        household.addEvent(event);

        // Show success message with points earned
        System.out.println("Recycling event logged! Points earned: " + event.getEcoPoints());
    }

    private static void displayHouseholds() {
        // Check if the households map is empty
        if (households.isEmpty()) {
            System.out.println("\nThere are no households registered.\n");
            return;
        }

        // If there are households, print a header first
        System.out.println("\n Registered households:\n ");

        for (Household h : households.values()) {
            System.out.println("ID = " + h.getId()
            + "\nName = " + h.getName()
            + "\nAddress = " + h.getAddress()
            + "\nJoined = " + h.getJoinDate()) ;
        }
    }

    private static void displayHouseholdRecyclingEvents() {
        // Prompt the user to enter the household ID
        System.out.println("Enter household Id: ");
        String id = scanner.nextLine().trim();

        // Look up the household in the households map using the ID
        Household household = households.get(id);

        if (household == null) {
            System.out.println("Error: Household not found.");
            return;
        }

        // Print a header with the household's name
        System.out.println("\nRecycling Events for " + household.getName() + ":");

        // Check if the household has any recycling events
        if (household.getEvents() == null) {
            System.out.println("No events found.");
        } else {
            for (RecyclingEvent e : household.getEvents()) {
                System.out.println(e);
            }

            // After listing events, show the total weight recycled by this household
            System.out.println("Total Weight: " + household.getTotalWeight() + " kg");
            // Show the total eco points earned by this household
            System.out.println("Total Points: " + household.getTotalPoints() + " pts");
        }

    }

    private static void generateReports() {
        if (households.isEmpty()) {
            System.out.println("\nThere are no households registered.\n");
            return;
        }

        System.out.println("\n____________REPORT___________ ");
        double maxPoint = 0.0;
        String maxId = "0";
        double totalCommunityWeight = 0.0;
        for (Map.Entry<String, Household> entry : households.entrySet()) {
            String id = entry.getKey();
            Household household = entry.getValue();
            if  (household.getTotalPoint() > maxPoint) {
                maxPoint = household.getTotalPoint();
                maxId = id;
            }
            totalCommunityWeight += household.getTotalWeight();
        }
        Household maxHousehold = households.get(maxId);
        System.out.println("\nHousehold with Highest Points:");
        System.out.println("id: " + maxId + "\nName: " + maxHousehold.getName() + "\nAddress: " + maxHousehold.getAddress() + "\nHighestPoint: " + maxHousehold.getTotalPoint());
        System.out.println("Total Community Weight: " + totalCommunityWeight + " kg");


    }

    private static void saveHouseholdsToFile() {
        try {
            // Create a FileOutputStream to write to the file named "households.ser"
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("households.ser")
            );
            // Write the entire households map to the file
            out.writeObject(households);
            // If successful, no message is printed here — could add confirmation if you like
        } catch (IOException e) {
            // Task 8
            // If something goes wrong while saving, print an error message
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Suppresses unchecked cast warning when reading the object
    private static void loadHouseholdsFromFile() {
        // Use a try-with-resources block to automatically close the input stream
        try (
                // Open an ObjectInputStream to read from the file "households.ser"
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("households.ser"))
        ) {
            // Read the object from the file and cast it back to the correct type
            households = (Map<String, Household>) in.readObject();

            // Confirmation message to let the user know data was loaded
            System.out.println("Household data loaded.");
        } catch (FileNotFoundException e) {
            // Task 8
            // If the file doesn't exist yet, that's okay — start with empty data
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            // Handle other errors, like if the file is corrupted or unreadable
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

}
