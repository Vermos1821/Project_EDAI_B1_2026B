package org.example.pilas;

/**
 * Pila de enteros implementada con un arreglo de capacidad fija.
 * El fondo de la pila es la posicion 0 y tope guarda el indice del
 * ultimo elemento apilado (-1 cuando la pila esta vacia).
 */
public class PilaArreglo {

    /** Arreglo que almacena los elementos; datos[0] es el fondo. */
    private int[] datos;

    /** Índice del elemento del tope; -1 si la pila esta vacía. */
    private int tope;

    /**
     * Crea una pila vacía con la capacidad indicada.
     * @param capacidad número máximo de elementos que puede almacenar
     */
    public PilaArreglo(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positivo");
        }

        datos = new int[capacidad];
        tope = -1;
    }

    /**
     * Agrega un elemento en el tope.
     * @param dato codigo de la página visitada a apilar
     * @throws IllegalStateException si la pila esta llena (desbordamiento)
     */
    public void push(int dato) {
        if (estaLlena()) {
            throw new IllegalStateException("Pila llena");
        }

        tope++;
        datos[tope] = dato;
    }

    /**
     * Quita el elemento del tope y lo devuelve.
     * @return el elemento que estaba en el tope
     * @throws IllegalStateException si la pila esta vacía (overflow)
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
     * Consulta el elemento del tope sin quitarlo.
     * @return el elemento del tope
     * @throws IllegalStateException si la pila esta vacía (overflow)
     */
    public int peek() {
        if (estaVacia()) {
            throw new IllegalStateException("Pila vacía");
        }

        return datos[tope];
    }

    /**
     * Indica si la pila no tiene elementos.
     * @return true si la pila esta vacia
     */
    public boolean estaVacia() {
        return tope == -1;
    }

    /**
     * Indica si la pila alcanzo su capacidad.
     * @return true si la pila esta llena
     */
    public boolean estaLlena() {
        return tope == datos.length - 1;
    }

    /**
     * Cuenta los elementos de la pila.
     * @return la cantidad de elementos apilados
     */
    public int size() {
        return tope + 1;
    }

    public int getTope(){
        return tope;
    }

    public int getCapacidad(){
        return datos.length;
    }

    public int getDato(int indice){
        if(indice < 0 || indice >= datos.length) {
            throw new IndexOutOfBoundsException("Índice fuera del arreglo");
        }

        return datos[indice];
    }

    /**
     * Describe el estado de la pila: contenido del fondo al tope y valor de tope.
     * No es una operacion de la pila; solo sirve para mostrar su estado.
     * @return texto con el estado de la pila
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
