
package gimnasiouq.gimnasiouq.model;

import java.util.ArrayList;
import java.util.List;

public class GimnasioUQ {

    private List<Usuario> listaUsuarios;
    private List<Recepcionista> listaRecepcionista;
    private List<Administador> listaAdministrador;
    private List<Clase> listaClases;
    private List<Entrenador> listaEntrenador;
    private List<RegistroAcceso> listaRegistrosAcceso = new ArrayList<>();

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
        // Verificar que la identificación no sea null
        if (identificacion == null || identificacion.isEmpty()) {
            return false;
        }

        // Buscar el usuario por identificación
        Usuario usuarioExistente = buscarUsuarioPorIdentificacion(identificacion);

        if (usuarioExistente != null) {
            // Verificar que los datos del usuario actualizado no sean null
            if (usuarioActualizado.getNombre() == null ||
                    usuarioActualizado.getIdentificacion() == null ||
                    usuarioActualizado.getEdad() == null ||
                    usuarioActualizado.getCelular() == null) {
                return false;
            }

            // Actualizar los datos básicos
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setEdad(usuarioActualizado.getEdad());
            usuarioExistente.setCelular(usuarioActualizado.getCelular());
            usuarioExistente.setTipoMembresia(usuarioActualizado.getTipoMembresia());
            
            // ⭐ Actualizar la membresía completa si existe
            if (usuarioActualizado.getMembresiaObj() != null) {
                usuarioExistente.setMembresiaObj(usuarioActualizado.getMembresiaObj());
            }
            
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

    public List<RegistroAcceso> getListaRegistrosAcceso() {
        return listaRegistrosAcceso;
    }

    public boolean agregarRegistroAcceso(RegistroAcceso registro) {
        return listaRegistrosAcceso.add(registro);
    }
}