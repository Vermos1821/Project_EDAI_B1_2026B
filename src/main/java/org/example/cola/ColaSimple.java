
package org.example.cola;
/**
 * Cola de enteros implementada mediante un arreglo de capacidad fija.
 * El frente indica el índice del primer elemento almacenado y fin
 * indica el índice del último elemento insertado.
 * Cuando la cola está vacía, fin tiene el valor -1.
 */
public class ColaSimple {

    /**
     * Arreglo que almacena los elementos de la cola.
     */
    private int[] datos;

    /**
     * Índice del primer elemento de la cola.
     */
    private int frente;

    /**
     * Índice del último elemento de la cola.
     * Tiene el valor -1 cuando la cola está vacía.
     */
    private int fin;

    /**
     * Crea una cola simple vacía con la capacidad indicada.
     * @param capacidad número máximo de elementos que puede almacenar.
     * @throws IllegalArgumentException si la capacidad es menor o igual a cero.
     */
    public ColaSimple(int capacidad) {

        // Comprueba que la capacidad sea válida.
        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        // Crea el arreglo con la capacidad indicada.
        datos = new int[capacidad];

        // Inicializa el frente en la primera posición.
        frente = 0;

        // Indica que la cola inicialmente está vacía.
        fin = -1;
    }

    /**
     * Inserta un elemento al final de la cola.
     * @param dato elemento que se desea insertar.
     * @throws IllegalStateException si la cola está llena (desbordamiento).
     */
    public void enqueue(int dato) {

        // Comprueba si se alcanzó la última posición del arreglo.
        if (estaLlena()) {
            throw new IllegalStateException(
                    "Cola simple llena. No se pueden insertar más elementos."
            );
        }

        // Si la cola está vacía, el primer elemento comienza en cero.
        if (estaVacia()) {
            frente = 0;
        }

        // Avanza el índice final una posición.
        fin++;

        // Almacena el elemento en la nueva posición final.
        datos[fin] = dato;
    }

    /**
     * Elimina y devuelve el elemento ubicado al frente de la cola.
     * @return elemento que fue eliminado.
     * @throws IllegalStateException si la cola está vacía (subdesbordamiento).
     */
    public int dequeue() {

        // Comprueba si existen elementos para eliminar.
        if (estaVacia()) {
            throw new IllegalStateException(
                    "Cola simple vacía. No se pueden eliminar elementos."
            );
        }

        // Guarda el elemento ubicado en el frente.
        int dato = datos[frente];

        // Comprueba si era el último elemento.
        if (frente == fin) {

            // Reinicia los índices porque la cola queda vacía.
            frente = 0;
            fin = -1;

        } else {

            // Avanza el frente hacia el siguiente elemento.
            frente++;
        }

        // Devuelve el elemento eliminado.
        return dato;
    }

    /**
     * Consulta el elemento ubicado al frente sin eliminarlo.
     * @return elemento que se encuentra al frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    public int peek() {

        // Comprueba si la cola contiene elementos.
        if (estaVacia()) {
            throw new IllegalStateException(
                    "No se puede consultar. La cola está vacía."
            );
        }

        // Devuelve el elemento del frente.
        return datos[frente];
    }

    /**
     * Indica si la cola no contiene elementos.
     * @return true si la cola está vacía; false en caso contrario.
     */
    public boolean estaVacia() {

        // Si fin es -1, no existen elementos almacenados.
        return fin == -1;
    }

    /**
     * Indica si la cola alcanzó la última posición del arreglo.
     * @return true si la cola está llena; false en caso contrario.
     */
    public boolean estaLlena() {

        // Comprueba si el índice final llegó al último espacio.
        return fin == datos.length - 1;
    }

    /**
     * Obtiene la cantidad actual de elementos de la cola.
     * @return número de elementos almacenados.
     */
    public int size() {

        // Si está vacía, la cantidad de elementos es cero.
        if (estaVacia()) {
            return 0;
        }

        // Calcula los elementos comprendidos entre frente y fin.
        return fin - frente + 1;
    }

    /**
     * Obtiene el índice actual del frente.
     * @return índice del frente; -1 cuando la cola está vacía.
     */
    public int getFrente() {

        // Devuelve -1 si no existen elementos.
        return estaVacia() ? -1 : frente;
    }

    /**
     * Obtiene el índice actual del final.
     * @return índice del final; -1 cuando la cola está vacía.
     */
    public int getFin() {

        return fin;
    }

    /**
     * Obtiene la capacidad máxima de almacenamiento.
     *
     * @return cantidad máxima de elementos que puede almacenar.
     */
    public int getCapacidad() {

        return datos.length;
    }

    /**
     * Obtiene el elemento almacenado en una posición específica.
     *
     * @param indice posición del arreglo que se desea consultar.
     * @return elemento almacenado en la posición indicada.
     * @throws IndexOutOfBoundsException si el índice está fuera del arreglo.
     */
    public int getDato(int indice) {

        // Comprueba que el índice pertenezca al arreglo.
        if (indice < 0 || indice >= datos.length) {
            throw new IndexOutOfBoundsException(
                    "Índice fuera de los límites del arreglo."
            );
        }

        // Devuelve el elemento de la posición indicada.
        return datos[indice];
    }

    /**
     * Describe el estado actual de la cola simple.
     *
     * @return texto con los elementos desde el frente hasta el final.
     */
    @Override
    public String toString() {

        // Comprueba si la cola está vacía.
        if (estaVacia()) {
            return "Cola vacía | frente = -1 | fin = -1";
        }

        // Variable que almacena la representación de la cola.
        String resultado = "(";

        // Recorre los elementos desde el frente hasta el final.
        for (int i = frente; i <= fin; i++) {

            // Agrega el elemento actual.
            resultado += datos[i];

            // Agrega una coma entre los elementos.
            if (i < fin) {
                resultado += ", ";
            }
        }

        // Agrega los índices actuales.
        resultado += ") | frente = " + frente + " | fin = " + fin;

        return resultado;
    }
}
