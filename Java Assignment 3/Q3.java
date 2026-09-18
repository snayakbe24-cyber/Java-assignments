import java.util.Scanner;

class Q3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter first number: ");
            double a = Double.parseDouble(sc.nextLine());
            System.out.print("Enter second number: ");
            double b = Double.parseDouble(sc.nextLine());
            System.out.print("Enter operator (+, -, *, /): ");
            String op = sc.nextLine();
            double result;
            switch (op){
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    if (b == 0)
                        throw new ArithmeticException("Cannot divide by zero");
                    result = a / b;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
            System.out.println("Result = " + result);
        }
        catch(NumberFormatException e){
            System.out.println("Invalid numeric input!");
        }
        catch(ArithmeticException e){
            System.out.println("Division by zero!");
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid operator!");
        }
        sc.close();
    }
}