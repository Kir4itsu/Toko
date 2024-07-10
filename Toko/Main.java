package Toko;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        DatabaseHelper.initializeDatabase();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
