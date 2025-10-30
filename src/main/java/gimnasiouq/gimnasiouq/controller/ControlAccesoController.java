package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ControlAcceso;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;

public class ControlAccesoController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public boolean validarIngreso(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) return false;
        Usuario u = modelFactory.buscarUsuario(identificacion.trim());
        return u != null && u.tieneMembresiaActiva();
    }

    public boolean registrarIngreso(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) return false;
        Usuario u = modelFactory.buscarUsuario(identificacion.trim());
        if (u == null) return false;
        if (!u.tieneMembresiaActiva()) return false;

        ControlAcceso registro = new ControlAcceso(
                LocalDate.now(),
                LocalTime.now(),
                u.getNombre(),
                u.getIdentificacion(),
                u.getTipoMembresia(),
                u.getEstadoMembresia()
        );

        return modelFactory.agregarRegistroAcceso(registro);
    }

    public boolean eliminarRegistro(ControlAcceso registro) {
        if (registro == null) return false;
        return modelFactory.eliminarRegistroAcceso(registro);
    }

    public ObservableList<ControlAcceso> obtenerRegistrosObservable() {
        return modelFactory.obtenerRegistrosAccesoObservable();
    }

    public Usuario buscarUsuario(String identificacion) {
        if (identificacion == null) return null;
        return modelFactory.buscarUsuario(identificacion);
    }
}
