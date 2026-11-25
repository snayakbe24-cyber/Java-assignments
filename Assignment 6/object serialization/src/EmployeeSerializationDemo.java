import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EmployeeSerializationDemo {
    private static final Path FILE = Path.of("output", "employees.ser");

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(2001, "Anaya Desai", "Engineering", 78000.00),
                new Employee(2002, "Kabir Mehta", "Human Resources", 64500.50),
                new Employee(2003, "Ishita Rao", "Finance", 71250.75));
        try {
            Files.createDirectories(FILE.getParent());
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE.toFile()))) {
                for (Employee employee : employees) out.writeObject(employee);
            }
            List<Employee> restored = new ArrayList<>();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE.toFile()))) {
                while (true) {
                    try {
                        Object item = in.readObject();
                        if (!(item instanceof Employee)) throw new IOException("Unexpected object in file.");
                        restored.add((Employee) item);
                    } catch (EOFException end) {
                        break;
                    }
                }
            }
            System.out.println("Employees written: " + employees.size());
            System.out.println("Employees read: " + restored.size());
            for (Employee employee : restored) System.out.println(employee);
            System.out.println("Serialized file: " + FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Employee file operation failed: " + e.getMessage());
            System.exit(1);
        }
    }
}