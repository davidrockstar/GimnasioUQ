package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.List;

public class RecepcionistaController {

    @FXML
    void cerrarSesion(ActionEvent event) {
        MyApplication.mainStage.setScene(MyApplication.sceneLogin);

    }

    @FXML
    void initialize() {

    }

    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.RECEPCIONISTA) && pass.equals(DataUtil.RECEP_CONTRASENA);
    }

    public List<Usuario> obtenerUsuarios() {
        return ModelFactory.getInstance().obtenerUsuarios();
    }

    public boolean agregarUsuario(Usuario usuario) {
        return ModelFactory.getInstance().agregarUsuario(usuario);
    }

}
