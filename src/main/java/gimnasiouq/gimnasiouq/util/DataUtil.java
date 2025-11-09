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

        gimnasioUQ.setAdministrador(new Administrador("Admin", "admin"));
        gimnasioUQ.setRecepcionista(new Recepcionista("Recep", "recep"));

        Usuario usuario1 = new Externo("Luis", "1094887139", "50", "3248054175", "Basica");

        Usuario usuario2 = new Estudiante("Juan", "1094887140", "28", "3110000000", "Premium");

        Usuario usuario3 = new TrabajadorUQ("Maria", "1094887141", "32", "3001111111", "VIP");

        gimnasioUQ.getListaUsuarios().add(usuario1);
        gimnasioUQ.getListaUsuarios().add(usuario2);
        gimnasioUQ.getListaUsuarios().add(usuario3);

        ReservaClase reserva1 = new ReservaClase("Yoga", "8:00 AM - 10:00 AM", "Entrenador A", LocalDate.now().plusDays(1).toString());
        reserva1.setIdentificacion("1094887140"); // Juan
        gimnasioUQ.getListaReservaClases().add(reserva1);

        ReservaClase reserva2 = new ReservaClase("Spinning", "10:00 AM - 12:00 PM", "Entrenador B", LocalDate.now().plusDays(2).toString());
        reserva2.setIdentificacion("1094887141"); // Maria
        gimnasioUQ.getListaReservaClases().add(reserva2);


        return gimnasioUQ;
    }
}
