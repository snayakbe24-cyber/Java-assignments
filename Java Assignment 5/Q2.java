import java.util.Optional;

public class Q2 {
    public static void main(String[] args) {
        // 1. Optional containing an email address
        Optional<String> email = Optional.of("student@university.edu");

        // 2. Empty Optional
        Optional<String> noEmail = Optional.empty();

        // 3. Display the email using ifPresent()
        email.ifPresent(e -> System.out.println("Email: " + e));
        noEmail.ifPresent(e -> System.out.println("This will not print"));

        // 4. Default email using orElse()
        String defaultEmail = noEmail.orElse("noemail@university.edu");
        System.out.println("Email (with default): " + defaultEmail);

        // 5. Handle a missing email using orElseThrow()
        try {
            String value = noEmail.orElseThrow(
                    () -> new IllegalArgumentException("Email address is missing!"));
            System.out.println(value);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        // orElseThrow() on a present value simply returns it
        System.out.println("Present value: " + email.orElseThrow());
    }
}
