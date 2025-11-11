package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReservaClaseController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import gimnasiouq.gimnasiouq.util.ReservaValidationResult;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class RecepReservaClasesViewController {

    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnActualizar;
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

    @FXML
    private TableView<Usuario> tableUsuario;
    @FXML
    private TableColumn<Usuario, String> tcUsuario;
    @FXML
    private TableColumn<Usuario, String> tcNombre;
    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;
    @FXML
    private TableColumn<Usuario, String> tcTipo;
    @FXML
    private TableColumn<Usuario, String> tcClase;
    @FXML
    private TableColumn<Usuario, String> tcHorarior;
    @FXML
    private TableColumn<Usuario, String> tcFecha;
    @FXML
    private TableColumn<Usuario, String> tcEntrenador;
    @FXML
    private TableColumn<Usuario, String> tcEstado;

    @FXML
    void onNuevo(ActionEvent event) {nuevaReserva();}

    @FXML
    void onConfirmar(ActionEvent event) {confirmarReserva();}

    @FXML
    void onActualizar(ActionEvent event) {actualizarReserva();}

    @FXML
    void onEliminar(ActionEvent event) {eliminarReserva();}


    private Usuario usuarioSeleccionado;

    private ReservaClaseController reservaController;

    @FXML
    void initialize() {
        reservaController = new ReservaClaseController();

        comboBoxClase.getItems().addAll("Yoga", "Spinning", "Zumba");
        comboBoxHorario.getItems().addAll("8:00 AM - 10:00 AM", "10:00 AM - 12:00 PM", "12:00 PM - 2:00 PM");
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(true);

        ObservableList<Usuario> listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        initDataBinding();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
        lblBeneficios.setText("Seleccione un usuario");

        cargarEntrenadoresDisponibles();
    }

    private ReservaClase getReservaForUser(String identificacion) {
        return reservaController.obtenerReservaPorUsuario(identificacion);
    }

    private void initDataBinding() {
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        }
        if (tcTipo != null) {
            tcTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMembresia()));
        }
        if (tcClase != null) {
            tcClase.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin clase" : reserva.getClase());
            });
        }
        if (tcHorarior != null) {
            tcHorarior.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin horario" : reserva.getHorario());
            });
        }
        if (tcFecha != null) {
            tcFecha.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin fecha" : reserva.getFecha());
            });
        }
        if (tcEntrenador != null) {
            tcEntrenador.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin entrenador" : reserva.getEntrenador());
            });
        }
        if (tcEstado != null) {
            tcEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstadoMembresia()));
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof TrabajadorUQ) {
                    return new SimpleStringProperty("Trabajador UQ");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            usuarioSeleccionado = newSel;
            if (newSel != null) {
                mostrarBeneficios(newSel);
                prepararEntrenadoresSegunMembresia(newSel);
                ReservaClase reserva = getReservaForUser(newSel.getIdentificacion());
                if (reserva != null) {
                    comboBoxClase.setValue(reserva.getClase());
                    comboBoxHorario.setValue(reserva.getHorario());
                    txtFecha.setText(reserva.getFecha());
                    comboBoxEntrenador.setValue(reserva.getEntrenador());
                } else {
                    limpiarCamposReserva();
                }
            } else {
                lblBeneficios.setText("Seleccione un usuario");
                comboBoxEntrenador.getItems().clear();
                comboBoxEntrenador.setDisable(true);
                limpiarCamposReserva();
            }
        });
    }

    private void cargarEntrenadoresDisponibles() {
        ObservableList<Entrenador> listaEntrenadores = ModelFactory.getInstance().obtenerEntrenadorObservable();
        listaEntrenadores.addListener((javafx.collections.ListChangeListener.Change<? extends Entrenador> c) -> {
            if (usuarioSeleccionado != null) {
                actualizarComboBoxEntrenadores();
            }
        });
    }

    private void actualizarComboBoxEntrenadores() {
        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getMembresia());
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(!esVIP);

        if (esVIP) {
            List<Entrenador> listaEntrenadores = ModelFactory.getInstance().obtenerEntrenadores();

            if (listaEntrenadores.isEmpty()) {
                comboBoxEntrenador.setDisable(true);
                mostrarAlerta("Sin entrenadores", "No hay entrenadores disponibles. Por favor, cree un entrenador primero en la sección de Gestión de Entrenadores.", Alert.AlertType.WARNING);
            } else {
                for (Entrenador e : listaEntrenadores) {
                    comboBoxEntrenador.getItems().add(e.getNombre());
                }
            }
        }
    }

    private void prepararEntrenadoresSegunMembresia(Usuario usuario) {
        boolean esVIP = "VIP".equalsIgnoreCase(usuario.getMembresia());
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(!esVIP);

        if (esVIP) {
            List<Entrenador> entrenadores = ModelFactory.getInstance().obtenerEntrenadores();

            if (entrenadores.isEmpty()) {
                comboBoxEntrenador.setDisable(true);
                mostrarAlerta("Sin entrenadores", "No hay entrenadores disponibles. Por favor, cree un entrenador primero en la sección de Gestión de Entrenadores.", Alert.AlertType.WARNING);
            } else {
                for (Entrenador e : entrenadores) {
                    comboBoxEntrenador.getItems().add(e.getNombre());
                }
            }
        }
    }

    private void mostrarBeneficios(Usuario usuario) {
        String tipoMembresia = usuario.getMembresia();
        String beneficios;
        if (tipoMembresia == null || tipoMembresia.isEmpty()) {
            beneficios = "Sin membresía asignada";
        } else {
            switch (tipoMembresia.toLowerCase()) {
                case "basica" -> beneficios = "Acceso general a máquinas";
                case "premium" -> beneficios = "Acceso general a máquinas, clases grupales";
                case "vip" ->
                        beneficios = "Acceso ilimitado a máquinas, clases grupales ilimitadas, área de spa, entrenador personal";
                default -> beneficios = "Tipo de membresía no reconocido";
            }
        }
        lblBeneficios.setText(beneficios);
    }

    private void nuevaReserva() {
        limpiarCampos();
        tableUsuario.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    private void confirmarReserva() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String fechaIngresada = txtFecha.getText();
        String entrenador = comboBoxEntrenador.getValue();

        if (clase == null || clase.isEmpty() || horario == null || horario.isEmpty() || fechaIngresada == null || fechaIngresada.isEmpty()) {
            mostrarAlerta("Error", "Debe completar todos los campos de la reserva.", Alert.AlertType.ERROR);
            return;
        }

        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getMembresia());
        if (esVIP && (entrenador == null || entrenador.isEmpty())) {
            mostrarAlerta("Error", "Los usuarios VIP deben seleccionar un entrenador.", Alert.AlertType.ERROR);
            return;
        }

        if (!esVIP) {
            entrenador = "Sin entrenador";
        }

        ReservaClase reserva = new ReservaClase(clase, horario, entrenador, fechaIngresada);
        reserva.setIdentificacion(usuarioSeleccionado.getIdentificacion());

        ReservaValidationResult result = reservaController.agregarReserva(usuarioSeleccionado.getIdentificacion(), reserva);

        if (result == ReservaValidationResult.EXITO) {
            mostrarAlerta("Éxito", "Reserva creada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh();
        } else {
            mostrarMensajeErrorReserva(result);
        }
    }

    private void actualizarReserva() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        ReservaClase existingReserva = getReservaForUser(usuarioSeleccionado.getIdentificacion());
        if (existingReserva == null) {
            mostrarAlerta("Error", "El usuario seleccionado no tiene reservas para actualizar.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String fechaIngresada = txtFecha.getText();
        String entrenador = comboBoxEntrenador.getValue();

        if (clase == null || clase.isEmpty() || horario == null || horario.isEmpty() || fechaIngresada == null || fechaIngresada.isEmpty()) {
            mostrarAlerta("Error", "Debe completar todos los campos de la reserva.", Alert.AlertType.ERROR);
            return;
        }

        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getMembresia());
        if (esVIP && (entrenador == null || entrenador.isEmpty())) {
            mostrarAlerta("Error", "Los usuarios VIP deben seleccionar un entrenador.", Alert.AlertType.ERROR);
            return;
        }

        if (!esVIP) {
            entrenador = "Sin entrenador";
        }

        ReservaClase reserva = new ReservaClase(clase, horario, entrenador, fechaIngresada);
        reserva.setIdentificacion(usuarioSeleccionado.getIdentificacion());

        ReservaValidationResult result = reservaController.actualizarReserva(usuarioSeleccionado.getIdentificacion(), reserva);

        if (result == ReservaValidationResult.EXITO) {
            mostrarAlerta("Éxito", "Reserva actualizada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh();
        } else {
            mostrarMensajeErrorReserva(result);
        }
    }

    private void eliminarReserva() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        ReservaClase existingReserva = getReservaForUser(usuarioSeleccionado.getIdentificacion());
        if (existingReserva == null) {
            mostrarAlerta("Error", "El usuario seleccionado no tiene reservas para eliminar.", Alert.AlertType.ERROR);
            return;
        }

        boolean exito = reservaController.eliminarReserva(usuarioSeleccionado.getIdentificacion());

        if (exito) {
            mostrarAlerta("Éxito", "Reserva eliminada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo eliminar la reserva.", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        limpiarCamposReserva();
        lblBeneficios.setText("Seleccione un usuario");
    }

    private void limpiarCamposReserva() {
        comboBoxClase.setValue(null);
        comboBoxHorario.setValue(null);
        comboBoxEntrenador.setValue(null);
        txtFecha.clear();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarMensajeErrorReserva(ReservaValidationResult result) {
        String mensaje = "";
        switch (result) {
            case FORMATO_FECHA_INVALIDO:
                mensaje = "Error: La fecha ingresada no tiene el formato dd/MM/yyyy.";
                break;
            case FECHA_EN_PASADO:
                mensaje = "Error: La fecha de la reserva no puede ser anterior a la fecha actual.";
                break;

            case EXCEDE_MAXIMO_RESERVAS:
                mensaje = "Error: Se ha excedido el límite de 3 reservas para esta clase.";
                break;
            case USUARIO_NO_ENCONTRADO:
                mensaje = "Error: Usuario no encontrado.";
                break;
            case MEMBRESIA_INACTIVA:
                mensaje = "Error: El usuario no tiene una membresía activa.";
                break;
            case FECHA_FUERA_MEMBRESIA:
                mensaje = "Error: La fecha de la reserva está fuera del período de validez de la membresía del usuario.";
                break;
            case DATOS_RESERVA_INVALIDOS:
                mensaje = "Error: Datos de reserva incompletos o inválidos.";
                break;
            case ERROR_DESCONOCIDO:
            default:
                mensaje = "Error desconocido al procesar la reserva.";
                break;
        }
        mostrarAlerta("Error de Reserva", mensaje, Alert.AlertType.ERROR);
    }
}
