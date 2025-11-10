package gimnasiouq.gimnasiouq.util;

import gimnasiouq.gimnasiouq.model.*;

import java.time.LocalDate;

public class DataUtil {

    public static final String ADMINISTRADOR = "Administrador";
    public static final String ADMIN_CONTRASENA = "hola000";
    public static final String RECEPCIONISTA = "Recepcionista";
    public static final String RECEP_CONTRASENA = "hola000";

    public static GimnasioUQ inicializarDatos() {
        GimnasioUQ gimnasioUQ = new GimnasioUQ(
                "© 2025 Gimnasio de la Universidad del Quindío",
                "Carrera 15 #12N"
        );

        gimnasioUQ.setAdministrador(Administrador.getInstance("Admin", "admin"));
        gimnasioUQ.setRecepcionista(Recepcionista.getInstance("Recep", "recep"));

        Usuario usuario1 = new Externo("Luis", "1094887139", "50", "3248054175", "Basica");
        gimnasioUQ.agregarUsuario(usuario1);

        Usuario usuario2 = new Estudiante("Juan", "1094887140", "28", "3110000000", "Premium");
        gimnasioUQ.agregarUsuario(usuario2);

        Usuario usuario3 = new TrabajadorUQ("Maria", "1094887141", "32", "3001111111", "VIP");
        gimnasioUQ.agregarUsuario(usuario3);

        return gimnasioUQ;
    }
}
