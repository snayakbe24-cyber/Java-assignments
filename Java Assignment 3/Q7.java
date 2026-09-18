import java.util.*;

class ApplicationException extends Exception {
    ApplicationException(String message) {
        super(message);
    }
}

class ProductException extends ApplicationException {
    ProductException(String message) {
        super(message);
    }
}

class ProductNotFoundException extends ProductException {
    ProductNotFoundException(String message) {
        super(message);
    }
}

class OutOfStockException extends ProductException {
    OutOfStockException(String message) {
        super(message);
    }
}

class PaymentException extends ApplicationException {
    PaymentException(String message) {
        super(message);
    }
}

class InvalidPaymentException extends PaymentException {
    InvalidPaymentException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends PaymentException {
    InsufficientFundsException(String message) {
        super(message);
    }
}

class OrderException extends ApplicationException {
    OrderException(String message) {
        super(message);
    }
}

class EmptyCartException extends OrderException {
    EmptyCartException(String message) {
        super(message);
    }
}

class Product {
    int id;
    String name;
    double price;
    int stock;

    Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String toString() {
        return id + " - " + name + " - Rs." + price + " - Stock: " + stock;
    }
}

public class Q7 {

    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Product> cart = new ArrayList<>();

    static void searchProduct(int id) throws ProductNotFoundException {
        for (Product p : products) {
            if (p.id == id) {
                System.out.println("Product found: " + p);
                return;
            }
        }
        throw new ProductNotFoundException("Product not found.");
    }

    static void addToCart(int id) throws ProductNotFoundException, OutOfStockException {
        for (Product p : products) {
            if (p.id == id) {
                if (p.stock <= 0) {
                    throw new OutOfStockException("Product is out of stock.");
                }
                cart.add(p);
                p.stock--;
                System.out.println("Product added to cart.");
                return;
            }
        }
        throw new ProductNotFoundException("Product not found.");
    }

    static void removeFromCart(int id) throws ProductNotFoundException {
        for (Product p : cart) {
            if (p.id == id) {
                cart.remove(p);
                p.stock++;
                System.out.println("Product removed from cart.");
                return;
            }
        }
        throw new ProductNotFoundException("Product is not in cart.");
    }

    static double getTotal() throws EmptyCartException {
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty.");
        }

        double total = 0;

        for (Product p : cart) {
            total += p.price;
        }

        return total;
    }

    static void makePayment(double amount)
            throws EmptyCartException, InvalidPaymentException,
            InsufficientFundsException {

        double total = getTotal();

        if (amount <= 0) {
            throw new InvalidPaymentException("Invalid payment amount.");
        }

        if (amount < total) {
            throw new InsufficientFundsException("Insufficient funds.");
        }

        System.out.println("Payment successful.");
        System.out.println("Total amount = Rs." + total);
        System.out.println("Change = Rs." + (amount - total));

        cart.clear();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        products.add(new Product(1, "Laptop", 50000, 5));
        products.add(new Product(2, "Mouse", 500, 10));
        products.add(new Product(3, "Keyboard", 1000, 3));

        try {
            System.out.println("Available Products:");
            for (Product p : products) {
                System.out.println(p);
            }

            System.out.print("\nEnter product ID to search: ");
            int id = sc.nextInt();
            searchProduct(id);

            System.out.print("Enter product ID to add to cart: ");
            id = sc.nextInt();
            addToCart(id);

            System.out.println("\nCart:");
            for (Product p : cart) {
                System.out.println(p.name + " - Rs." + p.price);
            }

            System.out.println("Cart Total = Rs." + getTotal());

            System.out.print("\nEnter payment amount: ");
            double amount = sc.nextDouble();

            makePayment(amount);
        }
        catch (ProductNotFoundException e) {
            System.out.println("Product Error: " + e.getMessage());
        }
        catch (OutOfStockException e) {
            System.out.println("Stock Error: " + e.getMessage());
        }
        catch (InvalidPaymentException e) {
            System.out.println("Payment Error: " + e.getMessage());
        }
        catch (InsufficientFundsException e) {
            System.out.println("Payment Error: " + e.getMessage());
        }
        catch (EmptyCartException e) {
            System.out.println("Order Error: " + e.getMessage());
        }
        finally {
            sc.close();
            System.out.println("Shopping process completed.");
        }
    }
}