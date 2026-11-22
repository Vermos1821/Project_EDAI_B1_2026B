
package org.example.array;

import java.util.Arrays;

/**
 * Implementación de la estructura de datos de un Array Unidimensional de capacidad fija.
 *
 * <p>Administra un conjunto de elementos enteros manteniendo un control explícito
 * del tamaño actual y la capacidad máxima disponible.</p>
 */
public class Array {

    /**
     * Arreglo interno para almacenar los datos.
     */
    private final int[] datos;

    /**
     * Cantidad actual de elementos almacenados en el arreglo.
     */
    private int tamanio;

    /**
     * Construye un arreglo unidimensional vacío con la capacidad especificada.
     *
     * @param capacidad Número máximo de elementos que puede contener el arreglo.
     * @throws IllegalArgumentException Si la capacidad es menor o igual a cero.
     */
    public Array(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.datos = new int[capacidad];
        this.tamanio = 0;
    }

    /**
     * Verifica si el arreglo no contiene elementos.
     *
     * @return {@code true} si el tamaño es 0; de lo contrario {@code false}.
     */
    public boolean estaVacio() {
        return tamanio == 0;
    }

    /**
     * Verifica si el arreglo ha alcanzado su capacidad máxima.
     *
     * @return {@code true} si el tamaño es igual a la capacidad; de lo contrario {@code false}.
     */
    public boolean estaLleno() {
        return tamanio == datos.length;
    }

    /**
     * Obtiene el número de elementos actualmente almacenados.
     *
     * @return Tamaño actual del arreglo.
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Obtiene la capacidad máxima de almacenamiento.
     *
     * @return Longitud del arreglo interno.
     */
    public int getCapacidad() {
        return datos.length;
    }

    /**
     * Obtiene el elemento almacenado en una posición específica.
     *
     * @param indice Posición a consultar (0 a capacidad - 1).
     * @return Valor almacenado en la posición indicada.
     * @throws IndexOutOfBoundsException Si el índice está fuera del rango del arreglo.
     */
    public int getDato(int indice) {
        if (indice < 0 || indice >= datos.length) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites del arreglo.");
        }
        return datos[indice];
    }

    /**
     * Inserta un elemento en una posición específica del arreglo.
     *
     * <p>Desplaza hacia la derecha los elementos desde la posición indicada
     * para mantener la continuidad y el orden de los datos.</p>
     *
     * @param posicion Posición donde se insertará el elemento (0 a tamano).
     * @param elemento Valor entero a insertar.
     * @throws IllegalStateException Si el arreglo está lleno.
     * @throws IndexOutOfBoundsException Si la posición es inválida.
     */
    public void insertar(int posicion, int elemento) {
        if (estaLleno()) {
            throw new IllegalStateException("El array está lleno.");
        }
        if (posicion < 0 || posicion > tamanio) {
            throw new IndexOutOfBoundsException("Posición inválida para inserción.");
        }

        // Desplazamiento hacia la derecha
        for (int i = tamanio; i > posicion; i--) {
            datos[i] = datos[i - 1]; //Desplazando los elementos
        }

        datos[posicion] = elemento;
        tamanio++;
    }

    /**
     * Elimina el elemento ubicado en una posición específica.
     *
     * <p>Desplaza hacia la izquierda los elementos restantes para cerrar el hueco
     * dejado por el elemento eliminado.</p>
     *
     * @param posicion Posición del elemento a eliminar (0 a tamano - 1).
     * @return El elemento que fue eliminado.
     * @throws IllegalStateException Si el arreglo está vacío.
     * @throws IndexOutOfBoundsException Si la posición es inválida.
     */
    public int eliminar(int posicion) {
        if (estaVacio()) {
            throw new IllegalStateException("El array está vacío.");
        }
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Posición inválida para eliminación.");
        }

        int elementoEliminado = datos[posicion];

        // Desplazamiento hacia la izquierda
        for (int i = posicion; i < tamanio - 1; i++) {
            datos[i] = datos[i + 1];
        }

        datos[tamanio - 1] = 0; // Limpiar la posición lógica liberada
        tamanio--;

        return elementoEliminado;
    }

    /**
     * Busca todas las apariciones de un valor entre los elementos almacenados.
     *
     * <p>Recorre únicamente las posiciones ocupadas (0 a tamano - 1) comparando
     * cada elemento con el valor buscado.</p>
     *
     * @param valor Valor entero a buscar.
     * @return Posiciones donde se encontró el valor, en orden ascendente;
     *         un arreglo vacío si el valor no existe.
     */
    public int[] buscar(int valor) {
        int[] posiciones = new int[tamanio];
        int coincidencias = 0;

        for (int i = 0; i < tamanio; i++) {
            if (datos[i] == valor) {
                posiciones[coincidencias] = i;
                coincidencias++;
            }
        }

        return Arrays.copyOf(posiciones, coincidencias);
    }

}