package gimnasiouq.gimnasiouq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static gimnasiouq.gimnasiouq.util.AdvertenciasConstantes.*;

public class AdministradorUsuariosController {

    AdministradorController AdministradorController;
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    Usuario usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<Usuario> tableUsuario;

    @FXML
    private TableColumn<Usuario, String> tcCelular;

    @FXML
    private TableColumn<Usuario, String> tcEdad;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcMembresía;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private ComboBox <String> comboBoxMembresia;

    @FXML
    private TextField txtNombre;

    @FXML
    void onActualizar(ActionEvent event) {}

    @FXML
    void onAgregar(ActionEvent event) {agregarUsuario();}

    @FXML
    void onEliminar(ActionEvent event) {}

    @FXML
    void onNuevo(ActionEvent event) {}

    @FXML
    void initialize() {
        initView();

        comboBoxMembresia.getItems().addAll("Basica", "Premium", "VIP");

    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.addAll(ModelFactory.getInstance().obtenerUsuarios());
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad()));
        tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCelular()));
        tcMembresía.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembresia()));
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void agregarUsuario() {
        Usuario usuario = crearUsuario();

        if(datosValidos(usuario)){
            if(ModelFactory.getInstance().agregarUsuario(usuario)){
                listaUsuarios.add(usuario);
                mostrarVentanaEmergente("Usuario agregado", "Éxito", "El usuario se agregó correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarVentanaEmergente("Usuario no agregado", "Error", "El usuario ya existe o los datos son inválidos", Alert.AlertType.ERROR);
            }
        } else {
            mostrarVentanaEmergente("Datos incompletos", "Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
        }
    }



    private boolean datosValidos(Usuario usuario) {
        if (usuario.getNombre().isEmpty() || usuario.getIdentificacion().isEmpty()
                || usuario.getEdad().isEmpty() || usuario.getCelular().isEmpty()
                || usuario.getMembresia().isEmpty()) {
            return false;} else {return true;}}

    private Usuario crearUsuario(){
        return new Usuario(txtNombre.getText(), txtIdentificacion.getText(),
                txtEdad.getText(), txtCelular.getText(), comboBoxMembresia.getValue());
    }

    private void mostrarInformacionUsuario(Usuario usuarioSeleccionado) {
        if(usuarioSeleccionado != null) {
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtIdentificacion.setText(usuarioSeleccionado.getIdentificacion());
            txtEdad.setText(usuarioSeleccionado.getEdad());
            txtCelular.setText(usuarioSeleccionado.getCelular());
            comboBoxMembresia.setValue(usuarioSeleccionado.getMembresia());
        }
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}
