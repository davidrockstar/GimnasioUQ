package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ReservaClase;

public class ReservaClaseController {

    public boolean agregarReserva(ReservaClase reserva) {
        if (reserva == null) return false;
        return ModelFactory.getInstance().agregarReservaAUsuario(reserva.getIdentificacion(), reserva);
    }

    public boolean eliminarReserva(String identificacionUsuario) {
        return ModelFactory.getInstance().eliminarReservasUsuario(identificacionUsuario);
    }

    public boolean actualizarReserva(ReservaClase reserva) {
        if (reserva == null) return false;
        return ModelFactory.getInstance().actualizarReservaUsuario(reserva.getIdentificacion(), reserva);
    }
}
