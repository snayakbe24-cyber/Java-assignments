import java.util.Objects;
import java.util.Optional;

public class Q3 {

    static class Result<T extends Number> {
        private final String studentName;
        private final T[] marks;          // marks stored using a generic type
        private final double maxMarksPerSubject;

        Result(String studentName, T[] marks, double maxMarksPerSubject) {
            // 5. Validate required inputs
            this.studentName = Objects.requireNonNull(studentName, "Student name must not be null");
            this.marks = Objects.requireNonNull(marks, "Marks must not be null");
            this.maxMarksPerSubject = maxMarksPerSubject;
        }

        // 2. Total / average / percentage using numeric conversion (doubleValue)
        double total() {
            double sum = 0;
            for (T m : marks) {
                sum += Objects.requireNonNull(m, "A mark must not be null").doubleValue();
            }
            return sum;
        }

        Optional<Double> average() {
            return marks.length == 0 ? Optional.empty() : Optional.of(total() / marks.length);
        }

        Optional<Double> percentage() {
            return marks.length == 0
                    ? Optional.empty()
                    : Optional.of(total() / (marks.length * maxMarksPerSubject) * 100);
        }

        // 3. Pass/fail based on a threshold percentage
        boolean hasPassed(double thresholdPercentage) {
            return percentage().map(p -> p >= thresholdPercentage).orElse(false);
        }

        // 4. Display using Optional (only if marks are available)
        void display(double thresholdPercentage) {
            System.out.println("Student: " + studentName);
            Optional<Double> avg = average();
            Optional<Double> pct = percentage();
            avg.ifPresentOrElse(
                    a -> System.out.printf("Total: %.2f | Average: %.2f%n", total(), a),
                    () -> System.out.println("Marks not available."));
            pct.ifPresent(p -> System.out.printf("Percentage: %.2f%%%n", p));
            if (pct.isPresent()) {
                System.out.println("Status: " + (hasPassed(thresholdPercentage) ? "PASS" : "FAIL"));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Integer[] intMarks = {78, 85, 90, 66};
        Double[] doubleMarks = {35.5, 28.0, 40.5};
        Float[] floatMarks = {};   // no marks available

        new Result<>("Asha (Integer marks)", intMarks, 100).display(40);
        new Result<>("Ravi (Double marks)", doubleMarks, 100).display(40);
        new Result<>("Meera (no marks)", floatMarks, 100).display(40);

        try {
            new Result<Integer>(null, intMarks, 100);
        } catch (NullPointerException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
}
