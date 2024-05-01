import javax.swing.*;
import java.util.Scanner;

public class Execute {

    public static void main(String[] args) {
        System.out.println("Update the shop with products");
        WestminsterShoppingManager manager1 = new WestminsterShoppingManager();
        manager1.menu();


        System.out.println("Dear customer , please enter your account and proceed to the shopping interface.");
        // Log in the user
        User user = enterAccount();
        // Start the GUI only if the user is logged in
        System.out.println("Shopping interface is loading...");
        if (user != null) {
            SwingUtilities.invokeLater(() -> {
                new ProductGUI(manager1);
            });
        }
    }

    public static User enterAccount() {
        Scanner scanner = new Scanner(System.in);
        User user = new User("", "");

        // Ask for username
        while (true) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine().trim(); // Trim leading and trailing spaces

            if (!username.isEmpty()) {
                user.setUserName(username);

                // Username is valid, break the loop
                break;
            } else {
                System.out.println("Username cannot be empty. Please enter a valid username.");
            }
        }

        // Ask for password
        while (true) {
            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim(); // Trim leading and trailing spaces

            if (!password.isEmpty()) {
                user.setPassword(password);

                // Display user information
                System.out.println("Username: " + user.getUserName());
                System.out.println("Password: " + user.getPassword());

                // Break the loop once both username and password are entered
                break;
            } else {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            }
        }
        scanner.close();
        return user;

    }
}
