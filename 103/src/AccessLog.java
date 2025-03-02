 
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccessLog {
    private static final String LOG_FILE = "access_log.txt";

    public static void logEntry(String entry) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(timestamp + " - " + entry + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }
}
