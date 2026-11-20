package org.example.bicola;

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
 *
 *  Desde el menu principal, opcion 4 ->  MenuBicola.ejecutar(sc);
 *  Para el archivo unico ProyectoEDAI.java: quitar la linea "package"
 *  y pegar las dos clases (Bicola y MenuBicola) en ese archivo.
 * ===================================================================== */

/**
 * TAD Bicola de enteros con arreglo circular de capacidad fija.
 * Cada operacion muestra: estado inicial, pasos (con variables auxiliares) y estado final.
 */
class Bicola {

    // ---- Colores ANSI propios (para no depender de otras clases) ----
    /** Código ANSI que restablece el formato de la consola. */
    static final String RESET = "\u001B[0m";
    /** Código ANSI para texto en negrita. */
    static final String NEG = "\u001B[1m";
    /** Código ANSI para texto rojo (errores y marca del fin). */
    static final String ROJO = "\u001B[31m";
    /** Código ANSI para texto verde (éxito, elementos y marca del frente). */
    static final String VERDE = "\u001B[32m";
    /** Código ANSI para texto amarillo (pasos y elemento resaltado). */
    static final String AMARILLO = "\u001B[33m";
    /** Código ANSI para texto azul (títulos de etapa). */
    static final String AZUL = "\u001B[34m";
    /** Código ANSI para texto morado (títulos principales). */
    static final String MORADO = "\u001B[35m";
    /** Código ANSI para texto cian (variables auxiliares). */
    static final String CIAN = "\u001B[36m";
    /** Código ANSI para texto gris (información secundaria). */
    static final String GRIS = "\u001B[90m";

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
        System.out.println(MORADO + NEG + "  ┌─────────── ESTADO ACTUAL DE LA BICOLA ───────────┐" + RESET);
        dibujar(-1);
        System.out.println(CIAN + "  " + variablesBase() + " | libres=" + (capacidad - tam)
                + " | isEmpty()=" + isEmpty() + " | size()=" + size() + RESET);
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
        System.out.println(MORADO + NEG + "  ╔══════════════════════════════════════════════════════╗" + RESET);
        System.out.println(MORADO + NEG + "  ║ " + titulo + RESET);
        System.out.println(MORADO + NEG + "  ╚══════════════════════════════════════════════════════╝" + RESET);
        System.out.println(GRIS + "  Tipo de bicola: " + nombreTipo(tipo) + RESET);
        System.out.println(AZUL + NEG + "\n  ■ 1. ESTADO INICIAL" + RESET);
        dibujar(-1);
        System.out.println(GRIS + "  " + variablesBase() + RESET);
        System.out.println(AZUL + NEG + "\n  ■ 2. EJECUCIÓN PASO A PASO (con variables auxiliares)" + RESET);
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
        System.out.println(AMARILLO + NEG + "  ▶ Paso " + numPaso + ": " + RESET + descripcion);
        System.out.println(CIAN + "    Variables: " + RESET + variables);
        dibujar(resaltado);
        pausar();
    }

    /** Muestra la etapa 3 (estado final) con el dibujo, las variables y los estados. */
    private void finalizar() {
        System.out.println(AZUL + NEG + "\n  ■ 3. ESTADO FINAL" + RESET);
        dibujar(-1);
        System.out.println(GRIS + "  " + variablesBase() + RESET);
        mostrarEstados();
    }

    /**
     * Identifica y explica los estados de la bicola: tipo (general, entrada
     * restringida o salida restringida, con las operaciones permitidas),
     * ocupación (vacía, llena o con espacio) y si da la vuelta al arreglo.
     */
    private void mostrarEstados() {
        System.out.println(AZUL + NEG + "  Estados de la bicola:" + RESET);

        // Estado segun restriccion (clase 3.3: entrada restringida / salida restringida)
        String iF = (tipo == ENTRADA_RESTRINGIDA) ? ROJO + "no" : VERDE + "sí";
        String iR = VERDE + "sí";
        String dF = VERDE + "sí";
        String dR = (tipo == SALIDA_RESTRINGIDA) ? ROJO + "no" : VERDE + "sí";
        System.out.println("   • TIPO: " + AMARILLO + NEG + nombreTipo(tipo) + RESET
                + "  →  insertFront: " + iF + RESET + " | insertRear: " + iR + RESET
                + " | deleteFront: " + dF + RESET + " | deleteRear: " + dR + RESET);

        // Estado segun ocupacion
        if (tam == 0) {
            System.out.println("   • " + AMARILLO + "VACÍA" + RESET
                    + " (n=0): no se puede eliminar. Se cumple frente = (fin + 1) mod N.");
        } else if (tam == capacidad) {
            System.out.println("   • " + ROJO + "LLENA" + RESET
                    + " (n=N): no se puede insertar. Se cumple frente = (fin + 1) mod N.");
        } else {
            System.out.println("   • " + VERDE + "CON ESPACIO" + RESET
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

            boolean esF = (i == frente);
            boolean esL = (i == fin);
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
        System.out.println(etiquetas + GRIS + "  (F = frente, L = fin)" + RESET);

        // Vista logica FRENTE <-> ... <-> FIN
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
        logica.append(" ⇄ ").append(ROJO).append(NEG).append(" FIN").append(RESET);
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
            System.out.print(GRIS + "    (ENTER para continuar...)" + RESET);
            sc.nextLine();
        }
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param m texto del mensaje.
     */
    private static void error(String m) { System.out.println(ROJO + NEG + "\n  ✖ " + m + RESET); }
    /**
     * Muestra un mensaje de éxito.
     *
     * @param m texto del mensaje.
     */
    private static void exito(String m) { System.out.println(VERDE + NEG + "\n  ✔ " + m + RESET); }
}

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
                    System.out.println(Bicola.VERDE + Bicola.NEG + "\n  ✔ Bicola creada: N=" + cap
                            + ", tipo " + Bicola.nombreTipo(tipo) + "." + Bicola.RESET);
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
                    System.out.println(Bicola.CIAN + "  ℹ Tipo de bicola cambiado a "
                            + Bicola.nombreTipo(bicola.getTipo()) + " (los elementos se conservan)." + Bicola.RESET);
                    bicola.mostrarEstadoActual();
                    break;
                case 10:
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

    /**
     * Muestra las opciones del menú de la bicola.
     *
     * @param b bicola activa, o null si todavía no se ha creado.
     */
    private static void mostrarMenu(Bicola b) {
        System.out.println();
        System.out.println(Bicola.MORADO + Bicola.NEG + "  ╔══════════════════════════════════════════════╗" + Bicola.RESET);
        System.out.println(Bicola.MORADO + Bicola.NEG + "  ║          BICOLA (DOBLE COLA / DEQUE)         ║" + Bicola.RESET);
        System.out.println(Bicola.MORADO + Bicola.NEG + "  ╚══════════════════════════════════════════════╝" + Bicola.RESET);
        if (b != null) {
            System.out.println(Bicola.GRIS + "   Bicola activa: N=" + b.getCapacidad()
                    + " | tipo " + Bicola.nombreTipo(b.getTipo())
                    + " | pausas " + (b.getPausas() ? "ON" : "OFF") + Bicola.RESET);
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
        System.out.println(Bicola.AMARILLO + Bicola.NEG + "   [0] Regresar al menú principal" + Bicola.RESET);
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

    /**
     * Muestra un mensaje de advertencia.
     *
     * @param m texto del mensaje.
     */
    private static void aviso(String m) {
        System.out.println(Bicola.AMARILLO + Bicola.NEG + "  ⚠ " + m + Bicola.RESET);
    }
}
