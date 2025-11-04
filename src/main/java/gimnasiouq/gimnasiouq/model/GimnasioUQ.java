package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        if (!validarEntrenador(entrenador)) {
            return false;
        }
        if (!existeEntrenador(entrenador.getIdentificacion())) {
            return listaEntrenador.add(entrenador);
        }
        return false;
    }

    private boolean existeEntrenador(String identificacion) {
        return buscarEntrenadorPorIdentificacion(identificacion) != null;
    }

    public Entrenador buscarEntrenadorPorIdentificacion(String identificacion) {
        return listaEntrenador.stream()
                .filter(u -> u.getIdentificacion().equals(identificacion))
                .findFirst()
                .orElse(null);
    }

    private boolean validarEntrenador(Entrenador entrenador) {
        if (entrenador == null) return false;
        if (entrenador.getNombre() == null || entrenador.getNombre().isEmpty()) return false;
        if (entrenador.getIdentificacion() == null || entrenador.getIdentificacion().isEmpty()) return false;
        if (entrenador.getEspecialidad() == null || entrenador.getEspecialidad().isEmpty()) return false;
        if (entrenador.getCorreo() == null || entrenador.getCorreo().isEmpty()) return false;
        if (entrenador.getCelular() == null || entrenador.getCelular().isEmpty()) return false;
        return true;
    }

    public boolean actualizarEntrenador(String identificacion, Entrenador entrenadorActualizado) {
        if (identificacion == null || identificacion.isEmpty()) {
            return false;
        }
        if (!validarEntrenador(entrenadorActualizado)) {
            return false;
        }
        Entrenador entrenadorExistente = buscarEntrenadorPorIdentificacion(identificacion);

        if (entrenadorExistente != null) {
            // Actualizar los datos básicos
            entrenadorExistente.setNombre(entrenadorActualizado.getNombre());
            entrenadorExistente.setIdentificacion(entrenadorActualizado.getIdentificacion());
            entrenadorExistente.setEspecialidad(entrenadorActualizado.getEspecialidad());
            entrenadorExistente.setCorreo(entrenadorActualizado.getCorreo());
            entrenadorExistente.setCelular(entrenadorActualizado.getCelular());
            if (entrenadorActualizado.getClasesDisponibles() != null) {
                entrenadorExistente.setClasesDisponibles(entrenadorActualizado.getClasesDisponibles());
            }

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

    private boolean validarReserva(ReservaClase reserva) {
        if (reserva == null) return false;
        if (reserva.getClase() == null || reserva.getClase().isEmpty()) return false;
        if (reserva.getHorario() == null || reserva.getHorario().isEmpty()) return false;
        if (reserva.getFecha() == null) return false;
        if (reserva.getIdentificacion() == null || reserva.getIdentificacion().isEmpty()) return false;
        return true;
    }

    public boolean agregarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (!validarReserva(reserva)) return false;
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;

        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        if (!usuario.tieneMembresiaActiva()) return false;

        LocalDate inicio = usuario.getFechaInicioMembresia();
        LocalDate fin = usuario.getFechaFinMembresia();

        // Intentar parsear la fecha con múltiples formatos
        LocalDate fecha = null;
        try {
            fecha = LocalDate.parse(reserva.getFecha()); // Formato ISO: yyyy-MM-dd
        } catch (Exception e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(reserva.getFecha(), formatter);
            } catch (Exception ex) {
                return false; // Si no se puede parsear, retornar false
            }
        }

        if (inicio == null || fin == null || fecha == null) return false;
        if (fecha.isBefore(inicio) || fecha.isAfter(fin)) return false;

        return usuario.getReservas().add(reserva);
    }

    public boolean actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (!validarReserva(reserva)) return false;
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;
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

    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoUsuario) {
        if (tipoPlan == null || tipoPlan.isEmpty()) return null;

        java.time.LocalDate fechaInicio = java.time.LocalDate.now();
        java.time.LocalDate fechaFin;
        double costo = 0;

        // Normalizar strings
        String plan = tipoPlan.trim().toLowerCase();
        String tipo = (tipoUsuario == null || tipoUsuario.isBlank()) ? "basica" : tipoUsuario.trim().toLowerCase();

        // Duración según plan
        switch (plan) {
            case "mensual" -> fechaFin = fechaInicio.plusMonths(1);
            case "trimestral" -> fechaFin = fechaInicio.plusMonths(3);
            case "anual" -> fechaFin = fechaInicio.plusYears(1);
            default -> {
                return null;
            }
        }

        // Tabla de tarifas por tipo y plan
        // {Básica = Mensual: 10000, Trimestral: 30000, Anual: 100000}
        // {Premium = Mensual: 15000, Trimestral: 40000, Anual: 150000}
        // {VIP = Mensual: 20000, Trimestral: 50000, Anual: 200000}
        if (tipo.equals("basica") || tipo.equalsIgnoreCase("básica")) {
            switch (plan) {
                case "mensual" -> costo = 10000;
                case "trimestral" -> costo = 30000;
                case "anual" -> costo = 100000;
            }
            return new MembresiaBasica(costo, fechaInicio, fechaFin);
        } else if (tipo.equals("premium")) {
            switch (plan) {
                case "mensual" -> costo = 15000;
                case "trimestral" -> costo = 40000;
                case "anual" -> costo = 150000;
            }
            return new MembresiaPremium(costo, fechaInicio, fechaFin);
        } else if (tipo.equals("vip")) {
            switch (plan) {
                case "mensual" -> costo = 20000;
                case "trimestral" -> costo = 50000;
                case "anual" -> costo = 200000;
            }
            return new MembresiaVIP(costo, fechaInicio, fechaFin);
        }

        // Por defecto, devolver MembresiaBasica
        return new MembresiaBasica(costo, fechaInicio, fechaFin);
    }

    public int contarMembresiasTotales() {
        // Ahora contamos como "membresía existente" cuando el usuario tiene un tipo de membresía
        // (p. ej. "Básica", "Premium", "VIP") aunque no tenga un objeto Membresia asignado.
        return (int) listaUsuarios.stream()
                .filter(u -> (u.getTipoMembresia() != null && !u.getTipoMembresia().isBlank())
                        || u.getMembresiaActiva() != null)
                .count();
    }

    public int contarMembresiasConValor() {
        // Membresías que efectivamente tienen un objeto Membresia asignado y un costo > 0
        return (int) listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .filter(u -> u.getCostoMembresia() > 0)
                .count();
    }

    public int contarMembresiasSinValor() {
        // Usuarios que declararon un tipo de membresía (p. ej. seleccionaron Básica/Premium/VIP)
        // pero no se les ha asignado un objeto Membresia aún (membresiaActiva == null)
        return (int) listaUsuarios.stream()
                .filter(u -> (u.getTipoMembresia() != null && !u.getTipoMembresia().isBlank()))
                .filter(u -> u.getMembresiaActiva() == null)
                .count();
    }

    public double calcularIngresosTotalesMembresias() {
        // Sumar el costo de todas las membresías que tienen un objeto Membresia asignado
        return listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .mapToDouble(Usuario::getCostoMembresia)
                .sum();
    }

    public int contarTotalUsuarios() {
        return listaUsuarios.size();
    }

    public int contarMembresiasUsuariosActivas() {
        return (int) listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() != null)
                .count();
    }

    public int contarMembresiasUsuariosInactivas() {
        return (int) listaUsuarios.stream()
                .filter(u -> u.getMembresiaActiva() == null)
                .count();
    }
}
