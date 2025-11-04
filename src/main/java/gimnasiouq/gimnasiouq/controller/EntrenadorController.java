package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Entrenador;

public class EntrenadorController {

    private final ModelFactory modelFactory;

    public EntrenadorController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public boolean agregarEntrenador(Entrenador entrenador) {
        return modelFactory.agregarEntrenador(entrenador);
    }

    public boolean actualizarEntrenador(String identificacion, Entrenador entrenador) {
        return modelFactory.actualizarEntrenador(identificacion, entrenador);
    }

    public boolean eliminarEntrenador(String identificacion) {
        return modelFactory.eliminarEntrenador(identificacion);
    }

    public Entrenador buscarEntrenador(String identificacion) {
        return modelFactory.buscarEntrenador(identificacion);
    }
}
