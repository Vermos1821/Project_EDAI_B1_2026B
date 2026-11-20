package org.example.bicola;

import org.example.util.Color;

import java.util.Scanner;

/* =====================================================================
 *  CASO 4 - BICOLA (DOBLE COLA / DEQUE)
 *  Representacion vista en clase (Tema 3.3):
 *    arreglo usado de forma circular de capacidad N, con frente, fin y n.
 *    Al crearla: frente = 0, fin = N - 1, n = 0.
 *    insertRear : fin    = (fin + 1) mod N
 *    insertFront: frente = (frente - 1 + N) mod N
 *    deleteFront: frente = (frente + 1) mod N
 *    deleteRear : fin    = (fin - 1 + N) mod N
 *  Estados de la bicola (clase): ENTRADA RESTRINGIDA y SALIDA RESTRINGIDA
 *  (ademas de la bicola GENERAL, que permite las cuatro operaciones).
 * ===================================================================== */

/**
 * TAD Bicola de enteros con arreglo circular de capacidad fija.
 * Cada operacion muestra: estado inicial, pasos (con variables auxiliares) y estado final.
 */
public class Bicola {


    /** Tipo de bicola que permite las cuatro operaciones. */
    static final int GENERAL = 1;
    /** Tipo de bicola de entrada restringida: se inserta solo por el final (no permite insertFront). */
    static final int ENTRADA_RESTRINGIDA = 2;   // no permite insertFront
    /** Tipo de bicola de salida restringida: se elimina solo por el frente (no permite deleteRear). */
    static final int SALIDA_RESTRINGIDA = 3;    // no permite deleteRear

    /** Ancho, en caracteres, de cada celda dibujada en consola. */
    private static final int CELDA = 7;

    /** Arreglo que almacena los elementos de la bicola (arr). */
    private final int[] datos;        // almacenamiento (arr)
    /** Marca las posiciones ocupadas del arreglo; solo se usa para dibujar las celdas vacías. */
    private final boolean[] ocupado;  // marca de posiciones ocupadas (solo para dibujar)
    /** Capacidad máxima de la bicola (N). */
    private final int capacidad;      // N
    /** Índice del primer elemento. Al crear la bicola vale 0. */
    private int frente;               // indice del primer elemento
    /** Índice del último elemento. Al crear la bicola vale N - 1. */
    private int fin;                  // indice del ultimo elemento
    /** Cantidad de elementos almacenados (n). Distingue la bicola vacía de la llena. */
    private int tam;                  // n: cantidad de elementos
    /** Tipo de bicola activo: GENERAL, ENTRADA_RESTRINGIDA o SALIDA_RESTRINGIDA. */
    private int tipo;

    /** Scanner usado para las pausas entre pasos. */
    private final Scanner sc;
    /** Indica si se espera un ENTER entre cada paso de la ejecución. */
    private boolean pausas = false;   // pausar (ENTER) entre pasos
    /** Número del paso que se está mostrando en la operación actual. */
    private int numPaso = 0;

    /**
     * Crea una bicola vacía con frente = 0, fin = N - 1 y n = 0.
     *
     * @param capacidad número máximo de elementos (N).
     * @param tipo tipo de bicola (GENERAL, ENTRADA_RESTRINGIDA o SALIDA_RESTRINGIDA).
     * @param sc Scanner utilizado para las pausas entre pasos.
     */
    Bicola(int capacidad, int tipo, Scanner sc) {
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.datos = new int[capacidad];
        this.ocupado = new boolean[capacidad];
        this.frente = 0;               // al crearla: frente = 0
        this.fin = capacidad - 1;      //             fin = N - 1
        this.tam = 0;                  //             n = 0
        this.sc = sc;
    }

    /**
     * Obtiene la capacidad máxima de la bicola.
     *
     * @return capacidad N.
     */
    int getCapacidad() { return capacidad; }
    /**
     * Comprueba si la bicola no contiene elementos (n = 0).
     *
     * @return true si está vacía; false en caso contrario.
     */
    boolean isEmpty() { return tam == 0; }
    /**
     * Comprueba si la bicola alcanzó su capacidad (n = N).
     *
     * @return true si está llena; false en caso contrario.
     */
    boolean estaLlena() { return tam == capacidad; }
    /**
     * Obtiene la cantidad de elementos almacenados.
     *
     * @return número de elementos n.
     */
    int size() { return tam; }
    /**
     * Obtiene el tipo de bicola activo.
     *
     * @return GENERAL, ENTRADA_RESTRINGIDA o SALIDA_RESTRINGIDA.
     */
    int getTipo() { return tipo; }
    /**
     * Cambia el tipo de bicola; los elementos almacenados se conservan.
     *
     * @param t nuevo tipo de bicola.
     */
    void setTipo(int t) { tipo = t; }
    /**
     * Activa o desactiva la pausa (ENTER) entre pasos.
     *
     * @param p true para activar las pausas.
     */
    void setPausas(boolean p) { pausas = p; }
    /**
     * Indica si las pausas entre pasos están activadas.
     *
     * @return true si están activadas.
     */
    boolean getPausas() { return pausas; }

    /**
     * Obtiene el nombre legible de un tipo de bicola.
     *
     * @param t tipo de bicola.
     * @return nombre del tipo en mayúsculas.
     */
    static String nombreTipo(int t) {
        if (t == ENTRADA_RESTRINGIDA) return "ENTRADA RESTRINGIDA";
        if (t == SALIDA_RESTRINGIDA) return "SALIDA RESTRINGIDA";
        return "GENERAL";
    }

    // =====================================================================
    //  OPERACIONES PRINCIPALES
    // =====================================================================

    /**
     * insertFront(e): agrega un elemento antes del primero (por el frente).
     * Pasos: verificar tipo permitido, verificar desbordamiento,
     * retroceder frente = (frente - 1 + N) mod N, guardar arr[frente] = e y n = n + 1.
     * Muestra estado inicial, pasos con variables auxiliares y estado final.
     * Si el tipo es entrada restringida o la bicola está llena, informa el error sin modificarla.
     *
     * @param e elemento que se desea insertar.
     */
    void insertFront(int e) {
        inicio("INSERTAR POR EL FRENTE: insertFront(" + e + ")");

        paso("Verificar que la operación esté permitida en este tipo de bicola",
                "tipo=" + nombreTipo(tipo) + " | insertFront permitida=" + (tipo != ENTRADA_RESTRINGIDA), -1);
        if (tipo == ENTRADA_RESTRINGIDA) {
            error("OPERACIÓN NO PERMITIDA: en la bicola de entrada restringida solo se inserta por el final (insertRear).");
            finalizar();
            return;
        }

        paso("Verificar desbordamiento: ¿n = N?",
                "e=" + e + " | n=" + tam + " | N=" + capacidad + " | ¿llena?=" + estaLlena(), -1);
        if (estaLlena()) {
            error("DESBORDAMIENTO: la bicola está llena, no se puede insertar.");
            finalizar();
            return;
        }

        int nuevo = (frente - 1 + capacidad) % capacidad;
        paso("Retroceder: frente = (frente - 1 + N) mod N",
                "nuevoFrente=(" + frente + " - 1 + " + capacidad + ") mod " + capacidad + " = " + nuevo
                        + " | e=" + e, -1);

        datos[nuevo] = e;
        ocupado[nuevo] = true;
        paso("Guardar: arr[" + nuevo + "] = " + e,
                "nuevoFrente=" + nuevo + " | e=" + e, nuevo);

        frente = nuevo;
        tam++;
        paso("Actualizar frente y n = n + 1",
                "frente=" + frente + " | fin=" + fin + " | n=" + tam, nuevo);

        exito("Elemento " + e + " insertado por el frente.");
        finalizar();
    }

    /**
     * insertRear(e): agrega un elemento después del último (por el final).
     * Pasos: verificar desbordamiento, avanzar fin = (fin + 1) mod N,
     * guardar arr[fin] = e y n = n + 1.
     * Muestra estado inicial, pasos con variables auxiliares y estado final.
     * Si la bicola está llena, informa el desbordamiento sin modificarla.
     *
     * @param e elemento que se desea insertar.
     */
    void insertRear(int e) {
        inicio("INSERTAR POR EL FINAL: insertRear(" + e + ")");

        paso("Verificar desbordamiento: ¿n = N?",
                "e=" + e + " | n=" + tam + " | N=" + capacidad + " | ¿llena?=" + estaLlena(), -1);
        if (estaLlena()) {
            error("DESBORDAMIENTO: la bicola está llena, no se puede insertar.");
            finalizar();
            return;
        }

        int nuevo = (fin + 1) % capacidad;
        paso("Avanzar: fin = (fin + 1) mod N",
                "nuevoFin=(" + fin + " + 1) mod " + capacidad + " = " + nuevo + " | e=" + e, -1);

        datos[nuevo] = e;
        ocupado[nuevo] = true;
        paso("Guardar: arr[" + nuevo + "] = " + e,
                "nuevoFin=" + nuevo + " | e=" + e, nuevo);

        fin = nuevo;
        tam++;
        paso("Actualizar fin y n = n + 1",
                "frente=" + frente + " | fin=" + fin + " | n=" + tam, nuevo);

        exito("Elemento " + e + " insertado por el final.");
        finalizar();
    }

    /**
     * deleteFront(): quita y muestra el primer elemento (por el frente).
     * Pasos: verificar subdesbordamiento, leer arr[frente], n = n - 1 y
     * avanzar frente = (frente + 1) mod N.
     * Muestra estado inicial, pasos con variables auxiliares y estado final.
     *
     * Si la bicola está vacía, informa el subdesbordamiento sin modificarla.
     */
    void deleteFront() {
        inicio("ELIMINAR POR EL FRENTE: deleteFront()");

        paso("Verificar subdesbordamiento: ¿n = 0?",
                "n=" + tam + " | ¿vacía?=" + isEmpty(), -1);
        if (isEmpty()) {
            error("SUBDESBORDAMIENTO: la bicola está vacía, no hay nada que eliminar.");
            finalizar();
            return;
        }

        int pos = frente;
        int e = datos[pos];
        paso("Leer: guardar arr[frente]",
                "e=arr[" + pos + "]=" + e + " | frente=" + frente, pos);

        ocupado[pos] = false;
        datos[pos] = 0;
        int anterior = frente;
        frente = (frente + 1) % capacidad;
        tam--;
        paso("n = n - 1 y avanzar: frente = (frente + 1) mod N",
                "frente=(" + anterior + " + 1) mod " + capacidad + " = " + frente
                        + " | fin=" + fin + " | n=" + tam + " | e=" + e, pos);

        exito("Elemento eliminado por el frente: " + e);
        finalizar();
    }

    /**
     * deleteRear(): quita y muestra el último elemento (por el final).
     * Pasos: verificar tipo permitido, verificar subdesbordamiento, leer arr[fin],
     * n = n - 1 y retroceder fin = (fin - 1 + N) mod N.
     * Muestra estado inicial, pasos con variables auxiliares y estado final.
     *
     * Si el tipo es salida restringida o la bicola está vacía, informa el error sin modificarla.
     */
    void deleteRear() {
        inicio("ELIMINAR POR EL FINAL: deleteRear()");

        paso("Verificar que la operación esté permitida en este tipo de bicola",
                "tipo=" + nombreTipo(tipo) + " | deleteRear permitida=" + (tipo != SALIDA_RESTRINGIDA), -1);
        if (tipo == SALIDA_RESTRINGIDA) {
            error("OPERACIÓN NO PERMITIDA: en la bicola de salida restringida solo se elimina por el frente (deleteFront).");
            finalizar();
            return;
        }

        paso("Verificar subdesbordamiento: ¿n = 0?",
                "n=" + tam + " | ¿vacía?=" + isEmpty(), -1);
        if (isEmpty()) {
            error("SUBDESBORDAMIENTO: la bicola está vacía, no hay nada que eliminar.");
            finalizar();
            return;
        }

        int pos = fin;
        int e = datos[pos];
        paso("Leer: guardar arr[fin]",
                "e=arr[" + pos + "]=" + e + " | fin=" + fin, pos);

        ocupado[pos] = false;
        datos[pos] = 0;
        int anterior = fin;
        fin = (fin - 1 + capacidad) % capacidad;
        tam--;
        paso("n = n - 1 y retroceder: fin = (fin - 1 + N) mod N",
                "fin=(" + anterior + " - 1 + " + capacidad + ") mod " + capacidad + " = " + fin
                        + " | frente=" + frente + " | n=" + tam + " | e=" + e, pos);

        exito("Elemento eliminado por el final: " + e);
        finalizar();
    }

    // =====================================================================
    //  OPERACIONES AUXILIARES (no modifican la bicola)
    // =====================================================================

    /**
     * front() y rear(): consulta el primer y el último elemento sin quitarlos.
     * No modifica la bicola; el estado final coincide con el inicial.
     *
     * Si la bicola está vacía, informa que no hay elementos en los extremos.
     */
    void consultarExtremos() {
        inicio("CONSULTAR LOS EXTREMOS: front() y rear()");

        paso("Verificar que la bicola no esté vacía: isEmpty()",
                "n=" + tam + " | isEmpty()=" + isEmpty(), -1);
        if (isEmpty()) {
            error("La bicola está vacía: no hay elementos en los extremos.");
            finalizar();
            return;
        }

        paso("front(): consultar arr[frente] sin quitarlo",
                "frente=" + frente + " | arr[" + frente + "]=" + datos[frente], frente);
        paso("rear(): consultar arr[fin] sin quitarlo",
                "fin=" + fin + " | arr[" + fin + "]=" + datos[fin], fin);

        exito("front() = " + datos[frente] + "   |   rear() = " + datos[fin]);
        finalizar();
    }

    /**
     * Recorre la bicola de frente a fin y muestra cada elemento visitado,
     * con indice = (frente + k) mod N y la posición resaltada en el dibujo.
     * No modifica la bicola.
     */
    void mostrarContenido() {
        inicio("RECORRER Y MOSTRAR EL CONTENIDO (de frente a fin)");

        if (isEmpty()) {
            paso("Verificar que la bicola no esté vacía", "n=" + tam, -1);
            error("La bicola está vacía: no hay contenido que mostrar.");
            finalizar();
            return;
        }

        StringBuilder acumulado = new StringBuilder();
        for (int k = 0; k < tam; k++) {
            int indice = (frente + k) % capacidad;
            acumulado.append(k == 0 ? "" : " , ").append(datos[indice]);
            paso("Visitar el elemento n.º " + (k + 1) + ": indice = (frente + k) mod N",
                    "k=" + k + " | indice=(" + frente + " + " + k + ") mod " + capacidad + " = " + indice
                            + " | valor=" + datos[indice], indice);
        }

        exito("Contenido (frente → fin): [ " + acumulado + " ]");
        finalizar();
    }

    // =====================================================================
    //  VISUALIZACION
    // =====================================================================

    /**
     * Muestra el estado actual sin ejecutar ninguna operación: dibujo del arreglo,
     * variables, isEmpty(), size() y los estados de la bicola.
     */
    void mostrarEstadoActual() {
        System.out.println();
        System.out.println(Color.MORADO + Color.NEGRITA + "  ┌─────────── ESTADO ACTUAL DE LA BICOLA ───────────┐" + Color.RESET);
        dibujar(-1);
        System.out.println(Color.CIAN + "  " + variablesBase() + " | libres=" + (capacidad - tam)
                + " | isEmpty()=" + isEmpty() + " | size()=" + size() + Color.RESET);
        mostrarEstados();
    }

    /**
     * Arma el texto con los valores actuales de frente, fin, n y N.
     *
     * @return cadena con las variables principales de la bicola.
     */
    private String variablesBase() {
        return "frente=" + frente + " | fin=" + fin + " | n=" + tam + " | N=" + capacidad;
    }

    /**
     * Muestra el encabezado de la operación y la etapa 1 (estado inicial).
     *
     * @param titulo nombre de la operación que se va a ejecutar.
     */
    private void inicio(String titulo) {
        numPaso = 0;
        System.out.println();
        System.out.println(Color.MORADO + Color.NEGRITA + "  ╔══════════════════════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.MORADO + Color.NEGRITA + "  ║ " + titulo + Color.RESET);
        System.out.println(Color.MORADO + Color.NEGRITA + "  ╚══════════════════════════════════════════════════════╝" + Color.RESET);
        System.out.println(Color.GRIS + "  Tipo de bicola: " + nombreTipo(tipo) + Color.RESET);
        System.out.println(Color.AZUL + Color.NEGRITA + "\n  ■ 1. ESTADO INICIAL" + Color.RESET);
        dibujar(-1);
        System.out.println(Color.GRIS + "  " + variablesBase() + Color.RESET);
        System.out.println(Color.AZUL + Color.NEGRITA + "\n  ■ 2. EJECUCIÓN PASO A PASO (con variables auxiliares)" + Color.RESET);
        pausar();
    }

    /**
     * Muestra un paso de la ejecución: descripción, variables auxiliares y dibujo.
     *
     * @param descripcion qué ocurre en este paso.
     * @param variables variables auxiliares y sus valores actuales.
     * @param resaltado índice de la celda a resaltar, o -1 si no hay ninguna.
     */
    private void paso(String descripcion, String variables, int resaltado) {
        numPaso++;
        System.out.println();
        System.out.println(Color.AMARILLO + Color.NEGRITA + "  ▶ Paso " + numPaso + ": " + Color.RESET + descripcion);
        System.out.println(Color.CIAN + "    Variables: " + Color.RESET + variables);
        dibujar(resaltado);
        pausar();
    }

    /** Muestra la etapa 3 (estado final) con el dibujo, las variables y los estados. */
    private void finalizar() {
        System.out.println(Color.AZUL + Color.NEGRITA + "\n  ■ 3. ESTADO FINAL" + Color.RESET);
        dibujar(-1);
        System.out.println(Color.GRIS + "  " + variablesBase() + Color.RESET);
        mostrarEstados();
    }

    /**
     * Identifica y explica los estados de la bicola: tipo (general, entrada
     * restringida o salida restringida, con las operaciones permitidas),
     * ocupación (vacía, llena o con espacio) y si da la vuelta al arreglo.
     */
    private void mostrarEstados() {
        System.out.println(Color.AZUL + Color.NEGRITA + "  Estados de la bicola:" + Color.RESET);

        // Estado segun restriccion (clase 3.3: entrada restringida / salida restringida)
        String iF = (tipo == ENTRADA_RESTRINGIDA) ? Color.ROJO + "no" : Color.VERDE + "sí";
        String iR = Color.VERDE + "sí";
        String dF = Color.VERDE + "sí";
        String dR = (tipo == SALIDA_RESTRINGIDA) ? Color.ROJO + "no" : Color.VERDE + "sí";
        System.out.println("   • TIPO: " + Color.AMARILLO + Color.NEGRITA + nombreTipo(tipo) + Color.RESET
                + "  →  insertFront: " + iF + Color.RESET + " | insertRear: " + iR + Color.RESET
                + " | deleteFront: " + dF + Color.RESET + " | deleteRear: " + dR + Color.RESET);

        // Estado segun ocupacion
        if (tam == 0) {
            System.out.println("   • " + Color.AMARILLO + "VACÍA" + Color.RESET
                    + " (n=0): no se puede eliminar. Se cumple frente = (fin + 1) mod N.");
        } else if (tam == capacidad) {
            System.out.println("   • " + Color.ROJO + "LLENA" + Color.RESET
                    + " (n=N): no se puede insertar. Se cumple frente = (fin + 1) mod N.");
        } else {
            System.out.println("   • " + Color.VERDE + "CON ESPACIO" + Color.RESET
                    + " (0 < n < N): se puede insertar y eliminar (según el tipo).");
        }
        if (tam > 0 && fin < frente) {
            System.out.println("   • DA LA VUELTA AL ARREGLO: fin < frente (la bicola ocupa el final y el inicio).");
        }
    }

    /**
     * Dibuja en consola el arreglo con sus índices, las marcas F (frente) y
     * L (fin) y la vista lógica FRENTE ⇄ ... ⇄ FIN.
     *
     * @param resaltado índice de la celda a resaltar, o -1 si no hay ninguna.
     */
    private void dibujar(int resaltado) {
        StringBuilder idx = new StringBuilder("   ");
        StringBuilder sup = new StringBuilder("  ┌");
        StringBuilder cuerpo = new StringBuilder("  │");
        StringBuilder inf = new StringBuilder("  └");
        StringBuilder flechas = new StringBuilder("   ");
        StringBuilder etiquetas = new StringBuilder("   ");

        for (int i = 0; i < capacidad; i++) {
            idx.append(Color.GRIS).append(centrar("[" + i + "]", CELDA)).append(Color.RESET).append(" ");
            sup.append("─".repeat(CELDA)).append(i < capacidad - 1 ? "┬" : "┐");
            inf.append("─".repeat(CELDA)).append(i < capacidad - 1 ? "┴" : "┘");

            String txt = ocupado[i] ? String.valueOf(datos[i]) : "";
            String celda = centrar(txt, CELDA);
            if (i == resaltado) {
                cuerpo.append(Color.AMARILLO).append(Color.NEGRITA).append(celda).append(Color.RESET);
            } else if (ocupado[i]) {
                cuerpo.append(Color.VERDE).append(celda).append(Color.RESET);
            } else {
                cuerpo.append(celda);
            }
            cuerpo.append("│");

            boolean esF = (i == frente);
            boolean esL = (i == fin);
            String lbl = (esF && esL) ? "F/L" : esF ? "F" : esL ? "L" : "";
            String color = (esF && esL) ? Color.MORADO : esF ? Color.VERDE : Color.ROJO;
            flechas.append(lbl.isEmpty() ? centrar("", CELDA) : color + Color.NEGRITA + centrar("▲", CELDA) + Color.RESET).append(" ");
            etiquetas.append(lbl.isEmpty() ? centrar("", CELDA) : color + Color.NEGRITA + centrar(lbl, CELDA) + Color.RESET).append(" ");
        }

        System.out.println(idx);
        System.out.println(sup);
        System.out.println(cuerpo);
        System.out.println(inf);
        System.out.println(flechas);
        System.out.println(etiquetas + Color.GRIS + "  (F = frente, L = fin)" + Color.RESET);

        // Vista logica FRENTE <-> ... <-> FIN
        StringBuilder logica = new StringBuilder("  " + Color.VERDE + Color.NEGRITA + "FRENTE " + Color.RESET + "⇄ ");
        if (tam == 0) {
            logica.append(Color.GRIS).append("( vacía )").append(Color.RESET);
        } else {
            for (int k = 0; k < tam; k++) {
                int i = (frente + k) % capacidad;
                logica.append("[ ").append(datos[i]).append(" ]");
                if (k < tam - 1) logica.append(" ⇄ ");
            }
        }
        logica.append(" ⇄ ").append(Color.ROJO).append(Color.NEGRITA).append(" FIN").append(Color.RESET);
        System.out.println(logica);
    }

    /**
     * Centra un texto dentro de un ancho fijo, rellenando con espacios.
     *
     * @param s texto a centrar.
     * @param ancho ancho total deseado.
     * @return texto centrado (o el mismo texto si ya excede el ancho).
     */
    private static String centrar(String s, int ancho) {
        if (s.length() >= ancho) return s;
        int izq = (ancho - s.length()) / 2;
        int der = ancho - s.length() - izq;
        return " ".repeat(izq) + s + " ".repeat(der);
    }

    /** Espera un ENTER del usuario si las pausas están activadas. */
    private void pausar() {
        if (pausas) {
            System.out.print(Color.GRIS + "    (ENTER para continuar...)" + Color.RESET);
            sc.nextLine();
        }
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param m texto del mensaje.
     */
    private static void error(String m) {
        System.out.println();
        Color.error(m);
    }
    /**
     * Muestra un mensaje de éxito.
     *
     * @param m texto del mensaje.
     */
    private static void exito(String m) {
        System.out.println();
        Color.exito(m);
    }
}
