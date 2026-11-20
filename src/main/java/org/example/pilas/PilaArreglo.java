package org.example.pilas;

/**
 * Implementa un Tipo Abstrato de Datos (TAD) Pila de enteros a través
 * de un arreglo unidimensional de capacidad fija.
 *
 * <p>La estructura sigue el principio LIFO (Last In, First Out), según
 * el cual el último elemento insertado es el primero en retirarse.</p>
 *
 * <p>El fondo de la pila corresponde a la posición 0 del arreglo,
 * mientras que el atributo {@code tope} almacena el índice del último
 * elemento insertado. Cuando la pila está vacía, {@code tope} vale -1.</p>
 */
public class PilaArreglo {

    /**
     * Arreglo unidimensional el cual almacena los elementos de la pila.
     * Las posiciones comprendidas entre 0 y {@code tope} tienen
     * elementos válidos de la estructura.
     */
    private int[] datos;

    /**
     * Índice de la posición que contiene el elemento que se encuentra en la cima.
     * Su valor inicial es -1, lo cual significa una pila vacía.
     */
    private int tope;

    /**
     * Construye una pila vacía con una capacidad máxima específica.
     *
     * @param capacidad Cantidad máxima de elementos que puede almacenar.
     * @throws IllegalArgumentException Si la capacidad es menor o igual a cero.
     */
    public PilaArreglo(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva.");
        }

        datos = new int[capacidad];
        tope = -1;
    }

    /**
     * Agrega un elemento en la cima de la pila.
     *
     * <p>Primero comprueba que haya espacio disponible. Si la pila no
     * está llena, aumenta el índice {@code tope} y almacena el dato
     * en la nueva posición.</p>
     *
     * @param dato Elemento entero que se desea insertar.
     * @throws IllegalStateException Si la pila está llena.
     */
    public void push(int dato) {
        if (estaLlena()) {
            throw new IllegalStateException("Pila llena");
        }

        tope++;
        datos[tope] = dato;
    }

    /**
     * Saca y devuelve el elemento que se encuentra en la cima de la pila.
     *
     * <p>El elemento se obtiene antes de disminuir el índice {@code tope}.
     * De este modo, la posición anterior deja de pertenecer al contenido
     * lógico de la estructura.</p>
     *
     * @return Elemento que se encontraba en la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public int pop() {
        if (estaVacia()) {
            throw new IllegalStateException("Pila vacía");
        }

        int dato = datos[tope];
        tope--;
        return dato;
    }

    /**
     * Consulta el elemento que está en la cima sin modificar la pila.
     *
     * @return Elemento que está almacenado en la posición indicada por {@code tope}.
     * @throws IllegalStateException Si la pila está vacía
     */
    public int peek() {
        if (estaVacia()) {
            throw new IllegalStateException("Pila vacía");
        }

        return datos[tope];
    }

    /**
     * Verifica si la pila no contiene elementos.
     *
     * @return {@code true} Si {@code tope} es -1; de lo contrario,
     *         {@code false}.
     */
    public boolean estaVacia() {
        return tope == -1;
    }

    /**
     * Comprueba si la pila alcanzó su capacidad máxima.
     *
     * @return {@code true} Si el índice del tope es correspondiente a la última
     *         posición del arreglo; de lo contrario, {@code false}.
     */
    public boolean estaLlena() {
        return tope == datos.length - 1;
    }

    /**
     * Obtiene la cantidad actual de los elementos almacenados.
     *
     * @return Número de elementos que hay entre el fondo y la cima.
     */
    public int size() {
        return tope + 1;
    }

    /**
     * Obtiene el índice actual del elemento que se encuentra ubicado
     * en el tope de la pila.
     *
     * @return Índice del elemento superior, o -1 si la pila está vacía
     */
    public int getTope(){
        return tope;
    }

    /**
     * Obtiene la capacidad máxima de almacenamiento de la estructura.
     *
     * @return Longitud del arreglo que representa la pila.
     */
    public int getCapacidad(){
        return datos.length;
    }

    /**
     * Averigua el valor almacenado en una posición específica del arreglo.
     *
     * <p>Este método permite revisar las posiciones de la estructura
     * para representar su contenido a lo largo de la ejecución.</p>
     *
     * @param indice Posición del arreglo que se desea consultar
     * @return Elemento almacenado en la posición indicada
     * @throws IndexOutOfBoundsException Si el índice no pertenece
     *         al intervalo válido del arreglo.
     */
    public int getDato(int indice){
        if(indice < 0 || indice >= datos.length) {
            throw new IndexOutOfBoundsException("Índice fuera del arreglo");
        }

        return datos[indice];
    }

    /**
     * Genera una representación de manera textual del contenido lógico de la pila.
     *
     * <p>Los elementos se presentan desde el fondo hasta la cima,
     * acompañados del índice actual del tope.</p>
     *
     * @return Representación textual de la pila y de su índice superior
     */
    @Override
    public String toString() {
        if(estaVacia()){
            return "vacia | tope = -1";
        }
        String resultado = "(";

        for(int i = 0; i <= tope; i++){
            resultado += datos[i];
            if(i < tope){
                resultado += ", ";
            }
        }

        resultado += ") | tope " + tope;
        return resultado;
    }
}
