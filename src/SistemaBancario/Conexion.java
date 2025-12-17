package SistemaBancario;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://uxjcpsebks6uid6m:cGypISPPpopfnhAoAFQo@bbva1t1laqoqzcqamcms-mysql.services.clever-cloud.com:3306/bbva1t1laqoqzcqamcms";
    private static final String USER = "uxjcpsebks6uid6m";
    private static final String PASS = "cGypISPPpopfnhAoAFQo";

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
            return null;
        }
    }
}
