package Toko;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OrdersPanel extends JInternalFrame {
    public OrdersPanel() {
        setTitle("Orders");
        setSize(600, 400);
        setClosable(true);
        setResizable(true);

        JPanel panel = new JPanel(new BorderLayout());

        // Fetch orders from database
        List<Order> orders = DatabaseHelper.getOrders();

        // Display orders in a JTextArea
        JTextArea ordersTextArea = new JTextArea();
        ordersTextArea.setEditable(false);
        for (Order order : orders) {
            ordersTextArea.append("Order ID: " + order.getId() + ", User ID: " + order.getUserId() +
                    ", Product ID: " + order.getProductId() + ", Quantity: " + order.getQuantity() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(ordersTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }
}
