package gimnasiouq.gimnasiouq.factory;

import gimnasiouq.gimnasiouq.model.Entrenador;
import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.ControlAcceso;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelFactory {

    private static ModelFactory modelFactory;
    private final GimnasioUQ gimnasioUQ;

    private final ObservableList<Usuario> listaUsuariosObservable;
    private final ObservableList<Entrenador> listaEntrenadorObservable;
    private final ObservableList<ControlAcceso> listaRegistrosAccesoObservable;

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        gimnasioUQ = DataUtil.inicializarDatos();
        listaUsuariosObservable = FXCollections.observableArrayList(gimnasioUQ.getListaUsuarios());
        listaEntrenadorObservable = FXCollections.observableArrayList(gimnasioUQ.getListaEntrenador());
        listaRegistrosAccesoObservable = FXCollections.observableArrayList(gimnasioUQ.getListaRegistrosAcceso());
    }

    public List<Usuario> obtenerUsuarios() { return gimnasioUQ.getListaUsuarios(); }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        // Asegurarse que la actualización del ObservableList ocurra en el hilo FX
        Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return listaUsuariosObservable;
    }

    public ObservableList<Entrenador> obtenerEntrenadorObservable(){
        Platform.runLater(() -> listaEntrenadorObservable.setAll(gimnasioUQ.getListaEntrenador()));
        return listaEntrenadorObservable;
    }

    public boolean agregarUsuario(Usuario usuario) {
        boolean ok = gimnasioUQ.agregarUsuario(usuario);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        boolean ok = gimnasioUQ.actualizarUsuario(identificacion, usuarioActualizado);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public boolean eliminarUsuario(String identificacion) {
        boolean ok = gimnasioUQ.eliminarUsuario(identificacion);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public Usuario buscarUsuario(String identificacion) {
        return gimnasioUQ.buscarUsuarioPorIdentificacion(identificacion);
    }

    public boolean agregarEntrenador(Entrenador entrenador) {
        boolean ok = gimnasioUQ.agregarEntrenador(entrenador);
        if (ok) Platform.runLater(() -> listaEntrenadorObservable.setAll(gimnasioUQ.getListaEntrenador()));
        return ok;
    }

    public List<ControlAcceso> getListaRegistrosAcceso() {
        return gimnasioUQ.getListaRegistrosAcceso();
    }

    public boolean agregarRegistroAcceso(ControlAcceso registro) {
        boolean ok = gimnasioUQ.agregarRegistroAcceso(registro);
        if (ok) Platform.runLater(() -> listaRegistrosAccesoObservable.setAll(gimnasioUQ.getListaRegistrosAcceso()));
        return ok;
    }

    public boolean eliminarRegistroAcceso(ControlAcceso registro) {
        boolean ok = gimnasioUQ.eliminarRegistroAcceso(registro);
        if (ok) Platform.runLater(() -> listaRegistrosAccesoObservable.setAll(gimnasioUQ.getListaRegistrosAcceso()));
        return ok;
    }

    public ObservableList<ControlAcceso> obtenerRegistrosAccesoObservable() {
        Platform.runLater(() -> listaRegistrosAccesoObservable.setAll(gimnasioUQ.getListaRegistrosAcceso()));
        return listaRegistrosAccesoObservable;
    }

    public ObservableList<ReservaClase> obtenerReservasDeUsuarios() {
        ObservableList<ReservaClase> reservas = FXCollections.observableArrayList();
        List<ReservaClase> lista = gimnasioUQ.obtenerReservasDeUsuarios();
        reservas.addAll(lista);
        return reservas;
    }

    public boolean agregarReservaAUsuario(String identificacionUsuario, ReservaClase reserva) {
        boolean ok = gimnasioUQ.agregarReservaUsuario(identificacionUsuario, reserva);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public boolean actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        boolean ok = gimnasioUQ.actualizarReservaUsuario(identificacionUsuario, reserva);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public boolean eliminarReservasUsuario(String identificacionUsuario) {
        boolean ok = gimnasioUQ.eliminarReservasUsuario(identificacionUsuario);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public ObservableList<ReservaClase> obtenerReservasDeUsuariosObservable() {
        ObservableList<ReservaClase> reservas = FXCollections.observableArrayList();
        List<ReservaClase> lista = gimnasioUQ.obtenerReservasDeUsuarios();
        reservas.addAll(lista);
        return reservas;
    }

    // ===== Nuevos métodos para CRUD de Membresías delegando en GimnasioUQ =====
    public boolean asignarMembresiaUsuario(String identificacionUsuario, gimnasiouq.gimnasiouq.model.Membresia membresia) {
        boolean ok = gimnasioUQ.asignarMembresiaUsuario(identificacionUsuario, membresia);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public boolean actualizarMembresiaUsuario(String identificacionUsuario, gimnasiouq.gimnasiouq.model.Membresia membresia) {
        boolean ok = gimnasioUQ.actualizarMembresiaUsuario(identificacionUsuario, membresia);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public boolean eliminarMembresiaUsuario(String identificacionUsuario) {
        boolean ok = gimnasioUQ.eliminarMembresiaUsuario(identificacionUsuario);
        if (ok) Platform.runLater(() -> listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios()));
        return ok;
    }

    public gimnasiouq.gimnasiouq.model.Membresia obtenerMembresiaUsuario(String identificacionUsuario) {
        return gimnasioUQ.obtenerMembresiaUsuario(identificacionUsuario);
    }

}