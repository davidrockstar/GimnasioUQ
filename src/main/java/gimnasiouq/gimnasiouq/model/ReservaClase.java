package gimnasiouq.gimnasiouq.model;

import java.util.Objects;

public class ReservaClase {

    private String clase;
    private String horario;
    private String entrenador;
    private String fecha;

    private String identificacionUsuario;

    public ReservaClase(String clase, String horario, String entrenador, String fecha) {
        this.clase = clase;
        this.horario = horario;
        this.entrenador = entrenador;
        this.fecha = fecha;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdentificacion() {
        return identificacionUsuario;
    }

    public void setIdentificacion(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaClase)) return false;
        ReservaClase that = (ReservaClase) o;
        return Objects.equals(clase, that.clase) &&
               Objects.equals(horario, that.horario) &&
               Objects.equals(fecha, that.fecha) &&
               Objects.equals(identificacionUsuario, that.identificacionUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clase, horario, fecha, identificacionUsuario);
    }

    @Override
    public String toString() {
        return clase + " - " + horario + " (" + fecha + ")";
    }
}
