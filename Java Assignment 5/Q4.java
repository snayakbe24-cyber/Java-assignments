import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;

public class Q4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Accept event name, date and start time
        System.out.print("Enter event name: ");
        String name = sc.nextLine().trim();

        LocalDate date;
        LocalTime start;
        try {
            System.out.print("Enter event date (yyyy-MM-dd): ");
            date = LocalDate.parse(sc.nextLine().trim());
            System.out.print("Enter start time (HH:mm): ");
            start = LocalTime.parse(sc.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format.");
            return;
        }

        // 6. Optional venue
        System.out.print("Enter venue (press Enter to skip): ");
        String venueInput = sc.nextLine().trim();
        Optional<String> venue = venueInput.isEmpty() ? Optional.empty() : Optional.of(venueInput);

        // 2. Display event details
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy (EEEE)");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
        System.out.println("\n--- Event Details ---");
        System.out.println("Event : " + name);
        System.out.println("Date  : " + date.format(dateFmt));
        System.out.println("Start : " + start.format(timeFmt));
        System.out.println("Venue : " + venue.orElse("Venue not specified"));

        // 3. Future, today or past
        LocalDate today = LocalDate.now();
        if (date.isAfter(today)) {
            System.out.println("Status: The event is in the future.");
        } else if (date.isEqual(today)) {
            System.out.println("Status: The event is today.");
        } else {
            System.out.println("Status: The event is in the past.");
        }

        // 4. Days remaining
        long days = ChronoUnit.DAYS.between(today, date);
        if (days > 0) {
            System.out.println("Days remaining: " + days);
        } else if (days == 0) {
            System.out.println("Days remaining: 0 (today)");
        } else {
            System.out.println("The event was " + Math.abs(days) + " day(s) ago.");
        }

        // 5. End time for a two-hour event
        LocalTime end = start.plusHours(2);
        System.out.println("End time (2-hour event): " + end.format(timeFmt));
    }
}
