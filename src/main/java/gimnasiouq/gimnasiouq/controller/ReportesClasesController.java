package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ReportesClasesController {
    private final ModelFactory modelFactory;

    public ReportesClasesController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public StringProperty claseMasReservadaProperty() {
        return modelFactory.claseMasReservadaProperty();
    }

    public IntegerProperty totalClasesReservadasProperty() {
        return modelFactory.totalClasesReservadasProperty();
    }
}
