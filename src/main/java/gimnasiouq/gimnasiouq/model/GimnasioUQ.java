package gimnasiouq.gimnasiouq.model;

import java.util.ArrayList;
import java.util.List;

public class GimnasioUQ {

    private List<Usuario> listaUsuarios;
    private List<Recepcionista> listaRecepcionista;
    private List<Administador> listaAdministrador;
    private List<Clase> listaClases;
    private List<Entrenador> listaEntrenador;
    private List<ControlAcceso> listaRegistrosAcceso;

    public GimnasioUQ() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRecepcionista = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();
        this.listaClases = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = new ArrayList<>();
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

    public void setListaRegistrosAcceso(List<ControlAcceso> listaRegistrosAcceso) {
        this.listaRegistrosAcceso = listaRegistrosAcceso;
    }

    public boolean agregarUsuario(Usuario usuario) {
        if (usuario != null && !existeUsuario(usuario.getIdentificacion())) {
            return listaUsuarios.add(usuario);
        }
        return false;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        if (identificacion == null || identificacion.isEmpty()) {
            return false;
        }

        Usuario usuarioExistente = buscarUsuarioPorIdentificacion(identificacion);

        if (usuarioExistente != null) {
            if (usuarioActualizado.getNombre() == null ||
                    usuarioActualizado.getIdentificacion() == null ||
                    usuarioActualizado.getEdad() == null ||
                    usuarioActualizado.getCelular() == null) {
                return false;
            }

            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setEdad(usuarioActualizado.getEdad());
            usuarioExistente.setCelular(usuarioActualizado.getCelular());
            usuarioExistente.setTipoMembresia(usuarioActualizado.getTipoMembresia());

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

    public List<ControlAcceso> getListaRegistrosAcceso() {
        return listaRegistrosAcceso;
    }

    public boolean agregarRegistroAcceso(ControlAcceso registro) {
        return listaRegistrosAcceso.add(registro);
    }

    public boolean eliminarRegistroAcceso(ControlAcceso registro) {
        if (registro == null) return false;
        return listaRegistrosAcceso.remove(registro);
    }

    public boolean agregarEntrenador(Entrenador entrenador) {
        if (entrenador != null && !existeEntrenador(entrenador.getIdentificacion())) {
            return listaEntrenador.add(entrenador);
        }
        return false;
    }

    private boolean existeEntrenador(String identificacion) {
        return buscarEntrenadorPorIdentificacion(identificacion) != null;
    }

    private Entrenador buscarEntrenadorPorIdentificacion(String identificacion) {

        return listaEntrenador.stream()
                .filter(u -> u.getIdentificacion().equals(identificacion))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarEntrenador(String identificacion, Entrenador entrenadorActualizado) {
        if (identificacion == null || identificacion.isEmpty()) {
            return false;
        }
        Entrenador entrenadorExistente = buscarEntrenadorPorIdentificacion(identificacion);

        if (entrenadorExistente != null) {
            // Verificar que los datos del usuario actualizado no sean null
            if (entrenadorActualizado.getNombre() == null ||
                    entrenadorActualizado.getIdentificacion() == null ||
                    entrenadorActualizado.getEspecialidad() == null ||
                    entrenadorActualizado.getClasesDisponibles() == null) {
                return false;
            }

            // Actualizar los datos básicos
            entrenadorExistente.setNombre(entrenadorActualizado.getNombre());
            entrenadorExistente.setIdentificacion(entrenadorActualizado.getIdentificacion());
            entrenadorExistente.setEspecialidad(entrenadorActualizado.getEspecialidad());
            entrenadorExistente.setClasesDisponibles(entrenadorActualizado.getClasesDisponibles());

            return true;
        }

        return false;
    }

    public boolean eliminarEntrenador(String identificacion) {
        Entrenador entrenador = buscarEntrenadorPorIdentificacion(identificacion);
        if (entrenador != null) {
            return listaEntrenador.remove(entrenador);
        }
        return false;
    }

    public boolean agregarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || reserva == null) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        return usuario.getReservas().add(reserva);
    }

    public boolean actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || reserva == null) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        if (usuario.getReservas().isEmpty()) {
            usuario.getReservas().add(reserva);
        } else {
            usuario.getReservas().set(0, reserva);
        }
        return true;
    }

    public boolean eliminarReservasUsuario(String identificacionUsuario) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        if (usuario.getReservas() == null || usuario.getReservas().isEmpty()) return false;
        usuario.getReservas().clear();
        return true;
    }

    public List<ReservaClase> obtenerReservasDeUsuarios() {
        List<ReservaClase> reservas = new ArrayList<>();
        for (Usuario u : listaUsuarios) {
            if (u.getReservas() != null && !u.getReservas().isEmpty()) {
                for (ReservaClase r : u.getReservas()) {
                    r.setIdentificacion(u.getIdentificacion());
                    reservas.add(r);
                }
            }
        }
        return reservas;
    }

    public boolean asignarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || membresia == null) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        usuario.setMembresiaActiva(membresia);
        return true;
    }

    public boolean actualizarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        return asignarMembresiaUsuario(identificacionUsuario, membresia);
    }

    public boolean eliminarMembresiaUsuario(String identificacionUsuario) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        if (usuario.getMembresiaActiva() == null) return false;
        usuario.setMembresiaActiva(null);
        return true;
    }

    public Membresia obtenerMembresiaUsuario(String identificacionUsuario) {
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        return usuario != null ? usuario.getMembresiaActiva() : null;
    }

    public Membresia calcularMembresiaPorPlan(String tipoPlan) {
        if (tipoPlan == null || tipoPlan.isEmpty()) return null;

        java.time.LocalDate fechaInicio = java.time.LocalDate.now();
        java.time.LocalDate fechaFin;
        double costo;

        switch (tipoPlan) {
            case "Mensual":
                fechaFin = fechaInicio.plusMonths(1);
                costo = 50000;
                return new MembresiaBasica(costo, fechaInicio, fechaFin);
            case "Trimestral":
                fechaFin = fechaInicio.plusMonths(3);
                costo = 135000;
                return new MembresiaPremium(costo, fechaInicio, fechaFin);
            case "Anual":
                fechaFin = fechaInicio.plusYears(1);
                costo = 540000;
                return new MembresiaVIP(costo, fechaInicio, fechaFin);
            default:
                return null;
        }
    }

    public int contarMembresiasTotales() {
        return (int) listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .count();
    }

    public int contarMembresiasConValor() {
        return (int) listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .filter(u -> u.getCostoMembresia() > 0)
                .count();
    }

    public int contarMembresiasSinValor() {
        return (int) listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .filter(u -> u.getCostoMembresia() == 0)
                .count();
    }

    public double calcularIngresosTotalesMembresias() {
        return listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .mapToDouble(Usuario::getCostoMembresia)
                .sum();
    }
}
