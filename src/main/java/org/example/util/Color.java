
package org.example.util;

/**
 * Centraliza los colores y estilos visuales del proyecto.
 */
public class Color {

    // Restablece el formato
    public static final String RESET = "\u001B[0m";

    // Estilos
    public static final String NEGRITA = "\u001B[1m";

    // Colores principales
    public static final String MORADO = "\u001B[35m";
    public static final String AZUL = "\u001B[34m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CIAN = "\u001B[36m";
    public static final String GRIS = "\u001B[90m";
    public static final String BLANCO = "\u001B[37m";

    /**
     * Muestra un mensaje de éxito.
     */
    public static void exito(String mensaje) {
        System.out.println(VERDE + NEGRITA + " ✔ " + mensaje + RESET);
    }

    /**
     * Muestra un mensaje de error.
     */
    public static void error(String mensaje) {
        System.out.println(ROJO + NEGRITA + " ✖ " + mensaje + RESET);
    }

    /**
     * Muestra un mensaje informativo.
     */
    public static void info(String mensaje) {
        System.out.println(CIAN + " ℹ " + mensaje + RESET);
    }

    /**
     * Muestra una advertencia.
     */
    public static void advertencia(String mensaje) {
        System.out.println(AMARILLO + NEGRITA + " ⚠ " + mensaje + RESET);
    }

    /**
     * Muestra un título.
     */
    public static void titulo(String mensaje) {
        System.out.println(MORADO + NEGRITA + mensaje + RESET);
    }

    /**
     * Muestra un separador.
     */
    public static void separador() {
        System.out.println(GRIS +
                "  ═══════════════════════════════════════════" + RESET);
    }
}
