package gimnasiouq.gimnasiouq.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GimnasioUQ {

    String nombre;
    String direccion;

    private List<Usuario> listaUsuarios;
    private List<Recepcionista> listaRecepcionista;
    private List<Administador> listaAdministrador;
    private List<ReservaClase> listaReservaClases;
    private List<Entrenador> listaEntrenador;
    private List<ControlAcceso> listaRegistrosAcceso;

    public GimnasioUQ() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRecepcionista = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();
        this.listaReservaClases = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = new ArrayList<>();
    }

    private LocalDate parseFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
            } catch (DateTimeParseException ex) {
                return null;
            }
        }
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

    public List<ReservaClase> getListaReservaClases() {
        return listaReservaClases;
    }

    public void setListaClases(List<ReservaClase> listaReservaClases) {
        this.listaReservaClases = listaReservaClases;
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

        // Regla de negocio: Solo Premium y VIP pueden reservar clases.
        if ("Basica".equalsIgnoreCase(usuario.getTipoMembresia())) {
            return false;
        }

        long count = obtenerReservasDeUsuarios().stream().filter(r -> r.getClase().equals(reserva.getClase())).count();
        if (count >= reserva.getCupoMaximo()) {
            return false;
        }

        if (!usuario.tieneMembresiaActiva()) return false;

        LocalDate inicio = usuario.getFechaInicioMembresia();
        LocalDate fin = usuario.getFechaFinMembresia();

        LocalDate fecha = parseFecha(reserva.getFecha());
        if (fecha == null) return false;

        if (inicio == null || fin == null) return false;
        if (fecha.isBefore(inicio) || fecha.isAfter(fin)) return false;

        return usuario.getReservas().add(reserva);
    }

    public boolean actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (!validarReserva(reserva)) return false;
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;

        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;

        // Regla de negocio: Solo Premium y VIP pueden reservar clases.
        if ("Basica".equalsIgnoreCase(usuario.getTipoMembresia())) {
            return false;
        }

        LocalDate inicio = usuario.getFechaInicioMembresia();
        LocalDate fin = usuario.getFechaFinMembresia();

        LocalDate fecha = parseFecha(reserva.getFecha());
        if (fecha == null) return false;

        if (inicio == null || fin == null) return false;
        if (fecha.isBefore(inicio) || fecha.isAfter(fin)) return false;

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

    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoMembresia, Usuario usuario) {
        if (tipoPlan == null || tipoPlan.isEmpty() || usuario == null) return null;

        java.time.LocalDate fechaInicio = java.time.LocalDate.now();
        java.time.LocalDate fechaFin;
        double costo = 0;

        String plan = tipoPlan.trim().toLowerCase();
        String tier = (tipoMembresia == null || tipoMembresia.isBlank()) ? "basica" : tipoMembresia.trim().toLowerCase();

        switch (plan) {
            case "mensual" -> fechaFin = fechaInicio.plusMonths(1);
            case "trimestral" -> fechaFin = fechaInicio.plusMonths(3);
            case "anual" -> fechaFin = fechaInicio.plusYears(1);
            default -> {
                return null;
            }
        }

        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            switch (plan) {
                case "mensual" -> costo = 10000;
                case "trimestral" -> costo = 30000;
                case "anual" -> costo = 100000;
            }
        } else if (tier.equals("premium")) {
            switch (plan) {
                case "mensual" -> costo = 15000;
                case "trimestral" -> costo = 40000;
                case "anual" -> costo = 150000;
            }
        } else if (tier.equals("vip")) {
            switch (plan) {
                case "mensual" -> costo = 20000;
                case "trimestral" -> costo = 50000;
                case "anual" -> costo = 200000;
            }
        }

        // Aplicar descuentos según el tipo de usuario
        if (usuario instanceof Estudiante) {
            costo *= 0.90; // 10% de descuento
        } else if (usuario instanceof TrabajadorUQ) {
            costo *= 0.80; // 20% de descuento
        }

        // Crear y devolver la membresía correcta
        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            return new MembresiaBasica(costo, fechaInicio, fechaFin);
        } else if (tier.equals("premium")) {
            return new MembresiaPremium(costo, fechaInicio, fechaFin);
        } else if (tier.equals("vip")) {
            return new MembresiaVIP(costo, fechaInicio, fechaFin);
        }

        return new MembresiaBasica(costo, fechaInicio, fechaFin);
    }

    public int contarMembresiasTotales() {
        return (int) listaUsuarios.stream()
                .filter(u -> (u.getTipoMembresia() != null && !u.getTipoMembresia().isBlank())
                        || u.getMembresiaActiva() != null)
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
                .filter(u -> (u.getTipoMembresia() != null && !u.getTipoMembresia().isBlank()))
                .filter(u -> u.getMembresiaActiva() == null)
                .count();
    }

    public double calcularIngresosTotalesMembresias() {
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

    public String contarClaseMasReservada() {
        Map<String, Long> conteoClases = obtenerReservasDeUsuarios().stream()
                .map(ReservaClase::getClase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return conteoClases.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Ninguna");
    }

    public int contarTotalClasesReservadas() {
        return obtenerReservasDeUsuarios().size();
    }
}