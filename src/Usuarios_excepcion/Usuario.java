package Usuarios_excepcion;

public class Usuario {
    private String usuario, clave,historialTransacciones;
    private double monto;

    public Usuario (String usuario,String clave, double monto,String historialTransacciones) throws DatoInvalidoExcepcion {
        if (usuario.isEmpty() || usuario.length() < 4) throw new DatoInvalidoExcepcion("Usuario invalido, un minimo de 4 caracteres.");
        if (clave.isEmpty() || clave.length() < 4) throw new DatoInvalidoExcepcion("Clave invalida, un minimo de 4 caracteres.");
        if (monto <= 0) {throw new DatoInvalidoExcepcion("Monto invalido, debe ser mayor a 0.");}
        this.usuario=usuario;
        this.clave=clave;
        this.monto=monto;
        this.historialTransacciones=historialTransacciones;
    }

    public double getMonto() {
        return monto;
    }

    public String getClave() {
        return clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Usuario: " + usuario + ", Clave: " + clave + ", Monto: " + monto;
    }

}
