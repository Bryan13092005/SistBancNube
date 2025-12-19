package Usuarios_excepcion;

import SistemaBancario.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuariosDAO {
    public static void insertarCliente(Usuario u){
        String sql = "insert into cuentasUsuario(usuario,clave,monto) values(?,?,?)";

        try (Connection con= Conexion.getConexion();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,u.getUsuario().toLowerCase());
            ps.setString(2,u.getClave());
            ps.setDouble(3, u.getMonto());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"REGISTRO EXITOSO");
        } catch (Exception _e){
            _e.printStackTrace();
        }
    }

    public static boolean eliminarCuenta(String usuario){
        String sql="delete from cuentasUsuario where usuario=?";
        try (Connection conn=Conexion.getConexion();
        PreparedStatement st=conn.prepareStatement(sql)){
            st.setString(1,usuario);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null,"Eliminacion Exitosa");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void modificarUsuario(String usuNuevo,String claveNueva,String usuarioActual){
        String sql="update cuentasUsuario set usuario=?,clave=? where usuario=?";
        try(Connection conn=Conexion.getConexion();
        PreparedStatement st=conn.prepareStatement(sql)){
        st.setString(1,usuNuevo);
        st.setString(2,claveNueva);
        st.setString(3,usuarioActual);
        st.executeUpdate();
        JOptionPane.showMessageDialog(null,"Actualizacion exitosa. En el proximo ingreso vera sus cambios");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean existeUsuario(String usuario) {

        String sql = "SELECT 1 FROM cuentasUsuario WHERE usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.toLowerCase());
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario validarLogin(String usuario, String clave) {

        String sql = "SELECT * FROM cuentasUsuario WHERE usuario = ? AND clave = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("usuario"),
                        rs.getString("clave"),
                        rs.getDouble("monto"),
                        rs.getString("historialTransacciones")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String Obtenerhistorial(String usuario) {
        String sql="select historialTransacciones from cuentasUsuario where usuario=?";
        try(Connection cnn=Conexion.getConexion();
        PreparedStatement ps= cnn.prepareStatement(sql)){
            if(UsuariosDAO.existeUsuario(usuario)){
                ps.setString(1,usuario);
                ResultSet rs=ps.executeQuery();
                if (rs.next()){
                    return rs.getString("historialTransacciones");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Sin historial";
    }

    public static void ActualizarHistorial(String nuevaAccion,String usuario) {
        String sql= """
        update cuentasUsuario
        set historialTransacciones =
            if(
                historialTransacciones = 'Ninguna',
                ?,
                concat(historialTransacciones,char(10),?)
            )
        where usuario = ?
        """;
        if (UsuariosDAO.existeUsuario(usuario)) {
            try (Connection conn = Conexion.getConexion();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1,nuevaAccion);
                ps.setString(2,nuevaAccion);
                ps.setString(3,usuario);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void actualizarMontos(String usuario,double montoNuevo){
        if(UsuariosDAO.existeUsuario(usuario)){
            String sql="update cuentasUsuario set monto=? where usuario=?";
            try(Connection conn=Conexion.getConexion();
                PreparedStatement ps=conn.prepareStatement(sql)){
                ps.setDouble(1,montoNuevo);
                ps.setString(2,usuario);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Accion realizada correctamente");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Usuario no encontrado","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean Transferencia(String usuario,double monto){
        String sql="update cuentasUsuario set monto=(monto+?) where usuario=?";
        try(Connection conn=Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setDouble(1,monto);
            ps.setString(2,usuario);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Transferencia exitosa");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
