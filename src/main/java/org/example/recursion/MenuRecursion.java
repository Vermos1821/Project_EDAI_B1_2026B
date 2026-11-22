package org.example.recursion;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Gestiona la interacción con el usuario para el tema de <b>Recursividad</b>.
 *
 * <p>Ofrece cuatro problemas recursivos (factorial, Fibonacci, potencia y Torres de
 * Hanoi). Para cada uno el usuario puede: (1) resolverlo de forma recursiva viendo
 * cada llamada, (2) simularlo con una pila explícita viendo qué se apila y en qué
 * orden se recupera, o (3) consultar su caso base y su caso recursivo.</p>
 *
 * <p>Cada operación imprime en consola, paso a paso, lo que se está haciendo.</p>
 */
public class MenuRecursion {

    /** Ejecutor de los algoritmos recursivos con traza. */
    private final Recursividad recursividad = new Recursividad();

    /** Simulador de la recursión mediante una pila. */
    private final SimuladorPila simulador = new SimuladorPila();

    /**
     * Lee un entero validando la entrada.
     *
     * @param scanner Scanner compartido.
     * @param mensaje Texto que se muestra al solicitar el dato.
     * @return Entero ingresado.
     */
    private int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + Color.BLANCO + mensaje
                    + Color.RESET);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                Color.error("Entrada inválida. Por favor ingrese un número entero.");
            }
        }
    }

    /**
     * Lee un entero que debe estar dentro de un intervalo.
     *
     * @param scanner Scanner compartido.
     * @param mensaje Texto que se muestra al solicitar el dato.
     * @param min     Valor mínimo permitido.
     * @param max     Valor máximo permitido.
     * @return Entero válido dentro de [min, max].
     */
    private int leerEnRango(Scanner scanner, String mensaje, int min, int max) {
        while (true) {
            int valor = leerEntero(scanner, mensaje + "(" + min + " a " + max + "): ");
            if (valor >= min && valor <= max) {
                return valor;
            }
            Color.advertencia("Valor fuera de rango. Debe estar entre " + min + " y " + max + ".");
        }
    }

    /**
     * Muestra el menú principal de recursividad.
     */
    private void mostrarMenu() {
        System.out.println();
        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║                RECURSIVIDAD                  ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println(Color.AZUL + Color.NEGRITA + "       SELECCIONE UN PROBLEMA:" + Color.RESET);
        Color.separador();
        System.out.println(Color.CIAN + "       [1] " + Color.RESET + "Factorial de un número");
        System.out.println(Color.CIAN + "       [2] " + Color.RESET + "Sucesión de Fibonacci");
        System.out.println(Color.CIAN + "       [3] " + Color.RESET + "Potencia (base ^ exponente)");
        System.out.println(Color.CIAN + "       [4] " + Color.RESET + "Torres de Hanoi");
        Color.separador();
        System.out.println(Color.AMARILLO + Color.NEGRITA + "       [0] Regresar al menú principal" + Color.RESET);
        System.out.println();
    }

    /**
     * Muestra las operaciones disponibles para el problema elegido.
     *
     * @param problema Nombre del problema.
     */
    private void mostrarSubmenu(String problema) {
        System.out.println();
        Color.titulo("  ┌──────────── " + problema.toUpperCase() + " ────────────┐");
        System.out.println(Color.CIAN + "       [1] " + Color.RESET + "Resolver con recursión (ver cada llamada)");
        System.out.println(Color.CIAN + "       [2] " + Color.RESET + "Simular las llamadas con una pila");
        System.out.println(Color.CIAN + "       [3] " + Color.RESET + "Ver caso base y caso recursivo");
        Color.separador();
        System.out.println(Color.AMARILLO + Color.NEGRITA + "       [0] Volver a la lista de problemas" + Color.RESET);
        System.out.println();
    }

    /**
     * Imprime el caso base y el caso recursivo de un problema.
     *
     * @param titulo    Nombre del problema.
     * @param definicion Definición matemática o lógica.
     * @param casoBase  Texto del caso base.
     * @param recursivo Texto del caso recursivo.
     * @param porque    Explicación de por qué la recursión termina.
     */
    private void mostrarCasos(String titulo, String definicion, String casoBase, String recursivo, String porque) {
        Color.titulo("\n  ----- " + titulo + ": CASO BASE Y CASO RECURSIVO -----");
        System.out.println(Color.CIAN + "  Definición:       " + Color.RESET + definicion);
        System.out.println(Color.VERDE + Color.NEGRITA + "  ✔ Caso base:      " + Color.RESET + casoBase);
        System.out.println(Color.CIAN + Color.NEGRITA + "  ↻ Caso recursivo: " + Color.RESET + recursivo);
        System.out.println(Color.GRIS + "  ¿Por qué termina?  " + porque + Color.RESET);
    }

    /**
     * Imprime el encabezado de un paso numerado de la operación.
     *
     * @param numero Número del paso.
     * @param texto  Descripción de lo que se hace en ese paso.
     */
    private void paso(int numero, String texto) {
        System.out.println();
        Color.titulo("PASO " + numero + ": " + texto);
    }

    // ------------------------------------------------------------------ FACTORIAL

    /** Muestra casos del factorial. */
    private void casosFactorial() {
        mostrarCasos("FACTORIAL", "n! = n * (n-1) * ... * 2 * 1,  con 0! = 1",
                "n <= 1  →  factorial(n) = 1",
                "n > 1   →  factorial(n) = n * factorial(n - 1)",
                "En cada llamada n disminuye en 1, hasta llegar a 1.");
    }

    /**
     * Resuelve el factorial de forma recursiva, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void resolverFactorial(Scanner scanner) {
        Color.titulo("\n========== FACTORIAL: EJECUCIÓN RECURSIVA ==========");
        paso(1, "Solicitar el dato.");
        int n = leerEnRango(scanner, "Ingrese n para calcular n! ", 0, 20);
        paso(2, "Identificar el caso base y el caso recursivo.");
        casosFactorial();
        paso(3, "Ejecutar el método recursivo (cada ▶ es una llamada y cada ◀ un retorno).");
        recursividad.reiniciar();
        recursividad.setTraza(true);
        long resultado = recursividad.factorial(n);
        paso(4, "Mostrar el resultado.");
        Color.exito(n + "! = " + resultado);
        Color.info("Llamadas realizadas: " + recursividad.getLlamadas()
                + " | Profundidad máxima de la recursión: " + recursividad.getProfundidadMaxima());
    }

    /**
     * Simula el factorial con una pila, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void simularFactorial(Scanner scanner) {
        Color.titulo("\n========== FACTORIAL: SIMULACIÓN CON PILA ==========");
        paso(1, "Solicitar el dato.");
        int n = leerEnRango(scanner, "Ingrese n para calcular n! ", 0, 20);
        paso(2, "Recordar el caso base y el caso recursivo.");
        casosFactorial();
        paso(3, "Simular las llamadas: PUSH al llamar y POP al regresar.");
        long resultado = simulador.simularFactorial(n);
        paso(4, "Mostrar el resultado.");
        Color.exito(n + "! = " + resultado);
    }

    // ------------------------------------------------------------------ FIBONACCI

    /** Muestra casos de Fibonacci. */
    private void casosFibonacci() {
        mostrarCasos("FIBONACCI", "0, 1, 1, 2, 3, 5, 8, 13, ...  (cada término es la suma de los dos anteriores)",
                "n <= 1  →  fibonacci(0) = 0  y  fibonacci(1) = 1",
                "n > 1   →  fibonacci(n) = fibonacci(n - 1) + fibonacci(n - 2)",
                "Cada llamada reduce n en 1 o en 2, hasta llegar a 0 o 1. Hay dos llamadas por nivel (recursión múltiple).");
    }

    /**
     * Resuelve Fibonacci de forma recursiva, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void resolverFibonacci(Scanner scanner) {
        Color.titulo("\n========== FIBONACCI: EJECUCIÓN RECURSIVA ==========");
        paso(1, "Solicitar el dato.");
        int n = leerEnRango(scanner, "Ingrese la posición n de la sucesión ", 0, 8);
        paso(2, "Identificar el caso base y el caso recursivo.");
        casosFibonacci();
        paso(3, "Ejecutar el método recursivo (cada ▶ es una llamada y cada ◀ un retorno).");
        recursividad.reiniciar();
        recursividad.setTraza(true);
        long resultado = recursividad.fibonacci(n);
        paso(4, "Mostrar el resultado.");
        Color.exito("fibonacci(" + n + ") = " + resultado);
        Color.info("Llamadas realizadas: " + recursividad.getLlamadas()
                + " | Profundidad máxima de la recursión: " + recursividad.getProfundidadMaxima());
    }

    /**
     * Simula Fibonacci con una pila, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void simularFibonacci(Scanner scanner) {
        Color.titulo("\n========== FIBONACCI: SIMULACIÓN CON PILA ==========");
        paso(1, "Solicitar el dato.");
        int n = leerEnRango(scanner, "Ingrese la posición n de la sucesión ", 0, 8);
        paso(2, "Recordar el caso base y el caso recursivo.");
        casosFibonacci();
        paso(3, "Simular las llamadas: PUSH al llamar y POP al regresar.");
        long resultado = simulador.simularFibonacci(n);
        paso(4, "Mostrar el resultado.");
        Color.exito("fibonacci(" + n + ") = " + resultado);
    }

    // ------------------------------------------------------------------ POTENCIA

    /** Muestra casos de la potencia. */
    private void casosPotencia() {
        mostrarCasos("POTENCIA", "base^exp = base * base * ... * base  (exp veces),  con base^0 = 1",
                "exp == 0  →  potencia(base, 0) = 1",
                "exp > 0   →  potencia(base, exp) = base * potencia(base, exp - 1)",
                "En cada llamada el exponente disminuye en 1, hasta llegar a 0.");
    }

    /**
     * Resuelve la potencia de forma recursiva, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void resolverPotencia(Scanner scanner) {
        Color.titulo("\n========== POTENCIA: EJECUCIÓN RECURSIVA ==========");
        paso(1, "Solicitar los datos.");
        int base = leerEnRango(scanner, "Ingrese la base ", -10, 10);
        int exp = leerEnRango(scanner, "Ingrese el exponente ", 0, 15);
        paso(2, "Identificar el caso base y el caso recursivo.");
        casosPotencia();
        paso(3, "Ejecutar el método recursivo (cada ▶ es una llamada y cada ◀ un retorno).");
        recursividad.reiniciar();
        recursividad.setTraza(true);
        long resultado = recursividad.potencia(base, exp);
        paso(4, "Mostrar el resultado.");
        Color.exito(base + "^" + exp + " = " + resultado);
        Color.info("Llamadas realizadas: " + recursividad.getLlamadas()
                + " | Profundidad máxima de la recursión: " + recursividad.getProfundidadMaxima());
    }

    /**
     * Simula la potencia con una pila, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void simularPotencia(Scanner scanner) {
        Color.titulo("\n========== POTENCIA: SIMULACIÓN CON PILA ==========");
        paso(1, "Solicitar los datos.");
        int base = leerEnRango(scanner, "Ingrese la base ", -10, 10);
        int exp = leerEnRango(scanner, "Ingrese el exponente ", 0, 15);
        paso(2, "Recordar el caso base y el caso recursivo.");
        casosPotencia();
        paso(3, "Simular las llamadas: PUSH al llamar y POP al regresar.");
        long resultado = simulador.simularPotencia(base, exp);
        paso(4, "Mostrar el resultado.");
        Color.exito(base + "^" + exp + " = " + resultado);
    }

    // ------------------------------------------------------------------ HANOI

    /** Muestra casos de Torres de Hanoi. */
    private void casosHanoi() {
        mostrarCasos("TORRES DE HANOI",
                "Pasar k discos de la torre origen a la torre destino, usando una auxiliar, sin poner un disco mayor sobre uno menor",
                "k == 1  →  mover un solo disco de origen a destino",
                "k > 1   →  (1) pasar k-1 discos de origen a auxiliar, (2) mover 1 disco de origen a destino, (3) pasar k-1 discos de auxiliar a destino",
                "En cada llamada k disminuye en 1, hasta llegar a 1.");
    }

    /**
     * Muestra el resumen final de una partida de Hanoi.
     *
     * @param torres Torres al terminar.
     * @param n      Número de discos.
     */
    private void resultadoHanoi(TorresHanoi torres, int n) {
        int esperados = (1 << n) - 1;
        Color.exito("Movimientos realizados: " + torres.getMovimientos() + " (esperados 2^" + n + " - 1 = "
                + esperados + ")");
        for (int d = 1; d <= n; d++) {
            System.out.println(Color.CIAN + "  Movimientos del disco " + d + ": " + Color.RESET + torres.getVeces(d));
        }
        if (torres.estaResuelto()) {
            Color.exito("Resuelto: todos los discos están en la torre C.");
        } else {
            Color.error("Sin resolver: faltan discos por llegar a la torre C.");
        }
    }

    /**
     * Resuelve Torres de Hanoi de forma recursiva, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void resolverHanoi(Scanner scanner) {
        Color.titulo("\n========== TORRES DE HANOI: EJECUCIÓN RECURSIVA ==========");
        paso(1, "Solicitar el dato.");
        int n = leerEnRango(scanner, "Ingrese el número de discos ", 1, 5);
        paso(2, "Identificar el caso base y el caso recursivo.");
        casosHanoi();
        paso(3, "Estado inicial: todos los discos en la torre A.");
        TorresHanoi torres = new TorresHanoi(n);
        torres.mostrarEstado("  ");
        paso(4, "Ejecutar el método recursivo (cada ▶ es una llamada y cada ◀ un retorno).");
        recursividad.reiniciar();
        recursividad.setTraza(true);
        recursividad.hanoi(torres, n, 0, 2, 1);
        paso(5, "Mostrar los resultados.");
        resultadoHanoi(torres, n);
        Color.info("Llamadas realizadas: " + recursividad.getLlamadas()
                + " | Profundidad máxima de la recursión: " + recursividad.getProfundidadMaxima());
    }

    /**
     * Simula Torres de Hanoi con una pila, paso a paso.
     *
     * @param scanner Scanner compartido.
     */
    private void simularHanoi(Scanner scanner) {
        Color.titulo("\n========== TORRES DE HANOI: SIMULACIÓN CON PILA ==========");
        paso(1, "Solicitar el dato.");
        int n = leerEnRango(scanner, "Ingrese el número de discos ", 1, 5);
        paso(2, "Recordar el caso base y el caso recursivo.");
        casosHanoi();
        paso(3, "Simular las llamadas: PUSH al llamar y POP al regresar.");
        TorresHanoi torres = simulador.simularHanoi(n);
        paso(4, "Mostrar los resultados.");
        resultadoHanoi(torres, n);
    }

    // ------------------------------------------------------------------ MENÚS

    /**
     * Ejecuta el submenú de un problema hasta que el usuario regrese.
     *
     * @param scanner  Scanner compartido.
     * @param problema Número del problema (1 a 4).
     */
    private void ejecutarProblema(Scanner scanner, int problema) {
        String[] nombres = {"", "Factorial", "Fibonacci", "Potencia", "Torres de Hanoi"};
        int opcion;
        do {
            mostrarSubmenu(nombres[problema]);
            opcion = leerEntero(scanner, "Ingrese una opción: ");
            switch (opcion) {
                case 1:
                    if (problema == 1) resolverFactorial(scanner);
                    else if (problema == 2) resolverFibonacci(scanner);
                    else if (problema == 3) resolverPotencia(scanner);
                    else resolverHanoi(scanner);
                    break;
                case 2:
                    if (problema == 1) simularFactorial(scanner);
                    else if (problema == 2) simularFibonacci(scanner);
                    else if (problema == 3) simularPotencia(scanner);
                    else simularHanoi(scanner);
                    break;
                case 3:
                    if (problema == 1) casosFactorial();
                    else if (problema == 2) casosFibonacci();
                    else if (problema == 3) casosPotencia();
                    else casosHanoi();
                    break;
                case 0:
                    Color.info("Regresando a la lista de problemas...");
                    break;
                default:
                    Color.advertencia("Opción inválida. Seleccione una opción del menú.");
                    break;
            }
        } while (opcion != 0);
    }

    /**
     * Ejecuta el menú principal de recursividad hasta que el usuario regrese.
     *
     * @param scanner Scanner compartido con el menú principal (no se cierra aquí).
     */
    public void ejecutaMenu(Scanner scanner) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Ingrese una opción: ");
            switch (opcion) {
                case 1:
                case 2:
                case 3:
                case 4:
                    ejecutarProblema(scanner, opcion);
                    break;
                case 0:
                    System.out.println();
                    Color.info("Regresando al menú principal...");
                    break;
                default:
                    Color.advertencia("Opción inválida. Seleccione una opción del menú.");
                    break;
            }
        } while (opcion != 0);
    }
}
