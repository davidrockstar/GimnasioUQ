package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ControlAccesoController;
import gimnasiouq.gimnasiouq.model.ControlAcceso;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RecepControlAccesoViewController {

    @FXML private Button btnBuscarUsuario;
    @FXML private Button btnEliminar;
    @FXML private Button btnValidarIngreso;
    @FXML private Label lblFechaVencimientoEncontrada;
    @FXML private Label lblMembresiaActivaNoActiva;
    @FXML private Label lblMembresiaEncontrada;
    @FXML private Label lblNombreEncontrado;
    @FXML private TableView<ControlAcceso> tableUsuario;
    @FXML private TableColumn<ControlAcceso, String> tcFecha;
    @FXML private TableColumn<ControlAcceso, String> tcHora;
    @FXML private TableColumn<ControlAcceso, String> tcIdentificacion;
    @FXML private TableColumn<ControlAcceso, String> tcNombre;
    @FXML private TableColumn<ControlAcceso, String> tcTipoMembresia;
    @FXML private TableColumn<ControlAcceso, String> tcEstado;
    @FXML private TextField txtfieldIdentificacion;

    private ControlAccesoController controlAccesoController = new ControlAccesoController();
    private ObservableList<ControlAcceso> listaRegistros;
    private Usuario usuarioActual;

    @FXML
    void initialize() {
        // Deshabilitar botón de validar ingreso al inicio
        btnValidarIngreso.setDisable(true);

        // Configurar tabla
        initDataBinding();
        listaRegistros = controlAccesoController.obtenerRegistrosObservable();
        tableUsuario.setItems(listaRegistros);

        // Limpiar labels
        limpiarInformacionUsuario();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsuario()));

        tcIdentificacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdentificacion()));

        tcTipoMembresia.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoMembresia()));

        tcFecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFecha();
            return new SimpleStringProperty(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        tcHora.setCellValueFactory(cellData -> {
            LocalTime hora = cellData.getValue().getHora();
            return new SimpleStringProperty(hora.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        });

        tcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado()));

        // Estilo para columna Estado
        tcEstado.setCellFactory(column -> new TableCell<ControlAcceso, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("ACTIVA".equals(item)) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    @FXML
    void onBuscarUsuario(ActionEvent event) {
        String identificacion = txtfieldIdentificacion.getText();

        if (identificacion == null || identificacion.trim().isEmpty()) {
            mostrarAlerta("Error", "Ingrese una identificación", Alert.AlertType.WARNING);
            return;
        }

        Usuario usuario = controlAccesoController.buscarUsuario(identificacion.trim());

        if (usuario != null) {
            usuarioActual = usuario;
            actualizarInformacionUsuario(usuario);

            // Habilitar botón solo si membresía está activa
            btnValidarIngreso.setDisable(!usuario.tieneMembresiaActiva());
        } else {
            limpiarInformacionUsuario();
            mostrarAlerta("Usuario no encontrado",
                    "No existe un usuario con la identificación: " + identificacion,
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onValidarIngreso(ActionEvent event) {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Primero debe buscar un usuario", Alert.AlertType.ERROR);
            return;
        }

        // Delegar validación y registro al controller
        if (!controlAccesoController.validarIngreso(usuarioActual.getIdentificacion())) {
            mostrarAlerta("Membresía Inactiva",
                    "El usuario no puede ingresar. Membresía NO ACTIVA.",
                    Alert.AlertType.ERROR);
            return;
        }

        boolean ok = controlAccesoController.registrarIngreso(usuarioActual.getIdentificacion());
        if (ok) {
            mostrarAlerta("Ingreso Validado",
                    "Acceso registrado exitosamente para " + usuarioActual.getNombre(),
                    Alert.AlertType.INFORMATION);
            limpiarFormulario();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el ingreso", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        ControlAcceso registroSeleccionado = tableUsuario.getSelectionModel().getSelectedItem();

        if (registroSeleccionado != null) {
            boolean ok = controlAccesoController.eliminarRegistro(registroSeleccionado);
            if (ok) {
                mostrarAlerta("Registro eliminado", "El registro ha sido eliminado", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el registro", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un registro para eliminar", Alert.AlertType.WARNING);
        }
    }

    private void actualizarInformacionUsuario(Usuario usuario) {
        lblNombreEncontrado.setText(usuario.getNombre());
        lblMembresiaEncontrada.setText(usuario.getTipoMembresia());
        lblFechaVencimientoEncontrada.setText(usuario.getFechaFinFormateada());

        String estado = usuario.getEstadoMembresia();
        lblMembresiaActivaNoActiva.setText(estado);

        // Aplicar color según estado
        if ("ACTIVA".equals(estado)) {
            lblMembresiaActivaNoActiva.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            lblMembresiaActivaNoActiva.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    private void limpiarInformacionUsuario() {
        lblNombreEncontrado.setText("-");
        lblMembresiaEncontrada.setText("-");
        lblFechaVencimientoEncontrada.setText("-");
        lblMembresiaActivaNoActiva.setText("-");
        lblMembresiaActivaNoActiva.setStyle("");
    }

    private void limpiarFormulario() {
        txtfieldIdentificacion.clear();
        limpiarInformacionUsuario();
        usuarioActual = null;
        btnValidarIngreso.setDisable(true);
        // Actualizar view bound a la colección observable (ModelFactory ya actualiza la observable)
    }

    private void mostrarAlerta(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}