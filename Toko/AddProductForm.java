package Toko;

import javax.swing.*;
import java.awt.*;

public class AddProductForm extends JInternalFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;

    public AddProductForm() {
        setTitle("Add Product");
        setSize(300, 200);
        setClosable(true);
        setResizable(true);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Stock:"));
        stockField = new JTextField();
        panel.add(stockField);

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProduct());
        panel.add(saveButton);

        add(panel);
    }

    private void saveProduct() {
        String name = nameField.getText();
        int id;
        double price;
        int stock;

        try {
            id = Integer.parseInt(idField.getText());
            price = Double.parseDouble(priceField.getText());
            stock = Integer.parseInt(stockField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID, price, or stock value", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Product newProduct = new Product(id, name, price, stock);
        DatabaseHelper.addProduct(newProduct);
        JOptionPane.showMessageDialog(this, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
