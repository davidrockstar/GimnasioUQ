package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ControlAcceso;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.collections.ObservableList;

public class ControlAccesoController {

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public boolean validarIngreso(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) return false;
        return modelFactory.validarIngresoUsuario(identificacion.trim());
    }

    public boolean registrarIngreso(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) return false;
        return modelFactory.registrarIngresoUsuario(identificacion.trim());
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

    public boolean puedeAccederSpa(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) return false;
        return modelFactory.puedeAccederSpa(identificacion.trim());
    }
}
