package Toko;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    private User user;
    private JDesktopPane desktopPane;

    public Dashboard(User user) {
        this.user = user;
        initialize();
    }

    private void initialize() {
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        JPanel panel;
        switch (user.getRole()) {
            case "admin":
                panel = new AdminPanel();
                break;
            case "seller":
                panel = new SellerPanel();
                break;
            default:
                panel = new CustomerPanel();
                break;
        }

        JInternalFrame internalFrame = new JInternalFrame("Dashboard", true, false, true, true);
        internalFrame.setContentPane(panel);
        internalFrame.pack();
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);

        setupMenuBar();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem logoutItem = new JMenuItem("Log Out");
        logoutItem.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
        menu.add(logoutItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void closeFrameIfExists(Class<? extends JInternalFrame> frameClass) {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frameClass.isInstance(frame)) {
                frame.dispose();
                break;
            }
        }
    }

    private class AdminPanel extends JPanel {
        private JTable productTable;
        private JButton addButton;
        private JButton editButton;
        private JButton deleteButton;
        private JButton ordersButton;

        public AdminPanel() {
            setLayout(new BorderLayout());
            initializeComponents();
        }

        private void initializeComponents() {
            List<Product> products = DatabaseHelper.getProducts();
            productTable = new JTable(new ProductTableModel(products));
            add(new JScrollPane(productTable), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            addButton = new JButton("Add");
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");
            ordersButton = new JButton("View Orders");

            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(ordersButton);

            add(buttonPanel, BorderLayout.SOUTH);

            addButton.addActionListener(e -> openAddProductForm());
            editButton.addActionListener(e -> openEditProductForm());
            deleteButton.addActionListener(e -> deleteProduct());
            ordersButton.addActionListener(e -> openOrdersPanel());
        }

        private void openAddProductForm() {
            closeFrameIfExists(AddProductForm.class);
            AddProductForm addProductForm = new AddProductForm();
            addProductForm.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    refreshProductTable();
                }
            });
            desktopPane.add(addProductForm);
            addProductForm.setVisible(true);
        }

        private void openEditProductForm() {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = ((ProductTableModel) productTable.getModel()).getProductAt(selectedRow);
                closeFrameIfExists(EditProductForm.class);
                EditProductForm editProductForm = new EditProductForm(selectedProduct);
                editProductForm.addInternalFrameListener(new InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosed(InternalFrameEvent e) {
                        refreshProductTable();
                    }
                });
                desktopPane.add(editProductForm);
                editProductForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void deleteProduct() {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = ((ProductTableModel) productTable.getModel()).getProductAt(selectedRow);
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    DatabaseHelper.deleteProduct(selectedProduct.getId());
                    ((ProductTableModel) productTable.getModel()).removeProductAt(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }
        

        private void refreshProductTable() {
            List<Product> products = DatabaseHelper.getProducts();
            productTable.setModel(new ProductTableModel(products));
        }

        private void openOrdersPanel() {
            closeFrameIfExists(OrdersPanel.class);
            OrdersPanel ordersPanel = new OrdersPanel();
            desktopPane.add(ordersPanel);
            ordersPanel.setVisible(true);
        }
    }

    private class SellerPanel extends JPanel {
        private JTable productTable;
        private JButton addButton;
        private JButton editButton;
        private JButton deleteButton;

        public SellerPanel() {
            setLayout(new BorderLayout());
            initializeComponents();
        }

        private void initializeComponents() {
            List<Product> products = DatabaseHelper.getProducts();
            productTable = new JTable(new ProductTableModel(products));
            add(new JScrollPane(productTable), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            addButton = new JButton("Add");
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            add(buttonPanel, BorderLayout.SOUTH);

            addButton.addActionListener(e -> openAddProductForm());
            editButton.addActionListener(e -> openEditProductForm());
            deleteButton.addActionListener(e -> deleteProduct());
        }

        private void openAddProductForm() {
            closeFrameIfExists(AddProductForm.class);
            AddProductForm addProductForm = new AddProductForm();
            addProductForm.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    refreshProductTable();
                }
            });
            desktopPane.add(addProductForm);
            addProductForm.setVisible(true);
        }

        private void openEditProductForm() {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = ((ProductTableModel) productTable.getModel()).getProductAt(selectedRow);
                closeFrameIfExists(EditProductForm.class);
                EditProductForm editProductForm = new EditProductForm(selectedProduct);
                editProductForm.addInternalFrameListener(new InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosed(InternalFrameEvent e) {
                        refreshProductTable();
                    }
                });
                desktopPane.add(editProductForm);
                editProductForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void deleteProduct() {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = ((ProductTableModel) productTable.getModel()).getProductAt(selectedRow);
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    DatabaseHelper.deleteProduct(selectedProduct.getId());
                    ((ProductTableModel) productTable.getModel()).removeProductAt(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }
        

        private void refreshProductTable() {
            List<Product> products = DatabaseHelper.getProducts();
            productTable.setModel(new ProductTableModel(products));
        }
    }

    private class CustomerPanel extends JPanel {
        private JTable productTable;
        private JButton addToCartButton;
        private JButton checkoutButton;

        public CustomerPanel() {
            setLayout(new BorderLayout());
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
            
        }
    }
}
