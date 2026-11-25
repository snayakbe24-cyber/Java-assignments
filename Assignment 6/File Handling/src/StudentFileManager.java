import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class StudentFileManager {
    private static final Path BINARY_IN = Path.of("data", "sample-binary.dat");
    private static final Path BINARY_OUT = Path.of("output", "sample-binary-copy.dat");
    private static final Path STUDENTS_IN = Path.of("data", "students.txt");
    private static final Path STUDENTS_OUT = Path.of("output", "students_processed.txt");

    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("output"));
            copyBinary();
            int count = processStudents();
            System.out.println("Student records written: " + count);
            System.out.println("Processed file: " + STUDENTS_OUT);
        } catch (IOException e) {
            System.err.println("File operation failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void copyBinary() throws IOException {
        long copied = 0;
        try (FileInputStream in = new FileInputStream(BINARY_IN.toFile());
             FileOutputStream out = new FileOutputStream(BINARY_OUT.toFile())) {
            byte[] buffer = new byte[4096];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                copied += n;
            }
        }
        System.out.println("Binary copy complete: " + copied + " bytes copied to " + BINARY_OUT);
    }

    private static int processStudents() throws IOException {
        int count = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(STUDENTS_IN.toFile(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(new FileWriter(STUDENTS_OUT.toFile(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = in.readLine()) != null) {
                String row = line.trim();
                if (row.isEmpty() || row.startsWith("#")) continue;
                if (row.split("\\|", -1).length != 4) throw new IOException("Invalid student row: " + line);
                out.write(row);
                out.newLine();
                count++;
            }
        }
        return count;
    }
}