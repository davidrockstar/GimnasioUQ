package gimnasiouq.gimnasiouq.viewcontroller;

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

    RecepViewController recepcionistaAppController;
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    Usuario usuarioSeleccionado;
    
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        
        // Listener para calcular fechas automáticamente
        comboBoxPlanMembresia.setOnAction(e -> calcularFechas());

    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.clear();
        listaUsuarios.addAll(ModelFactory.getInstance().obtenerUsuarios());
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

        // ⭐ NUEVAS COLUMNAS - Plan Membresía
        if (tcPlanMembresia != null) {
            tcPlanMembresia.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getPlanMembresia());
            });
        }

        // ⭐ NUEVAS COLUMNAS - Costo
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

            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText(), formatoFecha);
            LocalDate fechaFin = LocalDate.parse(txtFechaFin.getText(), formatoFecha);
            double costo = Double.parseDouble(txtCosto.getText());

            String tipoMembresia = usuarioSeleccionado.getTipoMembresia();
            if (tipoMembresia == null || tipoMembresia.isEmpty()) {
                mostrarVentanaEmergente("Tipo de membresía no definido", "Error",
                    "El usuario debe tener un tipo de membresía asignado (Basica/Premium/VIP)", 
                    Alert.AlertType.ERROR);
                return;
            }

            Membresia membresia = crearMembresia(tipoMembresia, costo, fechaInicio, fechaFin);
            
            if (membresia == null || membresia.getTipo() == null) {
                mostrarVentanaEmergente("Error al crear membresía", "Error",
                    "No se pudo crear la membresía correctamente", 
                    Alert.AlertType.ERROR);
                return;
            }

            usuarioSeleccionado.setMembresiaActiva(membresia);

            if (ModelFactory.getInstance().actualizarUsuario(
                    usuarioSeleccionado.getIdentificacion(), usuarioSeleccionado)) {

                tableUsuario.refresh();

                if (recepcionistaAppController != null) {
                    recepcionistaAppController.notificarActualizacion();
                }

                mostrarVentanaEmergente("Membresía asignada", "Éxito",
                        "La membresía se asignó correctamente al usuario " + usuarioSeleccionado.getNombre(),
                        Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarVentanaEmergente("Error al asignar", "Error",
                        "No se pudo asignar la membresía", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarVentanaEmergente("Error de datos", "Error",
                    "Verifique que todos los campos sean válidos: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
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
                usuarioSeleccionado.setMembresiaActiva(null);
                ModelFactory.getInstance().actualizarUsuario(
                    usuarioSeleccionado.getIdentificacion(), usuarioSeleccionado);
                tableUsuario.refresh();
                limpiarCampos();
                mostrarVentanaEmergente("Membresía eliminada", "Éxito",
                    "La membresía se eliminó correctamente", Alert.AlertType.INFORMATION);
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

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin;
        int costo;

        switch (plan) {
            case "Mensual":
                fechaFin = fechaInicio.plusMonths(1);
                costo = 50000;
                break;
            case "Trimestral":
                fechaFin = fechaInicio.plusMonths(3);
                costo = 135000;
                break;
            case "Anual":
                fechaFin = fechaInicio.plusYears(1);
                costo = 540000;
                break;
            default:
                fechaFin = fechaInicio;
                costo = 0;
        }

        txtFechaInicio.setText(fechaInicio.format(formatoFecha));
        txtFechaFin.setText(fechaFin.format(formatoFecha));
        txtCosto.setText(String.valueOf(costo));
    }

    private Membresia crearMembresia(String tipo, double costo, LocalDate inicio, LocalDate fin) {
        switch (tipo) {
            case "Premium":
                return new MembresiaPremium(costo, inicio, fin);
            case "VIP":
                return new MembresiaVIP(costo, inicio, fin);
            case "Basica":
            default:
                return new MembresiaBasica(costo, inicio, fin);
        }
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

    public void refrescarTabla() {
        obtenerUsuarios();
        tableUsuario.refresh();
    }

    public void setRecepcionistaAppController(RecepViewController recepcionistaAppController) {
        this.recepcionistaAppController = recepcionistaAppController;
        if (recepcionistaAppController != null) {
            recepcionistaAppController.setMembresiasController(this);
        }
    }
}