package gimnasiouq.gimnasiouq.model;

import gimnasiouq.gimnasiouq.util.ReservaValidationResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GimnasioUQ {

    private final String nombre;
    private final String direccion;

    private Administrador administrador;
    private Recepcionista recepcionista;
    private List<Usuario> listaUsuarios;
    private List<ReservaClase> listaReservaClases;
    private List<Entrenador> listaEntrenador;
    private List<ControlAcceso> listaRegistrosAcceso;

    public GimnasioUQ(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.listaUsuarios = new ArrayList<>();
        this.listaReservaClases = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = new ArrayList<>();
    }

    private LocalDate parseFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<ReservaClase> getListaReservaClases() {
        return listaReservaClases;
    }

    public void setListaReservaClases(List<ReservaClase> listaReservaClases) {
        this.listaReservaClases = listaReservaClases;
    }

    public List<Entrenador> getListaEntrenador() {
        return listaEntrenador;
    }

    public void setListaEntrenador(List<Entrenador> listaEntrenador) {
        this.listaEntrenador = listaEntrenador;
    }

    public List<ControlAcceso> getListaRegistrosAcceso() {
        return listaRegistrosAcceso;
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
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdentificacion().equals(identificacion)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean existeUsuario(String identificacion) {
        return buscarUsuarioPorIdentificacion(identificacion) != null;
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
        for (Entrenador entrenador : listaEntrenador) {
            if (entrenador.getIdentificacion().equals(identificacion)) {
                return entrenador;
            }
        }
        return null;
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
        return reserva != null &&
               reserva.getClase() != null && !reserva.getClase().isEmpty() &&
               reserva.getHorario() != null && !reserva.getHorario().isEmpty() &&
               reserva.getFecha() != null &&
               reserva.getIdentificacion() != null && !reserva.getIdentificacion().isEmpty();
    }

    public ReservaValidationResult agregarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (!validarReserva(reserva) || identificacionUsuario == null || identificacionUsuario.isEmpty()) {
            return ReservaValidationResult.DATOS_RESERVA_INVALIDOS;
        }

        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) {
            return ReservaValidationResult.USUARIO_NO_ENCONTRADO;
        }

        LocalDate fecha = parseFecha(reserva.getFecha());
        if (fecha == null) {
            return ReservaValidationResult.FORMATO_FECHA_INVALIDO;
        }

        if (fecha.isBefore(LocalDate.now())) {
            return ReservaValidationResult.FECHA_EN_PASADO;
        }

        int count = 0;
        for (ReservaClase r : listaReservaClases) {
            if (r.getClase().equals(reserva.getClase())) {
                count++;
            }
        }
        if (count >= 3) {
            return ReservaValidationResult.EXCEDE_MAXIMO_RESERVAS;
        }

        if (!usuario.tieneMembresiaActiva()) {
            return ReservaValidationResult.MEMBRESIA_INACTIVA;
        }

        LocalDate inicioMembresia = usuario.getFechaInicioMembresia();
        LocalDate finMembresia = usuario.getFechaFinMembresia();

        if (inicioMembresia == null || finMembresia == null || fecha.isBefore(inicioMembresia) || fecha.isAfter(finMembresia)) {
            return ReservaValidationResult.FECHA_FUERA_MEMBRESIA;
        }

        if (listaReservaClases.add(reserva)) {
            return ReservaValidationResult.EXITO;
        }
        return ReservaValidationResult.ERROR_DESCONOCIDO;
    }

    public ReservaValidationResult actualizarReservaUsuario(String identificacionUsuario, ReservaClase reserva) {
        if (!validarReserva(reserva) || identificacionUsuario == null || identificacionUsuario.isEmpty()) {
            return ReservaValidationResult.DATOS_RESERVA_INVALIDOS;
        }

        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) {
            return ReservaValidationResult.USUARIO_NO_ENCONTRADO;
        }

        LocalDate fecha = parseFecha(reserva.getFecha());
        if (fecha == null) {
            return ReservaValidationResult.FORMATO_FECHA_INVALIDO;
        }

        if (fecha.isBefore(LocalDate.now())) {
            return ReservaValidationResult.FECHA_EN_PASADO;
        }

        if (!usuario.tieneMembresiaActiva()) {
            return ReservaValidationResult.MEMBRESIA_INACTIVA;
        }

        LocalDate inicioMembresia = usuario.getFechaInicioMembresia();
        LocalDate finMembresia = usuario.getFechaFinMembresia();

        if (inicioMembresia == null || finMembresia == null || fecha.isBefore(inicioMembresia) || fecha.isAfter(finMembresia)) {
            return ReservaValidationResult.FECHA_FUERA_MEMBRESIA;
        }

        boolean updated = false;
        for (int i = 0; i < listaReservaClases.size(); i++) {
            if (listaReservaClases.get(i).getIdentificacion().equals(identificacionUsuario)) {
                listaReservaClases.set(i, reserva);
                updated = true;
                break;
            }
        }

        if (updated) {
            return ReservaValidationResult.EXITO;
        } else {
            return agregarReservaUsuario(identificacionUsuario, reserva);
        }
    }

    public boolean eliminarReservasUsuario(String identificacionUsuario) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;
        List<ReservaClase> aEliminar = new ArrayList<>();
        for (ReservaClase reserva : listaReservaClases) {
            if (reserva.getIdentificacion().equals(identificacionUsuario)) {
                aEliminar.add(reserva);
            }
        }
        return listaReservaClases.removeAll(aEliminar);
    }

    public List<ReservaClase> obtenerReservasDeUsuarios() {
        return new ArrayList<>(listaReservaClases);
    }

    public boolean asignarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || !validarMembresia(membresia)) return false;
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

    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoMembresia, Usuario usuario) {
        if (tipoPlan == null || tipoPlan.isEmpty() || usuario == null) return null;

        java.time.LocalDate fechaInicio = java.time.LocalDate.now();
        java.time.LocalDate fechaFin;
        double costo = 0;

        String plan = tipoPlan.trim().toLowerCase();
        String tier = (tipoMembresia == null || tipoMembresia.isBlank()) ? "basica" : tipoMembresia.trim().toLowerCase();

        switch (plan) {
            case "mensual":
                fechaFin = fechaInicio.plusMonths(1);
                break;
            case "trimestral":
                fechaFin = fechaInicio.plusMonths(3);
                break;
            case "anual":
                fechaFin = fechaInicio.plusYears(1);
                break;
            default:
                return null;
        }

        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            switch (plan) {
                case "mensual": costo = 10000; break;
                case "trimestral": costo = 30000; break;
                case "anual": costo = 100000; break;
            }
        } else if (tier.equals("premium")) {
            switch (plan) {
                case "mensual": costo = 15000; break;
                case "trimestral": costo = 40000; break;
                case "anual": costo = 150000; break;
            }
        } else if (tier.equals("vip")) {
            switch (plan) {
                case "mensual": costo = 20000; break;
                case "trimestral": costo = 50000; break;
                case "anual": costo = 200000; break;
            }
        }

        if (usuario instanceof Estudiante) {
            costo *= 0.90; // 10% de descuento
        } else if (usuario instanceof TrabajadorUQ) {
            costo *= 0.80; // 20% de descuento
        }

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
        int count = 0;
        for (Usuario u : listaUsuarios) {
            if ((u.getTipoMembresia() != null && !u.getTipoMembresia().isBlank()) || u.getMembresiaActiva() != null) {
                count++;
            }
        }
        return count;
    }

    public int contarMembresiasConValor() {
        int count = 0;
        for (Usuario u : listaUsuarios) {
            if (u.getMembresiaActiva() != null && u.getCostoMembresia() > 0) {
                count++;
            }
        }
        return count;
    }

    public int contarMembresiasSinValor() {
        int count = 0;
        for (Usuario u : listaUsuarios) {
            if ((u.getTipoMembresia() != null && !u.getTipoMembresia().isBlank()) && u.getMembresiaActiva() == null) {
                count++;
            }
        }
        return count;
    }

    public double calcularIngresosTotalesMembresias() {
        double total = 0;
        for (Usuario u : listaUsuarios) {
            if (u.getMembresiaActiva() != null) {
                total += u.getCostoMembresia();
            }
        }
        return total;
    }

    public int contarTotalUsuarios() {
        return listaUsuarios.size();
    }

    public int contarMembresiasUsuariosActivas() {
        int count = 0;
        for (Usuario u : listaUsuarios) {
            if (u.getMembresiaActiva() != null) {
                count++;
            }
        }
        return count;
    }

    public int contarMembresiasUsuariosInactivas() {
        int count = 0;
        for (Usuario u : listaUsuarios) {
            if (u.getMembresiaActiva() == null) {
                count++;
            }
        }
        return count;
    }

    public String contarClaseMasReservada() {
        if (listaReservaClases.isEmpty()) {
            return "Ninguna";
        }
        Map<String, Integer> conteoClases = new HashMap<>();
        for (ReservaClase reserva : listaReservaClases) {
            String clase = reserva.getClase();
            conteoClases.put(clase, conteoClases.getOrDefault(clase, 0) + 1);
        }

        String claseMasReservada = null;
        int maxReservas = 0;
        for (Map.Entry<String, Integer> entry : conteoClases.entrySet()) {
            if (entry.getValue() > maxReservas) {
                maxReservas = entry.getValue();
                claseMasReservada = entry.getKey();
            }
        }
        return claseMasReservada;
    }

    public int contarTotalClasesReservadas() {
        return listaReservaClases.size();
    }

    public boolean puedeAccederSpa(String identificacion) {
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacion);
        if (usuario == null || usuario.getMembresiaActiva() == null) {
            return false;
        }
        return "VIP".equalsIgnoreCase(usuario.getMembresiaActiva().getTipo());
    }

    public boolean validarIngresoUsuario(String identificacion) {
        Usuario u = buscarUsuarioPorIdentificacion(identificacion);
        return u != null && u.tieneMembresiaActiva();
    }

    public boolean registrarIngresoUsuario(String identificacion) {
        Usuario u = buscarUsuarioPorIdentificacion(identificacion);
        if (u == null || !u.tieneMembresiaActiva()) {
            return false;
        }

        ControlAcceso registro = new ControlAcceso(
                LocalDate.now(),
                LocalTime.now(),
                u.getNombre(),
                u.getIdentificacion(),
                u.getTipoMembresia(),
                u.getEstadoMembresia()
        );
        return agregarRegistroAcceso(registro);
    }

    public boolean validarMembresia(Membresia membresia) {
        if (membresia == null) return false;
        if (membresia.getInicio() == null || membresia.getFin() == null) return false;
        if (membresia.getInicio().isAfter(membresia.getFin())) return false;
        if (membresia.getCosto() < 0) return false;
        return true;
    }
}
