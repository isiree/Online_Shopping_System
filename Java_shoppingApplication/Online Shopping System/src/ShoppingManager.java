import java.util.List;

public interface ShoppingManager {
    void addNewProduct();
    void deleteProduct();
    void printProductList();
    void saveProductList();
    void loadProductList();
//    List<Product> filterProductsByType(String category);
    java.util.List<Product> getSystemProductList();

    void addToShoppingCart(Product product);
}
