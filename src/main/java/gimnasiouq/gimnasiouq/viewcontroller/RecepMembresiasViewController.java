
package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.MembresiaController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecepMembresiasViewController {

    ObservableList<Usuario> listaUsuarios;
    Usuario usuarioSeleccionado;

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private MembresiaController membresiaController;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAsignar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<String> comboBoxPlanMembresia;
    @FXML
    private TableView<Usuario> tableUsuario;

    @FXML
    private TableColumn<Usuario, String> tcFechaFin;

    @FXML
    private TableColumn<Usuario, String> tcFechaInicio;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    // ⭐ NUEVAS COLUMNAS
    @FXML
    private TableColumn<Usuario, String> tcPlanMembresia;

    @FXML
    private TableColumn<Usuario, String> tcCosto;

    @FXML
    private TableColumn<Usuario, String> tcEstado;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtFechaFin;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarMembresia();
    }

    @FXML
    void onAsignar(ActionEvent event) {
        asignarMembresia();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarMembresia();
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nuevoRegistro();
    }

    @FXML
    void initialize() {
        initView();
        comboBoxPlanMembresia.getItems().addAll("Mensual", "Trimestral", "Anual");
        comboBoxPlanMembresia.setOnAction(e -> calcularFechas());

    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdentificacion()));

        tcFechaInicio.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getFechaInicioFormateada());
        });

        tcFechaFin.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getFechaFinFormateada());
        });
        if (tcPlanMembresia != null) {
            tcPlanMembresia.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getPlanMembresia());
            });
        }

        if (tcCosto != null) {
            tcCosto.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getCostoMembresiaFormateado());
            });
        }

        tcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newSelection) -> {
                    usuarioSeleccionado = newSelection;
                    mostrarInformacionUsuario(usuarioSeleccionado);
                });
    }

    private void asignarMembresia() {
        if (usuarioSeleccionado == null) {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia",
                    "Debe seleccionar un usuario de la tabla para asignarle una membresía",
                    Alert.AlertType.WARNING);
            return;
        }

        if (comboBoxPlanMembresia.getValue() == null || comboBoxPlanMembresia.getValue().isEmpty()) {
            mostrarVentanaEmergente("Seleccione un plan", "Advertencia",
                    "Debe seleccionar un plan de membresía", Alert.AlertType.WARNING);
            return;
        }

        try {
            if (txtFechaInicio.getText() == null || txtFechaInicio.getText().isEmpty() ||
                    txtFechaFin.getText() == null || txtFechaFin.getText().isEmpty() ||
                    txtCosto.getText() == null || txtCosto.getText().isEmpty()) {
                mostrarVentanaEmergente("Campos incompletos", "Error",
                        "Todos los campos son obligatorios. Seleccione un plan primero.",
                        Alert.AlertType.ERROR);
                return;
            }

            Membresia membresia = crearMembresia();

            if (membresiaController == null) {
                membresiaController = new MembresiaController();
            }

            if (!membresiaController.asignarMembresiaUsuario(usuarioSeleccionado.getIdentificacion(), membresia)) {
                mostrarVentanaEmergente("Error al asignar", "Error",
                        "No se pudo asignar la membresía. Verifique los datos.", Alert.AlertType.ERROR);
                return;
            }

            tableUsuario.refresh();

            mostrarVentanaEmergente("Membresía asignada", "Éxito",
                    "La membresía se asignó correctamente al usuario " + usuarioSeleccionado.getNombre(),
                    Alert.AlertType.INFORMATION);
            limpiarCampos();

        } catch (Exception e) {
            mostrarVentanaEmergente("Error de datos", "Error",
                    "Verifique que todos los campos sean válidos: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void actualizarMembresia() {
        if (usuarioSeleccionado == null) {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia",
                    "Debe seleccionar un usuario de la tabla", Alert.AlertType.WARNING);
            return;
        }

        asignarMembresia();
    }

    private void eliminarMembresia() {
        if (usuarioSeleccionado == null) {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia",
                    "Debe seleccionar un usuario de la tabla", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro?");
        confirmacion.setContentText("¿Desea eliminar la membresía del usuario " +
                usuarioSeleccionado.getNombre() + "?");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (membresiaController == null) {
                    membresiaController = new MembresiaController();
                }

                if (membresiaController.eliminarMembresiaUsuario(usuarioSeleccionado)) {
                    tableUsuario.refresh();
                    limpiarCampos();
                    mostrarVentanaEmergente("Membresía eliminada", "Éxito",
                            "La membresía se eliminó correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarVentanaEmergente("Error al eliminar", "Error",
                            "No se pudo eliminar la membresía", Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void nuevoRegistro() {
        limpiarCampos();
        usuarioSeleccionado = null;
        tableUsuario.getSelectionModel().clearSelection();
    }

    private void calcularFechas() {
        String plan = comboBoxPlanMembresia.getValue();
        if (plan == null || plan.isEmpty()) return;

        Membresia membresiaCalculada = ModelFactory.getInstance().calcularMembresiaPorPlan(plan);

        if (membresiaCalculada != null) {
            txtFechaInicio.setText(membresiaCalculada.getInicio().format(formatoFecha));
            txtFechaFin.setText(membresiaCalculada.getFin().format(formatoFecha));
            txtCosto.setText(String.valueOf((int) membresiaCalculada.getCosto()));
        }
    }

    private Membresia crearMembresia() {
        String tipo = comboBoxPlanMembresia.getValue();
        LocalDate inicio = null;
        LocalDate fin = null;
        double costo = 0;
        try {
            if (txtFechaInicio.getText() != null && !txtFechaInicio.getText().isEmpty()) {
                inicio = LocalDate.parse(txtFechaInicio.getText(), formatoFecha);
            }
            if (txtFechaFin.getText() != null && !txtFechaFin.getText().isEmpty()) {
                fin = LocalDate.parse(txtFechaFin.getText(), formatoFecha);
            }
            if (txtCosto.getText() != null && !txtCosto.getText().isEmpty()) {
                costo = Double.parseDouble(txtCosto.getText());
            }
        } catch (Exception ignored) {
        }

        if (tipo != null) {
            tipo = tipo.trim().toLowerCase();
            return switch (tipo) {
                case "mensual" -> new MembresiaBasica(costo, inicio, fin);
                case "trimestral" -> new MembresiaPremium(costo, inicio, fin);
                case "anual" -> new MembresiaVIP(costo, inicio, fin);
                default -> new MembresiaBasica(costo, inicio, fin);
            };
        }
        return new MembresiaBasica(costo, inicio, fin);
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            LocalDate fechaInicio = usuario.getFechaInicioMembresia();
            LocalDate fechaFin = usuario.getFechaFinMembresia();

            if (fechaInicio != null && fechaFin != null) {
                txtFechaInicio.setText(fechaInicio.format(formatoFecha));
                txtFechaFin.setText(fechaFin.format(formatoFecha));
                txtCosto.setText(String.valueOf(usuario.getCostoMembresia()));

            } else {
                limpiarCampos();
            }
        }
    }

    private void limpiarCampos() {
        comboBoxPlanMembresia.setValue(null);
        txtFechaInicio.clear();
        txtFechaFin.clear();
        txtCosto.clear();
    }

    private void mostrarVentanaEmergente(String titulo, String header,
                                         String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
