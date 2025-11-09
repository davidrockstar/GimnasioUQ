package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.model.Membresia;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;

public class MembresiaController {

    public boolean eliminarMembresiaUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdentificacion() == null) return false;
        return ModelFactory.getInstance().eliminarMembresiaUsuario(usuario.getIdentificacion());
    }

    public boolean asignarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || membresia == null) return false;
        return ModelFactory.getInstance().asignarMembresiaUsuario(identificacionUsuario, membresia);
    }

    public boolean actualizarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || membresia == null) return false;
        return ModelFactory.getInstance().actualizarMembresiaUsuario(identificacionUsuario, membresia);
    }

    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoMembresia, Usuario usuario) {
        return ModelFactory.getInstance().calcularMembresiaPorPlan(tipoPlan, tipoMembresia, usuario);
    }
}
