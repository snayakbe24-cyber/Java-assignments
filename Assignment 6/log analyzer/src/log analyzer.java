import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Locale;

public class LogAnalyzer {
    private static final Path INPUT = Path.of("data", "application.log");
    private static final Path OUTPUT = Path.of("output", "filtered.log");

    public static void main(String[] args) {
        try {
            Files.createDirectories(OUTPUT.getParent());
            int total = 0, errors = 0, warnings = 0;
            try (BufferedReader in = Files.newBufferedReader(INPUT, StandardCharsets.UTF_8);
                 BufferedWriter out = Files.newBufferedWriter(OUTPUT, StandardCharsets.UTF_8)) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.isBlank()) continue;
                    total++;
                    String upper = line.toUpperCase(Locale.ROOT);
                    boolean error = upper.contains("ERROR"), warning = upper.contains("WARNING");
                    if (error || warning) {
                        out.write(line);
                        out.newLine();
                        if (error) errors++;
                        if (warning) warnings++;
                    }
                }
            }
            System.out.println("Total log entries: " + total);
            System.out.println("ERROR entries: " + errors);
            System.out.println("WARNING entries: " + warnings);
            System.out.println("Filtered entries saved to: " + OUTPUT);
        } catch (IOException e) {
            System.err.println("Log analysis failed: " + e.getMessage());
            System.exit(1);
        }
    }
}