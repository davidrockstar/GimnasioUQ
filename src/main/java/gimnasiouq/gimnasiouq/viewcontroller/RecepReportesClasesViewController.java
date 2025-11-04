package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReportesClasesController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RecepReportesClasesViewController {

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Label lblClaseMasReservada;

    @FXML
    private Label lblTotalClasesReservadas;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> tcClase;

    @FXML
    private TableColumn<Usuario, String> tcHorario;

    @FXML
    private TableColumn<Usuario, String> tcFecha;

    @FXML
    private TableColumn<Usuario, String> tcEntrenador;

    @FXML
    private TableColumn<Usuario, String> tcIdUsuario;

    @FXML
    void onExportarPdf(ActionEvent event) {}

    ObservableList<Usuario> listaUsuarios;

    private final ReportesClasesController reportesClasesController = new ReportesClasesController();

    @FXML
    void initialize() {
        initView();
        cargarIndicadores();
    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        listaUsuarios.addListener((ListChangeListener.Change<? extends Usuario> change) -> {
            cargarIndicadores();
            tableView.refresh();
        });
        tableView.setItems(listaUsuarios);

    }

    private void initDataBinding() {
        if (tcClase != null) {
            tcClase.setCellValueFactory(cellData -> {
                if (cellData.getValue().getReservas().isEmpty()) {
                    return new SimpleStringProperty("Sin reserva");
                }
                return new SimpleStringProperty(cellData.getValue().getReservas().get(0).getClase());
            });
        }
        if (tcHorario != null) {
            tcHorario.setCellValueFactory(cellData -> {
                if (cellData.getValue().getReservas().isEmpty()) {
                    return new SimpleStringProperty("Sin reserva");
                }
                return new SimpleStringProperty(cellData.getValue().getReservas().get(0).getHorario());
            });
        }
        if (tcFecha != null) {
            tcFecha.setCellValueFactory(cellData -> {
                if (cellData.getValue().getReservas().isEmpty()) {
                    return new SimpleStringProperty("Sin reserva");
                }
                return new SimpleStringProperty(cellData.getValue().getReservas().get(0).getFecha());
            });
        }
        if (tcEntrenador != null) {
            tcEntrenador.setCellValueFactory(cellData -> {
                if (cellData.getValue().getReservas().isEmpty()) {
                    return new SimpleStringProperty("Sin reserva");
                }
                return new SimpleStringProperty(cellData.getValue().getReservas().get(0).getEntrenador());
            });
        }
        if (tcIdUsuario != null) {
            tcIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
    }

    private void cargarIndicadores() {
        if (lblClaseMasReservada != null)
            lblClaseMasReservada.textProperty().bind(reportesClasesController.claseMasReservadaProperty());
        if (lblTotalClasesReservadas != null)
            lblTotalClasesReservadas.textProperty().bind(reportesClasesController.totalClasesReservadasProperty().asString());
    }

}
