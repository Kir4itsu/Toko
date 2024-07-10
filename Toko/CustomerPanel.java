package Toko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerPanel extends JPanel {
    private JTable productTable;
    private JButton addToCartButton;
    private JButton checkoutButton;
    private Map<Product, Integer> cart;
    private User user;

    public CustomerPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());
        cart = new HashMap<>();
        initializeComponents();
    }

    private void initializeComponents() {
        List<Product> products = DatabaseHelper.getProducts();
        productTable = new JTable(new ProductTableModel(products));
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addToCartButton = new JButton("Add to Cart");
        checkoutButton = new JButton("Checkout");

        buttonPanel.add(addToCartButton);
        buttonPanel.add(checkoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Product selectedProduct = ((ProductTableModel) productTable.getModel()).getProductAt(selectedRow);
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:", "Add to Cart", JOptionPane.PLAIN_MESSAGE);
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(this, "Quantity must be positive.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (quantity > selectedProduct.getStock()) {
                        JOptionPane.showMessageDialog(this, "Not enough stock available.", "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    cart.put(selectedProduct, cart.getOrDefault(selectedProduct, 0) + quantity);
                    JOptionPane.showMessageDialog(this, "Product added to cart.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add to cart.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Update product stock in the database
            product.setStock(product.getStock() - quantity);
            DatabaseHelper.updateProduct(product);

            // Insert order into the database
            DatabaseHelper.addOrder(user.getId(), product.getId(), quantity);
        }

        cart.clear();
        refreshProductTable();
        JOptionPane.showMessageDialog(this, "Checkout successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshProductTable() {
        List<Product> products = DatabaseHelper.getProducts();
        productTable.setModel(new ProductTableModel(products));
    }
}
