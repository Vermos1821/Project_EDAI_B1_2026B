package org.example.bicola;
import java.util.Scanner;

/* =====================================================================
 *  CASO 4 - BICOLA (DOBLE COLA / DEQUE)
 *  Pegar estas dos clases (Bicola y MenuBicola) AL FINAL de ProyectoEDAI.java
 *  (fuera de la clase ProyectoEDAI, son clases independientes del mismo archivo).
 *  Solo se necesita:  import java.util.Scanner;   (arriba del archivo)
 *  Desde el menu principal, opcion 4 ->  MenuBicola.ejecutar(sc);
 *  No depende de ninguna otra clase del proyecto.
 * ===================================================================== */

/**
 * TAD Bicola de enteros con arreglo de capacidad fija y avance CIRCULAR de indices.
 *  - frente : indice del primer elemento (-1 si esta vacia)
 *  - fin    : indice del ultimo elemento (-1 si esta vacia)
 *  - tam    : cantidad de elementos almacenados
 * Cada operacion muestra: estado inicial, pasos (con variables auxiliares) y estado final.
 */
class Bicola {

    // ---- Colores ANSI propios (para no depender de otras clases) ----
    static final String RESET = "\u001B[0m";
    static final String NEG = "\u001B[1m";
    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AMARILLO = "\u001B[33m";
    static final String AZUL = "\u001B[34m";
    static final String MORADO = "\u001B[35m";
    static final String CIAN = "\u001B[36m";
    static final String GRIS = "\u001B[90m";

    /** Ancho (en caracteres) de cada celda dibujada. */
    private static final int CELDA = 7;

    private final int[] datos;        // almacenamiento
    private final boolean[] ocupado;  // marca de posiciones ocupadas
    private final int capacidad;
    private int frente = -1;
    private int fin = -1;
    private int tam = 0;

    private final Scanner sc;
    private boolean pausas = false;   // pausar (ENTER) entre pasos
    private int numPaso = 0;

    Bicola(int capacidad, Scanner sc) {
        this.capacidad = capacidad;
        this.datos = new int[capacidad];
        this.ocupado = new boolean[capacidad];
        this.sc = sc;
    }

    int getCapacidad() { return capacidad; }
    boolean estaVacia() { return tam == 0; }
    boolean estaLlena() { return tam == capacidad; }
    void setPausas(boolean p) { pausas = p; }
    boolean getPausas() { return pausas; }

    // =====================================================================
    //  OPERACIONES
    // =====================================================================

    /** Insertar por el frente. */
    void insertarFrente(int elemento) {
        inicio("INSERTAR POR EL FRENTE  (elemento = " + elemento + ")");

        paso("Verificar desbordamiento: ¿tam == capacidad?",
                "elemento=" + elemento + " | tam=" + tam + " | capacidad=" + capacidad
                        + " | ¿llena?=" + estaLlena(), -1);
        if (estaLlena()) {
            error("DESBORDAMIENTO: la bicola está llena, no se puede insertar.");
            finalizar();
            return;
        }

        int nuevo;
        if (estaVacia()) {
            nuevo = 0;
            paso("Bicola vacía: la primera posición usada será 0",
                    "nuevoFrente=" + nuevo + " | elemento=" + elemento, -1);
        } else {
            nuevo = (frente - 1 + capacidad) % capacidad;
            paso("Retroceso circular: nuevoFrente = (frente - 1 + capacidad) % capacidad",
                    "nuevoFrente=(" + frente + " - 1 + " + capacidad + ") % " + capacidad + " = " + nuevo
                            + " | elemento=" + elemento, -1);
        }

        datos[nuevo] = elemento;
        ocupado[nuevo] = true;
        paso("Escribir el elemento: datos[" + nuevo + "] = " + elemento,
                "nuevoFrente=" + nuevo + " | elemento=" + elemento, nuevo);

        frente = nuevo;
        if (tam == 0) fin = nuevo;
        tam++;
        paso("Actualizar frente y tam (tam = tam + 1)",
                "frente=" + frente + " | fin=" + fin + " | tam=" + tam, nuevo);

        exito("Elemento " + elemento + " insertado por el frente.");
        finalizar();
    }

    /** Insertar por el final. */
    void insertarFinal(int elemento) {
        inicio("INSERTAR POR EL FINAL  (elemento = " + elemento + ")");

        paso("Verificar desbordamiento: ¿tam == capacidad?",
                "elemento=" + elemento + " | tam=" + tam + " | capacidad=" + capacidad
                        + " | ¿llena?=" + estaLlena(), -1);
        if (estaLlena()) {
            error("DESBORDAMIENTO: la bicola está llena, no se puede insertar.");
            finalizar();
            return;
        }

        int nuevo;
        if (estaVacia()) {
            nuevo = 0;
            paso("Bicola vacía: la primera posición usada será 0",
                    "nuevoFin=" + nuevo + " | elemento=" + elemento, -1);
        } else {
            nuevo = (fin + 1) % capacidad;
            paso("Avance circular: nuevoFin = (fin + 1) % capacidad",
                    "nuevoFin=(" + fin + " + 1) % " + capacidad + " = " + nuevo
                            + " | elemento=" + elemento, -1);
        }

        datos[nuevo] = elemento;
        ocupado[nuevo] = true;
        paso("Escribir el elemento: datos[" + nuevo + "] = " + elemento,
                "nuevoFin=" + nuevo + " | elemento=" + elemento, nuevo);

        fin = nuevo;
        if (tam == 0) frente = nuevo;
        tam++;
        paso("Actualizar fin y tam (tam = tam + 1)",
                "frente=" + frente + " | fin=" + fin + " | tam=" + tam, nuevo);

        exito("Elemento " + elemento + " insertado por el final.");
        finalizar();
    }

    /** Eliminar por el frente. */
    void eliminarFrente() {
        inicio("ELIMINAR POR EL FRENTE");

        paso("Verificar subdesbordamiento: ¿tam == 0?",
                "tam=" + tam + " | ¿vacía?=" + estaVacia(), -1);
        if (estaVacia()) {
            error("SUBDESBORDAMIENTO: la bicola está vacía, no hay nada que eliminar.");
            finalizar();
            return;
        }

        int elemento = datos[frente];
        int pos = frente;
        paso("Leer el elemento del frente: elemento = datos[frente]",
                "elemento=datos[" + frente + "]=" + elemento + " | frente=" + frente, pos);

        ocupado[pos] = false;
        datos[pos] = 0;
        paso("Liberar la posición " + pos,
                "elemento=" + elemento + " | posLiberada=" + pos, pos);

        if (tam == 1) {
            frente = -1;
            fin = -1;
            tam = 0;
            paso("Era el único elemento: la bicola queda vacía (frente = fin = -1, tam = 0)",
                    "frente=" + frente + " | fin=" + fin + " | tam=" + tam, -1);
        } else {
            int anterior = frente;
            frente = (frente + 1) % capacidad;
            tam--;
            paso("Avance circular: frente = (frente + 1) % capacidad;  tam = tam - 1",
                    "frente=(" + anterior + " + 1) % " + capacidad + " = " + frente
                            + " | fin=" + fin + " | tam=" + tam, -1);
        }

        exito("Elemento eliminado por el frente: " + elemento);
        finalizar();
    }

    /** Eliminar por el final. */
    void eliminarFinal() {
        inicio("ELIMINAR POR EL FINAL");

        paso("Verificar subdesbordamiento: ¿tam == 0?",
                "tam=" + tam + " | ¿vacía?=" + estaVacia(), -1);
        if (estaVacia()) {
            error("SUBDESBORDAMIENTO: la bicola está vacía, no hay nada que eliminar.");
            finalizar();
            return;
        }

        int elemento = datos[fin];
        int pos = fin;
        paso("Leer el elemento del final: elemento = datos[fin]",
                "elemento=datos[" + fin + "]=" + elemento + " | fin=" + fin, pos);

        ocupado[pos] = false;
        datos[pos] = 0;
        paso("Liberar la posición " + pos,
                "elemento=" + elemento + " | posLiberada=" + pos, pos);

        if (tam == 1) {
            frente = -1;
            fin = -1;
            tam = 0;
            paso("Era el único elemento: la bicola queda vacía (frente = fin = -1, tam = 0)",
                    "frente=" + frente + " | fin=" + fin + " | tam=" + tam, -1);
        } else {
            int anterior = fin;
            fin = (fin - 1 + capacidad) % capacidad;
            tam--;
            paso("Retroceso circular: fin = (fin - 1 + capacidad) % capacidad;  tam = tam - 1",
                    "fin=(" + anterior + " - 1 + " + capacidad + ") % " + capacidad + " = " + fin
                            + " | frente=" + frente + " | tam=" + tam, -1);
        }

        exito("Elemento eliminado por el final: " + elemento);
        finalizar();
    }

    /** Consultar ambos extremos sin modificar la bicola. */
    void consultarExtremos() {
        inicio("CONSULTAR LOS EXTREMOS (sin retirar elementos)");

        paso("Verificar que la bicola no esté vacía",
                "tam=" + tam + " | ¿vacía?=" + estaVacia(), -1);
        if (estaVacia()) {
            error("La bicola está vacía: no hay elementos en los extremos.");
            finalizar();
            return;
        }

        paso("Consultar el FRENTE: datos[frente]",
                "frente=" + frente + " | datos[" + frente + "]=" + datos[frente], frente);
        paso("Consultar el FINAL: datos[fin]",
                "fin=" + fin + " | datos[" + fin + "]=" + datos[fin], fin);

        exito("Frente = " + datos[frente] + "   |   Final = " + datos[fin]);
        finalizar();
    }

    /** Recorrer y mostrar el contenido de frente a final. */
    void mostrarContenido() {
        inicio("RECORRER Y MOSTRAR EL CONTENIDO (de frente a final)");

        if (estaVacia()) {
            paso("Verificar que la bicola no esté vacía", "tam=" + tam, -1);
            error("La bicola está vacía: no hay contenido que mostrar.");
            finalizar();
            return;
        }

        StringBuilder acumulado = new StringBuilder();
        for (int k = 0; k < tam; k++) {
            int indice = (frente + k) % capacidad;
            acumulado.append(k == 0 ? "" : " , ").append(datos[indice]);
            paso("Visitar el elemento n.º " + (k + 1) + ": indice = (frente + k) % capacidad",
                    "k=" + k + " | indice=(" + frente + " + " + k + ") % " + capacidad + " = " + indice
                            + " | valor=" + datos[indice], indice);
        }

        exito("Contenido (frente → final): [ " + acumulado + " ]");
        finalizar();
    }

    // =====================================================================
    //  VISUALIZACION
    // =====================================================================

    /** Muestra el estado actual (sin pasos) y la identificacion de estados. */
    void mostrarEstadoActual() {
        System.out.println();
        System.out.println(MORADO + NEG + "  ┌─────────── ESTADO ACTUAL DE LA BICOLA ───────────┐" + RESET);
        dibujar(-1);
        System.out.println(CIAN + "  frente=" + frente + " | fin=" + fin + " | tam=" + tam
                + " | capacidad=" + capacidad + " | libres=" + (capacidad - tam) + RESET);
        mostrarEstados();
    }

    private void inicio(String titulo) {
        numPaso = 0;
        System.out.println();
        System.out.println(MORADO + NEG + "  ╔══════════════════════════════════════════════════════╗" + RESET);
        System.out.println(MORADO + NEG + "  ║ " + titulo + RESET);
        System.out.println(MORADO + NEG + "  ╚══════════════════════════════════════════════════════╝" + RESET);
        System.out.println(AZUL + NEG + "\n  ■ 1. ESTADO INICIAL" + RESET);
        dibujar(-1);
        System.out.println(GRIS + "  frente=" + frente + " | fin=" + fin + " | tam=" + tam
                + " | capacidad=" + capacidad + RESET);
        System.out.println(AZUL + NEG + "\n  ■ 2. EJECUCIÓN PASO A PASO (con variables auxiliares)" + RESET);
        pausar();
    }

    private void paso(String descripcion, String variables, int resaltado) {
        numPaso++;
        System.out.println();
        System.out.println(AMARILLO + NEG + "  ▶ Paso " + numPaso + ": " + RESET + descripcion);
        System.out.println(CIAN + "    Variables: " + RESET + variables);
        dibujar(resaltado);
        pausar();
    }

    private void finalizar() {
        System.out.println(AZUL + NEG + "\n  ■ 4. ESTADO FINAL" + RESET);
        dibujar(-1);
        System.out.println(GRIS + "  frente=" + frente + " | fin=" + fin + " | tam=" + tam
                + " | capacidad=" + capacidad + RESET);
        mostrarEstados();
    }

    /** Identifica y explica los estados de la bicola. */
    private void mostrarEstados() {
        System.out.println(AZUL + NEG + "  Estados detectados:" + RESET);
        if (tam == 0) {
            System.out.println(AMARILLO + "   • VACÍA" + RESET
                    + " (tam=0, frente=fin=-1): no se puede eliminar ni consultar; se puede insertar.");
        } else if (tam == capacidad) {
            System.out.println(ROJO + "   • LLENA" + RESET
                    + " (tam=capacidad): no se puede insertar por ningún extremo.");
        } else {
            System.out.println(VERDE + "   • CON ESPACIO" + RESET
                    + " (0 < tam < capacidad): admite insertar y eliminar por ambos extremos.");
        }
        if (tam == 1) {
            System.out.println("   • UN SOLO ELEMENTO: frente == fin == " + frente + ".");
        }
        if (tam > 0 && frente == 0) {
            System.out.println("   • FRENTE EN LA POSICIÓN 0: insertar por el frente hace que frente salte a "
                    + (capacidad - 1) + " (avance circular).");
        }
        if (tam > 0 && fin == capacidad - 1) {
            System.out.println("   • FINAL EN LA ÚLTIMA POSICIÓN: insertar por el final hace que fin salte a 0 "
                    + "(avance circular).");
        }
        if (tam > 0 && fin < frente) {
            System.out.println("   • ENVUELTA (circular): la bicola ocupa el final y el inicio del arreglo "
                    + "(fin < frente).");
        }
    }

    /** Dibuja el arreglo, los indices y las marcas F (frente) y L (final), mas la vista logica. */
    private void dibujar(int resaltado) {
        StringBuilder idx = new StringBuilder("   ");
        StringBuilder sup = new StringBuilder("  ┌");
        StringBuilder cuerpo = new StringBuilder("  │");
        StringBuilder inf = new StringBuilder("  └");
        StringBuilder flechas = new StringBuilder("   ");
        StringBuilder etiquetas = new StringBuilder("   ");

        for (int i = 0; i < capacidad; i++) {
            idx.append(GRIS).append(centrar("[" + i + "]", CELDA)).append(RESET).append(" ");
            sup.append("─".repeat(CELDA)).append(i < capacidad - 1 ? "┬" : "┐");
            inf.append("─".repeat(CELDA)).append(i < capacidad - 1 ? "┴" : "┘");

            String txt = ocupado[i] ? String.valueOf(datos[i]) : "";
            String celda = centrar(txt, CELDA);
            if (i == resaltado) {
                cuerpo.append(AMARILLO).append(NEG).append(celda).append(RESET);
            } else if (ocupado[i]) {
                cuerpo.append(VERDE).append(celda).append(RESET);
            } else {
                cuerpo.append(celda);
            }
            cuerpo.append("│");

            boolean esF = (i == frente && tam > 0);
            boolean esL = (i == fin && tam > 0);
            String lbl = (esF && esL) ? "F/L" : esF ? "F" : esL ? "L" : "";
            String color = (esF && esL) ? MORADO : esF ? VERDE : ROJO;
            flechas.append(lbl.isEmpty() ? centrar("", CELDA) : color + NEG + centrar("▲", CELDA) + RESET).append(" ");
            etiquetas.append(lbl.isEmpty() ? centrar("", CELDA) : color + NEG + centrar(lbl, CELDA) + RESET).append(" ");
        }

        System.out.println(idx);
        System.out.println(sup);
        System.out.println(cuerpo);
        System.out.println(inf);
        System.out.println(flechas);
        System.out.println(etiquetas + GRIS + "  (F = frente, L = final)" + RESET);

        // Vista logica FRENTE <-> ... <-> FINAL
        StringBuilder logica = new StringBuilder("  " + VERDE + NEG + "FRENTE " + RESET + "⇄ ");
        if (tam == 0) {
            logica.append(GRIS).append("( vacía )").append(RESET);
        } else {
            for (int k = 0; k < tam; k++) {
                int i = (frente + k) % capacidad;
                logica.append("[ ").append(datos[i]).append(" ]");
                if (k < tam - 1) logica.append(" ⇄ ");
            }
        }
        logica.append(" ⇄ ").append(ROJO).append(NEG).append(" FINAL").append(RESET);
        System.out.println(logica);
    }

    private static String centrar(String s, int ancho) {
        if (s.length() >= ancho) return s;
        int izq = (ancho - s.length()) / 2;
        int der = ancho - s.length() - izq;
        return " ".repeat(izq) + s + " ".repeat(der);
    }

    private void pausar() {
        if (pausas) {
            System.out.print(GRIS + "    (ENTER para continuar...)" + RESET);
            sc.nextLine();
        }
    }

    private static void error(String m) { System.out.println(ROJO + NEG + "\n  ✖ " + m + RESET); }
    private static void exito(String m) { System.out.println(VERDE + NEG + "\n  ✔ " + m + RESET); }
}

/**
 * Menu del TAD Bicola. Se invoca desde el menu principal con:  MenuBicola.ejecutar(sc);
 */
public class MenuBicola {

    private static final int CAPACIDAD_MAX = 10;   // para que el dibujo quepa en consola

   public static void ejecutar(Scanner sc) {
        Bicola bicola = null;
        int opcion;

        do {
            mostrarMenu(bicola);
            opcion = leerEntero(sc, "Seleccione una opción: ", 0, 9);

            if (opcion >= 2 && opcion <= 8 && bicola == null) {
                aviso("Primero debe crear la bicola (opción 1).");
                continue;
            }

            switch (opcion) {
                case 1:
                    int cap = leerEntero(sc, "Capacidad de la bicola (1-" + CAPACIDAD_MAX + "): ",
                            1, CAPACIDAD_MAX);
                    boolean pausasPrevias = (bicola != null) && bicola.getPausas();
                    bicola = new Bicola(cap, sc);
                    bicola.setPausas(pausasPrevias);
                    System.out.println(Bicola.VERDE + Bicola.NEG + "\n  ✔ Bicola creada con capacidad "
                            + cap + "." + Bicola.RESET);
                    bicola.mostrarEstadoActual();
                    break;
                case 2:
                    bicola.insertarFrente(leerEntero(sc, "Elemento a insertar por el frente: ", -9999, 99999));
                    break;
                case 3:
                    bicola.insertarFinal(leerEntero(sc, "Elemento a insertar por el final: ", -9999, 99999));
                    break;
                case 4:
                    bicola.eliminarFrente();
                    break;
                case 5:
                    bicola.eliminarFinal();
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
                    if (bicola == null) {
                        aviso("Cree primero la bicola para activar/desactivar las pausas.");
                    } else {
                        bicola.setPausas(!bicola.getPausas());
                        System.out.println(Bicola.CIAN + "  ℹ Pausa entre pasos: "
                                + (bicola.getPausas() ? "ACTIVADA" : "DESACTIVADA") + Bicola.RESET);
                    }
                    break;
                default:
                    break;
            }
        } while (opcion != 0);

        System.out.println(Bicola.AMARILLO + "\n  Regresando al menú principal..." + Bicola.RESET);
    }

    private static void mostrarMenu(Bicola b) {
        System.out.println();
        System.out.println(Bicola.MORADO + Bicola.NEG + "  ╔══════════════════════════════════════════════╗" + Bicola.RESET);
        System.out.println(Bicola.MORADO + Bicola.NEG + "  ║          BICOLA (DOBLE COLA / DEQUE)         ║" + Bicola.RESET);
        System.out.println(Bicola.MORADO + Bicola.NEG + "  ╚══════════════════════════════════════════════╝" + Bicola.RESET);
        if (b != null) {
            System.out.println(Bicola.GRIS + "   Bicola activa: capacidad " + b.getCapacidad()
                    + " | pausas " + (b.getPausas() ? "ON" : "OFF") + Bicola.RESET);
        }
        System.out.println("   [1] Crear / reiniciar la bicola");
        System.out.println("   [2] Insertar por el frente");
        System.out.println("   [3] Insertar por el final");
        System.out.println("   [4] Eliminar por el frente");
        System.out.println("   [5] Eliminar por el final");
        System.out.println("   [6] Consultar los extremos (frente y final)");
        System.out.println("   [7] Recorrer / mostrar el contenido");
        System.out.println("   [8] Ver estado actual y estados de la bicola");
        System.out.println("   [9] Activar/desactivar pausa entre pasos");
        System.out.println(Bicola.AMARILLO + Bicola.NEG + "   [0] Regresar al menú principal" + Bicola.RESET);
    }

    private static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(Bicola.AMARILLO + Bicola.NEG + "  ➤ " + Bicola.RESET + mensaje);
            String entrada = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor < min || valor > max) {
                    System.out.println(Bicola.ROJO + "  ✖ Ingrese un valor entre " + min + " y " + max + "."
                            + Bicola.RESET);
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println(Bicola.ROJO + "  ✖ Entrada inválida. Ingrese un número entero." + Bicola.RESET);
            }
        }
    }

    private static void aviso(String m) {
        System.out.println(Bicola.AMARILLO + Bicola.NEG + "  ⚠ " + m + Bicola.RESET);
    }
}
