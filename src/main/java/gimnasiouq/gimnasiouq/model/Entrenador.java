package gimnasiouq.gimnasiouq.model;

import java.util.Objects;

public class Entrenador {
    private String nombre;
    private String identificacion;
    private String especialidad;
    private String clasesDisponibles;   // opcional: o relación con objeto Clase
    private String telefono;
    private String correo;

    public Entrenador(String nombre, String identificacion, String especialidad, String clasesDisponibles, String telefono, String correo) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.especialidad = especialidad;
        this.clasesDisponibles = clasesDisponibles;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Entrenador() {
    }

    public Entrenador(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getClasesDisponibles() {
        return clasesDisponibles;
    }

    public void setClasesDisponibles(String claseAsignada) {
        // corregido: usar el parámetro de método
        this.clasesDisponibles = claseAsignada;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

