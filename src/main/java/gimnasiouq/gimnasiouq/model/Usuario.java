package gimnasiouq.gimnasiouq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Usuario {
    private final String nombre;
    private final String identificacion;
    private final int edad;
    private final String telefono;
    private Membresia membresia;
    private final List<ReservaClase> reservas = new ArrayList<>();

    protected Usuario(String nombre, String identificacion, int edad, String telefono) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.edad = edad;
        this.telefono = telefono;
    }



    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public int getEdad() { return edad; }
    public String getTelefono() { return telefono; }
    public Membresia getMembresia() { return membresia; }
    public void setMembresia(Membresia membresia) { this.membresia = membresia; }
    public List<ReservaClase> getReservas() { return reservas; }
}
