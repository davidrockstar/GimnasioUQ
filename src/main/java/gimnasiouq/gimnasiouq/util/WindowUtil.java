package gimnasiouq.gimnasiouq.util;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.viewcontroller.MembresiaDetallesViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class WindowUtil {

    public static void mostrarVentanaDetalles(Usuario usuario, Window owner) {
        if (usuario == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("membresiaDetalles.fxml"));
            Parent parent = loader.load();

            // Obtener el controlador de la ventana de detalles
            MembresiaDetallesViewController controller = loader.getController();
            // Pasar los datos del usuario al controlador
            controller.initData(usuario);

            Stage stage = new Stage();
            stage.setTitle("Comprobante de Pago - " + usuario.getNombre());
            stage.setScene(new Scene(parent));

            // Configurar la ventana como modal
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.setResizable(false);

            // Mostrar la ventana y esperar a que se cierre
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
