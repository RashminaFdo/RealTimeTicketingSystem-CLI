import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Show menu on program start
        showMenu(scanner);
    }

    private static void showMenu(Scanner scanner) {
        System.out.println("\nMenu:");
        System.out.println("1. Load configuration from file");
        System.out.println("2. Manual configuration");
        System.out.println("3. Exit");
        System.out.print("Please choose an option: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            Configuration config = loadConfiguration(scanner);
            if (config != null) {
                runTicketSimulation(config, scanner);
            }
        } else if (choice.equals("2")) {
            Configuration config = createNewConfiguration(scanner);
            runTicketSimulation(config, scanner);
        } else if (choice.equals("3")) {
            System.out.println("Exiting system...");
            System.exit(0); // Exit the program
        } else {
            System.out.println("Invalid choice, please try again.");
            showMenu(scanner); // Show the menu again if invalid choice
        }
    }

    private static void runTicketSimulation(Configuration config, Scanner scanner) {
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
        Object lock = new Object();
        Vendor vendor = new Vendor(ticketPool, config.getTotalTickets(), config.getTicketReleaseRate(), lock);
        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate(), lock);

        Thread vendorThread = new Thread(vendor);
        Thread customerThread = new Thread(customer);

        vendorThread.start();
        customerThread.start();

        try {
            vendorThread.join();
            customerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // After ticket processing is complete, display the completion message
        System.out.println("Ticket processing completed.");
        System.out.println("Shutting down. Goodbye!");

        // Ensure the menu is displayed after all logs are printed.
        showMenu(scanner);  // Call showMenu to allow user to choose again
    }

    private static Configuration loadConfiguration(Scanner scanner) {
        while (true) {
            System.out.println("Enter the file path to load the configuration or press 'm' to manually enter:");
            String filePath = scanner.nextLine().trim();

            if (filePath.equalsIgnoreCase("m")) {
                return createNewConfiguration(scanner); // Proceed with manual configuration if user chooses 'm'
            }

            // Try loading the configuration file
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Error loading configuration: " + filePath + " (The file does not exist or path is incorrect).");
            } else {
                // If file exists, load configuration
                try {
                    return ConfigUtils.loadConfiguration(filePath);
                } catch (IOException e) {
                    System.out.println("Error loading configuration: " + e.getMessage());
                }
            }
        }
    }

    private static Configuration createNewConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        config.setTotalTickets(getValidatedInput(scanner, "Enter the total number of tickets:", value -> value > 0, "Total tickets must be a positive number."));
        config.setTicketReleaseRate(getValidatedInput(scanner, "Enter the ticket release rate (tickets per second):", value -> value > 0, "Ticket release rate must be a positive number."));
        config.setCustomerRetrievalRate(getValidatedInput(scanner, "Enter the customer retrieval rate (tickets per second):", value -> value > 0, "Customer retrieval rate must be a positive number."));
        config.setMaxTicketCapacity(getValidatedInput(scanner, "Enter the maximum ticket pool capacity:", value -> value > 0 && value >= config.getTotalTickets(), "Max ticket capacity must be greater than 0 and greater than or equal to total tickets."));

        // Ask if the user wants to save the configuration
        String choice = getValidatedYesNoInput(scanner, "Do you want to save this configuration? (y/n):");
        if (choice.equals("y")) {
            saveConfiguration(config, scanner);
        }

        return config;
    }

    // Save the configuration to a file
    private static void saveConfiguration(Configuration config, Scanner scanner) {
        while (true) {
            System.out.println("Enter the full file path (including filename) to save the configuration (e.g., C:\\path\\config.json):");
            String filePath = scanner.nextLine().trim();

            // Ensure the file path ends with .json
            if (!filePath.endsWith(".json")) {
                System.out.println("Error: File must have a .json extension. Please try again.");
                continue;
            }

            File file = new File(filePath);
            File parentDir = file.getParentFile();

            // Validate that the directory exists
            if (parentDir == null || !parentDir.exists()) {
                System.out.println("Invalid directory. Please enter a valid path.");
                continue;
            }

            try {
                ConfigUtils.saveConfiguration(config, filePath);
                System.out.println("Configuration saved successfully at: " + filePath);
                break;
            } catch (IOException e) {
                System.out.println("Error saving configuration: " + e.getMessage());
            }
        }
    }

    // Validate and get user input
    private static int getValidatedInput(Scanner scanner, String prompt, ValidationRule validation, String errorMessage) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (validation.isValid(value)) {
                    return value;
                } else {
                    System.out.println("Error: " + errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    // Ensure only 'y' or 'n' is entered
    private static String getValidatedYesNoInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("n")) {
                return input;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}
