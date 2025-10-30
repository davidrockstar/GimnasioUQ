package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;

import java.time.LocalDate;

public class ReservaClaseController {

    public boolean validarReserva(ReservaClase reserva) {
        if (reserva == null) return false;
        if (reserva.getClase() == null || reserva.getClase().isEmpty()) return false;
        if (reserva.getHorario() == null || reserva.getHorario().isEmpty()) return false;
        if (reserva.getFecha() == null) return false;
        if (reserva.getIdentificacion() == null || reserva.getIdentificacion().isEmpty()) return false;
        return true;
    }

    public boolean agregarReserva(ReservaClase reserva) {
        if (!validarReserva(reserva)) return false;
        Usuario u = ModelFactory.getInstance().buscarUsuario(reserva.getIdentificacion());
        if (u == null) return false;
        if (!u.tieneMembresiaActiva()) return false;

        LocalDate inicio = u.getFechaInicioMembresia();
        LocalDate fin = u.getFechaFinMembresia();
        LocalDate fecha = LocalDate.parse(reserva.getFecha());

        if (inicio == null || fin == null || fecha == null) return false;
        if (fecha.isBefore(inicio) || fecha.isAfter(fin)) return false;

        return ModelFactory.getInstance().agregarReservaAUsuario(u.getIdentificacion(), reserva);
    }

    public boolean eliminarReserva(String identificacionUsuario) {
        if (identificacionUsuario == null) return false;
        return ModelFactory.getInstance().eliminarReservasUsuario(identificacionUsuario);
    }

    public boolean actualizarReserva(ReservaClase reserva) {
        if (reserva == null) return false;
        if (!validarReserva(reserva)) return false;
        return ModelFactory.getInstance().actualizarReservaUsuario(reserva.getIdentificacion(), reserva);
    }
}
