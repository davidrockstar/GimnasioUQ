package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;

public abstract class Membresia {
    private String tipo;
    private double costo;
    private LocalDate inicio;
    private LocalDate fin;
    private boolean activa;

    public Membresia(String tipo, double costo, LocalDate inicio, LocalDate fin, boolean activa) {
        this.tipo = tipo;
        this.costo = costo;
        this.inicio = inicio;
        this.fin = fin;
        this.activa = activa;
    }

    public Membresia() {

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activa && (fin.isAfter(hoy) || fin.isEqual(hoy));
    }

    public long diasRestantes() {
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(fin)) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(hoy, fin);
    }

    @Override
    public String toString() {
        return tipo + " - $" + costo + " (Vigente hasta: " + fin + ")";
    }
}
