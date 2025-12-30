import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;
import java.io.IOException;

public class PetCareScheduler {
    private static Scanner sc = new Scanner(System.in);
    private static Map<String, Pet> pets = new HashMap<>();
    public static void main(String[] args) {
        // Load file
        loadPetAndAppointmentsFromFile();
        boolean running = true;
        while (running) {
            System.out.println("==========Welcome to Pet Care Scheduler!==========");
            System.out.println("1. Register Pet");
            System.out.println("2. Schedule appointments");
            System.out.println("3. Display records");
            System.out.println("4. Store date");
            System.out.println("5. Generate reports");
            System.out.println("6. Exit");
            System.out.println("Choose an option: ");

            String option = sc.nextLine();

            switch (option) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    scheduleAppointments();
                    break;
                case "3":
                    displayRecords();
                    break;
                case "4":
                    savePetAndAppointments();
                    break;
                case "5":
                    generateReports();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option!. Please select 1-5.");
                    break;
            }
        }
    }

    private static void registerPet() {
        System.out.println("\n===== Register Pet =====");

        try {
            // Enter Pet ID
            String newPetId;
            while (true) {
                System.out.println("Enter Pet Id: ");
                newPetId = sc.nextLine().trim();
                if (newPetId.isEmpty()) {
                    System.out.println("Pet ID cannot be empty! Enter again.");
                } else if (pets.containsKey(newPetId)) {
                    System.out.println("Pet already exists! Enter again.");
                } else {
                    break;
                }
            }

            // Enter Pet Name
            String newPetName;
            while (true) {
                System.out.println("Enter Pet Name: ");
                newPetName = sc.nextLine().trim();
                if (newPetName.isEmpty()) {
                    System.out.println("Pet Name cannot be empty! Enter again.");
                } else {
                    break;
                }
            }

            // Enter Pet Breed
            String newPetBreed;
            while (true) {
                System.out.println("Enter Pet Breed: ");
                newPetBreed = sc.nextLine().trim();
                if (newPetBreed.isEmpty()) {
                    System.out.println("Pet Breed cannot be empty! Enter again.");
                } else {
                    break;
                }
            }

            // Enter Pet Age
            int newPetAge;
            while (true) {
                System.out.println("Enter Pet Age: ");
                try {
                    newPetAge = Integer.parseInt(sc.nextLine().trim());
                    if (newPetAge < 0) {
                        System.out.println("Age cannot be negative! Enter again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number! Enter again.");
                }
            }

            // Enter Owner Name
            String newOwnerName;
            while (true) {
                System.out.println("Enter Owner Name: ");
                newOwnerName = sc.nextLine().trim();
                if (newOwnerName.isEmpty()) {
                    System.out.println("Owner Name cannot be empty! Enter again.");
                } else {
                    break;
                }
            }

            // Enter Contact Information
            int newContactInformation;
            while (true) {
                System.out.println("Enter Contact Information: ");
                try {
                    newContactInformation = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number! Enter again.");
                }
            }

            // Tạo Pet và thêm vào map
            Pet newPet = new Pet(newPetId, newPetName, newPetBreed, newPetAge, newOwnerName, newContactInformation);
            pets.put(newPetId, newPet);

            System.out.println("Pet registered successfully!");
        } catch (NullPointerException e) {
            System.out.println("Unexpected null input! Registration failed.");
        }
    }

    private static void scheduleAppointments() {
        System.out.println("\n===== Schedule Appointments =====");
        String petIdSchedule;
        while (true) {
            System.out.println("Enter petId you want to schedule appointments: ");
            petIdSchedule = sc.nextLine().trim();
            if (petIdSchedule.isEmpty() || !pets.containsKey(petIdSchedule)) {
                System.out.println("Pet ID is not existed or emptied ! Enter again.");
                continue;
            }
            break;
        }

        String typeSchedule;
        while (true) {
            System.out.println("Enter type you want to schedule appointments (\"vaccination\", \"checkup\", \"grooming\"):  ");
            typeSchedule = sc.nextLine().trim();
            List<String> validTypes = List.of("vaccination", "checkup", "grooming");
            if (!validTypes.contains(typeSchedule.toLowerCase())) {
                System.out.println("Invalid appointment type! Enter again.");
                continue;
            }
            break;

        }

        LocalDateTime appointmentDateTime;
        while (true) {
            //Enter Date
            System.out.println("Enter date you want to schedule appointments (Format: dd/MM/yyyy): ");
            String dateSchedule = sc.nextLine().trim();

            //Enter Time
            System.out.println("Enter time you want to schedule appointments (Format: HH:mm): ");
            String timeSchedule = sc.nextLine().trim();

            if (dateSchedule.isEmpty() || timeSchedule.isEmpty()) {
                System.out.println("Date and Time cannot be empty! Enter again.");
            } else {
                try {
                    //Format Date
                    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate appointmentDate = LocalDate.parse(dateSchedule, formatterDate);

                    //Format Time
                    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime appointmentTime = LocalTime.parse(timeSchedule, formatterTime);

                    //Format Date and Time
                    appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

                    if (!appointmentDateTime.isAfter(LocalDateTime.now())){
                        System.out.println("Date and Time must be after now! Enter again.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date or time format! Enter again.");
                }
            }
        }

        System.out.println("Enter notes for this appointment: ");
        String notes = sc.nextLine();

        Appointment newAppointment = new Appointment(typeSchedule, appointmentDateTime, notes);
        pets.get(petIdSchedule).getAppointments().add(newAppointment);

        System.out.println("Appointment scheduled successfully!");
    }

    private static void displayRecords() {
        if (pets.isEmpty()) {
            System.out.println("ERROR: There are no pets registered!");
            return;
        }
        while(true) {
            System.out.println("===== Display information =====");
            System.out.println("1. All registered pets");
            System.out.println("2. All appointments for a specific pet");
            System.out.println("3. Upcoming appointments for all pets");
            System.out.println("4. Past appointment history for each pet");
            System.out.println("5. Exit");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    System.out.println("=====All registered pets===== ");
                    for (Pet pet : pets.values()) {
                        System.out.println(pet.toString());
                    }
                    break;
                case "2":
                    System.out.println("=====All appointments for a specific pet===== ");
                    while (true){
                        System.out.println("Enter pet ID you want to display all appointments(enter 0 to exit): ");
                        String petId = sc.nextLine().trim();
                        if (petId.equals("0")) {
                            break;
                        }
                        if (!pets.containsKey(petId)) {
                            System.out.println("ERROR: Pet ID is not existed or emptied ! Enter again.");
                            continue;
                        }
                        List<Appointment> appointments = pets.get(petId).getAppointments();
                        if (!appointments.isEmpty()) {
                            for (Appointment appointment : appointments) {
                                System.out.println(appointment.toString());
                            }
                            break;
                        } else {
                            System.out.println("This pet do not have any appointments");
                            break;
                        }
                    }
                    break;
                case "3":
                    System.out.println("=====Upcoming appointments for all pets (Grouped by Pet)=====");
                    boolean upcoming = false;
                    for (Pet pet : pets.values()) {
                        System.out.println(pet.getPetId() + "__" + pet.getPetName());
                        List<Appointment> appointments = pet.getAppointments();
                        for (Appointment appointment : appointments) {
                            if(appointment.getDateTime().isAfter(LocalDateTime.now())){
                                upcoming = true;
                                System.out.println("   "+ appointment.toString());
                            }
                        }
                    }
                    if (!upcoming) {
                        System.out.println("ERROR: There are not upcoming appointments ");
                    }
                    break;
                case "4":
                    System.out.println("=====Past appointment history for each pet=====");
                    boolean past = false;
                    for (Pet pet : pets.values()) {
                        System.out.println("Past appointment history of " + pet.getPetId() + "__" + pet.getPetName());
                        for(Appointment appointment : pet.getAppointments()){
                            if(appointment.getDateTime().isBefore(LocalDateTime.now())){
                                past = true;
                                System.out.println("   "+ appointment.toString());
                            }
                        }
                    }
                    if (!past) {
                        System.out.println("ERROR: There are not past appointments ");
                    }
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;
                case "6":
                    System.out.println("=====Upcoming appointments for all pets (Grouped by date)=====");
                    ArrayList<AppointmentWithPet> allAppointments = new ArrayList<>();
                    for (Pet pet : pets.values()) {
                        for (Appointment appointment : pet.getAppointments()) {
                            if (appointment.getDateTime().isAfter(LocalDateTime.now())) {
                                allAppointments.add(new AppointmentWithPet(appointment, pet));
                            }
                        }
                    }
                    // Sort by Date Time
                    allAppointments.sort(Comparator.comparing(AppointmentWithPet::getDateTime));

                    LocalDate lastDate = null;

                    for (AppointmentWithPet awp : allAppointments) {
                        LocalDate currentDate = awp.getDateTime().toLocalDate();

                        if (!currentDate.equals(lastDate)) {
                            System.out.println("\n===== " + currentDate + " =====");
                            lastDate = currentDate;
                        }


                        System.out.println("   " + awp.getDateTime().toLocalTime()
                                + " - " + awp.getPet().getPetName()
                                + " - " + awp.getAppointment());
                    }
                    break;
                default:
                    System.out.println("Invalid option! Enter again.");
                    continue;
            }
            if (option.equals("5")) {
                break;
            }
        }
    }

    private static void savePetAndAppointments() {
        try {
            // Create a FileOutputStream to write the file named "PetAndAppointment.ser"
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("PetAndAppointment.ser"));
            // Write the entire map to the file
            out.writeObject(pets);
            // If successful print message
            System.out.println("Pet and appointments saved to file");
        } catch (IOException e) {
            System.out.println("ERROR: can not saving data "+ e.getMessage());
        }
    }

    private static void loadPetAndAppointmentsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("PetAndAppointment.ser"))){
            pets = (Map<String, Pet>) in.readObject();
            System.out.println("Pet and appointments loaded from file");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static void generateReports() {
        if (pets.isEmpty()) {
            System.out.println("ERROR: There are no pets registered!");
            return;
        }
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextWeek = today.plusDays(7);
        LocalDateTime pastSixMonths = today.minusMonths(6);


        while (true){
            System.out.println("===== Generating report =====");
            System.out.println("1. Pets with upcoming appointments in the next week ");
            System.out.println("2. Pets overdue for a vet visit (such as, no vet visit in the last 6 months) ");
            System.out.println("3. Exit ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    boolean upcoming = false;
                    System.out.println("===== Upcoming appointments in the next week =====");
                    for (Pet pet : pets.values()) {
                        for (Appointment appointment : pet.getAppointments()) {
                            if (!appointment.getDateTime().isBefore(today) && appointment.getDateTime().isBefore(nextWeek))
                            {
                                System.out.println("   " +appointment.getDateTime() + "__" + pet.getPetId() + "__" + pet.getPetName());
                                upcoming = true;
                            }
                        }
                    }
                    if (!upcoming) {
                        System.out.println("There are not upcoming appointments in the next week ");
                    }
                    break;
                case "2":
                    boolean anyOverdue = false;
                    System.out.println("===== Pets overdue for a vet visit (6 months) =====");
                    for (Pet pet : pets.values()) {
                        boolean hasRecentVisit = false;
                        for (Appointment appointment : pet.getAppointments()) {
                            if (appointment.getDateTime().isBefore(today) && appointment.getDateTime().isAfter(pastSixMonths)) {
                                hasRecentVisit = true;
                                break;
                            }
                        }
                        if (!hasRecentVisit) {
                            System.out.println("   " + pet.getPetId() + "__" + pet.getPetName());
                            anyOverdue = true;
                        }
                    }
                    if (!anyOverdue) {
                        System.out.println("There are not pets overdue for a vet visit (6 months)");
                    }
                    break;
                case "3":
                    System.out.println("Exiting report menu...");
                    break;
                default:
                    System.out.println("Invalid option! Enter again.");
            }
            if (option.equals("3")) {
                break;
            }
        }
    }
}

