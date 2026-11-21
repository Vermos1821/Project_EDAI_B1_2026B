package org.example.listas;

/**
 * Representa un nodo de una lista simplemente enlazada.
 * Cada nodo conserva un entero y una referencia al nodo siguiente.
 * No se emplean colecciones prediseñadas de Java.
 */
public class Nodo {
    /** Valor almacenado en el nodo. */
    int dato;
    /** Referencia al siguiente nodo; null indica fin de lista. */
    Nodo siguiente;

    /**
     * Crea un nodo inicialmente sin sucesor.
     * @param dato entero que guardará el nodo
     */
    public Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
