import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtils {

    // Method to log messages to a file
    public static void logToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("system_log.txt", true))) {
            writer.write(message);
            writer.newLine();  // New line for each log entry
        } catch (IOException e) {
            System.out.println("Error while writing to log file: " + e.getMessage());
        }
    }

    // Optional: Log to console as well
    public static void logToConsole(String message) {
        System.out.println(message);
    }
}
