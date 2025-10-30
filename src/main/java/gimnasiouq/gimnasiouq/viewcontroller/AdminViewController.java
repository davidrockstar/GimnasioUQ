package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.MyApplication;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class AdminViewController {


    @FXML
    private AdminUsuariosViewController adminUsuariosViewController;

    @FXML
    private ComboBox<String> comboSecciones;

    @FXML
    private AnchorPane contenedorPrincipal;

    @FXML
    void cerrarSesion(ActionEvent event) {
        MyApplication.mainStage.setScene(MyApplication.sceneLogin);
    }

    @FXML
    public void initialize() {
        comboSecciones.getItems().addAll(
                "Registrar Usuarios",
                "Asignar Membresías",
                "Reservar Clases",
                "Control de Acceso",
                "Reportes",
                "Gestión de Entrenadores",
                "Test"
        );

        comboSecciones.setOnAction(e -> cambiarVista(comboSecciones.getValue()));
    }

    // --- Carga dinámica del contenido según la opción seleccionada ---
    private void cambiarVista(String opcion) {
        // Asigna correctamente las rutas con / inicial y carpeta real
        String fxmlPath = switch (opcion) {
            case "Registrar Usuarios" -> "/gimnasiouq/gimnasiouq/administradorUsuarios.fxml";
            case "Asignar Membresías" -> "/gimnasiouq/gimnasiouq/administradorMembresias.fxml";
            case "Reservar Clases" -> "/gimnasiouq/gimnasiouq/administradorReservaClases.fxml";
            case "Control de Acceso" -> "/gimnasiouq/gimnasiouq/administradorControlAcceso.fxml";
            case "Reportes" -> "/gimnasiouq/gimnasiouq/administradorReportes.fxml";
            case "Gestión de Entrenadores" -> "/gimnasiouq/gimnasiouq/administradorGestionEntrenadores.fxml";
            case "Test" -> "/gimnasiouq/gimnasiouq/administradorTest.fxml";
            default -> null;
        };

        if (fxmlPath == null) {
            System.err.println("❌ Ruta FXML no encontrada para: " + opcion);
            return;
        }

        try {
            var url = getClass().getResource(fxmlPath);
            if (url == null) {
                System.err.println("⚠️ No se encontró el archivo: " + fxmlPath);
                return;
            }

            Parent vista = FXMLLoader.load(url);
            contenedorPrincipal.getChildren().setAll(vista);

        } catch (IOException ex) {
            System.err.println("⚠️ Error al cargar vista: " + fxmlPath);
            ex.printStackTrace();
        }
    }


    // --- Validación de credenciales (tu método original) ---
    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.ADMINISTRADOR) && pass.equals(DataUtil.ADMIN_CONTRASENA);
    }

    public void notificarActualizacion() {
        if (adminUsuariosViewController != null) {
            adminUsuariosViewController.refrescarTabla();
        }
    }
}
