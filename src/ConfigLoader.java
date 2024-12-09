import java.io.File;

public class ConfigLoader {

    public static void main(String[] args) {
        // Sample user input path
        String filePath = "C:\\Users\\fdora\\OneDrive\\Documents\\IIT Documents\\Real Time Event Ticketing System Cli\\.testconfig.json";

        // Check if path contains spaces or requires escaping
        if (filePath.contains(" ")) {
            filePath = filePath.replaceAll("\\\\", "\\\\\\\\"); // Handle backslashes for file path correctly
        }

        File configFile = new File(filePath);
        if (!configFile.exists()) {
            System.out.println("Error loading configuration: " + filePath + " (The file does not exist or path is incorrect)");
        } else {
            // Load the configuration here
            System.out.println("Configuration loaded successfully.");
        }
    }
}
