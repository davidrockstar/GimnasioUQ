package gimnasiouq.gimnasiouq.mapping.dto;

import java.time.LocalDate;

// DTO para representar una reserva de clase desde la UI
public record ReservaClaseDto(String clase, String horario, String entrenador, LocalDate fecha, String identificacionUsuario) {
}
