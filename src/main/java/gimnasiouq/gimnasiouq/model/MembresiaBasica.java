package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaBasica extends Membresia {

    boolean accesoGeneral;

    public MembresiaBasica(String tipo, double costo, LocalDate fechaInicio, LocalDate fechaFin, boolean estado, boolean accesoGeneral) {
        super(tipo, costo, fechaInicio, fechaFin, estado);
        this.accesoGeneral = accesoGeneral;
    }

    public MembresiaBasica(boolean accesoGeneral) {
        this.accesoGeneral = accesoGeneral;
    }

    // ⭐ CONSTRUCTOR CORREGIDO - Llama al constructor padre correctamente
    public MembresiaBasica(double costo, LocalDate inicio, LocalDate fin) {
        super("Basica", costo, inicio, fin, true); // ← Agregado super()
        this.accesoGeneral = true;
    }

    public boolean isAccesoGeneral() {
        return accesoGeneral;
    }

    public void setAccesoGeneral(boolean accesoGeneral) {
        this.accesoGeneral = accesoGeneral;
    }

    // ⭐ NUEVO: Método para obtener beneficios como texto
    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio";
    }
}
