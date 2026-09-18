public class Q2{
    public static void main(String[] args) {
        try {
            System.out.println("Outer try block");

            try {
                int a = 10 / 0;   
            }
            catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e);
            }
            int arr[] = {10, 20, 30};
            System.out.println(arr[5]);  
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Outer catch: " + e);
        }
        System.out.println("Program continues...");
    }
}