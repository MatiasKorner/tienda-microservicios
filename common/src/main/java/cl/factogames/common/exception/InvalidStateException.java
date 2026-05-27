package cl.factogames.common.exception;

public class InvalidStateException extends RuntimeException {

    /**
     * Para operaciones sobre entidades en un estado inválido para dicha operación.
     * @param entity    Nombre de la entidad (ej: "Carrito")
     * @param id        Identificador de la entidad
     * @param actual    Estado actual de la entidad (ej: "ABANDONADO")
     * @param required  Estado requerido para la operación (ej: "ACTIVO")
     */
    public InvalidStateException(String entity, Object id, String actual, String required) {
        super(String.format("No se puede operar sobre %s con ID '%s': "
            + "estado actual es '%s', se requiere '%s'.",
            entity, id.toString(), actual, required));
    }
}