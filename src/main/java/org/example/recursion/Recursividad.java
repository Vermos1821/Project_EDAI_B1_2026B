package org.example.recursion;

import org.example.util.Color;

/**
 * Algoritmos recursivos del proyecto: factorial, Fibonacci, potencia y
 * Torres de Hanoi.
 *
 * <p>Cada método está escrito de forma recursiva y, cuando la <b>traza</b> está
 * activada, imprime paso a paso qué llamada se realiza, si se trata del
 * <b>caso base</b> o del <b>caso recursivo</b>, y qué valor devuelve cada llamada
 * al regresar. La sangría de la consola indica la profundidad de la recursión.</p>
 *
 * <p>Todo problema recursivo debe tener:</p>
 * <ul>
 *   <li><b>Caso base:</b> la situación más simple, que se resuelve directamente y detiene la recursión.</li>
 *   <li><b>Caso recursivo:</b> la que se resuelve llamando al mismo método con un problema más pequeño
 *       que se acerca al caso base.</li>
 * </ul>
 */
public class Recursividad {

    /** Cantidad de llamadas realizadas desde el último {@link #reiniciar()}. */
    private int llamadas;

    /** Profundidad actual de la recursión (cuántas llamadas hay pendientes). */
    private int nivel;

    /** Mayor profundidad que alcanzó la recursión. */
    private int profundidadMaxima;

    /** Indica si se imprime la traza de la ejecución. */
    private boolean traza = true;

    /**
     * Pone en cero los contadores de llamadas y de profundidad.
     * Debe llamarse antes de cada ejecución nueva.
     */
    public void reiniciar() {
        llamadas = 0;
        nivel = 0;
        profundidadMaxima = 0;
    }

    /**
     * Activa o desactiva la impresión de la traza.
     *
     * @param traza {@code true} para mostrar cada llamada y cada retorno.
     */
    public void setTraza(boolean traza) {
        this.traza = traza;
    }

    /**
     * @return Cantidad de llamadas recursivas realizadas.
     */
    public int getLlamadas() {
        return llamadas;
    }

    /**
     * @return Profundidad máxima alcanzada (altura máxima de la pila de llamadas).
     */
    public int getProfundidadMaxima() {
        return profundidadMaxima;
    }

    /**
     * Construye la sangría de la consola según el nivel actual de recursión.
     *
     * @return Texto con una barra vertical por cada nivel.
     */
    private String sangria() {
        StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < nivel; i++) {
            sb.append(Color.GRIS).append("│  ").append(Color.RESET);
        }
        return sb.toString();
    }

    /**
     * Imprime una línea de la traza con la sangría correspondiente.
     *
     * @param texto Texto que se imprime.
     */
    private void escribir(String texto) {
        if (traza) {
            System.out.println(sangria() + texto);
        }
    }

    /**
     * Registra una llamada nueva: aumenta el contador y actualiza la profundidad máxima.
     *
     * @return Número consecutivo de la llamada.
     */
    private int registrarLlamada() {
        llamadas++;
        if (nivel + 1 > profundidadMaxima) {
            profundidadMaxima = nivel + 1;
        }
        return llamadas;
    }

    /**
     * Calcula {@code n!} de forma recursiva.
     *
     * <ul>
     *   <li>Caso base: {@code n <= 1} → {@code 1}.</li>
     *   <li>Caso recursivo: {@code n! = n * (n - 1)!}.</li>
     * </ul>
     *
     * @param n Número (entre 0 y 20 para que el resultado quepa en un {@code long}).
     * @return Factorial de {@code n}.
     */
    public long factorial(int n) {
        int id = registrarLlamada();
        escribir(Color.AMARILLO + "▶ Llamada #" + id + ": factorial(" + n + ")" + Color.RESET);

        if (n <= 1) {
            escribir(Color.VERDE + "✔ CASO BASE: n <= 1 (" + n + " <= 1) → devuelve 1" + Color.RESET);
            escribir(Color.AZUL + "◀ Retorno #" + id + ": factorial(" + n + ") = 1" + Color.RESET);
            return 1;
        }

        escribir(Color.CIAN + "↻ CASO RECURSIVO: factorial(" + n + ") = " + n + " * factorial(" + (n - 1) + ")" + Color.RESET);

        nivel++;
        long anterior = factorial(n - 1);
        nivel--;

        long resultado = n * anterior;
        escribir(Color.AZUL + "◀ Retorno #" + id + ": factorial(" + n + ") = " + n + " * " + anterior
                + " = " + resultado + Color.RESET);
        return resultado;
    }

    /**
     * Calcula el n-ésimo número de Fibonacci de forma recursiva
     * (versión directa, con dos llamadas recursivas por nivel).
     *
     * <ul>
     *   <li>Casos base: {@code fib(0) = 0} y {@code fib(1) = 1}.</li>
     *   <li>Caso recursivo: {@code fib(n) = fib(n - 1) + fib(n - 2)}.</li>
     * </ul>
     *
     * @param n Posición en la sucesión (se recomienda 0..8 al mostrar la traza).
     * @return Número de Fibonacci en la posición {@code n}.
     */
    public long fibonacci(int n) {
        int id = registrarLlamada();
        escribir(Color.AMARILLO + "▶ Llamada #" + id + ": fibonacci(" + n + ")" + Color.RESET);

        if (n <= 1) {
            escribir(Color.VERDE + "✔ CASO BASE: n <= 1 → fibonacci(" + n + ") = " + n + Color.RESET);
            escribir(Color.AZUL + "◀ Retorno #" + id + ": fibonacci(" + n + ") = " + n + Color.RESET);
            return n;
        }

        escribir(Color.CIAN + "↻ CASO RECURSIVO: fibonacci(" + n + ") = fibonacci(" + (n - 1) + ") + fibonacci("
                + (n - 2) + ")" + Color.RESET);

        nivel++;
        long izquierda = fibonacci(n - 1);
        nivel--;

        escribir(Color.GRIS + "  (#" + id + " ya tiene fibonacci(" + (n - 1) + ") = " + izquierda
                + "; ahora calcula fibonacci(" + (n - 2) + "))" + Color.RESET);

        nivel++;
        long derecha = fibonacci(n - 2);
        nivel--;

        long resultado = izquierda + derecha;
        escribir(Color.AZUL + "◀ Retorno #" + id + ": fibonacci(" + n + ") = " + izquierda + " + " + derecha
                + " = " + resultado + Color.RESET);
        return resultado;
    }

    /**
     * Calcula {@code base^exponente} de forma recursiva.
     *
     * <ul>
     *   <li>Caso base: {@code exponente == 0} → {@code 1}.</li>
     *   <li>Caso recursivo: {@code base^exp = base * base^(exp - 1)}.</li>
     * </ul>
     *
     * @param base      Base de la potencia.
     * @param exponente Exponente entero mayor o igual a cero.
     * @return Resultado de elevar la base al exponente.
     */
    public long potencia(int base, int exponente) {
        int id = registrarLlamada();
        escribir(Color.AMARILLO + "▶ Llamada #" + id + ": potencia(" + base + ", " + exponente + ")" + Color.RESET);

        if (exponente == 0) {
            escribir(Color.VERDE + "✔ CASO BASE: exponente == 0 → devuelve 1" + Color.RESET);
            escribir(Color.AZUL + "◀ Retorno #" + id + ": potencia(" + base + ", 0) = 1" + Color.RESET);
            return 1;
        }

        escribir(Color.CIAN + "↻ CASO RECURSIVO: potencia(" + base + ", " + exponente + ") = " + base
                + " * potencia(" + base + ", " + (exponente - 1) + ")" + Color.RESET);

        nivel++;
        long anterior = potencia(base, exponente - 1);
        nivel--;

        long resultado = base * anterior;
        escribir(Color.AZUL + "◀ Retorno #" + id + ": potencia(" + base + ", " + exponente + ") = " + base + " * "
                + anterior + " = " + resultado + Color.RESET);
        return resultado;
    }

    /**
     * Pasa {@code k} disco(s) de una torre a otra usando una torre auxiliar
     * (Torres de Hanoi).
     *
     * <ul>
     *   <li>Caso base: {@code k == 1} → se mueve un solo disco de origen a destino.</li>
     *   <li>Caso recursivo: (1) pasar {@code k - 1} disco(s) de origen a auxiliar,
     *       (2) mover el disco restante de origen a destino y
     *       (3) pasar {@code k - 1} disco(s) de auxiliar a destino.</li>
     * </ul>
     *
     * @param torres   Juego sobre el que se realizan los movimientos.
     * @param k        Cantidad de discos que se deben pasar.
     * @param origen   Torre de origen (0 = A, 1 = B, 2 = C).
     * @param destino  Torre de destino.
     * @param auxiliar Torre de apoyo.
     */
    public void hanoi(TorresHanoi torres, int k, int origen, int destino, int auxiliar) {
        int id = registrarLlamada();
        char o = TorresHanoi.nombre(origen);
        char d = TorresHanoi.nombre(destino);
        char a = TorresHanoi.nombre(auxiliar);

        escribir(Color.AMARILLO + "▶ Llamada #" + id + ": hanoi(" + k + ", " + o + ", " + d + ", " + a + ")"
                + Color.RESET);

        if (k == 1) {
            escribir(Color.VERDE + "✔ CASO BASE: k == 1 → se mueve un solo disco de " + o + " a " + d + Color.RESET);
            torres.ejecutarMovimiento(origen, destino, sangria());
            escribir(Color.AZUL + "◀ Retorno #" + id + ": hanoi(1, " + o + ", " + d + ", " + a + ") terminó" + Color.RESET);
            return;
        }

        escribir(Color.CIAN + "↻ CASO RECURSIVO: se pasan " + (k - 1) + " disco(s) de " + o + " a " + a
                + ", se mueve 1 disco de " + o + " a " + d + " y se pasan " + (k - 1) + " disco(s) de " + a
                + " a " + d + Color.RESET);

        escribir(Color.GRIS + "(1) Pasar " + (k - 1) + " disco(s) de " + o + " a " + a + Color.RESET);
        nivel++;
        hanoi(torres, k - 1, origen, auxiliar, destino);
        nivel--;

        escribir(Color.GRIS + "(2) Mover el disco mayor del grupo de " + o + " a " + d + Color.RESET);
        torres.ejecutarMovimiento(origen, destino, sangria());

        escribir(Color.GRIS + "(3) Pasar " + (k - 1) + " disco(s) de " + a + " a " + d + Color.RESET);
        nivel++;
        hanoi(torres, k - 1, auxiliar, destino, origen);
        nivel--;

        escribir(Color.AZUL + "◀ Retorno #" + id + ": hanoi(" + k + ", " + o + ", " + d + ", " + a + ") terminó"
                + Color.RESET);
    }
}
