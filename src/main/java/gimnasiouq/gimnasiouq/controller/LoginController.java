package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private Button loginButton;
    @FXML private Label txtAdvertencia;
    @FXML private PasswordField txtPasswordLogin;
    @FXML private ComboBox <String> comboBoxUser;

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

        String user = comboBoxUser.getValue();
        String pass = txtPasswordLogin.getText();
        
        // Delegar a un método que SÍ se puede testear
        String resultado = procesarLogin(user, pass);
        
        if ("ADMINISTRADOR".equals(resultado)) {
            MyApplication.goToAdministrador();
        } else if ("RECEPCIONISTA".equals(resultado)) {
            MyApplication.goToRecepcionista();
        } else {
            mostrarVentanaEmergente("Error de autenticación", null, "Credenciales incorrectas", Alert.AlertType.ERROR);
        }
    }

    // ⭐ NUEVO: Método testeable que contiene la lógica
    protected String procesarLogin(String user, String pass) {
        // Validación de campos vacíos
        if (user == null || user.trim().isEmpty()) {
            return "ERROR_USUARIO_VACIO";
        }
        
        if (pass == null || pass.trim().isEmpty()) {
            return "ERROR_PASSWORD_VACIO";
        }
        
        // Validación de credenciales
        if (AdministradorController.validarCredenciales(user, pass)) {
            return "ADMINISTRADOR";
        } else if (RecepcionistaController.validarCredenciales(user, pass)) {
            return "RECEPCIONISTA";
        } else {
            return "ERROR_CREDENCIALES";
        }
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        comboBoxUser.getItems().addAll("Administrador", "Recepcionista");
    }
}
