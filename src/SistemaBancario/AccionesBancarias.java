package SistemaBancario;

import Usuarios_excepcion.UsuariosDAO;

import javax.swing.*;

public class AccionesBancarias {
    private double saldo;
    private final String nombre;
    public AccionesBancarias(double saldo,String nombre){
        this.nombre=nombre;
        this.saldo=saldo;
    }
    UsuariosDAO ud=new UsuariosDAO();
    public void deposito(double monto){
        saldo+=monto;
        ud.actualizarMontos(nombre,saldo);
    }

    public void retiro(double monto){
        saldo-=monto;
        ud.actualizarMontos(nombre,saldo);
    }

    public boolean transferir(double monto,String usuarioTranferencia){
        if (UsuariosDAO.existeUsuario(nombre) && UsuariosDAO.existeUsuario(usuarioTranferencia.toLowerCase())){
            if (UsuariosDAO.Transferencia(usuarioTranferencia,monto)){
                saldo-=monto;
                ud.actualizarMontos(nombre,saldo);
                return true;
            }
        }else if (!UsuariosDAO.existeUsuario(usuarioTranferencia.toLowerCase())){
            JOptionPane.showMessageDialog(null,"No existe el usuario a transferir");
        }
        return false;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNombre() {
        return nombre;
    }
}
