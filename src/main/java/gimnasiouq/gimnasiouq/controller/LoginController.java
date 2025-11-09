package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.util.DataUtil;

public class LoginController {

    public LoginController() {
        // Constructor vacío
    }

    public String procesarLogin(String user, String pass) {
        if (user == null || user.trim().isEmpty()) {
            return "ERROR_USUARIO_VACIO";
        }

        if (pass == null || pass.trim().isEmpty()) {
            return "ERROR_PASSWORD_VACIO";
        }

        if (DataUtil.ADMINISTRADOR.equals(user) && DataUtil.ADMIN_CONTRASENA.equals(pass)) {
            return "ADMINISTRADOR";
        }

        if (DataUtil.RECEPCIONISTA.equals(user) && DataUtil.RECEP_CONTRASENA.equals(pass)) {
            return "RECEPCIONISTA";
        }
        return "ERROR_CREDENCIALES";
    }
}
