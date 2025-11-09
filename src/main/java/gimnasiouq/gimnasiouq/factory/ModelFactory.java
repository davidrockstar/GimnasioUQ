package gimnasiouq.gimnasiouq.factory;

import gimnasiouq.gimnasiouq.model.*;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelFactory {

    private static ModelFactory modelFactory;
    private final GimnasioUQ gimnasioUQ;

    private final ObservableList<Usuario> listaUsuariosObservable;
    private final ObservableList<Entrenador> listaEntrenadorObservable;
    private final ObservableList<ControlAcceso> listaRegistrosAccesoObservable;
    private final ObservableList<ReservaClase> listaReservaClasesObservable;

    // Properties para indicadores de membresías
    private final IntegerProperty membresiasTotales = new SimpleIntegerProperty(0);
    private final IntegerProperty membresiasConValor = new SimpleIntegerProperty(0);
    private final IntegerProperty membresiasSinValor = new SimpleIntegerProperty(0);
    private final DoubleProperty ingresosTotales = new SimpleDoubleProperty(0.0);
    // Properties para indicadores de usuarios
    private final IntegerProperty usuariosMembresiaActivas = new SimpleIntegerProperty(0);
    private final IntegerProperty usuariosMembresiaInactivas = new SimpleIntegerProperty(0);
    private final IntegerProperty usuariosTotales = new SimpleIntegerProperty(0);
    // Properties para indicadores de clases
    private final StringProperty claseMasReservada = new SimpleStringProperty("");
    private final IntegerProperty totalClasesReservadas = new SimpleIntegerProperty(0);

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
        listaReservaClasesObservable = FXCollections.observableArrayList(gimnasioUQ.obtenerReservasDeUsuarios());

        listaUsuariosObservable.addListener((ListChangeListener.Change<? extends Usuario> c) -> {
            actualizarIndicadores();
            listaReservaClasesObservable.setAll(gimnasioUQ.obtenerReservasDeUsuarios());
        });
        actualizarIndicadores();
    }

    private void actualizarIndicadores() {
        // Indicadores de membresías
        membresiasTotales.set(gimnasioUQ.contarMembresiasTotales());
        membresiasConValor.set(gimnasioUQ.contarMembresiasConValor());
        membresiasSinValor.set(gimnasioUQ.contarMembresiasSinValor());
        ingresosTotales.set(gimnasioUQ.calcularIngresosTotalesMembresias());
        // Indicadores de usuarios
        usuariosMembresiaActivas.set(gimnasioUQ.contarMembresiasUsuariosActivas());
        usuariosMembresiaInactivas.set(gimnasioUQ.contarMembresiasUsuariosInactivas());
        usuariosTotales.set(gimnasioUQ.contarTotalUsuarios());
        // Indicadores de clases
        claseMasReservada.set(gimnasioUQ.contarClaseMasReservada());
        totalClasesReservadas.set(gimnasioUQ.contarTotalClasesReservadas());

    }

    public String getNombreGimnasio() {
        return gimnasioUQ.getNombre();
    }

    public String getDireccionGimnasio() {
        return gimnasioUQ.getDireccion();
    }

    public List<Usuario> obtenerUsuarios() { return gimnasioUQ.getListaUsuarios(); }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return listaUsuariosObservable;
    }

    public List<Entrenador> obtenerEntrenadores() {
        return gimnasioUQ.getListaEntrenador();
    }

    public ObservableList<Entrenador> obtenerEntrenadorObservable(){
        listaEntrenadorObservable.setAll(gimnasioUQ.getListaEntrenador());
        return listaEntrenadorObservable;
    }

    public ObservableList<ReservaClase> obtenerReservasObservable(){
        return listaReservaClasesObservable;
    }

    public boolean agregarUsuario(Usuario usuario) {
        boolean ok = gimnasioUQ.agregarUsuario(usuario);
        if (ok) {
            listaUsuariosObservable.add(usuario);
            actualizarIndicadores();
        }
        return ok;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        boolean ok = gimnasioUQ.actualizarUsuario(identificacion, usuarioActualizado);
        if (ok) {
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
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

    public boolean actualizarEntrenador(String identificacion, Entrenador entrenadorActualizado) {
        boolean ok = gimnasioUQ.actualizarEntrenador(identificacion, entrenadorActualizado);
        if (ok) {
            // Actualizar la lista observable
            for (int i = 0; i < listaEntrenadorObservable.size(); i++) {
                if (listaEntrenadorObservable.get(i).getIdentificacion().equals(identificacion)) {
                    listaEntrenadorObservable.set(i, entrenadorActualizado);
                    break;
                }
            }
        }
        return ok;
    }

    public boolean eliminarEntrenador(String identificacion) {
        boolean ok = gimnasioUQ.eliminarEntrenador(identificacion);
        if (ok) {
            listaEntrenadorObservable.removeIf(e -> e.getIdentificacion().equals(identificacion));
        }
        return ok;
    }

    public Entrenador buscarEntrenador(String identificacion) {
        return gimnasioUQ.buscarEntrenadorPorIdentificacion(identificacion);
    }

    public boolean agregarRegistroAcceso(ControlAcceso registro) {
        boolean ok = gimnasioUQ.agregarRegistroAcceso(registro);
        if (ok) {
            listaRegistrosAccesoObservable.add(registro);
        }
        return ok;
    }

    public boolean eliminarRegistroAcceso(ControlAcceso registro) {
        boolean ok = gimnasioUQ.eliminarRegistroAcceso(registro);
        if (ok) {
            listaRegistrosAccesoObservable.remove(registro);
        }
        return ok;
    }

    public ObservableList<ControlAcceso> obtenerRegistrosAccesoObservable() {
        listaRegistrosAccesoObservable.setAll(gimnasioUQ.getListaRegistrosAcceso());
        return listaRegistrosAccesoObservable;
    }

    public boolean agregarReservaAUsuario(String identificacionUsuario, ReservaClase reserva) {
        boolean ok = gimnasioUQ.agregarReservaUsuario(identificacionUsuario, reserva);
        if (ok) {
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
            actualizarIndicadores();
        }
        return ok;
    }

    public boolean actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        boolean ok = gimnasioUQ.actualizarReservaUsuario(identificacionUsuario, reserva);
        if (ok) {
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
            actualizarIndicadores();
        }
        return ok;
    }

    public boolean eliminarReservasUsuario(String identificacionUsuario) {
        boolean ok = gimnasioUQ.eliminarReservasUsuario(identificacionUsuario);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        if (ok) actualizarIndicadores();
        return ok;
    }

    public ObservableList<ReservaClase> obtenerReservasDeUsuariosObservable() {
        return listaReservaClasesObservable;
    }

    public boolean asignarMembresiaUsuario(String identificacionUsuario, gimnasiouq.gimnasiouq.model.Membresia membresia) {
        boolean ok = gimnasioUQ.asignarMembresiaUsuario(identificacionUsuario, membresia);
        if (ok) {
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
            actualizarIndicadores();
        }
        return ok;
    }

    public boolean actualizarMembresiaUsuario(String identificacionUsuario, gimnasiouq.gimnasiouq.model.Membresia membresia) {
        boolean ok = gimnasioUQ.actualizarMembresiaUsuario(identificacionUsuario, membresia);
        if (ok) {
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
            actualizarIndicadores();
        }
        return ok;
    }

    public boolean eliminarMembresiaUsuario(String identificacionUsuario) {
        boolean ok = gimnasioUQ.eliminarMembresiaUsuario(identificacionUsuario);
        if (ok) {
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
            actualizarIndicadores();
        }
        return ok;
    }

    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoMembresia, Usuario usuario) {
        return gimnasioUQ.calcularMembresiaPorPlan(tipoPlan, tipoMembresia, usuario);
    }

    public boolean puedeAccederSpa(String identificacion) {
        return gimnasioUQ.puedeAccederSpa(identificacion);
    }

    public boolean validarIngresoUsuario(String identificacion) {
        return gimnasioUQ.validarIngresoUsuario(identificacion);
    }

    public boolean registrarIngresoUsuario(String identificacion) {
        boolean ok = gimnasioUQ.registrarIngresoUsuario(identificacion);
        if (ok) {
            listaRegistrosAccesoObservable.setAll(gimnasioUQ.getListaRegistrosAcceso());
        }
        return ok;
    }

    // REPORTES MEMBRESIAS
    public IntegerProperty membresiasTotalesProperty() { return membresiasTotales; }
    public IntegerProperty membresiasConValorProperty() { return membresiasConValor; }
    public IntegerProperty membresiasSinValorProperty() { return membresiasSinValor; }
    public DoubleProperty ingresosTotalesProperty() { return ingresosTotales; }
    // REPORTES USUARIOS
    public IntegerProperty usuariosMembresiaActivasProperty() { return usuariosMembresiaActivas; }
    public IntegerProperty usuariosMembresiaInativasProperty() { return usuariosMembresiaInactivas; }
    public IntegerProperty usuariosTotalesProperty() {return usuariosTotales;}
    // REPORTES CLASES
    public StringProperty claseMasReservadaProperty() {return claseMasReservada;}
    public IntegerProperty totalClasesReservadasProperty() {return totalClasesReservadas;}
}
