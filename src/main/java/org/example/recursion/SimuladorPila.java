package org.example.recursion;

import org.example.util.Color;

/**
 * Simula la recursividad <b>sin usar llamadas recursivas</b>: en su lugar se
 * maneja explícitamente la pila de llamadas con {@link PilaMarcos}.
 *
 * <p>Cada vez que el algoritmo recursivo "llamaría" a otro método, aquí se hace
 * un {@code push} de un {@link MarcoLlamada}. Cuando una llamada termina, se hace
 * {@code pop} y su resultado se entrega al marco que queda en la cima. La consola
 * muestra, en cada paso, qué se apila, qué se desapila y cómo queda la pila,
 * y al final resume el <b>orden de apilado</b> y el <b>orden de recuperación</b>.</p>
 */
public class SimuladorPila {

    /**
     * Define cómo avanza un marco para un problema concreto.
     */
    private interface Reglas {
        /**
         * Hace avanzar el marco de la cima.
         *
         * @param marco    Marco que está en la cima de la pila.
         * @param recibido Valor devuelto por la llamada hija que acaba de terminar,
         *                 o {@code null} si el marco acaba de apilarse.
         * @return Marco hijo que debe apilarse, o {@code null} si el marco terminó
         *         (su resultado queda en {@link MarcoLlamada#getParcial()}).
         */
        MarcoLlamada avanzar(MarcoLlamada marco, Long recibido);
    }

    /** Pila de llamadas de la simulación en curso. */
    private PilaMarcos pila;

    /** Contador de marcos creados (identifica cada llamada). */
    private int numeroMarco;

    /** Contador de pasos mostrados en la consola. */
    private int paso;

    /** Cantidad máxima de registros que guardan los arreglos de orden (Fibonacci(8) usa 67). */
    private static final int MAX_REGISTROS = 200;

    /** Registro de los marcos en el orden en que fueron apilados. */
    private final String[] ordenApilado = new String[MAX_REGISTROS];

    /** Cantidad de marcos registrados en {@link #ordenApilado}. */
    private int totalApilados;

    /** Registro de los marcos en el orden en que fueron recuperados (desapilados). */
    private final String[] ordenRecuperado = new String[MAX_REGISTROS];

    /** Cantidad de marcos registrados en {@link #ordenRecuperado}. */
    private int totalRecuperados;

    /** Torres usadas cuando se simula Hanoi; es {@code null} en los demás problemas. */
    private TorresHanoi torres;

    /**
     * Prepara una simulación nueva: pila vacía y contadores en cero.
     *
     * @param capacidad Altura máxima que tendrá la pila.
     */
    private void reiniciar(int capacidad) {
        pila = new PilaMarcos(capacidad);
        numeroMarco = 0;
        paso = 0;
        totalApilados = 0;
        totalRecuperados = 0;
    }

    /**
     * Crea un marco con el siguiente número consecutivo.
     *
     * @param etiqueta   Texto de la llamada.
     * @param argumentos Parámetros de la llamada.
     * @return Marco nuevo.
     */
    private MarcoLlamada nuevoMarco(String etiqueta, int... argumentos) {
        numeroMarco++;
        return new MarcoLlamada(numeroMarco, etiqueta, argumentos);
    }

    /**
     * Dibuja la pila de llamadas de fondo a cima en una sola línea.
     */
    private void mostrarPila() {
        StringBuilder sb = new StringBuilder("     " + Color.CIAN + "Pila (fondo → cima): " + Color.RESET);
        if (pila.estaVacia()) {
            sb.append(Color.GRIS).append("[ VACÍA ]").append(Color.RESET);
        } else {
            sb.append("[ ");
            for (int i = 0; i <= pila.getTope(); i++) {
                if (i == pila.getTope()) {
                    sb.append(Color.VERDE).append(Color.NEGRITA).append(pila.getMarco(i)).append(" ← TOPE")
                            .append(Color.RESET);
                } else {
                    sb.append(pila.getMarco(i)).append(" | ");
                }
            }
            sb.append(" ]");
        }
        sb.append(Color.GRIS).append("   (tamaño = ").append(pila.size()).append(")").append(Color.RESET);
        System.out.println(sb);
    }

    /**
     * Apila un marco (equivale a realizar la llamada) y lo muestra.
     *
     * @param marco Marco que se apila.
     */
    private void apilar(MarcoLlamada marco) {
        pila.push(marco);
        if (totalApilados == MAX_REGISTROS) {
            throw new IllegalStateException("Se superó el máximo de registros de la simulación");
        }
        ordenApilado[totalApilados] = marco.toString();
        totalApilados++;
        paso++;
        System.out.println();
        System.out.println(Color.AMARILLO + Color.NEGRITA + "  ( " + paso + " ) ▼ PUSH " + marco + Color.RESET
                + Color.GRIS + "  (llamada #" + marco.getNumero() + ")" + Color.RESET);
        System.out.println("     Se guarda en la pila el marco con sus parámetros y el punto de retorno.");
        mostrarPila();
    }

    /**
     * Desapila el marco de la cima (equivale a regresar de la llamada) y lo muestra.
     *
     * @param marco          Marco que terminó.
     * @param mostrarRetorno {@code true} si el problema devuelve un valor.
     */
    private void desapilar(MarcoLlamada marco, boolean mostrarRetorno) {
        pila.pop();
        paso++;
        System.out.println();
        if (mostrarRetorno) {
            registrarRecuperado(marco + " → " + marco.getParcial());
            System.out.println(Color.AZUL + Color.NEGRITA + "  ( " + paso + " ) ▲ POP " + marco + " → devuelve "
                    + marco.getParcial() + Color.RESET + Color.GRIS + "  (llamada #" + marco.getNumero() + ")"
                    + Color.RESET);
            System.out.println("     Se elimina el marco y su valor pasa a la llamada que quedó en la cima.");
        } else {
            registrarRecuperado(marco.toString());
            System.out.println(Color.AZUL + Color.NEGRITA + "  ( " + paso + " ) ▲ POP " + marco + " terminó"
                    + Color.RESET + Color.GRIS + "  (llamada #" + marco.getNumero() + ")" + Color.RESET);
            System.out.println("     Se elimina el marco y el control regresa a la llamada que quedó en la cima.");
        }
        mostrarPila();
    }

    /**
     * Guarda un marco en el registro del orden de recuperación.
     *
     * @param texto Descripción del marco recuperado.
     */
    private void registrarRecuperado(String texto) {
        if (totalRecuperados == MAX_REGISTROS) {
            throw new IllegalStateException("Se superó el máximo de registros de la simulación");
        }
        ordenRecuperado[totalRecuperados] = texto;
        totalRecuperados++;
    }

    /**
     * Ciclo principal: mientras haya marcos en la pila, hace avanzar el de la cima.
     *
     * @param raiz           Primera llamada del problema.
     * @param capacidad      Altura máxima de la pila.
     * @param reglas         Reglas de avance del problema.
     * @param mostrarRetorno Si el problema devuelve un valor.
     * @return Valor devuelto por la llamada raíz.
     */
    private long ejecutar(MarcoLlamada raiz, int capacidad, Reglas reglas, boolean mostrarRetorno) {
        System.out.println();
        Color.info("Pila vacía. Se apila la llamada inicial.");
        mostrarPila();
        apilar(raiz);

        Long recibido = null;
        long ultimo = 0;

        while (!pila.estaVacia()) {
            MarcoLlamada marco = pila.peek();
            MarcoLlamada hijo = reglas.avanzar(marco, recibido);
            recibido = null;

            if (hijo != null) {
                apilar(hijo);
            } else {
                desapilar(marco, mostrarRetorno);
                recibido = marco.getParcial();
                ultimo = recibido;
            }
        }
        return ultimo;
    }

    /**
     * Imprime una lista numerada o en una sola línea si es muy larga.
     *
     * @param titulo   Encabezado.
     * @param lista    Arreglo con los elementos en orden.
     * @param cantidad Cantidad de posiciones válidas del arreglo.
     */
    private void imprimirOrden(String titulo, String[] lista, int cantidad) {
        System.out.println(Color.CIAN + Color.NEGRITA + "  " + titulo + Color.RESET);
        if (cantidad <= 12) {
            for (int i = 0; i < cantidad; i++) {
                System.out.println("     " + (i + 1) + ". " + lista[i]);
            }
        } else {
            StringBuilder sb = new StringBuilder("     ");
            for (int i = 0; i < cantidad; i++) {
                sb.append(lista[i]);
                if (i < cantidad - 1) {
                    sb.append("  →  ");
                }
            }
            System.out.println(sb);
        }
    }

    /**
     * Muestra el resumen final: orden de apilado y orden de recuperación.
     */
    private void resumen() {
        System.out.println();
        Color.titulo("  ──────── RESUMEN DE LA SIMULACIÓN ────────");
        System.out.println(Color.CIAN + "  Marcos apilados: " + Color.RESET + totalApilados
                + Color.CIAN + "   Marcos desapilados: " + Color.RESET + totalRecuperados);
        imprimirOrden("Orden en que se APILAN (primero → último):", ordenApilado, totalApilados);
        imprimirOrden("Orden en que se RECUPERAN (primero → último):", ordenRecuperado, totalRecuperados);
        Color.info("Como la pila es LIFO, el último marco apilado es el primero en recuperarse.");
    }

    /**
     * Simula {@code factorial(n)} con una pila.
     *
     * @param n Número del que se calcula el factorial (0..20).
     * @return Factorial de {@code n}.
     */
    public long simularFactorial(int n) {
        reiniciar(n + 2);
        Reglas reglas = (marco, recibido) -> {
            int k = marco.getArgumento(0);
            if (marco.getEstado() == 0) {
                if (k <= 1) {
                    System.out.println(Color.VERDE + "     ✔ CASO BASE: " + k + " <= 1 → resultado = 1" + Color.RESET);
                    marco.setParcial(1);
                    return null;
                }
                System.out.println(Color.CIAN + "     ↻ CASO RECURSIVO: " + marco + " = " + k + " * factorial("
                        + (k - 1) + ") → se apila factorial(" + (k - 1) + ")" + Color.RESET);
                marco.setEstado(1);
                return nuevoMarco("factorial(" + (k - 1) + ")", k - 1);
            }
            marco.setParcial(k * recibido);
            System.out.println(Color.GRIS + "     " + marco + " recibió " + recibido + " → " + k + " * " + recibido
                    + " = " + marco.getParcial() + Color.RESET);
            return null;
        };
        long resultado = ejecutar(nuevoMarco("factorial(" + n + ")", n), n + 2, reglas, true);
        resumen();
        return resultado;
    }

    /**
     * Simula {@code fibonacci(n)} con una pila.
     *
     * @param n Posición en la sucesión (0..8 recomendado).
     * @return Número de Fibonacci en la posición {@code n}.
     */
    public long simularFibonacci(int n) {
        reiniciar(n + 2);
        Reglas reglas = (marco, recibido) -> {
            int k = marco.getArgumento(0);
            switch (marco.getEstado()) {
                case 0:
                    if (k <= 1) {
                        System.out.println(Color.VERDE + "     ✔ CASO BASE: " + k + " <= 1 → resultado = " + k
                                + Color.RESET);
                        marco.setParcial(k);
                        return null;
                    }
                    System.out.println(Color.CIAN + "     ↻ CASO RECURSIVO: " + marco + " = fibonacci(" + (k - 1)
                            + ") + fibonacci(" + (k - 2) + ") → primero se apila fibonacci(" + (k - 1) + ")"
                            + Color.RESET);
                    marco.setEstado(1);
                    return nuevoMarco("fibonacci(" + (k - 1) + ")", k - 1);
                case 1:
                    marco.setParcial(recibido);
                    System.out.println(Color.GRIS + "     " + marco + " guardó fibonacci(" + (k - 1) + ") = "
                            + recibido + " → ahora se apila fibonacci(" + (k - 2) + ")" + Color.RESET);
                    marco.setEstado(2);
                    return nuevoMarco("fibonacci(" + (k - 2) + ")", k - 2);
                default:
                    long suma = marco.getParcial() + recibido;
                    System.out.println(Color.GRIS + "     " + marco + ": " + marco.getParcial() + " + " + recibido
                            + " = " + suma + Color.RESET);
                    marco.setParcial(suma);
                    return null;
            }
        };
        long resultado = ejecutar(nuevoMarco("fibonacci(" + n + ")", n), n + 2, reglas, true);
        resumen();
        return resultado;
    }

    /**
     * Simula {@code potencia(base, exponente)} con una pila.
     *
     * @param base      Base de la potencia.
     * @param exponente Exponente (0..15).
     * @return Resultado de la potencia.
     */
    public long simularPotencia(int base, int exponente) {
        reiniciar(exponente + 2);
        Reglas reglas = (marco, recibido) -> {
            int b = marco.getArgumento(0);
            int e = marco.getArgumento(1);
            if (marco.getEstado() == 0) {
                if (e == 0) {
                    System.out.println(Color.VERDE + "     ✔ CASO BASE: exponente == 0 → resultado = 1" + Color.RESET);
                    marco.setParcial(1);
                    return null;
                }
                System.out.println(Color.CIAN + "     ↻ CASO RECURSIVO: " + marco + " = " + b + " * potencia(" + b
                        + ", " + (e - 1) + ") → se apila potencia(" + b + ", " + (e - 1) + ")" + Color.RESET);
                marco.setEstado(1);
                return nuevoMarco("potencia(" + b + ", " + (e - 1) + ")", b, e - 1);
            }
            marco.setParcial(b * recibido);
            System.out.println(Color.GRIS + "     " + marco + " recibió " + recibido + " → " + b + " * " + recibido
                    + " = " + marco.getParcial() + Color.RESET);
            return null;
        };
        long resultado = ejecutar(nuevoMarco("potencia(" + base + ", " + exponente + ")", base, exponente),
                exponente + 2, reglas, true);
        resumen();
        return resultado;
    }

    /**
     * Crea el marco de una llamada a Hanoi con su etiqueta legible.
     *
     * @param k Cantidad de discos.
     * @param o Torre de origen.
     * @param d Torre de destino.
     * @param a Torre auxiliar.
     * @return Marco listo para apilar.
     */
    private MarcoLlamada marcoHanoi(int k, int o, int d, int a) {
        return nuevoMarco("hanoi(" + k + ", " + TorresHanoi.nombre(o) + ", " + TorresHanoi.nombre(d) + ", "
                + TorresHanoi.nombre(a) + ")", k, o, d, a);
    }

    /**
     * Simula las Torres de Hanoi con una pila; muestra además cada movimiento
     * y el estado de las torres.
     *
     * @param n Número de discos (1..5).
     * @return Torres al finalizar, para consultar movimientos y estado.
     */
    public TorresHanoi simularHanoi(int n) {
        reiniciar(n + 2);
        torres = new TorresHanoi(n);
        Reglas reglas = (marco, recibido) -> {
            int k = marco.getArgumento(0);
            int o = marco.getArgumento(1);
            int d = marco.getArgumento(2);
            int a = marco.getArgumento(3);
            switch (marco.getEstado()) {
                case 0:
                    if (k == 1) {
                        System.out.println(Color.VERDE + "     ✔ CASO BASE: k == 1 → se mueve un disco de "
                                + TorresHanoi.nombre(o) + " a " + TorresHanoi.nombre(d) + Color.RESET);
                        torres.ejecutarMovimiento(o, d, "     ");
                        return null;
                    }
                    System.out.println(Color.CIAN + "     ↻ CASO RECURSIVO: (1) pasar " + (k - 1) + " disco(s) de "
                            + TorresHanoi.nombre(o) + " a " + TorresHanoi.nombre(a) + " → se apila esa subllamada"
                            + Color.RESET);
                    marco.setEstado(1);
                    return marcoHanoi(k - 1, o, a, d);
                case 1:
                    System.out.println(Color.GRIS + "     " + marco + ": (2) mover un disco de "
                            + TorresHanoi.nombre(o) + " a " + TorresHanoi.nombre(d) + Color.RESET);
                    torres.ejecutarMovimiento(o, d, "     ");
                    System.out.println(Color.GRIS + "     " + marco + ": (3) pasar " + (k - 1) + " disco(s) de "
                            + TorresHanoi.nombre(a) + " a " + TorresHanoi.nombre(d) + " → se apila esa subllamada"
                            + Color.RESET);
                    marco.setEstado(2);
                    return marcoHanoi(k - 1, a, d, o);
                default:
                    return null;
            }
        };
        ejecutar(marcoHanoi(n, 0, 2, 1), n + 2, reglas, false);
        resumen();
        return torres;
    }
}