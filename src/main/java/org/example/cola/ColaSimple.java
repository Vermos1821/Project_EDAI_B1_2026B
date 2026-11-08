
package org.example.cola;

/**
 * Estructura de datos de tipo cola simple
 * utilizando un arreglo de tamaño fijo.
 * Esta implementación no reutiliza las posiciones liberadas
 * al inicio del arreglo después de eliminar elementos.
 */
public class ColaSimple implements EstructuraCola {

    /** Arreglo que almacena los elementos de la cola. */
    private int[] elementos;

    /** Índice del primer elemento de la cola. */
    private int frente;

    /** Índice del último elemento de la cola. */
    private int fin;

    /** Cantidad actual de elementos almacenados. */
    private int cantidad;

    /**
     * Construye una cola simple con una capacidad determinada.
     * @throws IllegalArgumentException si la capacidad es menor o igual a cero.
     */
    public ColaSimple(int capacidad) {

        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        elementos = new int[capacidad];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    /**
     * Inserta un elemento al final de la cola.
     * @throws IllegalStateException si la cola está llena
     * o si no existen posiciones posteriores disponibles.
     */
    @Override
    public void enqueue(int dato) {

        if (estaLlena()) {
            throw new IllegalStateException("La cola está llena.");
        }

        if (fin == elementos.length - 1) {
            throw new IllegalStateException(
                    "No existen posiciones disponibles al final de la cola simple."
            );
        }

        elementos[++fin] = dato;
        cantidad++;
    }

    /**
     * Elimina y devuelve el elemento ubicado al frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    @Override
    public int dequeue() {

        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía.");
        }

        int eliminado = elementos[frente++];

        cantidad--;

        if (cantidad == 0) {
            frente = 0;
            fin = -1;
        }

        return eliminado;
    }

    /**
     * Consulta el primer elemento sin eliminarlo.
     * @return elemento ubicado al frente de la cola.
     * @throws IllegalStateException si la cola está vacía.
     */
    @Override
    public int front() {

        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía.");
        }

        return elementos[frente];
    }

    /**
     * Comprueba si la cola no contiene elementos.
     * @return true si está vacía; false en caso contrario.
     */
    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /**
     * Comprueba si se alcanzó la capacidad máxima.
     * @return true si está llena; false en caso contrario.
     */
    @Override
    public boolean estaLlena() {
        return cantidad == elementos.length;
    }

    /**
     * Obtiene la cantidad de elementos almacenados.
     * @return número actual de elementos.
     */
    @Override
    public int size() {
        return cantidad;
    }

    /**
     * Obtiene la capacidad máxima de la cola.
     * @return tamaño del arreglo.
     */
    @Override
    public int capacidad() {
        return elementos.length;
    }

    /**
     * Devuelve el nombre de la estructura implementada.
     */
    @Override
    public String tipo() {
        return "COLA SIMPLE";
    }

    /**
     * Genera una vista de todas las posiciones del arreglo.
     */
    @Override
    public String[] obtenerVista() {

        String[] vista = new String[elementos.length];

        for (int i = 0; i < elementos.length; i++) {

            if (!estaVacia() && i >= frente && i <= fin) {
                vista[i] = String.valueOf(elementos[i]);
            } else {
                vista[i] = "LIBRE";
            }
        }

        return vista;
    }

    /**
     * Obtiene el índice del primer elemento.
     */
    @Override
    public int indiceFrente() {
        return estaVacia() ? -1 : frente;
    }

    /**
     * Obtiene el índice del último elemento.
     */
    @Override
    public int indiceFinal() {
        return estaVacia() ? -1 : fin;
    }
}
