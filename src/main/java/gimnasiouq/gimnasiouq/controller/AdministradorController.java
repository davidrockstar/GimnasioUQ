package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdministradorController {

    @FXML
    private Button buttonLogout;

    @FXML
    void logout(ActionEvent event) {
        MyApplication.mainStage.setScene(MyApplication.sceneLogin);
    }

    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.ADMINISTRADOR) && pass.equals(DataUtil.ADMIN_CONTRASENA);
    }

}
