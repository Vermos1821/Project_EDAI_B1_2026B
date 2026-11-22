package org.example.recursion;

import java.util.Scanner;

/**
 * Punto de entrada para probar el módulo de recursividad de forma independiente.
 * En el proyecto completo basta con llamar a
 * {@code new MenuRecursion().ejecutaMenu(scanner)} desde el menú principal.
 */
public class AppRecursion {

    /**
     * Inicia el menú de recursividad.
     *
     * @param args Argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new MenuRecursion().ejecutaMenu(scanner);
        scanner.close();
    }
}
