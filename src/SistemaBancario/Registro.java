package SistemaBancario;

import Usuarios_excepcion.DatoInvalidoExcepcion;
import Usuarios_excepcion.Usuario;
import Usuarios_excepcion.UsuariosDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro extends JFrame{
    private JTextField txtUser;
    private JTextField txtClave;
    private JTextField txtMonto;
    private JPanel registro;
    private JPanel registro_panel;
    private JButton registrarButton;

    public Registro () {
        setContentPane(registro);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Registro Banco Togma-Bryan");
        setSize(400,270);
        setLocationRelativeTo(null);
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String nombre=txtUser.getText().trim();
                    String clave=txtClave.getText().trim();
                    double monto=Double.parseDouble(txtMonto.getText());
                    boolean existe = UsuariosDAO.existeUsuario(nombre);

                    if (!existe) {
                        Usuario nuevo = new Usuario(nombre, clave, monto,"Ninguna");

                        UsuariosDAO.insertarCliente(nuevo);
                        new Login();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "NOMBRE DE USUARIO YA EXISTENTE", "EXISTENTE", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Monto inválido. Ingresa un número válido.","INVALIDO",JOptionPane.ERROR_MESSAGE);
                    txtMonto.setText("");
                } catch (DatoInvalidoExcepcion ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    txtUser.setText("");
                    txtClave.setText("");
                    txtMonto.setText("");
                }


            }
        });
    }

}
