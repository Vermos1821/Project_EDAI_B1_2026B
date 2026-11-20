package org.example.bicola;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Menu del TAD Bicola. Se invoca desde el menu principal con:  MenuBicola.ejecutar(sc);
 */
public class MenuBicola {

    /** Capacidad máxima permitida, para que el dibujo quepa en la consola. */
    private static final int CAPACIDAD_MAX = 10;

    /** Clase de utilidad: no se instancia, se usa mediante MenuBicola.ejecutar(sc). */
    private MenuBicola() {
    }   // para que el dibujo quepa en consola

    /**
     * Ejecuta el menú del TAD Bicola hasta que el usuario regrese al menú principal.
     *
     * @param sc Scanner compartido con la aplicación para leer los datos.
     */
    public static void ejecutar(Scanner sc) {
        Bicola bicola = null;
        int opcion;

        do {
            mostrarMenu(bicola);
            opcion = leerEntero(sc, "Seleccione una opción: ", 0, 10);

            if (opcion >= 2 && opcion <= 9 && bicola == null) {
                aviso("Primero debe crear la bicola (opción 1).");
                continue;
            }

            switch (opcion) {
                case 1:
                    int cap = leerEntero(sc, "Capacidad N de la bicola (1-" + CAPACIDAD_MAX + "): ",
                            1, CAPACIDAD_MAX);
                    int tipo = leerTipo(sc);
                    boolean pausasPrevias = (bicola != null) && bicola.getPausas();
                    bicola = new Bicola(cap, tipo, sc);
                    bicola.setPausas(pausasPrevias);
                    System.out.println(Color.VERDE + Color.NEGRITA + "\n  ✔ Bicola creada: N=" + cap
                            + ", tipo " + Bicola.nombreTipo(tipo) + "." + Color.RESET);
                    bicola.mostrarEstadoActual();
                    break;
                case 2:
                    bicola.insertFront(leerEntero(sc, "Elemento e a insertar por el frente: ", -9999, 99999));
                    break;
                case 3:
                    bicola.insertRear(leerEntero(sc, "Elemento e a insertar por el final: ", -9999, 99999));
                    break;
                case 4:
                    bicola.deleteFront();
                    break;
                case 5:
                    bicola.deleteRear();
                    break;
                case 6:
                    bicola.consultarExtremos();
                    break;
                case 7:
                    bicola.mostrarContenido();
                    break;
                case 8:
                    bicola.mostrarEstadoActual();
                    break;
                case 9:
                    bicola.setTipo(leerTipo(sc));
                    System.out.println(Color.CIAN + "  ℹ Tipo de bicola cambiado a "
                            + Bicola.nombreTipo(bicola.getTipo()) + " (los elementos se conservan)." + Color.RESET);
                    bicola.mostrarEstadoActual();
                    break;
                case 10:
                    if (bicola == null) {
                        aviso("Cree primero la bicola para activar/desactivar las pausas.");
                    } else {
                        bicola.setPausas(!bicola.getPausas());
                        System.out.println(Color.CIAN + "  ℹ Pausa entre pasos: "
                                + (bicola.getPausas() ? "ACTIVADA" : "DESACTIVADA") + Color.RESET);
                    }
                    break;
                default:
                    break;
            }
        } while (opcion != 0);

        System.out.println(Color.AMARILLO + "\n  Regresando al menú principal..." + Color.RESET);
    }

    /**
     * Muestra las opciones del menú de la bicola.
     *
     * @param b bicola activa, o null si todavía no se ha creado.
     */
    private static void mostrarMenu(Bicola b) {
        System.out.println();
        System.out.println(Color.MORADO + Color.NEGRITA + "  ╔══════════════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.MORADO + Color.NEGRITA + "  ║          BICOLA (DOBLE COLA / DEQUE)         ║" + Color.RESET);
        System.out.println(Color.MORADO + Color.NEGRITA + "  ╚══════════════════════════════════════════════╝" + Color.RESET);
        if (b != null) {
            System.out.println(Color.GRIS + "   Bicola activa: N=" + b.getCapacidad()
                    + " | tipo " + Bicola.nombreTipo(b.getTipo())
                    + " | pausas " + (b.getPausas() ? "ON" : "OFF") + Color.RESET);
        }
        System.out.println("   [1] Crear / reiniciar la bicola");
        System.out.println("   [2] Insertar por el frente   (insertFront)");
        System.out.println("   [3] Insertar por el final    (insertRear)");
        System.out.println("   [4] Eliminar por el frente   (deleteFront)");
        System.out.println("   [5] Eliminar por el final    (deleteRear)");
        System.out.println("   [6] Consultar los extremos   (front y rear)");
        System.out.println("   [7] Recorrer / mostrar el contenido");
        System.out.println("   [8] Ver estado actual y estados de la bicola");
        System.out.println("   [9] Cambiar el tipo (general / entrada restringida / salida restringida)");
        System.out.println("   [10] Activar/desactivar pausa entre pasos");
        System.out.println(Color.AMARILLO + Color.NEGRITA + "   [0] Regresar al menú principal" + Color.RESET);
    }

    /**
     * Muestra los tipos de bicola y solicita que el usuario elija uno.
     *
     * @param sc Scanner utilizado para leer la opción.
     * @return GENERAL, ENTRADA_RESTRINGIDA o SALIDA_RESTRINGIDA.
     */
    private static int leerTipo(Scanner sc) {
        System.out.println("   Tipo de bicola:");
        System.out.println("     [1] General (las cuatro operaciones)");
        System.out.println("     [2] Entrada restringida (no permite insertFront)");
        System.out.println("     [3] Salida restringida (no permite deleteRear)");
        return leerEntero(sc, "Elija el tipo: ", 1, 3);
    }

    /**
     * Lee un entero dentro de un rango, repitiendo la petición hasta que sea válido.
     *
     * @param sc Scanner utilizado para leer.
     * @param mensaje texto que se muestra al solicitar el dato.
     * @param min valor mínimo permitido.
     * @param max valor máximo permitido.
     * @return número entero válido ingresado por el usuario.
     */
    private static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + mensaje);
            String entrada = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor < min || valor > max) {
                    System.out.println(Color.ROJO + "  ✖ Ingrese un valor entre " + min + " y " + max + "."
                            + Color.RESET);
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println(Color.ROJO + "  ✖ Entrada inválida. Ingrese un número entero." + Color.RESET);
            }
        }
    }

    /**
     * Muestra un mensaje de advertencia.
     *
     * @param m texto del mensaje.
     */
    private static void aviso(String m) {
        Color.advertencia(m);
    }
}
