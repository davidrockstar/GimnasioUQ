package gimnasiouq.gimnasiouq.model;

public class Estudiante extends Usuario {
    public Estudiante(String nombre, String identificacion, String edad, String telefono) {
        super(nombre, identificacion, String.valueOf(edad), telefono, "Estudiante");
    }
}
