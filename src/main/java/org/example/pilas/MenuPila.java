package org.example.pilas;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Maneja el submenú de operaciones de la estructura Pila.
 */
public class MenuPila {
    private PilaArreglo pila;

    /**
     * Muestra las opciones que estarán disponibles para trabajar con la pila.
     */
    private void mostrarMenu(){
        System.out.println();

        Color.titulo(
                "  ╔══════════════════════════════════════════════╗"
        );

        Color.titulo(
                "  ║              ESTRUCTURA DE PILAS             ║"
        );

        Color.titulo(
                "  ╚══════════════════════════════════════════════╝"
        );

        System.out.println();

        System.out.println(Color.AZUL + Color.NEGRITA
                + "       SELECCIONE UNA OPERACIÓN:" + Color.RESET);

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET
                + "Push     - Insertar elemento");

        System.out.println(Color.CIAN + "       [2] " + Color.RESET
                + "Pop      - Eliminar elemento");

        System.out.println(Color.CIAN + "       [3] " + Color.RESET
                + "Peek     - Consultar elemento superior");

        System.out.println(Color.CIAN + "       [4] " + Color.RESET
                + "Verificar si está vacía");

        System.out.println(Color.CIAN + "       [5] " + Color.RESET
                + "Verificar si está llena");

        System.out.println(Color.CIAN + "       [6] " + Color.RESET
                + "Consultar tamaño");

        System.out.println(Color.CIAN + "       [7] " + Color.RESET
                + "Mostrar contenido de la pila");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Regresar al menú principal"
                + Color.RESET);

        System.out.println();

    }

    /**
     * Lee un número entero y válida la entrada.
     */
    private int leerEntero(Scanner scanner, String mensaje){
        while(true){
            System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + Color.BLANCO + mensaje +
                                Color.RESET);
            String entrada = scanner.nextLine();

            try{
                return Integer.parseInt(entrada);
            } catch(NumberFormatException e){
                Color.error("Entrada inválida. Por favor ingrese un número entero. ");
            }

        }

    }

    /**
     * Solicita la capacidad inicial de la pila.
     */

    private void iniciarPila(Scanner scanner){
        int capacidad;
        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║               CONFIGURAR PILA                ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        do {
            capacidad = leerEntero(scanner, "Ingrese la capacidad de la pila: ");
            if (capacidad < 1){
                Color.advertencia("Capacidad inválida. La capacidad debe ser mayor que cero. ");
            }
        } while (capacidad < 1);

        pila = new PilaArreglo(capacidad);

        Color.exito("La pila fue creada con éxito. ");
        Color.info("Capacidad establecida: " + capacidad + " elementos.");
    }

    /**
     * Ejecuta submenú de operaciones de pila.
     */
    public void ejecutaMenu(Scanner scanner){

        // La pila la creamos solo una vez
        if (pila == null){
            iniciarPila(scanner);
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Ingrese una opción: ");

            try{
                switch (opcion){
                    case 1:
                        System.out.println();
                        Color.titulo("  OPERACIÓN PUSH - INSERTAR ELEMENTO");
                        int elemento = leerEntero(scanner, "Ingrese el elemento que desea insertar: ");
                        pila.push(elemento);
                        Color.exito("El elemento " + elemento + " se insertó con éxito. ");
                        break;
                    case 2:
                        System.out.println();
                        Color.titulo("  OPERACIÓN POP - ELIMINAR ELEMENTO");
                        Color.exito("Se eliminó el elemento: " +  pila.pop());
                        break;
                    case 3:
                        System.out.println();
                        Color.titulo("  OPERACIÓN PEEK - CONSULTAR TOPE");
                        Color.info("El elemento en la cima de la pila es: " + pila.peek());
                        break;
                    case 4:
                        System.out.println();
                        Color.titulo("  VERIFICACIÓN DE PILA VACÍA");

                        if (pila.estaVacia()){
                            Color.advertencia("La pila está vacía. ");
                        } else {
                            Color.exito("La pila contiene elementos. ");
                        }
                        break;
                    case 5:
                        System.out.println();
                        Color.titulo("  VERIFICACIÓN DE CAPACIDAD");

                        if (pila.estaLlena()){
                            Color.advertencia("La pila alcanzó su capacidad máxima. ");
                        } else {
                            Color.exito("La pila tiene espacio disponible. ");
                        }
                        break;
                    case 6:
                        System.out.println();
                        Color.titulo("  CONSULTAR TAMAÑO DE LA PILA");

                        Color.exito("Cantidad de elementos en la pila: " + pila.size());
                        break;
                    case 7:
                        System.out.println();
                        Color.titulo("  CONTENIDO ACTUAL DE LA PILA");
                        Color.separador();

                        System.out.println(Color.VERDE + Color.NEGRITA + " " + pila + Color.RESET);
                        Color.separador();

                        break;
                    case 0:
                        System.out.println();
                        Color.info("Regresando al menú principal...");
                        break;
                    default:
                        Color.advertencia("Opción inválida. Seleccione una opción del menú.");
                        break;

                }
                if (opcion > 0 && opcion < 4){
                    System.out.println();
                    Color.separador();

                    System.out.println(Color.AZUL + Color.NEGRITA + "  ESTADO ACTUAL DE LA PILA:" + Color.RESET);

                    System.out.println(Color.VERDE + "  " + pila + Color.RESET);

                    Color.separador();
                }

            } catch(IllegalStateException illegalStateException){
                Color.error("No se pudo realizar la operación: " + illegalStateException.getMessage());
            }

        } while (opcion != 0);
    }
}
