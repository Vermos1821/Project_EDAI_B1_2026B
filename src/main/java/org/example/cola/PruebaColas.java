
package org.example.cola;

import java.util.Scanner;

public class PruebaColas {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Crear el menu de colas
        ColaMenu menu = new ColaMenu();

        // Ejecutar el submenu
        menu.ejecutaMenu(scanner);

        System.out.println("Modulo de colas finalizado.");
    }
}
