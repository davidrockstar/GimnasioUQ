package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;

public class ReportesMembresiasController {

    private final ModelFactory modelFactory;

    public ReportesMembresiasController() {
        this.modelFactory = ModelFactory.getInstance();
    }

    public int obtenerMembresiasTotales() {
        return modelFactory.contarMembresiasTotales();
    }

    public int obtenerMembresiasConValor() {
        return modelFactory.contarMembresiasConValor();
    }

    public int obtenerMembresiasSinValor() {
        return modelFactory.contarMembresiasSinValor();
    }

    public double obtenerIngresosTotales() {
        return modelFactory.calcularIngresosTotalesMembresias();
    }
}
