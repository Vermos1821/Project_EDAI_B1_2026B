
package org.example.cola;

/**
 * Cola circular de enteros implementada mediante un arreglo de capacidad fija.
 *
 * Utiliza el operador módulo (%) para regresar al inicio del arreglo
 * cuando se alcanza la última posición.
 */
public class ColaCircular {

    /**
     * Arreglo que almacena los elementos de la cola circular.
     */
    private int[] datos;

    /**
     * Índice del primer elemento almacenado.
     */
    private int frente;

    /**
     * Índice del último elemento almacenado.
     */
    private int fin;

    /**
     * Cantidad actual de elementos almacenados.
     */
    private int cantidad;

    /**
     * Crea una cola circular vacía con la capacidad indicada.
     *
     * @param capacidad número máximo de elementos que puede almacenar.
     * @throws IllegalArgumentException si la capacidad es menor o igual a cero.
     */
    public ColaCircular(int capacidad) {

        // Comprueba que la capacidad sea válida.
        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        // Crea el arreglo.
        datos = new int[capacidad];

        // Inicializa los índices.
        frente = 0;
        fin = -1;

        // Inicialmente no existen elementos.
        cantidad = 0;
    }

    /**
     * Inserta un elemento al final de la cola circular.
     * @param dato elemento que se desea insertar.
     * @throws IllegalStateException si la cola está llena (desbordamiento).
     */
    public void enqueue(int dato) {

        // Comprueba si se alcanzó la capacidad máxima.
        if (estaLlena()) {
            throw new IllegalStateException(
                    "Cola circular llena. No se pueden insertar más elementos."
            );
        }

        // Si está vacía, el primer elemento comienza en cero.
        if (estaVacia()) {
            frente = 0;
        }

        // Calcula la siguiente posición final de forma circular.
        fin = (fin + 1) % datos.length;

        // Almacena el elemento en la posición calculada.
        datos[fin] = dato;

        // Incrementa la cantidad de elementos.
        cantidad++;
    }

    /**
     * Elimina y devuelve el elemento ubicado al frente.
     * @return elemento que fue eliminado.
     * @throws IllegalStateException si la cola está vacía (subdesbordamiento).
     */
    public int dequeue() {

        // Comprueba si existen elementos.
        if (estaVacia()) {
            throw new IllegalStateException(
                    "Cola circular vacía. No se pueden eliminar elementos."
            );
        }

        // Guarda el elemento que será eliminado.
        int dato = datos[frente];

        // Disminuye la cantidad de elementos.
        cantidad--;

        // Comprueba si se eliminó el último elemento.
        if (cantidad == 0) {

            // Reinicia los índices porque la cola queda vacía.
            frente = 0;
            fin = -1;

        } else {

            // Avanza el frente de forma circular.
            frente = (frente + 1) % datos.length;
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
                    "No se puede consultar. La cola circular está vacía."
            );
        }

        // Devuelve el elemento del frente.
        return datos[frente];
    }

    /**
     * Indica si la cola circular está vacía
     * @return true si no existen elementos; false en caso contrario.
     */
    public boolean estaVacia() {

        // Si la cantidad es cero, la cola está vacía.
        return cantidad == 0;
    }

    /**
     * Indica si la cola circular alcanzó su capacidad máxima.
     * @return true si está llena; false en caso contrario.
     */
    public boolean estaLlena() {

        // Compara la cantidad actual con la capacidad.
        return cantidad == datos.length;
    }

    /**
     * Obtiene la cantidad actual de elementos.
     * @return número de elementos almacenados.
     */
    public int size() {

        return cantidad;
    }

    /**
     * Obtiene el índice actual del frente.
     * @return índice del frente; -1 si la cola está vacía.
     */
    public int getFrente() {

        return estaVacia() ? -1 : frente;
    }

    /**
     * Obtiene el índice actual del final.
     * @return índice del final; -1 si la cola está vacía.
     */
    public int getFin() {

        return fin;
    }

    /**
     * Obtiene la capacidad máxima del arreglo.
     * @return cantidad máxima de elementos que puede almacenar.
     */
    public int getCapacidad() {

        return datos.length;
    }

    /**
     * Obtiene el elemento almacenado en una posición del arreglo.
     * @param indice posición que se desea consultar.
     * @return elemento almacenado en la posición indicada.
     * @throws IndexOutOfBoundsException si el índice está fuera del arreglo.
     */
    public int getDato(int indice) {

        // Comprueba que el índice esté dentro de los límites.
        if (indice < 0 || indice >= datos.length) {
            throw new IndexOutOfBoundsException(
                    "Índice fuera de los límites del arreglo."
            );
        }

        // Devuelve el elemento almacenado.
        return datos[indice];
    }

    /**
     * Describe el estado actual de la cola circular.
     * @return texto con los elementos desde el frente siguiendo el recorrido circular.
     */
    @Override
    public String toString() {

        // Comprueba si la cola está vacía.
        if (estaVacia()) {
            return "Cola circular vacía | frente = -1 | fin = -1";
        }

        // Inicializa la representación.
        String resultado = "(";

        // Recorre únicamente los elementos que pertenecen a la cola.
        for (int i = 0; i < cantidad; i++) {

            // Calcula la posición circular del elemento.
            int indice = (frente + i) % datos.length;

            // Agrega el elemento actual.
            resultado += datos[indice];

            // Separa los elementos mediante comas.
            if (i < cantidad - 1) {
                resultado += ", ";
            }
        }

        // Agrega los índices actuales.
        resultado += ") | frente = " + frente + " | fin = " + fin;

        return resultado;
    }
}
