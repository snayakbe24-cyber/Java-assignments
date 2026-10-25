import java.util.Objects;
import java.util.Optional;

public class Q5 {

    static class GenericUtils {

        // 1. Null-safe equality check
        public static <T> boolean isEqual(T a, T b) {
            return Objects.equals(a, b);
        }

        // 2. Contained value or default
        public static <T> T getDefault(Optional<T> value, T defaultValue) {
            return value.orElse(defaultValue);
        }

        // 3. Larger of two comparable objects
        public static <T extends Comparable<T>> T max(T a, T b) {
            return a.compareTo(b) >= 0 ? a : b;
        }

        // 4. Null check using the Objects class
        public static boolean isNull(Object obj) {
            return Objects.isNull(obj);
        }
    }

    public static void main(String[] args) {
        // isEqual
        System.out.println("isEqual(\"java\", \"java\") = " + GenericUtils.isEqual("java", "java"));
        System.out.println("isEqual(10, 20)           = " + GenericUtils.isEqual(10, 20));
        System.out.println("isEqual(null, null)       = " + GenericUtils.isEqual(null, null));
        System.out.println("isEqual(3.5, 3.5)         = " + GenericUtils.isEqual(3.5, 3.5));

        // getDefault
        Optional<String> present = Optional.of("Hello");
        Optional<String> empty = Optional.empty();
        System.out.println("getDefault(present, \"N/A\") = " + GenericUtils.getDefault(present, "N/A"));
        System.out.println("getDefault(empty, \"N/A\")   = " + GenericUtils.getDefault(empty, "N/A"));
        System.out.println("getDefault(empty, 0)       = " + GenericUtils.getDefault(Optional.<Integer>empty(), 0));

        // max
        System.out.println("max(10, 25)               = " + GenericUtils.max(10, 25));
        System.out.println("max(\"apple\", \"banana\")    = " + GenericUtils.max("apple", "banana"));
        System.out.println("max(3.14, 2.71)           = " + GenericUtils.max(3.14, 2.71));
        System.out.println("max('a', 'z')             = " + GenericUtils.max('a', 'z'));

        // isNull
        String s = null;
        System.out.println("isNull(null)              = " + GenericUtils.isNull(s));
        System.out.println("isNull(\"text\")            = " + GenericUtils.isNull("text"));
    }
}
