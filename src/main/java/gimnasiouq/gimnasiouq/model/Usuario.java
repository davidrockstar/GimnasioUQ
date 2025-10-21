package gimnasiouq.gimnasiouq.model;

import gimnasiouq.gimnasiouq.model.ReservaClase;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nombre;
    private String identificacion;
    private String edad;
    private String celular;
    private String membresia;
    private final List<ReservaClase> reservas = new ArrayList<>();

    public Usuario(String nombre, String identificacion, String edad, String celular, String membresia) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.edad = edad;
        this.celular = celular;
        this.membresia = membresia;
    }

    // Solo getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMembresia() {
        return membresia;
    }

    public void setMembresia(String membresia) {
        this.membresia = membresia;
    }

    public List<ReservaClase> getReservas() {
        return reservas;
    }
}