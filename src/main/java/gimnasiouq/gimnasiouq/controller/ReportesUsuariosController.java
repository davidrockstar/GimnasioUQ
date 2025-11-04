package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import javafx.beans.property.IntegerProperty;

public class ReportesUsuariosController {

    private final ModelFactory modelFactory;

    public ReportesUsuariosController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public IntegerProperty usuariosMembresiaActivasProperty() {
        return modelFactory.usuariosMembresiaActivasProperty();
    }

    public IntegerProperty usuariosMembresiaInactivasProperty() {
        return modelFactory.usuariosMembresiaInativasProperty();
    }

    public IntegerProperty usuariosTotalesProperty() {
        return modelFactory.usuariosTotalesProperty();
    }
}
