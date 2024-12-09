import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class ConfigUtils {

    private static final Gson gson = new Gson();

    public static void saveConfiguration(Configuration config, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(config, writer);
        }
    }

    public static Configuration loadConfiguration(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Invalid JSON format in configuration file.");
        }
    }
}
