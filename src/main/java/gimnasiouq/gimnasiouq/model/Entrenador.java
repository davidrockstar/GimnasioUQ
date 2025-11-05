package gimnasiouq.gimnasiouq.model;

import java.util.Objects;

public class Entrenador {
    private String nombre;
    private String identificacion;
    private String especialidad;
    private String clasesDisponibles;
    private String celular;
    private String correo;

    public Entrenador(String nombre, String identificacion, String especialidad, String clasesDisponibles, String celular, String correo) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.especialidad = especialidad;
        this.clasesDisponibles = clasesDisponibles;
        this.celular = celular;
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
        this.clasesDisponibles = claseAsignada;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String telefono) {
        this.celular = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

