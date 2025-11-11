package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.util.ReservaValidationResult;

import java.util.List;

public class ReservaClaseController {

    public ReservaValidationResult agregarReserva(String identificacionUsuario, ReservaClase reserva) {
        if (reserva == null || identificacionUsuario == null || identificacionUsuario.isEmpty()) return ReservaValidationResult.DATOS_RESERVA_INVALIDOS;
        return ModelFactory.getInstance().agregarReservaAUsuario(identificacionUsuario, reserva);
    }

    public boolean eliminarReserva(String identificacionUsuario) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;
        return ModelFactory.getInstance().eliminarReservasUsuario(identificacionUsuario);
    }

    public ReservaValidationResult actualizarReserva(String identificacionUsuario, ReservaClase reserva) {
        if (reserva == null || identificacionUsuario == null || identificacionUsuario.isEmpty()) return ReservaValidationResult.DATOS_RESERVA_INVALIDOS;
        return ModelFactory.getInstance().actualizarReservaUsuario(identificacionUsuario, reserva);
    }

    public ReservaClase obtenerReservaPorUsuario(String identificacionUsuario) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) {
            return null;
        }
        List<ReservaClase> reservas = ModelFactory.getInstance().obtenerReservasObservable();
        for (ReservaClase reserva : reservas) {
            if (reserva.getIdentificacion().equals(identificacionUsuario)) {
                return reserva;
            }
        }
        return null;
    }
}
