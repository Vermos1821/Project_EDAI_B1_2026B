package org.example.recursion;

/**
 * Implementa el TAD Pila (LIFO) para almacenar {@link MarcoLlamada}, es decir,
 * la <b>pila de llamadas</b> (call stack) que usa el sistema para ejecutar
 * métodos recursivos.
 *
 * <p>Utiliza un arreglo de capacidad fija. El fondo es la posición 0 y el atributo
 * {@code tope} guarda el índice del último marco apilado (-1 si está vacía).
 * Es la misma estrategia de {@code PilaArreglo}, pero guardando objetos en lugar
 * de enteros, porque una llamada recursiva necesita guardar varios datos a la vez.</p>
 */
public class PilaMarcos {

    /** Arreglo que almacena los marcos; las posiciones 0..tope son válidas. */
    private final MarcoLlamada[] datos;

    /** Índice del marco de la cima; vale -1 cuando la pila está vacía. */
    private int tope;

    /**
     * Construye una pila vacía.
     *
     * @param capacidad Número máximo de marcos que puede contener.
     * @throws IllegalArgumentException Si la capacidad es menor o igual a cero.
     */
    public PilaMarcos(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva.");
        }
        datos = new MarcoLlamada[capacidad];
        tope = -1;
    }

    /**
     * Apila un marco en la cima (equivale a <i>hacer una llamada</i>).
     *
     * @param marco Marco que se desea apilar.
     * @throws IllegalStateException Si la pila está llena (desbordamiento de pila).
     */
    public void push(MarcoLlamada marco) {
        if (estaLlena()) {
            throw new IllegalStateException("Pila de llamadas llena (StackOverflow simulado)");
        }
        tope++;
        datos[tope] = marco;
    }

    /**
     * Retira y devuelve el marco de la cima (equivale a <i>regresar de una llamada</i>).
     *
     * @return Marco que estaba en la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public MarcoLlamada pop() {
        if (estaVacia()) {
            throw new IllegalStateException("Pila de llamadas vacía");
        }
        MarcoLlamada marco = datos[tope];
        datos[tope] = null;
        tope--;
        return marco;
    }

    /**
     * Consulta el marco de la cima sin retirarlo.
     *
     * @return Marco de la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public MarcoLlamada peek() {
        if (estaVacia()) {
            throw new IllegalStateException("Pila de llamadas vacía");
        }
        return datos[tope];
    }

    /**
     * @return {@code true} si no hay marcos apilados.
     */
    public boolean estaVacia() {
        return tope == -1;
    }

    /**
     * @return {@code true} si se alcanzó la capacidad máxima.
     */
    public boolean estaLlena() {
        return tope == datos.length - 1;
    }

    /**
     * @return Cantidad de marcos apilados.
     */
    public int size() {
        return tope + 1;
    }

    /**
     * @return Índice del marco de la cima (-1 si está vacía).
     */
    public int getTope() {
        return tope;
    }

    /**
     * Consulta el marco de una posición sin modificar la pila.
     *
     * @param indice Posición entre 0 (fondo) y {@code tope} (cima).
     * @return Marco almacenado en esa posición.
     * @throws IndexOutOfBoundsException Si el índice no es válido.
     */
    public MarcoLlamada getMarco(int indice) {
        if (indice < 0 || indice > tope) {
            throw new IndexOutOfBoundsException("Índice fuera de la pila");
        }
        return datos[indice];
    }
}
