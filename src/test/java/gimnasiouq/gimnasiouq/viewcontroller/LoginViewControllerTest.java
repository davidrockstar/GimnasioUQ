package gimnasiouq.gimnasiouq.viewcontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginViewControllerTest {

    private LoginViewController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginViewController();
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(controller, "El controller debe poder ser instanciado");
    }

    @Test
    void testControllerExists() {
        // Verificar que la clase existe y se puede crear
        LoginViewController loginViewController = new LoginViewController();
        assertNotNull(loginViewController);
    }

    @Test
    void testMethodsExist() {
        // Verificar que los métodos existen (sin ejecutarlos)
        assertDoesNotThrow(() -> {
            // Solo verifica que el método existe en la clase
            LoginViewController.class.getDeclaredMethod("initialize");
        });
    }

    @Test
    void testPrivateMethodsExist() throws NoSuchMethodException {
        // Verificar que los métodos privados existen
        assertNotNull(LoginViewController.class.getDeclaredMethod("mostrarError", String.class));
        assertNotNull(LoginViewController.class.getDeclaredMethod("limpiarAdvertenciaExito"));
        assertNotNull(LoginViewController.class.getDeclaredMethod("mostrarVentanaEmergente",
                String.class, String.class, String.class, javafx.scene.control.Alert.AlertType.class));
    }

    @Test
    void testFieldsExist() throws NoSuchFieldException {
        // Verificar que los campos FXML existen
        assertNotNull(LoginViewController.class.getDeclaredField("loginButton"));
        assertNotNull(LoginViewController.class.getDeclaredField("txtAdvertencia"));
        assertNotNull(LoginViewController.class.getDeclaredField("txtPasswordLogin"));
        assertNotNull(LoginViewController.class.getDeclaredField("comboBoxUser"));
    }

    // ⭐ NUEVOS TESTS para la lógica de login

    @Test
    void testProcesarLoginConUsuarioVacio() {
        String resultado = controller.procesarLogin("", "password123");
        assertEquals("ERROR_USUARIO_VACIO", resultado);
    }

    @Test
    void testProcesarLoginConUsuarioNulo() {
        String resultado = controller.procesarLogin(null, "password123");
        assertEquals("ERROR_USUARIO_VACIO", resultado);
    }

    @Test
    void testProcesarLoginConPasswordVacio() {
        String resultado = controller.procesarLogin("admin", "");
        assertEquals("ERROR_PASSWORD_VACIO", resultado);
    }

    @Test
    void testProcesarLoginConPasswordNulo() {
        String resultado = controller.procesarLogin("admin", null);
        assertEquals("ERROR_PASSWORD_VACIO", resultado);
    }

    @Test
    void testProcesarLoginConCredencialesInvalidas() {
        // Este test funcionará si las credenciales no existen en tu sistema
        String resultado = controller.procesarLogin("usuarioInexistente", "passwordIncorrecto");
        assertEquals("ERROR_CREDENCIALES", resultado);
    }

    @Test
    void testLoginMethodExists() throws NoSuchMethodException {
        // Verificar que el método login existe
        assertNotNull(LoginViewController.class.getDeclaredMethod("login", javafx.event.ActionEvent.class));
    }

    @Test
    void testProcesarLoginMethodExists() throws NoSuchMethodException {
        // Verificar que el nuevo método procesarLogin existe
        assertNotNull(LoginViewController.class.getDeclaredMethod("procesarLogin", String.class, String.class));
    }
}
