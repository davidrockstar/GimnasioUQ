package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;

public class GimnasioController {

    private final ModelFactory modelFactory;

    public GimnasioController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public String getNombreGimnasio() {
        return modelFactory.getNombreGimnasio();
    }

    public String getDireccionGimnasio() {
        return modelFactory.getDireccionGimnasio();
    }
}
