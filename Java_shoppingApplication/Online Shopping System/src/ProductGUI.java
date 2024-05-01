import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductGUI extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JTable productTable;
    private JButton cartButton;
    private JButton sortButton;
    private ProductTableModel productTableModel;
    private WestminsterShoppingManager westminsterShoppingManagerIns1;
    private JTextArea productDetailsTextArea;

    private ShoppingCartGUI cartGUI;
    private String selectedCategory;

    public ProductGUI(WestminsterShoppingManager manager) {
        this.westminsterShoppingManagerIns1 = manager;
        this.cartGUI = new ShoppingCartGUI(manager.getShoppingCart());


        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTable = new JTable();
        productTableModel = new ProductTableModel(westminsterShoppingManagerIns1.getSystemProductList(), productTable);
        productTableModel.setData(westminsterShoppingManagerIns1.getSystemProductList());  // Set initial data
        productTable.setModel(productTableModel);

        cartButton = new JButton("Shopping Cart");


        JButton addToCartButton = new JButton("Add to Cart");

        // Add the addToCartButton to the frame's SOUTH position
        add(addToCartButton, BorderLayout.SOUTH);
        productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setEditable(false);  // Make it non-editable

        // Add the productDetailsTextArea to the frame's SOUTH position
        add(new JScrollPane(productDetailsTextArea), BorderLayout.SOUTH);

        // Set up the layout
        setLayout(new BorderLayout());

        // Create a panel for the product selection components
        JPanel selectionPanel = new JPanel();
        selectionPanel.add(new JLabel("Select Product Category: "));
        selectionPanel.add(categoryComboBox);
        selectionPanel.add(cartButton);

        // Add the selection panel to the frame
        add(selectionPanel, BorderLayout.NORTH);

        // Add the product table to the frame
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Set up the frame
        setTitle("Westminster Shopping Centre");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add action listener to the categoryComboBox
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable();  // Update the table when a category is selected
            }
        });

        // Add action listener to the cartButton
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement code to view the cart
                openShoppingCart();
            }
        });

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JScrollPane(productDetailsTextArea), BorderLayout.CENTER);

        // Create a button for adding items to the shopping cart
        addToCartButton = new JButton("Add to Cart");
        detailsPanel.add(addToCartButton, BorderLayout.SOUTH);

        // Add the details panel to the frame's SOUTH position
        add(detailsPanel, BorderLayout.SOUTH);

        // Add action listener to the addToCartButton
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();  //  add the selected item to the shopping cart
            }
        });

        // Add action listener to the productTable
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                displayProductDetails(selectedRow);  // Display product details when a row is selected
            }
        });

        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private Border columnHeaderBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
            private Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Apply custom border based on whether it's a header or a cell
                if (row == -1) {
                    // Column header
                    setBorder(columnHeaderBorder);
                } else {
                    // Cell
                    setBorder(cellBorder);
                }

                return component;
            }
        });

        // Set custom renderer for the productTable
        productTable.setModel(new ProductTableModel(westminsterShoppingManagerIns1.getSystemProductList(), productTable));

        // Set custom renderer for the productTable
        productTable.setDefaultRenderer(Object.class, new ProductTableModel(westminsterShoppingManagerIns1.getSystemProductList(), productTable).new ProductTableCellRenderer());



        // Set custom renderer for column headers
        JTableHeader header = productTable.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            private Border columnHeaderBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(columnHeaderBorder);
                return component;
            }
        });

        // Create a "Sort" button
        JButton sortButton = new JButton("Sort");

        // Add the sortButton to the frame
        selectionPanel.add(sortButton);

        // Add action listener to the sortButton
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortProductsAlphabetically();  // Sort products alphabetically
            }
        });

        // Make the frame visible after all components are added
        setVisible(true);
    }

    // Method to update the product table based on the selected category
    private void updateProductTable() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        List<Product> filteredProducts;

        if ("All".equalsIgnoreCase(selectedCategory)) {
            // Show all products
            filteredProducts = westminsterShoppingManagerIns1.getSystemProductList();
        } else {
            // Filter products based on the first letter of the selected category
            char selectedCategoryFirstLetter = selectedCategory.charAt(0);
            filteredProducts = westminsterShoppingManagerIns1.getSystemProductList().stream()
                    .filter(product -> selectedCategory.equalsIgnoreCase(product.getpCat()))
                    .collect(Collectors.toList());

        }

        // Update the table model
        productTableModel.setData(filteredProducts);
        productTableModel.fireTableDataChanged();
        productTable.revalidate();
        productTable.repaint();
    }



    private void addToShoppingCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Product selectedProduct = productTableModel.getProductAt(selectedRow);
            if (selectedProduct != null) {
                if (westminsterShoppingManagerIns1.getShoppingCart() == null) {
                    westminsterShoppingManagerIns1.setShoppingCart(new ShoppingCart()); // Initialize the shopping cart
                }
                westminsterShoppingManagerIns1.addToShoppingCart(selectedProduct);


                // Recalculate and update the total cost in the ShoppingCartGUI
                cartGUI.updateTotalCost();
                cartGUI.updateTable();
                cartGUI.setVisible(true);

                JOptionPane.showMessageDialog(this, "Item added to the shopping cart!");
            }
        }
    }

    private void sortProductsAlphabetically() {
        List<Product> sortedProducts = westminsterShoppingManagerIns1.getSystemProductList()
                .stream()
                .sorted(Comparator.comparing(p -> p.getpName().toLowerCase(), Comparator.naturalOrder()))
                .collect(Collectors.toList());

        // Update the table model data with sorted products
        productTableModel.setData(sortedProducts);
        productTableModel.fireTableDataChanged();
    }


    private void openShoppingCart() {
        //  open the shopping cart window
        cartGUI.setVisible(true);
    }

    private void displayProductDetails(int rowIndex) {
        // Display product details in the JTextArea
        Product selectedProduct = productTableModel.getProductAt(rowIndex);
        if (selectedProduct != null) {
            StringBuilder details = new StringBuilder();
            details.append("Product ID: ").append(selectedProduct.getProductId()).append("\n");
            details.append("Product Name: ").append(selectedProduct.getpName()).append("\n");
            details.append("Category: ").append(selectedProduct.getpCat()).append("\n");
            details.append("Price: $").append(selectedProduct.getPrice()).append("\n");
            details.append("Available Quantity: ").append(selectedProduct.getNumItemLeft()).append("\n");

            // Additional details based on the product category
            if (selectedProduct instanceof Electronics) {
                Electronics electronics = (Electronics) selectedProduct;
                details.append("Brand: ").append(electronics.getBrand()).append("\n");
                details.append("Warranty Period: ").append(electronics.getWarrantyPeriod()).append(" weeks\n");
            } else if (selectedProduct instanceof Clothing) {
                Clothing clothing = (Clothing) selectedProduct;
                details.append("Colour: ").append(clothing.getColour()).append("\n");
                details.append("Size: ").append(clothing.getSize()).append("\n");
            }

            productDetailsTextArea.setText(details.toString());
        }
    }
}
