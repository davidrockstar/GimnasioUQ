package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.util.List;

public class RecepcionistaController {

    @FXML
    private TabPane tabPane;

    // Referencias inyectadas automáticamente por JavaFX
    @FXML
    private RecepcionistaUsuariosController recepcionistaUsuariosController;

    @FXML
    private RecepcionistaMembresiasController recepcionistaMembresiasController;

    @FXML
    private RecepcionistaControlAccesoController recepcionistaControlAccesoController;

    @FXML
    void cerrarSesion(ActionEvent event) {
        MyApplication.mainStage.setScene(MyApplication.sceneLogin);
    }

    // Listener para refrescar automáticamente al cambiar de pestaña
    @FXML
    void initialize() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                String tabName = newTab.getText();

                switch (tabName) {
                    case "Registrar Usuarios":
                        if (recepcionistaUsuariosController != null) {
                            recepcionistaUsuariosController.refrescarTabla();
                        }
                        break;
                    case "Asignar Membresías":
                        if (recepcionistaMembresiasController != null) {
                            recepcionistaMembresiasController.refrescarTabla();
                        }
                }
            }
        });
    }

    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.RECEPCIONISTA) && pass.equals(DataUtil.RECEP_CONTRASENA);
    }

    public List<Usuario> obtenerUsuarios() {
        return ModelFactory.getInstance().obtenerUsuarios();
    }

    /**
     * Registra el controlador de usuarios
     */
    public void setUsuariosController(RecepcionistaUsuariosController controller) {
        this.recepcionistaUsuariosController = controller;
    }

    /**
     * Registra el controlador de membresías
     */
    public void setMembresiasController(RecepcionistaMembresiasController controller) {
        this.recepcionistaMembresiasController = controller;
    }

    /**
     * Notifica a todos los controladores hijos que deben refrescar sus datos
     */
    public void notificarActualizacion() {
        if (recepcionistaUsuariosController != null) {
            recepcionistaUsuariosController.refrescarTabla();
        }
        if (recepcionistaMembresiasController != null) {
            recepcionistaMembresiasController.refrescarTabla();
        }
    }
}