package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaPremium extends Membresia {
    public MembresiaPremium(double costo, LocalDate inicio, LocalDate fin) {
        super("Premium", costo, inicio, fin, true); // ⭐ Llama al constructor padre
    }

    public boolean accesoGeneral() { 
        return true; 
    }
    
    public boolean clasesGrupales() { 
        return true; 
    }

    // ⭐ NUEVO: Método para obtener beneficios como texto
    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio\n• Clases grupales ilimitadas";
    }
}
