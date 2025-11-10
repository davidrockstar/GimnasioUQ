package gimnasiouq.gimnasiouq.model;

public class Administrador {
    private static Administrador instance;
    final String username;
    final String password;

    private Administrador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Administrador getInstance(String username, String password) {
        if (instance == null) {
            instance = new Administrador(username, password);
        }
        return instance;
    }
}
