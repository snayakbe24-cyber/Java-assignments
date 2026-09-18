import java.util.Scanner;

class InvalidUsernameException extends Exception {
    InvalidUsernameException(String message) {
        super(message);
    }
}

class InvalidPasswordException extends Exception {
    InvalidPasswordException(String message) {
        super(message);
    }
}

class AccountLockedException extends Exception {
    AccountLockedException(String message) {
        super(message);
    }
}

public class Q6 {

    static String correctUsername = "admin";
    static String correctPassword = "1234";
    static int failedAttempts = 0;
    static final int MAX_ATTEMPTS = 3;
    static boolean locked = false;

    static void login(String username, String password)
            throws InvalidUsernameException,
            InvalidPasswordException,
            AccountLockedException {

        if (locked) {
            throw new AccountLockedException("Account is locked.");
        }

        if (!username.equals(correctUsername)) {
            failedAttempts++;
            throw new InvalidUsernameException("Invalid username.");
        }

        if (!password.equals(correctPassword)) {
            failedAttempts++;
            throw new InvalidPasswordException("Invalid password.");
        }

        System.out.println("Login successful!");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            while (!locked && failedAttempts < MAX_ATTEMPTS) {
                System.out.print("Enter username: ");
                String username = sc.nextLine();

                System.out.print("Enter password: ");
                String password = sc.nextLine();

                try {
                    login(username, password);
                    break;
                }
                catch (InvalidUsernameException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                catch (InvalidPasswordException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                catch (AccountLockedException e) {
                    System.out.println("Error: " + e.getMessage());
                    locked = true;
                }

                if (failedAttempts >= MAX_ATTEMPTS) {
                    locked = true;
                    System.out.println("Account locked after maximum failed attempts.");
                }
                else {
                    System.out.println("Attempts remaining: " +
                            (MAX_ATTEMPTS - failedAttempts));
                }
            }
        }
        finally {
            System.out.println("Login process completed.");
            sc.close();
        }
    }
}