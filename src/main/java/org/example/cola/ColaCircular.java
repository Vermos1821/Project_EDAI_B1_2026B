
package org.example.cola;

/**
 * Implementa una cola circular utilizando un arreglo de enteros.
 *
 */
public class ColaCircular implements EstructuraCola {

    /**
     * Arreglo que almacena los elementos de la cola.
     */
    private int[] datos;

    /**
     * Índice que indica la posición del primer elemento de la cola.
     */
    private int frente = 0;

    /**
     * Índice que indica la posición del último elemento insertado.
     */
    private int fin = -1;

    /**
     * Cantidad actual de elementos almacenados en la cola.
     */
    private int cantidad = 0;

    /**
     * Construye una cola circular con una capacidad determinada.
     * @throws IllegalArgumentException si la capacidad es menor o igual a cero.
     */
    public ColaCircular(int capacidad) {

        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero"
            );
        }

        datos = new int[capacidad];
    }

    /**
     * Inserta un nuevo elemento al final de la cola.
     *
     * Se utiliza el operador módulo (%) para que el índice final
     * vuelva al inicio del arreglo cuando alcanza la última posición.
     * @throws IllegalStateException si la cola está llena.
     */
    @Override
    public void enqueue(int dato) {

        if (estaLlena()) {
            throw new IllegalStateException("Cola circular llena");
        }

        fin = (fin + 1) % datos.length;

        datos[fin] = dato;

        cantidad++;
    }

    /**
     * Elimina y devuelve el primer elemento de la cola.
     * @return elemento eliminado de la cola.
     * @throws IllegalStateException si la cola está vacía.
     */
    @Override
    public int dequeue() {

        if (estaVacia()) {
            throw new IllegalStateException("Cola circular vacia");
        }

        int dato = datos[frente];

        frente = (frente + 1) % datos.length;

        cantidad--;

        if (cantidad == 0) {
            frente = 0;
            fin = -1;
        }

        return dato;
    }

    /**
     * Consulta el primer elemento de la cola sin eliminarlo.
     * @return elemento ubicado en el frente de la cola.
     * @throws IllegalStateException si la cola está vacía.
     */
    @Override
    public int front() {

        if (estaVacia()) {
            throw new IllegalStateException("Cola vacia");
        }

        return datos[frente];
    }

    /**
     * Comprueba si la cola no contiene elementos.
     * @return true si la cola está vacía; false en caso contrario.
     */
    @Override
    public boolean estaVacia() {

        return cantidad == 0;
    }

    /**
     * Comprueba si la cola alcanzó su capacidad máxima.
     * @return true si la cola está llena; false en caso contrario.
     */
    @Override
    public boolean estaLlena() {

        return cantidad == datos.length;
    }

    /**
     * Obtiene la cantidad actual de elementos almacenados
     * @return número de elementos que contiene la cola.
     */
    @Override
    public int size() {

        return cantidad;
    }

    /**
     * Obtiene la capacidad máxima del arreglo.
     * @return cantidad máxima de elementos que puede almacenar.
     */
    @Override
    public int capacidad() {
        return datos.length;
    }

    /**
     * Obtiene el nombre de la estructura de datos.
     * @return texto que identifica la estructura como COLA CIRCULAR.
     */
    @Override
    public String tipo() {
        return "COLA CIRCULAR";
    }

    /**
     * Obtiene el índice donde se encuentra el primer elemento.
     * @return índice del frente o -1 si la cola está vacía.
     */
    @Override
    public int indiceFrente() {
        return estaVacia() ? -1 : frente;
    }

    /**
     * Obtiene el índice donde se encuentra el último elemento.
     * @return índice final o -1 si la cola está vacía.
     */
    @Override
    public int indiceFinal() {
        return estaVacia() ? -1 : fin;
    }

    /**
     * Genera una representación visual del arreglo de la cola.
     * @return arreglo de cadenas que representa el estado actual de la cola.
     */
    @Override
    public String[] obtenerVista() {

        String[] vista = new String[datos.length];

        for (int i = 0; i < datos.length; i++) {
            vista[i] = " ";
        }

        for (int i = 0; i < cantidad; i++) {

            int indice = (frente + i) % datos.length;

            vista[indice] = String.valueOf(datos[indice]);
        }

        return vista;
    }
}
