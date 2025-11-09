package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.controller.GimnasioController;
import gimnasiouq.gimnasiouq.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController {

    private final LoginController loginController = new LoginController();
    private final GimnasioController gimnasioController = new GimnasioController();

    @FXML
    private Button btnIngresar;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ComboBox<String> comboBoxUser;

    @FXML
    private Label lblGimnasioNombre;

    @FXML
    private Label lblGimnasioDireccion;

    @FXML
    void onIngresar(ActionEvent event) {
        String usuario = comboBoxUser.getValue();
        String contrasena = txtContrasena.getText();
        String resultado = loginController.procesarLogin(usuario, contrasena);

        switch (resultado) {
            case "ADMINISTRADOR":
                MyApplication.cambiarEscena("Administrador");
                break;
            case "RECEPCIONISTA":
                MyApplication.cambiarEscena("Recepcionista");
                break;
            case "ERROR_USUARIO_VACIO":
                mostrarAlerta("Error de Validación", "El campo de usuario no puede estar vacío.", Alert.AlertType.WARNING);
                break;
            case "ERROR_PASSWORD_VACIO":
                mostrarAlerta("Error de Validación", "El campo de contraseña no puede estar vacío.", Alert.AlertType.WARNING);
                break;
            case "ERROR_CREDENCIALES":
                mostrarAlerta("Datos Incorrectos", "El usuario o la contraseña no son válidos.", Alert.AlertType.ERROR);
                break;
        }
    }

    @FXML
    void initialize() {
        comboBoxUser.getItems().addAll("Administrador", "Recepcionista");
        if (lblGimnasioNombre != null) {
            lblGimnasioNombre.setText(gimnasioController.getNombreGimnasio());
        }
        if (lblGimnasioDireccion != null) {
            lblGimnasioDireccion.setText(gimnasioController.getDireccionGimnasio());
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
