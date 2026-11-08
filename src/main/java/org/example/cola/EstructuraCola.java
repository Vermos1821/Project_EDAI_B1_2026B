
package org.example.cola;

/**
 * Esta interfaz permite trabajar de manera uniforme con una
 * cola simple y una cola circular, estableciendo los métodos
 * necesarios para insertar, eliminar, consultar y visualizar
 * los elementos almacenados.
 *
 */
public interface EstructuraCola {

    /**
     * Inserta un nuevo elemento al final de la cola.
     *
     * @throws IllegalStateException si la cola se encuentra llena.
     */
    void enqueue(int dato);

    /**
     * Elimina y devuelve el elemento ubicado al frente de la cola.
     *
     * @return elemento entero que fue eliminado.
     * @throws IllegalStateException si la cola se encuentra vacía.
     */
    int dequeue();

    /**
     * Consulta el elemento ubicado al frente de la cola
     * sin eliminarlo.
     *
     * @return elemento entero ubicado al frente.
     * @throws IllegalStateException si la cola se encuentra vacía.
     */
    int front();

    /**
     * Comprueba si la cola no contiene elementos.
     *
     * @return true si la cola está vacía; false en caso contrario.
     */
    boolean estaVacia();

    /**
     * Comprueba si la cola alcanzó su capacidad máxima.
     *
     * @return true si la cola está llena; false en caso contrario.
     */
    boolean estaLlena();

    /**
     * Obtiene la cantidad actual de elementos almacenados.
     *
     * @return número de elementos que contiene la cola.
     */
    int size();

    /**
     * Obtiene la capacidad máxima de almacenamiento de la cola.
     *
     * @return número máximo de elementos que puede almacenar.
     */
    int capacidad();

    /**
     * Obtiene el nombre del tipo de cola implementada.
     *
     * @return descripción del tipo de estructura de cola.
     */
    String tipo();

    /**
     * Genera una representación de las posiciones del arreglo
     * para facilitar la visualización de la estructura.
     *
     * @return arreglo de cadenas con los elementos almacenados
     * y las posiciones disponibles.
     */
    String[] obtenerVista();

    /**
     * Obtiene el índice de la posición que contiene el primer
     * elemento de la cola.
     */
    int indiceFrente();

    /**
     * Obtiene el índice de la posición que contiene el último
     * elemento de la cola.
     */
    int indiceFinal();
}
