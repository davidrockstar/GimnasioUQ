package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;
import java.util.Objects;

public class Recepcionista {

    private final String username;
    private final String password;

    public Recepcionista(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean validarCredenciales() {
        return username.equals("Recepcionista") && password.equals("mortadela10");
    }
}
