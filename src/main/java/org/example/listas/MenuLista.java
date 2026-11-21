package org.example.listas;

import java.util.Scanner;
import org.example.util.Color;

/**
 * Menú interactivo de listas simplemente enlazadas.
 * Mantiene la misma interfaz pública que MenuBicola.ejecutar(sc),
 * reutiliza org.example.util.Color y valida todos los enteros ingresados.
 */
public final class MenuLista {
    /** Impide instanciar esta clase de utilidades. */
    private MenuLista() { }

    /**
     * Ejecuta repetidamente el menú hasta seleccionar volver.
     * La lista persiste entre operaciones dentro de esta ejecución del menú.
     * @param sc Scanner compartido con el menú principal (no se cierra aquí)
     */
    public static void ejecutar(Scanner sc) {
        ListaSimple lista = new ListaSimple(sc);
        int opcion;
        do {
            mostrarMenu(lista);
            opcion = leerEntero(sc, "Seleccione una opcion: ");
            switch (opcion) {
                case 1: lista.inicializar(); break;
                case 2: lista.insertarInicio(leerEntero(sc, "Dato a insertar: ")); break;
                case 3: lista.insertarFinal(leerEntero(sc, "Dato a insertar: ")); break;
                case 4:
                    int dato = leerEntero(sc, "Dato a insertar: ");
                    int posicion = leerEntero(sc, "Posicion (0 a " + lista.getCantidad() + "): ");
                    lista.insertarPosicion(dato, posicion);
                    break;
                case 5: lista.recorrer(); break;
                case 6: lista.buscar(leerEntero(sc, "Dato a buscar: ")); break;
                case 7: lista.eliminarValor(leerEntero(sc, "Dato a eliminar: ")); break;
                case 8: lista.eliminarPosicion(leerEntero(sc, "Posicion a eliminar: ")); break;
                case 9: lista.consultarVacia(); break;
                case 10: lista.consultarCantidad(); break;
                case 11: lista.mostrarEnlaces(); break;
                case 12:
                    lista.setPausas(!lista.getPausas());
                    Color.info("Pausas entre pasos: " + (lista.getPausas() ? "ACTIVADAS" : "DESACTIVADAS"));
                    break;
                case 0: Color.info("Regresando al menu principal..."); break;
                default: Color.error("Opcion invalida. Seleccione de 0 a 12.");
            }
        } while (opcion != 0);
    }

    /**
     * Presenta las opciones y el estado resumido de la lista actual.
     * @param lista lista activa
     */
    private static void mostrarMenu(ListaSimple lista) {
        System.out.println();
        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║          LISTAS SIMPLEMENTE ENLAZADAS       ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");
        System.out.println(Color.GRIS + "   Nodos: " + lista.getCantidad() + " | Pausas: "
                + (lista.getPausas() ? "ON" : "OFF") + Color.RESET);
        Color.separador();
        System.out.println(Color.CIAN + "   [1]  " + Color.RESET + "Inicializar / reiniciar lista vacia");
        System.out.println(Color.CIAN + "   [2]  " + Color.RESET + "Insertar al inicio");
        System.out.println(Color.CIAN + "   [3]  " + Color.RESET + "Insertar al final");
        System.out.println(Color.CIAN + "   [4]  " + Color.RESET + "Insertar en una posicion (desde 0)");
        System.out.println(Color.CIAN + "   [5]  " + Color.RESET + "Recorrer y mostrar lista");
        System.out.println(Color.CIAN + "   [6]  " + Color.RESET + "Buscar un valor");
        System.out.println(Color.CIAN + "   [7]  " + Color.RESET + "Eliminar por valor");
        System.out.println(Color.CIAN + "   [8]  " + Color.RESET + "Eliminar por posicion");
        System.out.println(Color.CIAN + "   [9]  " + Color.RESET + "Comprobar si esta vacia");
        System.out.println(Color.CIAN + "   [10] " + Color.RESET + "Consultar cantidad de nodos");
        System.out.println(Color.CIAN + "   [11] " + Color.RESET + "Mostrar enlaces entre nodos");
        System.out.println(Color.CIAN + "   [12] " + Color.RESET + "Activar/desactivar pausas entre pasos");
        Color.separador();
        System.out.println(Color.AMARILLO + Color.NEGRITA + "   [0]  Regresar al menu principal" + Color.RESET);
    }

    /**
     * Solicita un entero hasta que el usuario escriba un valor válido.
     * @param sc lector de entrada
     * @param mensaje texto de solicitud
     * @return entero introducido
     */
    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + mensaje);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                Color.error("Entrada invalida. Ingrese un numero entero.");
            }
        }
    }
}
