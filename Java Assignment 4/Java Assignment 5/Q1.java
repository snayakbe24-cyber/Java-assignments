public class Q1 {

    // Bounded type parameter: T must be Integer, Float, Double, Long, etc.
    public static <T extends Number> double sum(T[] array) {
        double total = 0;
        for (T value : array) {
            total += value.doubleValue();
        }
        return total;
    }

    public static void main(String[] args) {
        Integer[] ints = {1, 2, 3, 4, 5};
        Float[] floats = {1.5f, 2.5f, 3.5f};
        Double[] doubles = {10.25, 20.75, 30.5};

        System.out.println("Sum of integers: " + sum(ints));
        System.out.println("Sum of floats:   " + sum(floats));
        System.out.println("Sum of doubles:  " + sum(doubles));
    }
}
