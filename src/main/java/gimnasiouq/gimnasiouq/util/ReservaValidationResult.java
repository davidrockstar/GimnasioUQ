package gimnasiouq.gimnasiouq.util;

public enum ReservaValidationResult {
    EXITO,
    FORMATO_FECHA_INVALIDO,
    FECHA_EN_PASADO,
    EXCEDE_MAXIMO_RESERVAS,
    USUARIO_NO_ENCONTRADO,
    MEMBRESIA_INACTIVA,
    FECHA_FUERA_MEMBRESIA,
    DATOS_RESERVA_INVALIDOS, // Para validaciones generales (nulos, cadenas vacías)
    ERROR_DESCONOCIDO
}
