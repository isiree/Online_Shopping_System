import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> data;
    private JTable productTable;

    public ProductTableModel(List<Product> systemProductList, JTable productTable) {
        this.data = new ArrayList<>(systemProductList);
        this.productTable = productTable;
        // Set up a compound border for the table
        Border outerBorder = BorderFactory.createLineBorder(Color.BLACK, 2);  // Thicker outer border
        Border innerBorder = BorderFactory.createLineBorder(Color.BLACK, 1);  // Slimmer inner border
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

        // Apply the compound border to the table
        productTable.setBorder(compoundBorder);
    }

    public void setData(List<Product> newData) {
        this.data = new ArrayList<>(newData);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Product ID";
            case 1:
                return "Name";
            case 2:
                return "Category";
            case 3:
                return "Price ($)";
            case 4:
                return "Info";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getProductId();
            case 1:
                return product.getpName();
            case 2:
                String catName = product.getpCat();
                if (catName.equalsIgnoreCase("e"))
                    return "Electronics";
                else {
                    return "Clothing";
                }
            case 3:
                return product.getPrice();
            case 4:
                List<String> infoList = new ArrayList<>();

                if (product instanceof Electronics) {
                    Electronics electronicsProduct = (Electronics) product;
                    if (electronicsProduct.getBrand() != null) {
                        infoList.add("Brand: "+ electronicsProduct.getBrand());
                    }
                    infoList.add("Warranty Period: " + electronicsProduct.getWarrantyPeriod() + " weeks");
                } else if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    if (clothingProduct.getSize() != null) {
                        infoList.add("Colour: " + clothingProduct.getSize());
                    }
                    if (clothingProduct.getColour() != null) {
                        infoList.add("Size: " + clothingProduct.getColour());
                    }
                }

                return String.join(", ", infoList);

            default:
                return null;
        }
    }

    public Product getProductAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            return data.get(rowIndex);
        }
        return null;
    }

    class ProductTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Assuming the available quantity is a variable in the Product class
            Product product = ((ProductTableModel) table.getModel()).getProductAt(row);

            // Check if the product is not null and has a valid available quantity
            if (product != null && product.getNumItemLeft() < 3) {
                component.setBackground(Color.RED);
                component.setForeground(Color.WHITE); // Set text color to white for better visibility
            } else {
                // Reset background and text color for other rows
                component.setBackground(table.getBackground());
                component.setForeground(table.getForeground());
            }

            return component;
        }
    }

}

