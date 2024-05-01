import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ShoppingCartGUI extends JFrame {
    private JTable cartTable;
    private ShoppingCartTableModel cartTableModel;
    private ShoppingCart shoppingCart;
    private JLabel totalCostLabel;
    private JLabel firstPurchaseDiscountLabel;
    private JLabel categoryDiscountLabel;
    private JLabel finalPriceLabel;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        cartTableModel = new ShoppingCartTableModel(shoppingCart.getCartItems());
        cartTable = new JTable(cartTableModel);

        setLayout(new BorderLayout());

        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        totalCostLabel = new JLabel("Total Cost: $" + shoppingCart.getTotalCost());
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount: $" + calculateFirstPurchaseDiscount());
        categoryDiscountLabel = new JLabel("Category Discount: $" + shoppingCart.applyCategoryDiscount());
        finalPriceLabel = new JLabel("Final Price: $" + calculateFinalPrice());

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(4, 1));
        labelsPanel.add(totalCostLabel);
        labelsPanel.add(firstPurchaseDiscountLabel);
        labelsPanel.add(categoryDiscountLabel);
        labelsPanel.add(finalPriceLabel);

        add(labelsPanel, BorderLayout.SOUTH);

        setTitle("Shopping Cart");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cartTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private Border columnHeaderBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
            private Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row == -1) {
                    setBorder(columnHeaderBorder);
                } else {
                    setBorder(cellBorder);
                }

                return component;
            }
        });

        JTableHeader header = cartTable.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            private Border columnHeaderBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(columnHeaderBorder);
                return component;
            }
        });
    }

    public void updateTable() {
        if (shoppingCart != null) {
            cartTableModel.updateData(shoppingCart.getCartItems());
            updateTotalCost();
            firstPurchaseDiscountLabel.setText("First Purchase Discount: $" + calculateFirstPurchaseDiscount());
            categoryDiscountLabel.setText("Category Discount: $" + shoppingCart.applyCategoryDiscount());
            finalPriceLabel.setText("Final Price: $" + calculateFinalPrice());
        }
    }
    public void updateTotalCost() {
        if (shoppingCart != null) {
            // Recalculate the total cost based on the contents of the shopping cart
            shoppingCart.calculateTotalCost();
            // Update the total cost label
            totalCostLabel.setText("Total Cost: $" + shoppingCart.getTotalCost());
        }
    }

    private double calculateFirstPurchaseDiscount() {
        return  Math.round((shoppingCart.getTotalCost() * 0.1)*10.0)/10.0;
    }


    private double calculateFinalPrice() {
        double finalPrice = shoppingCart.getTotalCost() - calculateFirstPurchaseDiscount() - shoppingCart.applyCategoryDiscount();
        return Math.max(finalPrice, 0); // make sure the final price is not negative
    }
}
