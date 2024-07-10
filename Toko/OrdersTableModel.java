package Toko;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrdersTableModel extends AbstractTableModel {
    private final List<Order> orders;
    private final String[] columnNames = {"Order ID", "User ID", "Product ID", "Quantity"};

    public OrdersTableModel(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return order.getId();
            case 1:
                return order.getUserId();
            case 2:
                return order.getProductId();
            case 3:
                return order.getQuantity();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
