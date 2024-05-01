import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> cartItems;
    private List<Product> productList;
    private int totalCost;
    private boolean firstPurchaseDiscountApplied;

    private  double categoryDiscount;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.productList = new ArrayList<>();
        this.firstPurchaseDiscountApplied = false;
    }

    public void addItem(Product product) {
        // Check if the product is already in the cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(product)) {
                cartItem.incrementQuantity();
                product.setNumItemLeft(product.getNumItemLeft() - 1); // Reduce available quantity
                return;
            }
        }

        // If the product is not in the cart, add a new cart item
        cartItems.add(new CartItem(product));
        product.setNumItemLeft(product.getNumItemLeft() - 1); // Reduce available quantity
        productList.add(product);
    }

    /*public void removeItem(String productIdInput) {
        for (Product product : productList) {
            if (product.getProductId().equals(productIdInput)) {
                System.out.println("Found product: " + product);
                productList.remove(product);
                System.out.println("Product deleted");
                break; // Exit the loop once the product is found and removed
            }
        }
    }*/

    public void calculateTotalCost() {
        totalCost = 0;  // Reset totalCost to 0

        // Calculate total cost based on the contents of the shopping cart
        for (CartItem cartItem : cartItems) {
            totalCost += cartItem.getTotalPrice();  // Use getTotalPrice from CartItem, which considers quantity
        }

        // Apply discounts
        applyFirstPurchaseDiscount();
        applyCategoryDiscount();
    }


    public int getTotalCost() {
        return totalCost;
    }

   /* public List<Product> getProductList() {
        return productList;
    }*/

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    private double applyFirstPurchaseDiscount() {
        if (!firstPurchaseDiscountApplied) {
            firstPurchaseDiscountApplied = true;
            return totalCost * 0.1; // 10% discount for the very first purchase
        } else {
            return 0;
        }
    }

    public double applyCategoryDiscount() {
        categoryDiscount = 0;
        int electronicsCount = 0;
        int clothingCount = 0;

        // Count the number of items in each category
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product instanceof Electronics) {
                electronicsCount += cartItem.getQuantity();
            }if (product instanceof Clothing) {
                clothingCount += cartItem.getQuantity();
            }
        }

        // Apply 20% discount when the user buys at least three products of the same category
        if (electronicsCount >= 3) {
            categoryDiscount += totalCost * 0.2;
        }
        else if (clothingCount >= 3) {
            categoryDiscount += totalCost * 0.2;
        }




        return Math.round(categoryDiscount*10.0)/10.0;
    }


    public boolean isFirstPurchaseDiscountApplied() {
        return firstPurchaseDiscountApplied;
    }
}
