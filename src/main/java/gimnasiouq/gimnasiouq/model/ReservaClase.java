package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservaClase {
    private String clase;
    private String horario;
    private String entrenador;
    private String fecha;

    public ReservaClase(String clase, String horario, String entrenador, String fecha) {
        this.clase = clase;
        this.horario = horario;
        this.entrenador = entrenador;
        this.fecha = fecha;
    }

    // Getters y setters
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

    /**
     * Valida si la fecha de reserva está dentro del rango permitido según el tipo de membresía
     * @param fechaInicio La fecha de inicio de la membresía
     * @param tipoMembresia El tipo de membresía (Mensual, Trimestral, Anual)
     * @return true si la fecha es válida, false en caso contrario
     */
    public boolean validarFechaReserva(LocalDate fechaInicio, String tipoMembresia) {
        try {
            LocalDate fechaReserva = LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate fechaFin;

            switch (tipoMembresia.toLowerCase()) {
                case "mensual":
                    fechaFin = fechaInicio.plusMonths(1);
                    break;
                case "trimestral":
                    fechaFin = fechaInicio.plusMonths(3);
                    break;
                case "anual":
                    fechaFin = fechaInicio.plusYears(1);
                    break;
                default:
                    return false;
            }

            return !fechaReserva.isBefore(fechaInicio) && !fechaReserva.isAfter(fechaFin);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
