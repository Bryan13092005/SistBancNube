package app;

import SistemaBancario.Login;

public class Main {
    //clase principal
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
