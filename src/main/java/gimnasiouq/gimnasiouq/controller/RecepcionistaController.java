package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RecepcionistaController {

    @FXML
    private Button buttonLogout;

    @FXML
    void logout(ActionEvent event) {
        MyApplication.mainStage.setScene(MyApplication.sceneLogin);
    }

}
