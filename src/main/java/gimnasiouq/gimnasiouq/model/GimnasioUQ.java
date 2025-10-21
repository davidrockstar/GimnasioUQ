package gimnasiouq.gimnasiouq.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GimnasioUQ {

    private List<Usuario> listaUsuarios;
    private List<Recepcionista> listaRecepcionista;
    private List<Administador> listaAdministrador;
    private List<Clase> listaClases;
    private List<Entrenador> listaEntrenador;

    public GimnasioUQ() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRecepcionista = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();
        this.listaClases = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Recepcionista> getListaRecepcionista() {
        return listaRecepcionista;
    }

    public void setListaRecepcionista(List<Recepcionista> listaRecepcionista) {
        this.listaRecepcionista = listaRecepcionista;
    }

    public List<Administador> getListaAdministrador() {
        return listaAdministrador;
    }

    public void setListaAdministrador(List<Administador> listaAdministrador) {
        this.listaAdministrador = listaAdministrador;
    }

    public List<Clase> getListaClases() {
        return listaClases;
    }

    public void setListaClases(List<Clase> listaClases) {
        this.listaClases = listaClases;
    }

    public List<Entrenador> getListaEntrenador() {
        return listaEntrenador;
    }

    public void setListaEntrenador(List<Entrenador> listaEntrenador) {
        this.listaEntrenador = listaEntrenador;
    }

    // CRUD de Usuario
    public boolean agregarUsuario(Usuario usuario) {
        if (usuario != null && !existeUsuario(usuario.getIdentificacion())) {
            return listaUsuarios.add(usuario);
        }
        return false;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        Usuario usuarioExistente = buscarUsuarioPorIdentificacion(identificacion);
        if (usuarioExistente != null && usuarioActualizado != null) {
            int index = listaUsuarios.indexOf(usuarioExistente);
            listaUsuarios.set(index, usuarioActualizado);
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario(String identificacion) {
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacion);
        if (usuario != null) {
            return listaUsuarios.remove(usuario);
        }
        return false;
    }

    public Usuario buscarUsuarioPorIdentificacion(String identificacion) {
        return listaUsuarios.stream()
                .filter(u -> u.getIdentificacion().equals(identificacion))
                .findFirst()
                .orElse(null);
    }

    public boolean existeUsuario(String identificacion) {
        return buscarUsuarioPorIdentificacion(identificacion) != null;
    }
}
