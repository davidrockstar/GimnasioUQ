package gimnasiouq.gimnasiouq.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RecepcionistaReservaClasesViewController {

    // ⭐ NUEVO: Referencia al controlador padre
    private RecepcionistaViewController recepcionistaAppController;

    @FXML private Button btnConfirmar;

    @FXML private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TextField txtFecha;

    @FXML
    private ComboBox<String> comboBoxClase;

    @FXML
    private ComboBox<String> comboBoxEntrenador;

    @FXML
    private ComboBox<String> comboBoxHorario;

    @FXML
    private Label lblBeneficios;

    @FXML private TableView<Usuario> tableUsuario;
    @FXML private TableColumn<Usuario, String> tcNombre;
    @FXML private TableColumn<Usuario, String> tcIdentificacion;
    @FXML private TableColumn<Usuario, String> tcTipo;
    @FXML private TableColumn<Usuario, String> tcClase;
    @FXML private TableColumn<Usuario, String> tcHorarior;
    @FXML private TableColumn<Usuario, String> tcFecha;
    @FXML private TableColumn<Usuario, String> tcEntrenador;
    @FXML private TableColumn<Usuario, String> tcEstado;

    private Usuario usuarioSeleccionado;
    private ReservaClase reservaSeleccionada;
    private RecepcionistaViewController recepcionistaViewController;

    @FXML
    void initialize() {
        // Inicializar ComboBoxes
        comboBoxClase.getItems().addAll("Yoga", "Spinning", "Zumba");
        comboBoxEntrenador.getItems().addAll("Camilo", "Andrés", "Sofía");
        comboBoxHorario.getItems().addAll("8:00 AM - 10:00 AM", "10:00 AM - 12:00 PM", "12:00 PM - 2:00 PM");

        // ⭐ CAMBIO: Usar la lista observable compartida
        // ⭐ Eliminar = FXCollections.observableArrayList()
        ObservableList<Usuario> listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();

        // Configurar tabla
        initDataBinding();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();

        // Inicializar label de beneficios
        lblBeneficios.setText("Seleccione un usuario");
    }

    private void initDataBinding() {
        // Columnas básicas de usuario
        tcNombre.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getNombre()));

        tcIdentificacion.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getIdentificacion()));

        tcTipo.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getMembresia()));

        // ⭐ CAMBIO: Columnas de reserva con datos reales
        tcClase.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            if (!usuario.getReservas().isEmpty()) {
                return new SimpleStringProperty(usuario.getReservas().getFirst().getClase());
            }
            return new SimpleStringProperty("Sin clase");
        });

        tcHorarior.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            if (!usuario.getReservas().isEmpty()) {
                return new SimpleStringProperty(usuario.getReservas().getFirst().getHorario());
            }
            return new SimpleStringProperty("Sin horario");
        });

        tcFecha.setCellValueFactory(cellData -> {
                    Usuario usuario = cellData.getValue();
                    if (!usuario.getReservas().isEmpty()) {
                        return new SimpleStringProperty(usuario.getReservas().get(0).getFecha());}
                    return new SimpleStringProperty("Sin fecha");
        });

        tcEntrenador.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            if (!usuario.getReservas().isEmpty()) {
                return new SimpleStringProperty(usuario.getReservas().get(0).getEntrenador());
            }
            return new SimpleStringProperty("Sin entrenador");
        });

        // ⭐ Columna de ESTADO (ACTIVA/NO ACTIVA)
        tcEstado.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));

        // Estilo para la columna Estado
        tcEstado.setCellFactory(column -> new TableCell<Usuario, String>() {
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

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            if (newSelection != null) {
                mostrarBeneficios(newSelection);
            } else {
                lblBeneficios.setText("Seleccione un usuario");
            }
        });
    }

    // ⭐ NUEVO: Mostrar beneficios según tipo de membresía
    private void mostrarBeneficios(Usuario usuario) {
        String tipoMembresia = usuario.getMembresia();
        String beneficios = "";

        if (tipoMembresia == null || tipoMembresia.isEmpty()) {
            beneficios = "Sin membresía asignada";
        } else {
            switch (tipoMembresia.toLowerCase()) {
                case "basica":
                    beneficios = "Acceso general al gimnasio";
                    break;
                case "premium":
                    beneficios = "Acceso general al gimnasio, clases grupales ilimitadas";
                    break;
                case "vip":
                    beneficios = "Acceso general al gimnasio, clases grupales ilimitadas, entrenador personal";
                    break;
                default:
                    beneficios = "Tipo de membresía no reconocido";
            }
        }

        lblBeneficios.setText(beneficios);
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un usuario", Alert.AlertType.ERROR);
            return;
        }

        if (!"ACTIVA".equals(usuarioSeleccionado.getEstadoMembresia())) {
            mostrarAlerta("Error", "El usuario no tiene una membresía activa", Alert.AlertType.ERROR);
            return;
        }

        if (comboBoxClase.getValue() == null) {
            mostrarAlerta("Error", "Seleccione una clase", Alert.AlertType.ERROR);
            return;
        }

        if (comboBoxHorario.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un horario", Alert.AlertType.ERROR);
            return;
        }

        if (comboBoxEntrenador.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un entrenador", Alert.AlertType.ERROR);
            return;
        }

        String fechaIngresada = txtFecha.getText();
        LocalDate fechaActual = LocalDate.now();

        try {
            LocalDate fecha = LocalDate.parse(fechaIngresada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (fecha.isBefore(fechaActual)) {
                mostrarAlerta("Error", "La fecha ingresada no puede ser menor a la fecha actual.", Alert.AlertType.ERROR);
                return;
            }
        } catch (DateTimeParseException e) {
            mostrarAlerta("Error", "Formato de fecha incorrecto. Ingrese la fecha en el formato dd/MM/yyyy.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String entrenador = comboBoxEntrenador.getValue();

        ReservaClase reserva = new ReservaClase(clase, horario, entrenador, fechaIngresada);

        // Limpiar reservas anteriores (opcional, si solo permites una reserva)
        usuarioSeleccionado.getReservas().clear();

        // Agregar la nueva reserva
        usuarioSeleccionado.getReservas().add(reserva);

        // Actualizar en el modelo
        ModelFactory.getInstance().actualizarUsuario(
                usuarioSeleccionado.getIdentificacion(), usuarioSeleccionado);

        // Refrescar tabla
        tableUsuario.refresh();

        if (recepcionistaAppController != null) {
            recepcionistaAppController.notificarActualizacion();
        }

        mostrarAlerta("Éxito", "Reserva confirmada para " + usuarioSeleccionado.getNombre(),
                Alert.AlertType.INFORMATION);
        limpiarCampos();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (usuarioSeleccionado != null && !usuarioSeleccionado.getReservas().isEmpty()) {
            // ⭐ IMPLEMENTAR: Eliminar la reserva
            usuarioSeleccionado.getReservas().clear();

            // Actualizar en el modelo
            ModelFactory.getInstance().actualizarUsuario(
                usuarioSeleccionado.getIdentificacion(), usuarioSeleccionado);

            // Refrescar tabla
            tableUsuario.refresh();

            mostrarAlerta("Éxito", "Reserva eliminada", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "Seleccione un usuario con reserva", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un usuario", Alert.AlertType.ERROR);
            return;
        }

        // Validar los datos ingresados
        if (!validarDatos()) {
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String entrenador = comboBoxEntrenador.getValue();
        String fechaIngresada = txtFecha.getText();

        LocalDate fechaActual = LocalDate.now();
        try {
            LocalDate fecha = LocalDate.parse(fechaIngresada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (fecha.isBefore(fechaActual)) {
                mostrarAlerta("Error", "La fecha ingresada no puede ser menor a la fecha actual.", Alert.AlertType.ERROR);
                return;
            }
        } catch (DateTimeParseException e) {
            mostrarAlerta("Error", "Formato de fecha incorrecto. Ingrese la fecha en el formato dd/MM/yyyy.", Alert.AlertType.ERROR);
            return;
        }

        // Actualizar los datos del usuario
        reservaSeleccionada.setClase(clase);
        reservaSeleccionada.setHorario(horario);
        reservaSeleccionada.setEntrenador(entrenador);
        reservaSeleccionada.setFecha(fechaIngresada);

        // Actualizar en el modelo
        ModelFactory.getInstance().actualizarUsuario(usuarioSeleccionado.getIdentificacion(), usuarioSeleccionado);

        // Refrescar la tabla
        tableUsuario.refresh();

        mostrarAlerta("Éxito", "Datos actualizados para " + usuarioSeleccionado.getNombre(),
                Alert.AlertType.INFORMATION);
        limpiarCampos();
    }

    private boolean validarDatos() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un usuario", Alert.AlertType.ERROR);
            return false;
        }

        if (!"ACTIVA".equals(usuarioSeleccionado.getEstadoMembresia())) {
            mostrarAlerta("Error", "El usuario no tiene una membresía activa", Alert.AlertType.ERROR);
            return false;
        }

        if (comboBoxClase.getValue() == null) {
            mostrarAlerta("Error", "Seleccione una clase", Alert.AlertType.ERROR);
            return false;
        }

        if (comboBoxHorario.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un horario", Alert.AlertType.ERROR);
            return false;
        }

        if (comboBoxEntrenador.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un entrenador", Alert.AlertType.ERROR);
            return false;
        }

        if (txtFecha.getText() == null || txtFecha.getText().isEmpty()) {
            mostrarAlerta("Error", "Introduzca una fecha", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        comboBoxClase.setValue(null);
        comboBoxHorario.setValue(null);
        comboBoxEntrenador.setValue(null);
        txtFecha.clear();
        lblBeneficios.setText("Seleccione un usuario");
        tableUsuario.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public void setRecepcionistaController(RecepcionistaViewController recepcionistaViewController) {
        this.recepcionistaAppController = recepcionistaViewController;
    }
}
