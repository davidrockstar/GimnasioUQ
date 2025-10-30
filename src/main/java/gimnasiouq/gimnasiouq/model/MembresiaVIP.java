package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaVIP extends Membresia {

    public MembresiaVIP(double costo, LocalDate inicio, LocalDate fin) {
        super("VIP", costo, inicio, fin, true);
    }

    public boolean accesoGeneral() { 
        return true; 
    }
    
    public boolean clasesGrupales() { 
        return true; 
    }
    
    public boolean entrenadorPersonal() { 
        return true; 
    }

    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio\n• Clases grupales ilimitadas\n• Entrenador personal";
    }
}
