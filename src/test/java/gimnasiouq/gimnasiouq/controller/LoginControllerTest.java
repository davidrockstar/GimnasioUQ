package gimnasiouq.gimnasiouq.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(controller, "El controller debe poder ser instanciado");
    }

    @Test
    void testControllerExists() {
        // Verificar que la clase existe y se puede crear
        LoginController loginController = new LoginController();
        assertNotNull(loginController);
    }

    @Test
    void testMethodsExist() {
        // Verificar que los métodos existen (sin ejecutarlos)
        assertDoesNotThrow(() -> {
            // Solo verifica que el método existe en la clase
            LoginController.class.getDeclaredMethod("initialize");
        });
    }

    @Test
    void testPrivateMethodsExist() throws NoSuchMethodException {
        // Verificar que los métodos privados existen
        assertNotNull(LoginController.class.getDeclaredMethod("mostrarError", String.class));
        assertNotNull(LoginController.class.getDeclaredMethod("limpiarAdvertenciaExito"));
        assertNotNull(LoginController.class.getDeclaredMethod("mostrarVentanaEmergente", 
                String.class, String.class, String.class, javafx.scene.control.Alert.AlertType.class));
    }

    @Test
    void testFieldsExist() throws NoSuchFieldException {
        // Verificar que los campos FXML existen
        assertNotNull(LoginController.class.getDeclaredField("loginButton"));
        assertNotNull(LoginController.class.getDeclaredField("txtAdvertencia"));
        assertNotNull(LoginController.class.getDeclaredField("txtPasswordLogin"));
        assertNotNull(LoginController.class.getDeclaredField("comboBoxUser"));
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
        assertNotNull(LoginController.class.getDeclaredMethod("login", javafx.event.ActionEvent.class));
    }

    @Test
    void testProcesarLoginMethodExists() throws NoSuchMethodException {
        // Verificar que el nuevo método procesarLogin existe
        assertNotNull(LoginController.class.getDeclaredMethod("procesarLogin", String.class, String.class));
    }
}
