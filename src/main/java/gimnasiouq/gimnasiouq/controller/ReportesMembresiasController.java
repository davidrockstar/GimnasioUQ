package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

public class ReportesMembresiasController {

    private final ModelFactory modelFactory;

    public ReportesMembresiasController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    // Exponer properties para binding
    public IntegerProperty membresiasTotalesProperty() {
        return modelFactory.membresiasTotalesProperty();
    }

    public IntegerProperty membresiasConValorProperty() {
        return modelFactory.membresiasConValorProperty();
    }

    public IntegerProperty membresiasSinValorProperty() {
        return modelFactory.membresiasSinValorProperty();
    }

    public DoubleProperty ingresosTotalesProperty() {
        return modelFactory.ingresosTotalesProperty();
    }
}
