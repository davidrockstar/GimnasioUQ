package gimnasiouq.gimnasiouq.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminReportesUsuariosViewController {

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Label lblUsuariosActivos;

    @FXML
    private Label lblUsuariosMensualidadActiva;

    @FXML
    private Label lblUsuariosSinMensualidadActiva;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcIdentificacion;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcTipoMembresia;

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

}
