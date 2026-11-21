package org.example.listas;

import org.example.util.Color;
import java.util.Scanner;

/**
 * Lista simplemente enlazada de enteros con demostraciones de consola.
 * Cada operación presenta el estado inicial, cambios reales, variables
 * auxiliares y estado final. Las posiciones comienzan en cero.
 */
public class ListaSimple {
    /** Primer nodo; null significa que la lista está vacía. */
    private Nodo cabeza;
    /** Número de nodos enlazados. */
    private int cantidad;
    /** Scanner compartido, utilizado únicamente para las pausas opcionales. */
    private final Scanner scanner;
    /** Controla si se detiene la ejecución después de cada paso. */
    private boolean pausas;
    /** Contador de pasos de la operación actual. */
    private int paso;

    /**
     * Construye una lista inicialmente vacía.
     * @param scanner lector compartido por el menú principal
     */
    public ListaSimple(Scanner scanner) {
        this.scanner = scanner;
        this.cabeza = null;
        this.cantidad = 0;
    }

    /** @return número actual de nodos */
    public int getCantidad() { return cantidad; }
    /** @return true si no existe ningún nodo */
    public boolean estaVacia() { return cabeza == null; }
    /** @return true cuando están habilitadas las pausas */
    public boolean getPausas() { return pausas; }
    /** @param activadas nuevo estado de las pausas */
    public void setPausas(boolean activadas) { pausas = activadas; }

    /**
     * Dibuja la cadena de referencias tal como existe en memoria.
     * Se representan valores y enlaces, no direcciones de memoria ficticias.
     */
    public void dibujar() {
        System.out.print(Color.CIAN + "  cabeza -> " + Color.RESET);
        Nodo actual = cabeza;
        if (actual == null) {
            System.out.println(Color.GRIS + "null  (lista vacia)" + Color.RESET);
        } else {
            while (actual != null) {
                System.out.print(Color.BLANCO + "[" + actual.dato + " | "
                        + (actual.siguiente == null ? "null" : "*") + "]" + Color.RESET);
                actual = actual.siguiente;
                if (actual != null) System.out.print(Color.VERDE + " --> " + Color.RESET);
            }
            System.out.println();
        }
        System.out.println(Color.GRIS + "  cantidad = " + cantidad + Color.RESET);
    }

    /**
     * Inicia la demostración de una operación mostrando la estructura previa.
     * @param nombre título de la operación
     */
    private void inicio(String nombre) {
        paso = 0;
        System.out.println();
        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║  " + nombre);
        Color.titulo("  ╚══════════════════════════════════════════════╝");
        System.out.println(Color.AZUL + Color.NEGRITA + "  [1] ESTADO INICIAL" + Color.RESET);
        dibujar();
        System.out.println(Color.AZUL + Color.NEGRITA + "  [2] EJECUCION PASO A PASO" + Color.RESET);
    }

    /**
     * Registra una acción que acaba de ocurrir y las variables relevantes.
     * @param accion descripción de la acción real
     * @param variables estado actual de variables auxiliares
     */
    private void paso(String accion, String variables) {
        System.out.println(Color.CIAN + "  Paso " + (++paso) + ": " + Color.RESET + accion);
        System.out.println(Color.AMARILLO + "    Variables: " + variables + Color.RESET);
        dibujar();
        if (pausas) {
            System.out.print(Color.GRIS + "    ENTER para continuar..." + Color.RESET);
            scanner.nextLine();
        }
    }

    /** Cierra una operación con el estado final real. */
    private void fin() {
        System.out.println(Color.AZUL + Color.NEGRITA + "  [3] ESTADO FINAL" + Color.RESET);
        dibujar();
        Color.separador();
    }

    /**
     * Elimina todas las referencias de la lista, mostrando el cambio.
     */
    public void inicializar() {
        inicio("INICIALIZAR / REINICIAR LISTA");
        cabeza = null;
        cantidad = 0;
        paso("cabeza = null; cantidad = 0", "cabeza=null, cantidad=0");
        Color.exito("Lista vacia inicializada.");
        fin();
    }

    /**
     * Inserta un nuevo nodo antes de la cabeza existente. O(1).
     * @param dato valor del nuevo nodo
     */
    public void insertarInicio(int dato) {
        inicio("INSERTAR AL INICIO");
        Nodo nuevo = new Nodo(dato);
        paso("Se crea el nodo nuevo sin sucesor.", "nuevo.dato=" + dato + ", nuevo.siguiente=null");
        nuevo.siguiente = cabeza;
        paso("nuevo.siguiente apunta a la cabeza anterior.", "nuevo=" + dato + ", siguiente=" + valor(nuevo.siguiente));
        cabeza = nuevo;
        cantidad++;
        paso("La cabeza ahora apunta a nuevo; aumenta cantidad.", "cabeza=" + cabeza.dato + ", cantidad=" + cantidad);
        Color.exito("Elemento insertado al inicio.");
        fin();
    }

    /**
     * Inserta un nodo después del último elemento, o como cabeza si está vacía.
     * @param dato valor a insertar
     */
    public void insertarFinal(int dato) {
        inicio("INSERTAR AL FINAL");
        Nodo nuevo = new Nodo(dato);
        paso("Se crea el nodo nuevo.", "nuevo.dato=" + dato + ", nuevo.siguiente=null");
        if (cabeza == null) {
            cabeza = nuevo;
            cantidad++;
            paso("Lista vacia: cabeza apunta al nuevo nodo.", "cabeza=" + dato + ", cantidad=" + cantidad);
        } else {
            Nodo actual = cabeza;
            paso("actual comienza en cabeza.", "actual=" + actual.dato);
            while (actual.siguiente != null) {
                actual = actual.siguiente;
                paso("actual avanza al siguiente nodo.", "actual=" + actual.dato);
            }
            actual.siguiente = nuevo;
            cantidad++;
            paso("El ultimo nodo enlaza a nuevo; aumenta cantidad.", "actual=" + actual.dato + ", nuevo=" + dato + ", cantidad=" + cantidad);
        }
        Color.exito("Elemento insertado al final.");
        fin();
    }

    /**
     * Inserta en la posición indicada (índice base cero).
     * @param dato entero a insertar
     * @param posicion posición válida entre 0 y cantidad, inclusive
     */
    public void insertarPosicion(int dato, int posicion) {
        inicio("INSERTAR EN POSICION " + posicion);
        if (posicion < 0 || posicion > cantidad) {
            paso("Se rechaza la posicion fuera de rango; no hay cambios.", "posicion=" + posicion + ", rango=[0," + cantidad + "]");
            Color.error("Posicion invalida.");
            fin();
            return;
        }
        Nodo nuevo = new Nodo(dato);
        paso("Se crea nuevo nodo.", "nuevo=" + dato + ", posicion=" + posicion);
        if (posicion == 0) {
            nuevo.siguiente = cabeza;
            paso("nuevo.siguiente recibe la cabeza anterior.", "nuevo.siguiente=" + valor(nuevo.siguiente));
            cabeza = nuevo;
            cantidad++;
            paso("Se cambia cabeza y aumenta cantidad.", "cabeza=" + dato + ", cantidad=" + cantidad);
        } else {
            Nodo anterior = cabeza;
            int indice = 0;
            paso("anterior comienza en cabeza.", "anterior=" + anterior.dato + ", indice=0");
            while (indice < posicion - 1) {
                anterior = anterior.siguiente;
                indice++;
                paso("anterior avanza hasta posicion-1.", "anterior=" + anterior.dato + ", indice=" + indice);
            }
            nuevo.siguiente = anterior.siguiente;
            paso("nuevo conserva el enlace al sucesor original.", "nuevo.siguiente=" + valor(nuevo.siguiente) + ", anterior=" + anterior.dato);
            anterior.siguiente = nuevo;
            cantidad++;
            paso("anterior enlaza al nuevo nodo; aumenta cantidad.", "anterior=" + anterior.dato + ", nuevo=" + dato + ", cantidad=" + cantidad);
        }
        Color.exito("Insercion completada.");
        fin();
    }

    /** Recorre todos los nodos y muestra el índice de cada uno. */
    public void recorrer() {
        inicio("RECORRER Y MOSTRAR");
        Nodo actual = cabeza;
        int indice = 0;
        if (actual == null) paso("No hay nodos para recorrer.", "actual=null, indice=0");
        while (actual != null) {
            paso("Se visita el nodo en la posicion " + indice + ".", "actual=" + actual.dato + ", indice=" + indice + ", siguiente=" + valor(actual.siguiente));
            actual = actual.siguiente;
            indice++;
        }
        paso("Termina el recorrido al llegar a null.", "actual=null, nodosVisitados=" + indice);
        fin();
    }

    /**
     * Busca todas las apariciones del valor, informando sus posiciones.
     * @param dato valor buscado
     */
    public void buscar(int dato) {
        inicio("BUSCAR VALOR " + dato);
        Nodo actual = cabeza;
        int indice = 0, encontrados = 0;
        while (actual != null) {
            boolean coincide = actual.dato == dato;
            paso("Comparar " + actual.dato + " == " + dato + " -> " + coincide,
                    "actual=" + actual.dato + ", indice=" + indice + ", encontrado=" + coincide);
            if (coincide) {
                encontrados++;
                Color.exito("Coincidencia en posicion " + indice + ".");
            }
            actual = actual.siguiente;
            indice++;
        }
        if (encontrados == 0) Color.advertencia("Valor no encontrado.");
        else Color.info("Coincidencias encontradas: " + encontrados);
        fin();
    }

    /**
     * Elimina la primera aparición de un valor y reconecta los nodos.
     * @param dato valor a eliminar
     */
    public void eliminarValor(int dato) {
        inicio("ELIMINAR VALOR " + dato);
        Nodo actual = cabeza;
        Nodo anterior = null;
        int indice = 0;
        while (actual != null && actual.dato != dato) {
            paso("Comparar: " + actual.dato + " != " + dato + "; continuar.",
                    "anterior=" + valor(anterior) + ", actual=" + actual.dato + ", indice=" + indice);
            anterior = actual;
            actual = actual.siguiente;
            indice++;
        }
        if (actual == null) {
            paso("Se alcanzo null sin hallar el valor.", "anterior=" + valor(anterior) + ", actual=null");
            Color.advertencia("No se encontro el valor; lista sin cambios.");
        } else {
            paso("Se encontro el nodo que debe eliminarse.", "anterior=" + valor(anterior) + ", actual=" + actual.dato + ", siguiente=" + valor(actual.siguiente));
            if (anterior == null) {
                cabeza = actual.siguiente;
                cantidad--;
                paso("Se actualiza cabeza para saltar el primer nodo.", "cabeza=" + valor(cabeza) + ", cantidad=" + cantidad);
            } else {
                anterior.siguiente = actual.siguiente;
                cantidad--;
                paso("anterior.siguiente salta el nodo eliminado.", "anterior=" + anterior.dato + ", siguiente=" + valor(anterior.siguiente) + ", cantidad=" + cantidad);
            }
            actual.siguiente = null;
            paso("Se desconecta el nodo retirado.", "retirado=" + actual.dato + ", retirado.siguiente=null");
            Color.exito("Primera aparicion del valor eliminada.");
        }
        fin();
    }

    /**
     * Elimina el nodo de una posición válida (base cero).
     * @param posicion índice del nodo a eliminar
     */
    public void eliminarPosicion(int posicion) ("Se desconecta el nodo retirado.", "retirado=" + actual.dato + ", retirado.siguiente=null");
        Co{
            inicio("ELIMINAR POSICION " + posicion);
            if (posicion < 0 || posicion >= cantidad) {
                paso("Posicion inexistente; no se modifica la lista.", "posicion=" + posicion + ", rango=[0," + (cantidad - 1) + "]");
                Color.error("Posicion invalida.");
                fin();
                return;
            }
            Nodo actual;
            if (posicion == 0) {
                actual = cabeza;
                paso("Se identifica el primer nodo.", "actual=" + actual.dato + ", siguiente=" + valor(actual.siguiente));
                cabeza = cabeza.siguiente;
                cantidad--;
                paso("cabeza avanza al siguiente nodo.", "cabeza=" + valor(cabeza) + ", cantidad=" + cantidad);
            } else {
                Nodo anterior = cabeza;
                int indice = 0;
                paso("anterior comienza en cabeza.", "anterior=" + anterior.dato + ", indice=0");
                while (indice < posicion - 1) {
                    anterior = anterior.siguiente;
                    indice++;
                    paso("Se avanza anterior.", "anterior=" + anterior.dato + ", indice=" + indice);
                }
                actual = anterior.siguiente;
                paso("Se identifica el nodo objetivo.", "anterior=" + anterior.dato + ", actual=" + actual.dato + ", siguiente=" + valor(actual.siguiente));
                anterior.siguiente = actual.siguiente;
                cantidad--;
                paso("Se reconecta anterior con el sucesor.", "anterior=" + anterior.dato + ", siguiente=" + valor(anterior.siguiente) + ", cantidad=" + cantidad);
            }
            actual.siguiente = null;
            pasolor.exito("Nodo eliminado en posicion " + posicion + ".");
        fin();
    }

    /** Muestra si la lista está vacía sin modificarla. */
    public void consultarVacia() {
        inicio("CONSULTAR SI ESTA VACIA");
        paso("Se comprueba cabeza == null.", "cabeza=" + valor(cabeza) + ", estaVacia=" + estaVacia());
        Color.info(estaVacia() ? "La lista esta vacia." : "La lista contiene elementos.");
        fin();
    }

    /** Muestra la cantidad sin modificar la lista. */
    public void consultarCantidad() {
        inicio("CONSULTAR CANTIDAD");
        paso("Se lee el contador actualizado por las operaciones.", "cantidad=" + cantidad);
        Color.info("Cantidad de nodos: " + cantidad);
        fin();
    }

    /** Muestra cada referencia siguiente, con el índice y dato del nodo. */
    public void mostrarEnlaces() {
        inicio("MOSTRAR ENLACES");
        Nodo actual = cabeza;
        int indice = 0;
        if (actual == null) paso("No existen enlaces porque cabeza es null.", "cabeza=null");
        while (actual != null) {
            paso("Nodo[" + indice + "] dato=" + actual.dato + " -> " + valor(actual.siguiente),
                    "indice=" + indice + ", actual=" + actual.dato + ", siguiente=" + valor(actual.siguiente));
            actual = actual.siguiente;
            indice++;
        }
        fin();
    }

    /** @param nodo referencia que se desea representar @return dato o null */
    private String valor(Nodo nodo) {
        return nodo == null ? "null" : String.valueOf(nodo.dato);
    }
}
