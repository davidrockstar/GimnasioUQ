package gimnasiouq.gimnasiouq.factory;

import gimnasiouq.gimnasiouq.model.Entrenador;
import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.ControlAcceso;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelFactory {

    private static ModelFactory modelFactory;
    private final GimnasioUQ gimnasioUQ;

    private final ObservableList<Usuario> listaUsuariosObservable;
    private final ObservableList<Entrenador> listaEntrenadorObservable;
    private final ObservableList<ControlAcceso> listaRegistrosAccesoObservable;

    // Properties para indicadores
    private final IntegerProperty membresiasTotales = new SimpleIntegerProperty(0);
    private final IntegerProperty membresiasConValor = new SimpleIntegerProperty(0);
    private final IntegerProperty membresiasSinValor = new SimpleIntegerProperty(0);
    private final DoubleProperty ingresosTotales = new SimpleDoubleProperty(0.0);

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
        // Listener para recalcular indicadores cuando la lista observable cambie
        listaUsuariosObservable.addListener((javafx.collections.ListChangeListener.Change<? extends Usuario> c) -> actualizarIndicadores());
        // Inicializar indicadores basados en los datos actuales
        actualizarIndicadores();
    }

    public void refrescarIndicadores() {
        actualizarIndicadores();
    }

    private void actualizarIndicadores() {
        membresiasTotales.set(gimnasioUQ.contarMembresiasTotales());
        membresiasConValor.set(gimnasioUQ.contarMembresiasConValor());
        membresiasSinValor.set(gimnasioUQ.contarMembresiasSinValor());
        ingresosTotales.set(gimnasioUQ.calcularIngresosTotalesMembresias());
    }

    public List<Usuario> obtenerUsuarios() { return gimnasioUQ.getListaUsuarios(); }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return listaUsuariosObservable;
    }

    public ObservableList<Entrenador> obtenerEntrenadorObservable(){
        listaEntrenadorObservable.setAll(gimnasioUQ.getListaEntrenador());
        return listaEntrenadorObservable;
    }

    public boolean agregarUsuario(Usuario usuario) {
        boolean ok = gimnasioUQ.agregarUsuario(usuario);
        if (ok) listaUsuariosObservable.add(usuario);
        if (ok) actualizarIndicadores();
        return ok;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        boolean ok = gimnasioUQ.actualizarUsuario(identificacion, usuarioActualizado);
        if (ok) {
            // Buscar el índice del usuario en la lista observable
            for (int i = 0; i < listaUsuariosObservable.size(); i++) {
                if (listaUsuariosObservable.get(i).getIdentificacion().equals(identificacion)) {
                    listaUsuariosObservable.set(i, usuarioActualizado);
                    break;
                }
            }
            actualizarIndicadores();
        }
        return ok;
    }

    public boolean eliminarUsuario(String identificacion) {
        boolean ok = gimnasioUQ.eliminarUsuario(identificacion);
        if (ok) {
            listaUsuariosObservable.removeIf(u -> u.getIdentificacion().equals(identificacion));
            actualizarIndicadores();
        }
        return ok;
    }

    public Usuario buscarUsuario(String identificacion) {
        return gimnasioUQ.buscarUsuarioPorIdentificacion(identificacion);
    }

    public boolean agregarEntrenador(Entrenador entrenador) {
        boolean ok = gimnasioUQ.agregarEntrenador(entrenador);
        if (ok) listaEntrenadorObservable.add(entrenador);
        return ok;
    }

    public List<ControlAcceso> getListaRegistrosAcceso() {
        return gimnasioUQ.getListaRegistrosAcceso();
    }

    public boolean agregarRegistroAcceso(ControlAcceso registro) {
        boolean ok = gimnasioUQ.agregarRegistroAcceso(registro);
        if (ok) listaRegistrosAccesoObservable.add(registro);
        return ok;
    }

    public boolean eliminarRegistroAcceso(ControlAcceso registro) {
        boolean ok = gimnasioUQ.eliminarRegistroAcceso(registro);
        if (ok) listaRegistrosAccesoObservable.remove(registro);
        return ok;
    }

    public ObservableList<ControlAcceso> obtenerRegistrosAccesoObservable() {
        listaRegistrosAccesoObservable.setAll(gimnasioUQ.getListaRegistrosAcceso());
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
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public boolean actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        boolean ok = gimnasioUQ.actualizarReservaUsuario(identificacionUsuario, reserva);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public boolean eliminarReservasUsuario(String identificacionUsuario) {
        boolean ok = gimnasioUQ.eliminarReservasUsuario(identificacionUsuario);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public ObservableList<ReservaClase> obtenerReservasDeUsuariosObservable() {
        ObservableList<ReservaClase> reservas = FXCollections.observableArrayList();
        List<ReservaClase> lista = gimnasioUQ.obtenerReservasDeUsuarios();
        reservas.addAll(lista);
        return reservas;
    }

    public boolean asignarMembresiaUsuario(String identificacionUsuario, gimnasiouq.gimnasiouq.model.Membresia membresia) {
        boolean ok = gimnasioUQ.asignarMembresiaUsuario(identificacionUsuario, membresia);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public boolean actualizarMembresiaUsuario(String identificacionUsuario, gimnasiouq.gimnasiouq.model.Membresia membresia) {
        boolean ok = gimnasioUQ.actualizarMembresiaUsuario(identificacionUsuario, membresia);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public boolean eliminarMembresiaUsuario(String identificacionUsuario) {
        boolean ok = gimnasioUQ.eliminarMembresiaUsuario(identificacionUsuario);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public gimnasiouq.gimnasiouq.model.Membresia obtenerMembresiaUsuario(String identificacionUsuario) {
        return gimnasioUQ.obtenerMembresiaUsuario(identificacionUsuario);
    }

    public gimnasiouq.gimnasiouq.model.Membresia calcularMembresiaPorPlan(String tipoPlan) {
        return gimnasioUQ.calcularMembresiaPorPlan(tipoPlan);
    }

    public int contarMembresiasTotales() {
        return gimnasioUQ.contarMembresiasTotales();
    }

    public int contarMembresiasConValor() {
        return gimnasioUQ.contarMembresiasConValor();
    }

    public int contarMembresiasSinValor() {
        return gimnasioUQ.contarMembresiasSinValor();
    }

    public double calcularIngresosTotalesMembresias() {
        return gimnasioUQ.calcularIngresosTotalesMembresias();
    }

    // Exponer properties para binding
    public IntegerProperty membresiasTotalesProperty() { return membresiasTotales; }
    public IntegerProperty membresiasConValorProperty() { return membresiasConValor; }
    public IntegerProperty membresiasSinValorProperty() { return membresiasSinValor; }
    public DoubleProperty ingresosTotalesProperty() { return ingresosTotales; }

}