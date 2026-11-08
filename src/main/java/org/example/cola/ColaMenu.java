
package org.example.cola;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Maneja el submenu de operaciones de las estructuras de colas.
 * Permite trabajar con colas simples y circulares.
 */
public class ColaMenu {

    private EstructuraCola cola;

    /*
     * Muestra el menu para seleccionar el tipo de cola.
     */
    private void mostrarMenuTipos() {

        System.out.println();

        Color.titulo(
                "  ╔══════════════════════════════════════════════════════╗"
        );

        Color.titulo(
                "  ║                  ESTRUCTURA DE COLAS                 ║"
        );

        Color.titulo(
                "  ╚══════════════════════════════════════════════════════╝"
        );

        System.out.println();

        System.out.println(Color.AZUL + Color.NEGRITA
                + "             SELECCIONE EL TIPO DE COLA:"
                + Color.RESET);

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET
                + "Cola simple");

        System.out.println(Color.CIAN + "       [2] " + Color.RESET
                + "Cola circular");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Regresar al menu principal"
                + Color.RESET);

        System.out.println();
    }

    /*
     * Muestra el submenu de operaciones.
     */
    private void mostrarMenuOperaciones() {

        System.out.println();

        Color.titulo(
                "  ╔══════════════════════════════════════════════════════╗"
        );

        Color.titulo(
                "  ║                 OPERACIONES DE COLAS                 ║"
        );

        Color.titulo(
                "  ╚══════════════════════════════════════════════════════╝"
        );

        System.out.println();

        System.out.println(Color.AZUL + Color.NEGRITA
                + "             SELECCIONE UNA OPERACION:"
                + Color.RESET);

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET
                + "Enqueue  - Insertar elemento al final");

        System.out.println(Color.CIAN + "       [2] " + Color.RESET
                + "Dequeue  - Eliminar elemento del frente");

        System.out.println(Color.CIAN + "       [3] " + Color.RESET
                + "Front    - Consultar primer elemento");

        System.out.println(Color.CIAN + "       [4] " + Color.RESET
                + "Mostrar  - Visualizar contenido de la cola");

        System.out.println(Color.CIAN + "       [5] " + Color.RESET
                + "Estado   - Consultar capacidad y elementos");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Regresar al menu principal"
                + Color.RESET);

        System.out.println();
    }

    /*
     * Lee un numero entero y valida la entrada.
     */
    private int leerEntero(Scanner scanner, String mensaje) {

        while (true) {

            System.out.print(
                    Color.AMARILLO + Color.NEGRITA
                            + "  ➤ " + Color.RESET
                            + Color.BLANCO + mensaje + Color.RESET
            );

            String entrada = scanner.nextLine();

            try {

                return Integer.parseInt(entrada.trim());

            } catch (NumberFormatException e) {

                Color.error(
                        "Entrada invalida. Ingrese un numero entero."
                );
            }
        }
    }

    /*
     * Configura el tipo y la capacidad de la cola.
     */
    private boolean iniciarCola(Scanner scanner) {

        int opcion;

        do {

            mostrarMenuTipos();

            opcion = leerEntero(
                    scanner,
                    "Seleccione el tipo de cola: "
            );

            if (opcion < 0 || opcion > 2) {

                Color.advertencia(
                        "Opcion invalida. Seleccione 1, 2 o 0."
                );
            }

        } while (opcion < 0 || opcion > 2);

        if (opcion == 0) {
            return false;
        }

        System.out.println();

        Color.titulo(
                "  ╔══════════════════════════════════════════════════════╗"
        );

        Color.titulo(
                "  ║                  CONFIGURAR COLA                     ║"
        );

        Color.titulo(
                "  ╚══════════════════════════════════════════════════════╝"
        );

        int capacidad;

        do {

            capacidad = leerEntero(
                    scanner,
                    "Ingrese la capacidad de la cola: "
            );

            if (capacidad < 1 || capacidad > 15) {

                Color.advertencia(
                        "La capacidad debe estar entre 1 y 15."
                );
            }

        } while (capacidad < 1 || capacidad > 15);

        if (opcion == 1) {

            cola = new ColaSimple(capacidad);

        } else {

            cola = new ColaCircular(capacidad);
        }

        Color.exito(
                "La " + cola.tipo() + " fue creada correctamente."
        );

        Color.info(
                "Capacidad establecida: " + capacidad + " elementos."
        );

        return true;
    }

    /*
     * Representacion grafica de la cola.
     */
    private void mostrarCola() {

        System.out.println();

        Color.titulo(
                "  ╔══════════════════════════════════════════════════════════════════╗"
        );

        Color.titulo(
                "  ║                    REPRESENTACION DE LA COLA                    ║"
        );

        Color.titulo(
                "  ╚══════════════════════════════════════════════════════════════════╝"
        );

        System.out.println();

        System.out.println(
                Color.AZUL + Color.NEGRITA
                        + "  TIPO DE ESTRUCTURA: "
                        + Color.RESET + Color.BLANCO + cola.tipo()
                        + Color.RESET
        );

        System.out.println(
                Color.CIAN + "  CAPACIDAD: " + Color.RESET
                        + cola.capacidad()
                        + Color.CIAN + "       ELEMENTOS: " + Color.RESET
                        + cola.size()
        );

        System.out.println();

        if (cola.estaVacia()) {

            Color.advertencia(
                    "La cola esta vacia. No existen elementos para mostrar."
            );

        } else {

            System.out.println(
                    Color.VERDE + Color.NEGRITA
                            + "                <-- SALE             ENTRA -->"
                            + Color.RESET
            );

        }

        String[] vista = cola.obtenerVista();

        // Borde superior
        System.out.print("  ");

        for (int i = 0; i < cola.capacidad(); i++) {
            System.out.print("+-------------");
        }

        System.out.println("+");

        // Contenido de las posiciones
        System.out.print("  ");

        for (int i = 0; i < cola.capacidad(); i++) {

            String valor = vista[i];

            if (valor == null || valor.isBlank()) {
                valor = "LIBRE";
            }

            System.out.print("|");

            if (i == cola.indiceFrente()) {

                System.out.print(Color.VERDE + Color.NEGRITA);

            } else if (i == cola.indiceFinal()) {

                System.out.print(Color.AMARILLO + Color.NEGRITA);

            } else {

                System.out.print(Color.BLANCO);
            }

            System.out.printf("%13s", valor);

            System.out.print(Color.RESET);
        }

        System.out.println("|");

        // Borde inferior
        System.out.print("  ");

        for (int i = 0; i < cola.capacidad(); i++) {
            System.out.print("+-------------");
        }

        System.out.println("+");

        // Indices del arreglo
        System.out.print("  ");

        for (int i = 0; i < cola.capacidad(); i++) {

            System.out.printf("|%13d", i);
        }

        System.out.println("|");

        System.out.println();

        // Indicadores de frente y final
        System.out.println(
                Color.VERDE + Color.NEGRITA
                        + "  FRENTE: " + Color.RESET
                        + (cola.estaVacia() ? "Sin elementos" : cola.indiceFrente())
        );

        System.out.println(
                Color.AMARILLO + Color.NEGRITA
                        + "  FINAL:  " + Color.RESET
                        + (cola.estaVacia() ? "Sin elementos" : cola.indiceFinal())
        );

        System.out.println();

        // Estado de la estructura
        if (cola.estaVacia()) {

            Color.advertencia("ESTADO: COLA VACIA");

        } else if (cola.estaLlena()) {

            Color.advertencia("ESTADO: COLA LLENA");

        } else {

            Color.exito("ESTADO: COLA DISPONIBLE");
        }

        // Informacion adicional para cola circular
        if (cola instanceof ColaCircular) {

            Color.info(
                    "Avance circular: (indice + 1) % capacidad"
            );
        }

        // Informacion adicional para cola simple
        if (cola instanceof ColaSimple
                && !cola.estaLlena()
                && cola.indiceFrente() > 0) {

            Color.advertencia(
                    "Existen posiciones libres al inicio que no se reutilizan."
            );
        }

        System.out.println();

        Color.separador();
    }

    /*
     * Muestra informacion del estado de la cola.
     */
    private void mostrarEstado() {

        System.out.println();

        Color.titulo("  ESTADO ACTUAL DE LA COLA");

        Color.separador();

        Color.info("Tipo de cola: " + cola.tipo());

        Color.info("Capacidad maxima: " + cola.capacidad());

        Color.info("Elementos almacenados: " + cola.size());

        Color.info(
                "Espacios disponibles: "
                        + (cola.capacidad() - cola.size())
        );

        if (cola.estaVacia()) {

            Color.advertencia("La cola se encuentra vacia.");

        } else if (cola.estaLlena()) {

            Color.advertencia("La cola alcanzo su capacidad maxima.");

        } else {

            Color.exito("La cola tiene espacio disponible.");
        }

        Color.separador();
    }

    /*
     * Ejecuta el submenu de operaciones de colas.
     */
    public void ejecutaMenu(Scanner scanner) {

        // Seleccionar y crear la estructura.
        if (!iniciarCola(scanner)) {
            return;
        }

        int opcion;

        do {

            mostrarCola();

            mostrarMenuOperaciones();

            opcion = leerEntero(
                    scanner,
                    "Ingrese una opcion: "
            );

            try {

                switch (opcion) {

                    case 1:

                        System.out.println();

                        Color.titulo(
                                "  OPERACION ENQUEUE - INSERTAR ELEMENTO"
                        );

                        int elemento = leerEntero(
                                scanner,
                                "Ingrese el elemento que desea insertar: "
                        );

                        cola.enqueue(elemento);

                        Color.exito(
                                "El elemento " + elemento
                                        + " se inserto correctamente."
                        );

                        break;

                    case 2:

                        System.out.println();

                        Color.titulo(
                                "  OPERACION DEQUEUE - ELIMINAR ELEMENTO"
                        );

                        int eliminado = cola.dequeue();

                        Color.exito(
                                "Se elimino el elemento: " + eliminado
                        );

                        break;

                    case 3:

                        System.out.println();

                        Color.titulo(
                                "  OPERACION FRONT - CONSULTAR FRENTE"
                        );

                        Color.info(
                                "El siguiente elemento en salir es: "
                                        + cola.front()
                        );

                        break;

                    case 4:

                        mostrarCola();

                        break;

                    case 5:

                        mostrarEstado();

                        break;

                    case 0:

                        Color.info(
                                "Regresando al menu principal..."
                        );

                        break;

                    default:

                        Color.advertencia(
                                "Opcion invalida. Seleccione una opcion del menu."
                        );

                        break;
                }

                // Mostrar el estado actualizado despues de operar.
                if (opcion >= 1 && opcion <= 3) {

                    mostrarCola();
                }

                if (opcion >= 1 && opcion <= 5) {

                    System.out.println();

                    System.out.print(
                            Color.AMARILLO
                                    + "Presione ENTER para continuar..."
                                    + Color.RESET
                    );

                    scanner.nextLine();
                }

            } catch (IllegalStateException e) {

                Color.error(
                        "No se pudo realizar la operacion: "
                                + e.getMessage()
                );

                System.out.println();

                System.out.print(
                        Color.AMARILLO
                                + "Presione ENTER para continuar..."
                                + Color.RESET
                );

                scanner.nextLine();
            }

        } while (opcion != 0);
    }
}
