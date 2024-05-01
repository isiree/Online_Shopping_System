
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    List<Product> systemProductList = new ArrayList<>();
    String productName;
    String productId;
    int productAvailableNum;
    int  productPrice;

    private ShoppingCart shoppingCart = new ShoppingCart();





    /*maintains the list of the products in the system and provides all
               the methods for the system manager defined in the console menu.*/
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        boolean continueMenu = true;
        while (continueMenu) {
            try {
                System.out.println("\nProduct Management Menu:");
                System.out.println("1. Add a new product");
                System.out.println("2. Delete a product");
                System.out.println("3. Print the list of products");
                System.out.println("4. Save to a file");
                System.out.println("5. Load from file");
                System.out.println("6. Clear saved content in the file"); // New option
                System.out.println("7. Exit");

                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addNewProduct();
                        break;
                    case 2:
                        deleteProduct();
                        break;
                    case 3:
                        printProductList();
                        break;
                    case 4:
                        saveProductList();
                        break;
                    case 5:
                        loadProductList();
                        break;
                    case 6:
                        clearFileContent(); // New case to clear saved content in the file
                        break;
                    case 7:
                        System.out.println("Exiting the program. Thank you for using our system!");
                        continueMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric option.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    Scanner scanner = new Scanner(System.in);

    @Override
    public void addNewProduct() {
        if (systemProductList.size() < 51) {
            System.out.println("Enter product details to add to system : ");
            getProductNameInput();
            getProductIdInput();

            if (exitAddProduct) {
                return;  // Exit the addNewProduct method if exitAddProduct is true
            }

            productAvailableNum = getAvailNumInput();
            productPrice = getPriceInput();
            getCatInput();  // validation and getting the input of the product category, the product is added to the system in here.
        } else {
            System.out.println("Maximum amount of products have been added to the system.");
        }
    }
    boolean exitAddProduct = false;  // New boolean variable to control the loop in addNewProduct

    public void getProductNameInput(){
        while (true) {
            System.out.print("Enter product name : ");
            productName = scanner.nextLine().trim(); // Trim to handle spaces

            if (!productName.isEmpty()) {
                break; // Exit the loop if a non-empty name is provided
            }

            System.out.println("Product name cannot be empty . Please re-enter.");
        }
    }

    public void getProductIdInput() {
        while (true) {
            System.out.println("Enter product ID {format (A1009)}: ");
            String enteredProductId = scanner.nextLine();

            // Check if the entered product ID is not empty and is in the correct format
            if (!enteredProductId.isEmpty() && Character.isLetter(enteredProductId.charAt(0)) && Character.isDigit(enteredProductId.charAt(1))) {
                // Check if the product ID already exists in the systemProductList
                boolean productExists = false;
                for (Product product : systemProductList) {
                    if (product.getProductId().equals(enteredProductId)) {
                        productExists = true;

                        // Product already exists, ask the user to re-enter or update
                        System.out.println("Product with ID " + enteredProductId + " already exists.");

                        // Ask the user if they want to re-enter or update
                        System.out.print("Do you want to (R)e-enter or (U)pdate the quantity? Enter 'R' or 'U': ");
                        String choice = scanner.nextLine();

                        if (choice.equalsIgnoreCase("R")) {
                            // Re-enter the product ID
                            break;
                        } else if (choice.equalsIgnoreCase("U")) {
                            // Update the quantity
                            System.out.print("Enter the updated quantity for product with ID " + enteredProductId + ": ");
                            int updatedQuantity = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline character

                            // Update the quantity in the existing product
                            product.setNumItemLeft(updatedQuantity);
                            System.out.println("Quantity updated successfully.");
                            exitAddProduct = true;  // Set the flag to exit the addNewProduct method
                            return;  // Exit the method after updating
                        } else {
                            System.out.println("Invalid choice. Please enter 'R' or 'U'.");
                            break;  // Exit the loop after displaying an error message
                        }
                    }
                }

                if (!productExists) {
                    // Product ID is in the correct format and does not exist, you can proceed
                    productId = enteredProductId;  // Update the global productId variable
                    exitAddProduct = false;  // Reset the flag to proceed with adding a new product
                    return;  // Exit the method after getting a valid product ID
                }
            } else {
                System.out.println("Invalid product ID entered. Please re-enter a valid input.");
            }
        }
    }


    public int  getAvailNumInput(){
        while (true){
            try{
                System.out.print("Enter available items  : ");
                productAvailableNum = scanner.nextInt();
                return productAvailableNum;
            } catch (InputMismatchException e) {
                // Handle the exception
                System.out.println("Invalid input. Please enter a valid integer for the available number of products.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
    public int  getPriceInput(){
        while (true){
            try{
                System.out.print("Enter product price in USD : ");
                int productPrice = scanner.nextInt();
                return productPrice;
            } catch (InputMismatchException e) {
                // Handle the exception
                System.out.println("Invalid input. Please enter a valid integer for the price.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
    public int  getWarrantyInput(){
        while (true){
            try{
                System.out.print("Enter Warranty Period  of the electronic product (in weeks)  : ");
                int productWarranty = scanner.nextInt();
                return productWarranty;
            } catch (InputMismatchException e) {
                // Handle the exception
                System.out.println("Invalid input. Please enter a valid integer for the warranty period .");

                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public void getCatInput(){
        while (true) {

            System.out.print("Enter E for electronics\nEnter C for clothing\nEnter product Category: ");
            String productCat = scanner.next();//to avoid the new line characters.
            scanner.nextLine();//to avoid the new line characters.

            if ("E".equalsIgnoreCase(productCat)) {
                String productBrand;
                do {
                    System.out.print("Enter brand of the electronic product  : ");
                    productBrand = scanner.nextLine();

                    if (productBrand.isEmpty()) {
                        System.out.println("Product brand cannot be empty. Please re-enter.");
                    }
                } while (productBrand.isEmpty());

                int productWarranty=getWarrantyInput();
                Electronics elec1 = new Electronics(productId,productName,productCat,productAvailableNum,productBrand,productWarranty,productPrice);
                systemProductList.add(elec1);
                System.out.println(systemProductList);
                break;
            }
            else if ("C".equalsIgnoreCase(productCat)) {
                System.out.print("Enter colour of the clothing product: ");
                String productColour = scanner.nextLine();

                while (true) {
                    System.out.print("Enter size of the clothing product (S/M/L): ");
                    String productSize = scanner.nextLine();

                    if ("S".equalsIgnoreCase(productSize) || "M".equalsIgnoreCase(productSize) || "L".equalsIgnoreCase(productSize)) {
                        // Valid size entered
                        Clothing cloth1 = new Clothing(productId,productName,productCat,productAvailableNum,productColour,productSize,productPrice);
                        systemProductList.add(cloth1);
                        System.out.println(systemProductList);
                        break;
                    } else {
                        System.out.println("Invalid size entered. Please enter S, M, or L.");
                    }
                }
                break;
            }
            else {
                System.out.println("Invalid input entered. please re-enter correctly!");
            }
        }

    }
    @Override
    public void deleteProduct() {
        System.out.print("Enter product Id of the product to be deleted {format (A1009)}: ");
        String productId = scanner.nextLine();

        boolean found = false;  // Flag to track if a match is found

        for (int i = 0; i < systemProductList.size(); i++) {
            Product product = systemProductList.get(i);
            if (product.getProductId().equals(productId)) {
                System.out.println("Found product: " + product);
                systemProductList.remove(i);  // Remove the product from the list
                System.out.println("Product deleted");
                System.out.println("Remaining products in the system: " + systemProductList.size());
                found = true;  // Set the flag to true since a match is found
                break;  // Exit the loop after deleting the product
            }
        }

        // Display an error message if no match is found
        if (!found) {
            System.out.println("Entered product ID not available");
        }
    }
    @Override
    public void printProductList() {

        bubbleSort(systemProductList);
        // Print the sorted list
        System.out.println("Sorted List of Products:");
        for (Product product : systemProductList) {
            System.out.println(product);
        }

    }

    static void bubbleSort(List<Product> systemProductList) {
        int n =systemProductList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare product IDs as strings and swap if necessary
                if (systemProductList.get(j).getProductId().compareTo(systemProductList.get(j + 1).getProductId()) > 0) {
                    // Swap products if they are in the wrong order
                    Product temp = systemProductList.get(j);
                    systemProductList.set(j, systemProductList.get(j + 1));
                    systemProductList.set(j + 1, temp);
                }
            }
        }
    }
    @Override
    public void saveProductList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productList.ser"))) {
            oos.writeObject(systemProductList);
            System.out.println("Product list saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving product list to file: " + e.getMessage());
        }
    }

    @Override
    public void loadProductList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productList.ser"))) {
            List<Product> loadedProductList = (List<Product>) ois.readObject();
            systemProductList.addAll(loadedProductList); // Append the deserialized data to the existing systemProductList
            System.out.println("Products loaded successfully from the file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. No products loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading product list from file: " + e.getMessage());
        }
    }

    private void clearFileContent() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productList.ser"))) {
            oos.writeObject(new ArrayList<Product>()); // Save an empty list to clear the content
            System.out.println("Content in the file cleared successfully.");
        } catch (IOException e) {
            System.out.println("Error clearing content in the file: " + e.getMessage());
        }
    }

   /* @Override
    public List<Product> filterProductsByType(String category) {
        return null;
    }*/









    @Override
     public List<Product> getSystemProductList() {
        return systemProductList;
    }

    @Override
    public void addToShoppingCart(Product product) {
        shoppingCart.addItem(product);
    }



    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }



}




