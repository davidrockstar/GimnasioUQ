package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaPremium extends Membresia {
    public MembresiaPremium(double costo, LocalDate inicio, LocalDate fin) {
        super("Premium", costo, inicio, fin, true);
    }

    public boolean accesoGeneral() { 
        return true; 
    }
    
    public boolean clasesGrupales() { 
        return true; 
    }

    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio\n• Clases grupales ilimitadas";
    }
}
