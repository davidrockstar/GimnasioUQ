package gimnasiouq.gimnasiouq.model;

public class Administador {
    final String username;
    final String password;

    public Administador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validarCredenciales() {
        return username.equals("Administrador") && password.equals("jovenesayer777");
    }
}
