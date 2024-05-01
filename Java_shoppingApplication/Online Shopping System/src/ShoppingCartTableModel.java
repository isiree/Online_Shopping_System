import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ShoppingCartTableModel extends AbstractTableModel {
    private List<CartItem> cartItems;
    private final String[] columnNames = {"Product", "Quantity", "Price"};

    public ShoppingCartTableModel(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public int getRowCount() {
        return cartItems.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CartItem cartItem = cartItems.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cartItem.getProduct().getpName();
            case 1:
                return cartItem.getQuantity();
            case 2:
                return cartItem.getTotalPrice();
            default:
                return null;
        }
    }

    public void updateData(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        fireTableDataChanged();
    }


}

