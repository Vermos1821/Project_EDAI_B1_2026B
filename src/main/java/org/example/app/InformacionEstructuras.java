package org.example.app;

import org.example.util.Color;

/**
 * Proporciona información general sobre las
 * estructuras de datos disponibles en el programa.
 */
public final class InformacionEstructuras {

    private InformacionEstructuras() {
        // Evita crear instancias de esta clase.
    }

    /**
     * Muestra información educativa sobre
     * cada estructura de datos.
     */
    public static void mostrar() {

        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║          INFORMACIÓN Y AYUDA                 ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        System.out.println();

        Color.separador();

        Color.info("1. ARRAYS UNIDIMENSIONALES");

        System.out.println(
                "  Estructura que almacena elementos del mismo tipo."
        );
        System.out.println(
                "  Permite acceder a los elementos mediante índices."
        );
        System.out.println(
                "  Operaciones: recorrer, insertar, eliminar y buscar."
        );

        System.out.println();
        Color.separador();

        Color.info("2. PILAS");

        System.out.println(
                "  Estructura lineal que utiliza el principio LIFO."
        );
        System.out.println(
                "  El último elemento en entrar es el primero en salir."
        );
        System.out.println(
                "  Operaciones: push, pop y peek."
        );

        System.out.println();
        Color.separador();

        Color.info("3. COLAS");

        System.out.println(
                "  Estructura lineal que utiliza el principio FIFO."
        );
        System.out.println(
                "  El primer elemento en entrar es el primero en salir."
        );
        System.out.println(
                "  Tipos disponibles: cola simple y cola circular."
        );
        System.out.println(
                "  Operaciones: enqueue, dequeue y peek."
        );

        System.out.println();
        Color.separador();

        Color.info("4. BICOLAS");

        System.out.println(
                "  Estructura de doble extremo, también llamada deque."
        );
        System.out.println(
                "  Permite insertar y eliminar elementos por ambos extremos."
        );
        System.out.println(
                "  Tipos: general, entrada restringida y salida restringida."
        );

        System.out.println();
        Color.separador();

        Color.info("5. LISTAS SIMPLEMENTE ENLAZADAS");

        System.out.println(
                "  Estructura dinámica formada por nodos enlazados."
        );
        System.out.println(
                "  Cada nodo almacena un dato y una referencia al siguiente."
        );
        System.out.println(
                "  Permite insertar, recorrer, buscar y eliminar nodos."
        );

        System.out.println();
        Color.separador();

        Color.info("6. RECURSIÓN");

        System.out.println(
                "  Técnica de programación en la que un método"
        );
        System.out.println(
                "  se llama a sí mismo para resolver un problema."
        );
        System.out.println(
                "  Necesita un caso base y un caso recursivo."
        );

        System.out.println();
        Color.separador();

        Color.exito("Fin de la información de estructuras.");
        Color.info("Regresando al menú principal...");
    }
}