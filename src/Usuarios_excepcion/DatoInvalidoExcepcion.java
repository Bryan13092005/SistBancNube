package Usuarios_excepcion;

public class DatoInvalidoExcepcion extends RuntimeException {
    public DatoInvalidoExcepcion(String message) {
        super(message);
    }
}
