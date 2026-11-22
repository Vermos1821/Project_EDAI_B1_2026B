package org.example.recursion;

/**
 * Representa un <b>marco de activación</b> (stack frame): el registro que el
 * sistema crea cada vez que se invoca un método y que guarda sus datos
 * mientras la llamada está pendiente.
 *
 * <p>En la simulación de la recursividad, cada marco almacena:</p>
 * <ul>
 *   <li>Los <b>parámetros</b> con los que se hizo la llamada.</li>
 *   <li>El <b>estado</b> de avance de la llamada (equivale al "punto de retorno":
 *       indica en qué línea del método debe continuar cuando regrese una
 *       llamada hija).</li>
 *   <li>Un <b>resultado parcial</b> (variable local), por ejemplo el valor de
 *       {@code fibonacci(n - 1)} mientras se calcula {@code fibonacci(n - 2)}.</li>
 * </ul>
 */
public class MarcoLlamada {

    /** Número consecutivo de la llamada; sirve para identificarla en la consola. */
    private final int numero;

    /** Texto que describe la llamada, por ejemplo {@code factorial(4)}. */
    private final String etiqueta;

    /** Parámetros con los que se invocó el método. */
    private final int[] argumentos;

    /** Fase de avance de la llamada: 0 = recién creada; 1, 2, ... = llamadas hijas ya resueltas. */
    private int estado;

    /** Resultado parcial o resultado final de la llamada. */
    private long parcial;

    /**
     * Crea un marco nuevo en estado 0 (recién apilado).
     *
     * @param numero     Número consecutivo de la llamada.
     * @param etiqueta   Texto descriptivo de la llamada, por ejemplo {@code fibonacci(5)}.
     * @param argumentos Parámetros de la llamada.
     */
    public MarcoLlamada(int numero, String etiqueta, int... argumentos) {
        this.numero = numero;
        this.etiqueta = etiqueta;
        this.argumentos = argumentos;
        this.estado = 0;
        this.parcial = 0;
    }

    /**
     * @return Número consecutivo de la llamada.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Obtiene un parámetro de la llamada.
     *
     * @param indice Posición del parámetro (0 es el primero).
     * @return Valor del parámetro.
     */
    public int getArgumento(int indice) {
        return argumentos[indice];
    }

    /**
     * @return Fase de avance de la llamada.
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Cambia la fase de avance de la llamada.
     *
     * @param estado Nueva fase.
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return Resultado parcial (o final) guardado en el marco.
     */
    public long getParcial() {
        return parcial;
    }

    /**
     * Guarda un resultado parcial o final en el marco.
     *
     * @param parcial Valor que se almacena.
     */
    public void setParcial(long parcial) {
        this.parcial = parcial;
    }

    /**
     * @return Texto descriptivo de la llamada, por ejemplo {@code factorial(4)}.
     */
    @Override
    public String toString() {
        return etiqueta;
    }
}
