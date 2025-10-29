package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.util.List;

public class RecepcionistaViewController {

    @FXML
    private TabPane tabPane;

    // Referencias inyectadas automáticamente por JavaFX
    @FXML
    private RecepcionistaUsuariosViewController recepcionistaUsuariosViewController;

    @FXML
    private RecepcionistaMembresiasViewController recepcionistaMembresiasViewController;

    @FXML
    private RecepcionistaControlAccesoViewController recepcionistaControlAccesoViewController;

    @FXML
    private RecepcionistaReservaClasesViewController reservaClasesController;

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
                        if (recepcionistaUsuariosViewController != null) {
                            recepcionistaUsuariosViewController.refrescarTabla();
                        }
                        break;
                    case "Asignar Membresías":
                        if (recepcionistaMembresiasViewController != null) {
                            recepcionistaMembresiasViewController.refrescarTabla();
                        }
                        break;
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
    public void setUsuariosController(RecepcionistaUsuariosViewController controller) {
        this.recepcionistaUsuariosViewController = controller;
    }

    /**
     * Registra el controlador de membresías
     */
    public void setMembresiasController(RecepcionistaMembresiasViewController controller) {
        this.recepcionistaMembresiasViewController = controller;
    }

    /**
     * Notifica a todos los controladores hijos que deben refrescar sus datos
     */
    public void notificarActualizacion() {
        if (recepcionistaUsuariosViewController != null) {
            recepcionistaUsuariosViewController.refrescarTabla();
        }
        if (recepcionistaMembresiasViewController != null) {
            recepcionistaMembresiasViewController.refrescarTabla();
        }
    }
}