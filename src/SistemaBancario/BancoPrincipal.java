package SistemaBancario;

import Usuarios_excepcion.UsuariosDAO;

import javax.swing.*;


public class BancoPrincipal extends JFrame{
    private JButton depositoButton;
    private JButton salirButton;
    private JButton retiroButton;
    private JButton transferenciaButton;
    private JPanel Banca;
    private JLabel saldo;
    private JLabel usuario;
    private JTextArea HTransacciones;
    private JButton actualizarDatosButton;
    private JButton eliminarCuentaButton;

    public BancoPrincipal(AccionesBancarias usuarioIngresado){
        setContentPane(Banca);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,300);
        setLocationRelativeTo(null);
        saldo.setText(String.valueOf(usuarioIngresado.getSaldo()));
        usuario.setText(usuarioIngresado.getNombre().toUpperCase());
        HTransacciones.setText(UsuariosDAO.Obtenerhistorial(usuarioIngresado.getNombre().toLowerCase()));

        eliminarCuentaButton.addActionListener(e -> {
            String usuario=usuarioIngresado.getNombre().toLowerCase();
            boolean eliminacion=false;
            String respuesta=JOptionPane.showInputDialog("Ingresa 'SI' para eliminar tu cuenta. Todos tus datos se perderan definitivamente: ");
            if (respuesta.equalsIgnoreCase("SI")){
                eliminacion=UsuariosDAO.eliminarCuenta(usuario);
            }else{
                JOptionPane.showMessageDialog(null,"Eliminacion cancelada","ABORTADO",JOptionPane.WARNING_MESSAGE);
            }
             if (eliminacion){
                 dispose();
                 new Login();
             }
        });

        actualizarDatosButton.addActionListener(e -> {
            String usuarioNuevo=JOptionPane.showInputDialog("Ingresa el nuevo nombre de usuario (Al menos 4 digitos): ");
            String claveNueva=JOptionPane.showInputDialog("Ingresa la nueva clave (Al menos 4 digitos): ");
            if (usuarioNuevo.trim().isEmpty()||usuarioNuevo.length()<4||claveNueva.trim().isEmpty()||claveNueva.length()<4){
                JOptionPane.showMessageDialog(null,"Dato Incorrecto","Error Actualizacion",JOptionPane.ERROR_MESSAGE);
            }else {
                UsuariosDAO.modificarUsuario(usuarioNuevo,claveNueva,usuarioIngresado.getNombre().toLowerCase());
            }
        });
        depositoButton.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingresa el monto a depositar: $"));
                if (monto > 0) {
                    usuarioIngresado.deposito(monto);
                    saldo.setText(String.valueOf(usuarioIngresado.getSaldo()));
                    UsuariosDAO.ActualizarHistorial("Deposito de: $"+monto+".",usuarioIngresado.getNombre().toLowerCase());
                    HTransacciones.setText(UsuariosDAO.Obtenerhistorial(usuarioIngresado.getNombre().toLowerCase()));
                    JOptionPane.showMessageDialog(null,"Transaccion exitosa");
                } else {
                    JOptionPane.showMessageDialog(null, "Monto invalido", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Ingresaste una letra","ERROR",JOptionPane.ERROR_MESSAGE);
            }catch(NullPointerException ex){}
        });

        retiroButton.addActionListener(e -> {
            try{
                double monto=Double.parseDouble(JOptionPane.showInputDialog("Ingresa el monto: $"));
                if (monto>Double.parseDouble(saldo.getText())||monto<0){
                    JOptionPane.showMessageDialog(null, "Monto invalido", "ERROR", JOptionPane.WARNING_MESSAGE);
                }else{
                    usuarioIngresado.retiro(monto);
                    saldo.setText(String.valueOf(usuarioIngresado.getSaldo()));
                    UsuariosDAO.ActualizarHistorial("Retiro de: $"+monto+".",usuarioIngresado.getNombre().toLowerCase());
                    HTransacciones.setText(UsuariosDAO.Obtenerhistorial(usuarioIngresado.getNombre().toLowerCase()));
                    JOptionPane.showMessageDialog(null,"Transaccion exitosa");
                }
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Monto invalido","ERROR",JOptionPane.ERROR_MESSAGE);
            }catch(NullPointerException ex){}
        });

        transferenciaButton.addActionListener(e -> {
            try{
                double monto=Double.parseDouble(JOptionPane.showInputDialog("Ingresa el monto: $"));
                String destinatario=JOptionPane.showInputDialog("Ingresa el destinatario: ");
                if (monto>Double.parseDouble(saldo.getText())||monto<0){
                    JOptionPane.showMessageDialog(null, "Monto invalido", "ERROR", JOptionPane.WARNING_MESSAGE);
                }else if(destinatario.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Usuario vacio", "ERROR", JOptionPane.WARNING_MESSAGE);
                }else{
                    if(usuarioIngresado.transferir(monto,destinatario)) {
                        saldo.setText(String.valueOf(usuarioIngresado.getSaldo()));
                        UsuariosDAO.ActualizarHistorial("Transferencia de: $" + monto + " hacia " + destinatario + ".", usuarioIngresado.getNombre().toLowerCase());
                        HTransacciones.setText(UsuariosDAO.Obtenerhistorial(usuarioIngresado.getNombre().toLowerCase()));
                        UsuariosDAO.ActualizarHistorial("Recibido de " + usuarioIngresado.getNombre().toUpperCase() + " por: $ " + monto+".", destinatario);
                    }
                }
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Monto invalido","ERROR",JOptionPane.ERROR_MESSAGE);
            }catch(NullPointerException ex){}
        });

        salirButton.addActionListener(e -> {
            dispose();
            new Login();
        });
    }
}
