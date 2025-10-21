package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private Button loginButton;
    @FXML private Label txtAdvertencia;
    @FXML private PasswordField txtPasswordLogin;
    @FXML private TextField txtUserLogin;

    private void mostrarError(String mensaje) {
        txtAdvertencia.setText(mensaje);
        txtAdvertencia.getStyleClass().remove("advertencia-oculta");
        if (!txtAdvertencia.getStyleClass().contains("advertencia")) {
            txtAdvertencia.getStyleClass().add("advertencia");
        }
    }

    private void limpiarAdvertenciaExito() {
        txtAdvertencia.setText("");
        txtAdvertencia.getStyleClass().remove("advertencia");
        if (!txtAdvertencia.getStyleClass().contains("advertencia-oculta")) {
            txtAdvertencia.getStyleClass().add("advertencia-oculta");
        }
    }

    @FXML
    void login(ActionEvent event) {

        String user = txtUserLogin.getText();
        String pass = txtPasswordLogin.getText();

        if(AdministradorController.validarCredenciales(user, pass)){
            MyApplication.goToAdministrador();
            txtUserLogin.clear();
        } else if (RecepcionistaController.validarCredenciales(user, pass)) {
            MyApplication.goToRecepcionista();
        } else {
            mostrarVentanaEmergente("Error de autenticación", null, "Credenciales incorrectas", Alert.AlertType.ERROR);
        }


    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
