package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class RecepViewController {

    @FXML
    private TabPane tabPane;

    // Referencias inyectadas automáticamente por JavaFX
    @FXML
    private RecepUsuariosViewController recepUsuariosViewController;

    @FXML
    private RecepMembresiasViewController recepMembresiasViewController;

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
                        if (recepUsuariosViewController != null) {
                            recepUsuariosViewController.refrescarTabla();
                        }
                        break;
                    case "Asignar Membresías":
                        if (recepMembresiasViewController != null) {
                            recepMembresiasViewController.refrescarTabla();
                        }
                        break;
                }
            }
        });
    }

    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.RECEPCIONISTA) && pass.equals(DataUtil.RECEP_CONTRASENA);
    }
}