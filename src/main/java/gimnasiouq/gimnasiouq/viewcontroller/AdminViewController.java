package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class AdminViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    void cerrarSesion(ActionEvent event) {
        MyApplication.mainStage.setScene(MyApplication.sceneLogin);
    }

    @FXML
    void initialize() {}

    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.ADMINISTRADOR) && pass.equals(DataUtil.ADMIN_CONTRASENA);
    }
}