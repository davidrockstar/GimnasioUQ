package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroAcceso {
    LocalDate fecha;
    LocalTime hora;
    String usuario;
    String identificacion;
    String tipoMembresia;
    String estado;

    public RegistroAcceso(LocalDate fecha, LocalTime hora, String usuario,
                          String identificacion, String tipoMembresia, String estado) {
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.identificacion = identificacion;
        this.tipoMembresia = tipoMembresia;
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
