import java.util.Scanner;

class InsufficientBalanceException extends Exception{
    InsufficientBalanceException(String message){
        super(message);
    }
}

class InvalidAmountException extends Exception{
    InvalidAmountException(String message){
        super(message);
    }
}

class AccountNotFoundException extends Exception{
    AccountNotFoundException(String message){
        super(message);
    }
}

class BankAccount{
    int accountNo;
    double balance;

    BankAccount(int accountNo, double balance){
        this.accountNo = accountNo;
        this.balance = balance;
    }

    void deposit(double amount) throws InvalidAmountException{
        if(amount <= 0){
            throw new InvalidAmountException("Deposit must be greater than zero");
        }
        balance += amount;
        System.out.println("Deposit successful");
        System.out.println("Balance = " + balance);
    }

    void withdraw(double amount)
            throws InvalidAmountException, InsufficientBalanceException {
        if(amount <= 0){
            throw new InvalidAmountException("Withdrawal must be greater than zero");
        }

        if(amount > balance){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        balance -= amount;
        System.out.println("Withdrawal successful");
        System.out.println("Balance = " + balance);
    }
}

public class Q5 {
    static BankAccount findAccount(int accountNo)
            throws AccountNotFoundException{

        if(accountNo != 1001){
            throw new AccountNotFoundException("Account not found");
        }
        return new BankAccount(1001, 5000);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try{
            System.out.print("Enter account number: ");
            int accountNo = sc.nextInt();

            BankAccount account = findAccount(accountNo);

            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            System.out.print("Enter amount: ");
            double amount = sc.nextDouble();

            if(choice == 1){
                account.deposit(amount);
            }
            else if(choice == 2){
                account.withdraw(amount);
            }
            else{
                System.out.println("Invalid choice");
            }
        }
        catch(AccountNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch(InvalidAmountException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch(InsufficientBalanceException e){
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}