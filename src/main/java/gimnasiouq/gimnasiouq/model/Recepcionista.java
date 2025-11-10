package gimnasiouq.gimnasiouq.model;

public class Recepcionista {

    private static Recepcionista instance;
    private final String username;
    private final String password;

    private Recepcionista(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Recepcionista getInstance(String username, String password) {
        if (instance == null) {
            instance = new Recepcionista(username, password);
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
