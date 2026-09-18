public class Q1{
    public static void main(String[] args){

        // ArithmeticException
        try{
            int result = 10 / 0;
        }catch(ArithmeticException e){
            System.out.println("Cannot divide by zero");
        }

        // NullPointerException
        try{
            String str = null;
            System.out.println(str.length());
        }catch(NullPointerException e){
            System.out.println("Object is null");
        }

        // ArrayIndexOutOfBoundsException
        try{
            int[] arr = {10, 20, 30};
            System.out.println(arr[5]);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid array index");
        }

        // NumberFormatException
        try{
            int number = Integer.parseInt("abc");
        }catch(NumberFormatException e){
            System.out.println("Invalid number format");
        }
    }
}