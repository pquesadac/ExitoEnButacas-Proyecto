/**
 * Enumeración que define los posibles estados de un asiento:
 * - LIBRE: El asiento está disponible para ser reservado.
 * - RESERVADO: El asiento ha sido reservado, pero no comprado.
 * - VENDIDO: El asiento ya ha sido comprado.
 */

package model;

public enum EstadoAsiento {
    LIBRE, RESERVADO, VENDIDO
}
