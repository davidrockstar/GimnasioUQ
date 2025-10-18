package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaBasica extends Membresia {

    public MembresiaBasica(double costo, LocalDate inicio, LocalDate fin) {
        super("Basica", costo, inicio, fin, true);
    }

}
