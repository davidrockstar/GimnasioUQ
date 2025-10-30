package gimnasiouq.gimnasiouq.util;

import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.Usuario;

public class DataUtil {

    public static final String ADMINISTRADOR = "Administrador";
    public static final String ADMIN_CONTRASENA = "hola000";
    public static final String RECEPCIONISTA = "Recepcionista";
    public static final String RECEP_CONTRASENA = "hola000";

    public static GimnasioUQ inicializarDatos() {
        GimnasioUQ gimnasioUQ = new GimnasioUQ();
        Usuario usuario1 = new Usuario("Luis", "1094887139", "50", "3248054175", "Basica");
        Usuario usuario2 = new Usuario("Juan", "1094887140", "28", "3110000000", "Premium");
        Usuario usuario3 = new Usuario("Maria", "1094887141", "32", "3001111111", "VIP");

        gimnasioUQ.getListaUsuarios().add(usuario1);
        gimnasioUQ.getListaUsuarios().add(usuario2);
        gimnasioUQ.getListaUsuarios().add(usuario3);

        return gimnasioUQ;
    }
}
