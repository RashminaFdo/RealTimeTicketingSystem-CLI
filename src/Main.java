import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config;

        // Ask user whether to load the configuration from a file or manually enter
        while (true) {
            System.out.println("Do you want to load configuration from a file? (y/n):");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                config = loadConfiguration(scanner);
                if (config != null) break;
            } else if (choice.equals("n")) {
                config = createNewConfiguration(scanner);
                break;
            } else {
                System.out.println("Invalid option. Please enter 'y' for yes or 'n' for no.");
            }
        }

        // Create the TicketPool and threads for Vendor and Customer
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

        // After simulation ends, ask if the configuration should be saved
        saveConfigurationPrompt(scanner, config);

        // Shutdown message
        System.out.println("\nAll operations completed successfully.");
        System.out.println("System logs have been successfully processed.");
        System.out.println("Shutting down gracefully. See you next time!");

        // Option to run simulation again
        System.out.println("Do you want to run the simulation again? (y/n):");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            main(null); // Restart the program
        } else {
            System.out.println("Bye! Thank you.");
        }
    }

    private static Configuration createNewConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        config.setTotalTickets(getValidatedInput(scanner, "Enter the total number of tickets:", value -> value > 0, "Total tickets must be a positive number."));
        config.setTicketReleaseRate(getValidatedInput(scanner, "Enter the ticket release rate (tickets per second):", value -> value > 0, "Ticket release rate must be a positive number."));
        config.setCustomerRetrievalRate(getValidatedInput(scanner, "Enter the customer retrieval rate (tickets per second):", value -> value > 0, "Customer retrieval rate must be a positive number."));
        config.setMaxTicketCapacity(getValidatedInput(scanner, "Enter the maximum ticket pool capacity:", value -> value > 0 && value >= config.getTotalTickets(), "Max ticket capacity must be greater than 0 and greater than or equal to total tickets."));

        // Ask if the user wants to save the configuration
        System.out.println("Do you want to save this configuration? (y/n):");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            saveConfiguration(config, scanner);
        }

        return config;
    }

    private static Configuration loadConfiguration(Scanner scanner) {
        while (true) {
            System.out.println("Enter the file path to load the configuration or press 'm' to manually enter:");
            String filePath = scanner.nextLine().trim();

            if (filePath.equalsIgnoreCase("m")) {
                return createNewConfiguration(scanner); // Proceed with manual configuration if user chooses 'm'
            }

            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Error loading configuration: " + filePath + " (The file does not exist or path is incorrect).");
            } else {
                try {
                    return ConfigUtils.loadConfiguration(filePath);
                } catch (IOException e) {
                    System.out.println("Error loading configuration: " + e.getMessage());
                }
            }
        }
    }

    private static void saveConfigurationPrompt(Scanner scanner, Configuration config) {
        System.out.println("Do you want to save the current configuration? (y/n):");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            saveConfiguration(config, scanner);
        }
    }

    private static void saveConfiguration(Configuration config, Scanner scanner) {
        while (true) {
            System.out.println("Enter the full file path (including filename) to save the configuration (e.g., C:\\path\\config.json):");
            String filePath = scanner.nextLine().trim();

            if (!filePath.endsWith(".json")) {
                System.out.println("Error: File must have a .json extension. Please try again.");
                continue;
            }

            File file = new File(filePath);
            File parentDir = file.getParentFile();

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
}