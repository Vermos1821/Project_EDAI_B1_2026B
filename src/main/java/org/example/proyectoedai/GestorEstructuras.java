package org.example.proyectoedai;

import org.example.array.ArrayMenu;
import org.example.bicola.MenuBicola;
import org.example.cola.ColaMenu;
import org.example.listas.MenuLista;
import org.example.pilas.MenuPila;
import org.example.recursion.MenuRecursion;
import org.example.util.Color;

import java.util.Scanner;

/**
 * Gestiona el menú principal y la integración de
 * los diferentes módulos de estructuras de datos.
 *
 * Cada módulo conserva su propia implementación.
 */
public class GestorEstructuras {

    private final Scanner scanner;

    private final ArrayMenu arrayMenu;
    private final MenuPila menuPila;
    private final ColaMenu colaMenu;
    private final MenuRecursion menuRecursion;

    /**
     * Inicializa el gestor con un Scanner compartido.
     *
     * @param scanner Lector utilizado por toda la aplicación.
     */
    public GestorEstructuras(Scanner scanner) {
        this.scanner = scanner;

        this.arrayMenu = new ArrayMenu();
        this.menuPila = new MenuPila();
        this.colaMenu = new ColaMenu();
        this.menuRecursion = new MenuRecursion();
    }

    /**
     * Muestra las opciones disponibles.
     */
    private void mostrarMenu() {

        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║            ESTRUCTURAS DE DATOS              ║");
        Color.titulo("  ║                 MENÚ PRINCIPAL               ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        System.out.println();

        System.out.println(Color.AZUL + Color.NEGRITA
                + "       SELECCIONE UNA ESTRUCTURA:"
                + Color.RESET);

        Color.separador();

        System.out.println(Color.CIAN + "       [1] "
                + Color.RESET + "Arrays unidimensionales");

        System.out.println(Color.CIAN + "       [2] "
                + Color.RESET + "Pilas");

        System.out.println(Color.CIAN + "       [3] "
                + Color.RESET + "Colas simples y circulares");

        System.out.println(Color.CIAN + "       [4] "
                + Color.RESET + "Bicolas");

        System.out.println(Color.CIAN + "       [5] "
                + Color.RESET + "Recursión");

        System.out.println(Color.CIAN + "       [6] "
                + Color.RESET + "Listas simplemente enlazadas");

        Color.separador();

        System.out.println(Color.MORADO + "       [7] "
                + Color.RESET + "Información y ayuda");

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Salir del programa"
                + Color.RESET);

        System.out.println();
    }

    /**
     * Lee y valida una opción numérica.
     *
     * @return Opción ingresada por el usuario.
     */
    private int leerOpcion() {

        while (true) {

            System.out.print(Color.AMARILLO
                    + Color.NEGRITA
                    + "  ➤ "
                    + Color.RESET
                    + "Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {
                Color.error("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    /**
     * Ejecuta el menú principal y redirige al
     * módulo seleccionado por el usuario.
     */
    public void ejecutar() {

        int opcion;

        do {

            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {

                case 1:
                    arrayMenu.ejecutarMenu(scanner);
                    break;

                case 2:
                    menuPila.ejecutaMenu(scanner);
                    break;

                case 3:
                    colaMenu.ejecutaMenu(scanner);
                    break;

                case 4:
                    MenuBicola.ejecutar(scanner);
                    break;

                case 5:
                    menuRecursion.ejecutaMenu(scanner);
                    break;

                case 6:
                    MenuLista.ejecutar(scanner);
                    break;

                case 7:
                    InformacionEstructuras.mostrar();
                    break;

                case 0:
                    System.out.println();
                    Color.exito("Gracias por utilizar el programa.");
                    Color.info("Finalizando la aplicación...");
                    break;

                default:
                    Color.advertencia(
                            "Opción inválida. Seleccione una opción entre 0 y 7."
                    );
                    break;
            }

        } while (opcion != 0);
    }
}