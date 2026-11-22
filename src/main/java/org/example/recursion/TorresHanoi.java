package org.example.recursion;

import org.example.util.Color;

/**
 * Modelo del juego de las Torres de Hanoi.
 *
 * <p>Cada una de las tres torres (A, B y C) se guarda como una pila de discos
 * dentro de un arreglo: el disco de la posición 0 es la base y el de la posición
 * {@code altura - 1} es la cima. Los discos se numeran del 1 (el más pequeño)
 * al {@code n} (el más grande).</p>
 *
 * <p>Esta clase solo se encarga de <i>mover y mostrar</i> los discos; el
 * algoritmo recursivo vive en {@link Recursividad} y su simulación con pila
 * en {@link SimuladorPila}.</p>
 */
public class TorresHanoi {

    /** Letras que identifican a las torres 0, 1 y 2. */
    private static final char[] NOMBRE = {'A', 'B', 'C'};

    /** Cantidad total de discos del juego. */
    private final int n;

    /** {@code discos[t][k]}: disco ubicado en la posición k de la torre t (k = 0 es la base). */
    private final int[][] discos;

    /** {@code altura[t]}: cantidad de discos que tiene la torre t. */
    private final int[] altura;

    /** Cantidad de movimientos realizados hasta el momento. */
    private int movimientos = 0;

    /** {@code vecesPorDisco[d]}: cuántas veces se movió el disco d. */
    private final int[] vecesPorDisco;

    /**
     * Crea el juego con todos los discos en la torre A, el mayor en la base.
     *
     * @param n Número de discos (mayor que cero).
     */
    public TorresHanoi(int n) {
        this.n = n;
        discos = new int[3][n];
        altura = new int[3];
        vecesPorDisco = new int[n + 1];

        for (int i = 0; i < n; i++) {
            discos[0][i] = n - i;
        }
        altura[0] = n;
    }

    /**
     * Devuelve la letra que identifica a una torre.
     *
     * @param torre Índice de la torre (0, 1 o 2).
     * @return Letra A, B o C.
     */
    public static char nombre(int torre) {
        return NOMBRE[torre];
    }

    /**
     * Pasa el disco de la cima de la torre de origen a la cima de la torre de destino.
     *
     * @param origen  Torre de la que se retira el disco.
     * @param destino Torre en la que se coloca el disco.
     * @return Número del disco movido.
     * @throws IllegalStateException Si el origen está vacío o si se coloca un disco mayor sobre uno menor.
     */
    public int moverDisco(int origen, int destino) {
        if (altura[origen] == 0) {
            throw new IllegalStateException("Torre de origen vacía");
        }

        int disco = discos[origen][altura[origen] - 1];

        if (altura[destino] > 0 && discos[destino][altura[destino] - 1] < disco) {
            throw new IllegalStateException("Disco mayor sobre menor");
        }

        altura[origen]--;
        discos[destino][altura[destino]] = disco;
        altura[destino]++;

        return disco;
    }

    /**
     * Mueve un disco, lo registra, y muestra el movimiento y el estado resultante.
     *
     * @param origen  Torre de origen.
     * @param destino Torre de destino.
     * @param prefijo Texto que se antepone a cada línea (sirve para la sangría de la traza).
     * @return Número del disco movido.
     */
    public int ejecutarMovimiento(int origen, int destino, String prefijo) {
        int disco = moverDisco(origen, destino);
        movimientos++;
        vecesPorDisco[disco]++;

        System.out.println(prefijo + Color.VERDE + Color.NEGRITA + "MOVIMIENTO " + movimientos + ": disco " + disco
                + " de " + NOMBRE[origen] + " a " + NOMBRE[destino] + Color.RESET);
        mostrarEstado(prefijo + "   ");
        return disco;
    }

    /**
     * Imprime las tres torres, de base a cima, por ejemplo {@code A: 5 4 3 2 1}.
     * Si una torre no tiene discos imprime {@code A: vacía}.
     *
     * @param prefijo Texto que se antepone a cada línea.
     */
    public void mostrarEstado(String prefijo) {
        for (int t = 0; t < 3; t++) {
            StringBuilder linea = new StringBuilder(prefijo);
            linea.append(Color.CIAN).append(NOMBRE[t]).append(": ").append(Color.RESET);
            if (altura[t] == 0) {
                linea.append(Color.GRIS).append("vacía").append(Color.RESET);
            } else {
                for (int k = 0; k < altura[t]; k++) {
                    linea.append(discos[t][k]);
                    if (k < altura[t] - 1) {
                        linea.append(' ');
                    }
                }
            }
            System.out.println(linea);
        }
    }

    /**
     * @return {@code true} si todos los discos están en la torre C.
     */
    public boolean estaResuelto() {
        return altura[2] == n;
    }

    /**
     * @return Cantidad de movimientos realizados.
     */
    public int getMovimientos() {
        return movimientos;
    }

    /**
     * @param disco Número del disco (1..n).
     * @return Veces que se movió ese disco.
     */
    public int getVeces(int disco) {
        return vecesPorDisco[disco];
    }

    /**
     * @return Número de discos del juego.
     */
    public int getN() {
        return n;
    }
}
