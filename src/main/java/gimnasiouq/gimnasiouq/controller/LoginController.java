package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.model.Administador;
import gimnasiouq.gimnasiouq.model.Recepcionista;
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
        var user = txtUserLogin.getText();
        var pass = txtPasswordLogin.getText();

        var recepcionista = new Recepcionista(user, pass);
        if (recepcionista.validarCredenciales()) {
            txtUserLogin.clear();
            txtPasswordLogin.clear();
            limpiarAdvertenciaExito();
            MyApplication.goToRecepcionista();
            return;
        }

        var administrador = new Administador(user, pass);
        if (administrador.validarCredenciales()) {
            txtUserLogin.clear();
            txtPasswordLogin.clear();
            limpiarAdvertenciaExito();
            MyApplication.goToAdministrador();
            return;
        }

        mostrarError("Usuario o contraseña incorrectas");
        mostrarVentanaEmergente("Información inválida", "Información inválida",
                "Credenciales incorrectas", Alert.AlertType.CONFIRMATION);
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
