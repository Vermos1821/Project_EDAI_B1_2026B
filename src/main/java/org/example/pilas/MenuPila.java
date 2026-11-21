package org.example.pilas;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Hace la gestión de la interacción entre el usuario y el TAD Pila implementado
 * a través de un arreglo de capacidad fija.
 *
 * <p>Brinda un menú interactivo para poner en ejecución las operaciones
 * fundamentales de la estructura, controlar sus estados y visualizar
 * los cambios producidos durante cada procedimiento.</p>
 *
 * <p>También incorpora la conversión de expresiones infijas a notación
 * postfija y prefija, usando la pila como estructura auxiliar para
 * administrar los operadores y respetar su precedencia y asociatividad.</p>
 */
public class MenuPila {
    /**
     * Pila de enteros sobre la que se ejecutarán las operaciones.
     */
    private PilaArreglo pila;

    /**
     * Muestra las opciones disponibles para poder trabajar con la pila.
     * Presenta el menú principal de esta estructura con sus respectivas
     * operaciones y la opción para regresar al menú principal.
     */
    private void mostrarMenu(){
        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║              ESTRUCTURA DE PILAS             ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        System.out.println();

        System.out.println(Color.AZUL + Color.NEGRITA + "       SELECCIONE UNA OPERACIÓN:" + Color.RESET);

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET + "Push     - Insertar elemento");
        System.out.println(Color.CIAN + "       [2] " + Color.RESET + "Pop      - Eliminar elemento");
        System.out.println(Color.CIAN + "       [3] " + Color.RESET + "Peek     - Consultar elemento superior");
        System.out.println(Color.CIAN + "       [4] " + Color.RESET + "Verificar si está vacía");
        System.out.println(Color.CIAN + "       [5] " + Color.RESET + "Verificar si está llena");
        System.out.println(Color.CIAN + "       [6] " + Color.RESET + "Consultar tamaño");
        System.out.println(Color.CIAN + "       [7] " + Color.RESET + "Mostrar contenido de la pila");
        System.out.println(Color.CIAN + "       [8] " + Color.RESET + "Convertir expresiones infijas");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA + "       [0] Regresar al menú principal" + Color.RESET);

        System.out.println();

    }

    /**
     * Lee un número entero que fue ingresado por el usuario y valida
     * que la entrada sea correcta.
     *
     * @param scanner Objeto ({@code Scanner}) usado para leer la entrada.
     * @param mensaje Texto que se muestra al solicitar el número.
     * @return Número entero que fue ingresado por el usuario.
     */
    private int leerEntero(Scanner scanner, String mensaje){
        while(true){
            System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + Color.BLANCO + mensaje + Color.RESET);
            String entrada = scanner.nextLine();

            try{
                return Integer.parseInt(entrada);
            } catch(NumberFormatException e){
                Color.error("Entrada inválida. Por favor ingrese un número entero. ");
            }

        }

    }

    /**
     * Solicita al usuario la capacidad inicial de la pila y crea
     * una instancia de {@link PilaArreglo} con la capacidad indicada.
     *
     * @param scanner Objeto ({@code Scanner}) usado para leer la capacidad.
     */
    private void iniciarPila(Scanner scanner){
        int capacidad;
        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║               CONFIGURAR PILA                ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        do {
            capacidad = leerEntero(scanner, "Ingrese la capacidad de la pila: ");
            if (capacidad < 1){
                Color.advertencia("Capacidad inválida. La capacidad debe ser mayor que cero. ");
            }
        } while (capacidad < 1);

        pila = new PilaArreglo(capacidad);

        Color.exito("La pila fue creada con éxito. ");
        Color.info("Capacidad establecida: " + capacidad + " elementos.");

        mostrarEstadoDetallado();
    }

    /**
     * Muestra de manera detallada y vertical el estado actual de la pila.
     *
     * <p>Representa de manera gráfica la pila desde la posición superior (capacidad - 1)
     * hasta el fondo (0), indicando la posición del tope, las posiciones disponibles,
     * la cantidad de elementos y la capacidad máxima.</p>
     */
    private void mostrarEstadoDetallado(){
        System.out.println();
        Color.titulo("  ┌──────────── ESTADO ACTUAL DE LA PILA ────────────┐");

        System.out.println(Color.CIAN + Color.NEGRITA + "  Representación vertical (LIFO):" + Color.RESET);
        System.out.println();

        System.out.println(Color.BLANCO + "        ┌─────────┐" + Color.RESET);

        for (int i = pila.getCapacidad() - 1; i >= 0; i--){
            String etiquetaIndice = String.format("  [%d]  ", i);
            String contenido;
            String colorContenido;

            if (i <= pila.getTope()){
                contenido = String.format("%5d  ", pila.getDato(i));
                colorContenido = Color.VERDE + Color.NEGRITA;

            } else {
                contenido = "   _   ";
                colorContenido = Color.GRIS;
            }

            String indicador = "";
            if (i == pila.getTope()){
                indicador = Color.AMARILLO + Color.NEGRITA + "  ◄── TOPE" + Color.RESET;

            } else if (i == 0 && pila.estaVacia()) {
                indicador = Color.GRIS + "  (Fondo)" + Color.RESET;

            } else if (i == 0) {
                indicador = Color.CIAN + "  (Fondo)" + Color.RESET;

            }

            System.out.println(Color.CIAN + etiquetaIndice + Color.BLANCO + " │ " + colorContenido + contenido +
                                Color.BLANCO + "│" + indicador);

            if (i > 0) {
                System.out.println(Color.BLANCO + "        ├─────────┤" + Color.RESET);

            } else {
                System.out.println(Color.BLANCO + "        └─────────┘" + Color.RESET);

            }

        }

        if (pila.estaVacia()) {
            System.out.println(Color.AMARILLO + Color.NEGRITA + "  [-1]   ◄── TOPE (Pila vacía)" + Color.RESET);
        }

        System.out.println(Color.CIAN + "  Tope: " + Color.RESET + Color.AMARILLO + pila.getTope() + Color.RESET);
        System.out.println(Color.CIAN + "  Elementos: " + Color.RESET + Color.BLANCO + pila.size() + Color.RESET);
        System.out.println(Color.CIAN + "  Capacidad: " + Color.RESET + Color.BLANCO + pila.getCapacidad() + Color.RESET);
        System.out.println(Color.CIAN + "  Espacios disponibles: " + Color.RESET + Color.BLANCO
                            + (pila.getCapacidad() - pila.size()) + Color.RESET);

        Color.titulo("  └────────────────────────────────────────────────┘");

        System.out.println();
    }

    /**
     * Pone en ejecución la operación Push para insertar un elemento en la pila.
     * Muestra el estado inicial, verifica si existe espacio disponible,
     * incrementa el índice del tope e inserta el nuevo elemento.
     *
     * @param scanner objeto ({@code Scanner}) utilizado para leer el elemento
     */
    private void ejecutarPush(Scanner scanner){
        Color.titulo("\n========== OPERACIÓN PUSH ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoDetallado();

        int elemento = leerEntero(scanner, "Ingrese el elemento que desea apilar: ");

        System.out.println();

        Color.titulo("PASO 1: Verificar si la pila está llena.");
        System.out.println(Color.CIAN + "Condición: " + Color.RESET + "tope == capacidad - 1");
        System.out.println(Color.BLANCO + pila.getTope() + " == " + (pila.getCapacidad() - 1) + " -> " +
                            pila.estaLlena() + Color.RESET);

        if (pila.estaLlena()) {
            Color.error("No se puede insertar. La pila está llena.");

            Color.info("ESTADO FINAL");
            mostrarEstadoDetallado();

            return;
        }

        Color.exito("Existe espacio disponible para insertar el elemento.");

        System.out.println();

        Color.titulo("PASO 2: Incrementar el índice del tope.");
        System.out.println(Color.CIAN + "tope = " + Color.RESET + pila.getTope() + " + 1");

        System.out.println(Color.VERDE + "tope = " + (pila.getTope() + 1) + Color.RESET);

        System.out.println();

        Color.titulo("PASO 3: Insertar el elemento en el arreglo.");
        System.out.println(Color.CIAN + "datos[" + (pila.getTope() + 1) + "] = " + Color.RESET + elemento);
        pila.push(elemento);

        Color.info("ESTADO FINAL");
        mostrarEstadoDetallado();

        Color.exito("El elemento fue apilado correctamente.");
    }

    /**
     * Pone en ejecución la operación Pop para eliminar el elemento ubicado
     * en el tope de la pila.
     *
     * <p>Comprueba que la pila no esté vacía, obtiene el elemento que se eliminó
     * y actualiza el índice del tope.</p>
     */
    private void ejecutarPop(){
        Color.titulo("\n========== OPERACIÓN POP ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoDetallado();

        Color.titulo("PASO 1: Verificar si la pila está vacía.");
        System.out.println(Color.CIAN + "Condición: " + Color.RESET + "tope == -1");
        System.out.println(Color.BLANCO + pila.getTope() + " == -1 -> " + pila.estaVacia() + Color.RESET);

        if (pila.estaVacia()) {
            Color.error("No se puede eliminar. La pila está vacía.");

            Color.info("ESTADO FINAL");
            mostrarEstadoDetallado();

            return;
        }

        Color.exito("La pila contiene elementos y permite realizar la eliminación.");

        System.out.println();

        Color.titulo("PASO 2: Obtener el elemento ubicado en el tope.");
        System.out.println(Color.CIAN + "dato = datos[" + pila.getTope() + "]" + Color.RESET);
        System.out.println(Color.VERDE + "dato = " + pila.getDato(pila.getTope()) + Color.RESET);

        System.out.println();

        Color.titulo("PASO 3: Decrementar el índice del tope.");
        System.out.println(Color.CIAN + "tope = " + Color.RESET + pila.getTope() + " - 1");
        System.out.println(Color.VERDE + "tope = " + (pila.getTope() - 1) + Color.RESET);

        // Se ejecuta la eliminación antes de mostrar el estado final.
        int dato = pila.pop();

        Color.info("ESTADO FINAL");
        mostrarEstadoDetallado();

        Color.exito("Se eliminó el elemento: " + dato);
    }

    /**
     * Ejecuta la operación Peek para la consulta del elemento ubicado
     * en el tope de la pila sin eliminarlo.
     * Muestra la comprobación de la pila y el elemento consultado.
     */
    private void ejecutarPeek(){
        Color.titulo("\n========== OPERACIÓN PEEK ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoDetallado();

        Color.titulo("PASO 1: Verificar si la pila está vacía.");
        System.out.println(Color.CIAN + "Condición: " + Color.RESET + "tope == -1");

        System.out.println(Color.BLANCO + pila.getTope() + " == -1 -> " + pila.estaVacia() + Color.RESET);

        if (pila.estaVacia()) {
            Color.error("No se puede consultar. La pila está vacía.");

            Color.info("ESTADO FINAL");
            mostrarEstadoDetallado();

            return;
        }

        System.out.println();

        Color.titulo("PASO 2: Consultar el elemento del tope.");
        System.out.println(Color.CIAN + "dato = datos[" + pila.getTope() + "]" + Color.RESET);
        System.out.println(Color.VERDE + "dato = " + pila.getDato(pila.getTope()) + Color.RESET);
        int dato = pila.peek();
        Color.exito("El elemento de la cima es: " + dato);

        Color.info("ESTADO FINAL");
        mostrarEstadoDetallado();

        Color.info("La consulta no modificó el contenido de la pila.");
    }

    /**
     * Comprueba si la pila está vacía.
     * Muestra el valor del índice del tope y evalúa la condición
     * usada para determinar si existen elementos almacenados.
     */
    private void ejecutarEstaVacia(){
        Color.titulo("\n========== VERIFICAR PILA VACÍA ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoDetallado();

        Color.titulo("PASO 1: Consultar el índice del tope.");

        System.out.println(Color.CIAN + "tope = " + Color.RESET + pila.getTope());

        System.out.println();

        Color.titulo("PASO 2: Evaluar la condición.");
        System.out.println(Color.CIAN + "Condición: " + Color.RESET + "tope == -1");

        System.out.println(Color.BLANCO + pila.getTope() + " == -1 -> " + pila.estaVacia() + Color.RESET);

        Color.info("ESTADO FINAL");
        mostrarEstadoDetallado();

        if (pila.estaVacia()) {
            Color.info("Resultado: La pila está vacía.");
        } else {
            Color.exito("Resultado: La pila contiene elementos.");
        }
    }

    /**
     * Comprueba si la pila alcanzó su capacidad máxima.
     *
     * <p>Muestra los valores del tope y la capacidad, y evalúa
     * la condición que determina si se puede insertar elementos.</p>
     */
    private void ejecutarEstaLlena(){
        Color.titulo("\n========== VERIFICAR PILA LLENA ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoDetallado();

        Color.titulo("PASO 1: Consultar el tope y la capacidad.");
        System.out.println(Color.CIAN + "tope = " + Color.RESET + pila.getTope());
        System.out.println(Color.CIAN + "capacidad = " + Color.RESET + pila.getCapacidad());

        System.out.println();

        Color.titulo("PASO 2: Evaluar la condición.");
        System.out.println(Color.CIAN + "Condición: " + Color.RESET + "tope == capacidad - 1");
        System.out.println(Color.BLANCO + pila.getTope() + " == " + (pila.getCapacidad() - 1) + " -> " +
                            pila.estaLlena() + Color.RESET);

        Color.info("ESTADO FINAL");
        mostrarEstadoDetallado();

        if (pila.estaLlena()) {
            Color.advertencia("Resultado: La pila está llena.");
        } else {
            Color.exito("Resultado: La pila tiene espacios disponibles.");
        }
    }

    /**
     * Consulta la cantidad de elementos almacenados en la pila.
     *
     * <p>Muestra el valor del tope y el cálculo que se realizó a través
     * de la expresión size = tope + 1.</p>
     */
    private void ejecutarSize(){
        Color.titulo("\n========== CONSULTAR TAMAÑO DE LA PILA ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoDetallado();

        Color.titulo("PASO 1: Consultar el índice del tope.");
        System.out.println(Color.CIAN + "tope = " + Color.RESET + pila.getTope());

        System.out.println();

        Color.titulo("PASO 2: Calcular el número de elementos.");
        System.out.println(Color.CIAN + "size = tope + 1" + Color.RESET);
        System.out.println("size = " + pila.getTope() + " + 1");
        System.out.println(Color.VERDE + "size = " + pila.size() + Color.RESET);

        Color.info("ESTADO FINAL");
        mostrarEstadoDetallado();

        Color.exito("La cantidad de elementos es: " + pila.size());
    }

    /**
     * Muestra todos los elementos almacenados en la pila.
     *
     * <p>Recorre el arreglo desde la posición del fondo hasta la posición
     * que se indica por el tope, mostrando el índice y el valor de
     * cada elemento.</p>
     */
    private void ejecutarMostrarContenido(){
        System.out.println("\nCONTENIDO DE LA PILA");
        System.out.println("ESTADO INICIAL:");
        mostrarEstadoDetallado();

        if (pila.estaVacia()){
            System.out.println("La pila no contiene elementos.");

        } else {
            System.out.println("PASO 1:Recorrer el arreglo desde el fondo hasta el tope.");

            for (int i = 0; i <= pila.getTope(); i++){
                System.out.println("i = " + i + " -> datos[" + i + "] = " + pila.getDato(i));
            }
        }

        System.out.println("\nESTADO FINAL:");
        mostrarEstadoDetallado();

    }

    /**
     * Muestra el submenú de conversión de expresiones.
     *
     * <p>Facilita la selección de la conversión de infija a postfija
     * o de infija a prefija usando una pila auxiliar.</p>
     *
     * @param scanner Objeto {@code Scanner} usado para leer las opciones.
     */
    private void ejecutarConversionExpresiones(Scanner scanner){
        int opcion;

        do {
            System.out.println();

            Color.titulo("  ╔══════════════════════════════════════════════╗");
            Color.titulo("  ║          CONVERSIÓN DE EXPRESIONES           ║");
            Color.titulo("  ╚══════════════════════════════════════════════╝");

            System.out.println();

            System.out.println(Color.CIAN + "       [1] " + Color.RESET + "Infija a Postfija");

            System.out.println(Color.CIAN + "       [2] " + Color.RESET + "Infija a Prefija");

            Color.separador();

            System.out.println(Color.AMARILLO + Color.NEGRITA + "       [0] Regresar al menú de pilas" + Color.RESET);

            System.out.println();

            opcion = leerEntero(scanner, "Seleccione una conversión: ");

            switch (opcion) {

                case 1:
                    convertirExpresion(scanner, false);
                    break;

                case 2:
                    convertirExpresion(scanner, true);
                    break;

                case 0:
                    Color.info("Regresando al menú de pilas...");
                    break;

                default:
                    Color.advertencia("Opción inválida. Seleccione una opción del menú.");
                    break;
            }

        } while (opcion != 0);

    }

    /**
     * Decide si un carácter corresponde a un operador matemático válido.
     *
     * @param simbolo Carácter que se desea evaluar.
     * @return {@code true} Si es un operador admitido; {@code false} en caso contrario.
     */
    private boolean esOperador(char simbolo){
        return simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/' || simbolo == '^';
    }

    /**
     * Obtiene el nivel de precedencia de un operador matemático.
     *
     * <p>La potencia tiene precedencia 3, la multiplicación y división
     * precedencia 2, y la suma y la resta precendencia 1.</p>
     *
     * @param operador Operador cuyo nivel de precedencia se consulta.
     * @return Valor entero el cual representa su precedencia.
     */
    private int precedencia(char operador){
        switch (operador) {
            case '^':
                return 3;

            case '*':
            case  '/':
                return 2;

            case '+':
            case '-':
                return 1;

            default:
                return 0;
        }

    }

    /**
     * Valida la estructura de la sintaxis básica de una expresión infija.
     *
     * <p>Verifica que haya una secuencia correcta de operandos
     * y operadores, que los paréntesis estén equilibrados y que
     * no existan operadores consecutivos ni operandos adyacentes.</p>
     *
     * @param expresion Expresión infija que se desea validar.
     * @return {@code true} Si la expresión cumple las válidaciones básicas.
     */
    private boolean validarExpresion(String expresion){
        if (expresion.isEmpty()) {
            return false;
        }

        boolean esperarOperando = true;
        int parentesisAbiertos = 0;

        for (int i = 0; i < expresion.length(); i++) {
            char simbolo = expresion.charAt(i);

            if (Character.isLetterOrDigit(simbolo)) {
                if (!esperarOperando) {
                    return false;
                }

                esperarOperando = false;
                
            } else if (simbolo == '(') {
                if (!esperarOperando) {
                    return false;
                }

                parentesisAbiertos++;
                esperarOperando = true;
            } else if (simbolo == ')') {
                if (esperarOperando || parentesisAbiertos == 0) {
                    return false;
                }

                parentesisAbiertos--;
                esperarOperando = false;
            } else if (esOperador(simbolo)) {
                if (esperarOperando) {
                    return false;
                }

                esperarOperando = true;
            } else {
                return false;

            }
        }

        return !esperarOperando && parentesisAbiertos == 0;
    }

    /**
     * Ilustra de manera gráfica el contenido de la pila auxliar.
     *
     * <p>Los valores enteros que están almacenados se interpretan como caracteres
     * y se presentan desde el fondo hasta la cima, identificando el tope.</p>
     *
     * @param pilaAux Pila auxiliar usada durante la conversión.
     */
    private void mostrarPilaConversion(PilaArreglo pilaAux){
        System.out.print(Color.CIAN + "  Pila auxiliar: " + Color.RESET);

        if (pilaAux.estaVacia()) {

            System.out.println(Color.GRIS + "[ VACÍA ]" + Color.RESET);

        } else {

            System.out.print("[ ");

            for (int i = 0; i <= pilaAux.getTope(); i++) {

                char dato = (char) pilaAux.getDato(i);

                if (i == pilaAux.getTope()) {

                    System.out.print(Color.VERDE + Color.NEGRITA + dato + " ← TOPE " + Color.RESET);

                } else {

                    System.out.print(Color.BLANCO + dato + Color.RESET);
                }

                if (i < pilaAux.getTope()) {
                    System.out.print(" | ");
                }
            }

            System.out.println(" ]");
        }

    }

    private void convertirExpresion(Scanner scanner, boolean convertirPrefija){
        System.out.println();

        Color.titulo(convertirPrefija
                ? "========== CONVERSIÓN INFIJA A PREFIJA ==========" :
                "========== CONVERSIÓN INFIJA A POSTFIJA ==========");

        System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + "Ingrese la expresión infija: ");

        String expresionOriginal = scanner.nextLine();

        // Se eliminan los espacios de la expresión.
        String expresion = expresionOriginal.replaceAll("\\s+", "");

        // Se valida la estructura de la expresión.
        if (!validarExpresion(expresion)) {

            Color.error("La expresión ingresada no es válida.");

            Color.info("Verifique los operandos, operadores y paréntesis.");

            return;
        }

        // Se prepara la expresión que será recorrida.
        String expresionProcesar = expresion;

        if (convertirPrefija) {

            StringBuilder invertida = new StringBuilder();

            for (int i = expresion.length() - 1; i >= 0; i--) {

                char simbolo = expresion.charAt(i);

                if (simbolo == '(') {
                    simbolo = ')';

                } else if (simbolo == ')') {
                    simbolo = '(';
                }

                invertida.append(simbolo);
            }

            expresionProcesar = invertida.toString();
        }

        // Pila auxiliar con capacidad suficiente para la expresión.
        PilaArreglo pilaAux = new PilaArreglo(expresion.length());

        // Almacena los operandos y operadores de salida.
        StringBuilder resultado = new StringBuilder();

        // Variables auxiliares del procedimiento.
        char simbolo;
        char operadorTope;
        int i;

        System.out.println();

        Color.info("ESTADO INICIAL");

        System.out.println(Color.CIAN + "  Expresión original: " + Color.RESET + expresion);

        System.out.println(Color.CIAN + "  Expresión procesada: " + Color.RESET + expresionProcesar);

        System.out.println(Color.CIAN + "  Resultado inicial: " + Color.RESET + "[ vacío ]");

        mostrarPilaConversion(pilaAux);

        System.out.println();

        Color.titulo("========== EJECUCIÓN PASO A PASO ==========");

        // Recorrido de los símbolos de la expresión.
        for (i = 0; i < expresionProcesar.length(); i++) {

            simbolo = expresionProcesar.charAt(i);

            System.out.println();

            Color.titulo("PASO " + (i + 1));

            System.out.println(Color.CIAN + "  Variable i: " + Color.RESET + i);

            System.out.println(Color.CIAN + "  Símbolo actual: " + Color.RESET + simbolo);

            // Caso 1: el símbolo es un operando.
            if (Character.isLetterOrDigit(simbolo)) {

                Color.info("El símbolo es un operando.");

                resultado.append(simbolo);

                System.out.println(Color.VERDE + "  Se agrega al resultado: " + simbolo + Color.RESET);
            }

            // Caso 2: el símbolo es un paréntesis de apertura.
            else if (simbolo == '(') {

                Color.info("Se encontró un paréntesis de apertura.");

                System.out.println(Color.CIAN + "  push(" + simbolo + ")" + Color.RESET);

                pilaAux.push(simbolo);
            }

            // Caso 3: el símbolo es un paréntesis de cierre.
            else if (simbolo == ')') {

                Color.info("Se encontró un paréntesis de cierre.");

                while (!pilaAux.estaVacia()
                        && (char) pilaAux.peek() != '(') {

                    char eliminado = (char) pilaAux.pop();

                    resultado.append(eliminado);

                    System.out.println(Color.AMARILLO + "  pop() -> " + eliminado + Color.RESET);

                    System.out.println(Color.VERDE + "  Resultado: " + resultado + Color.RESET);

                    mostrarPilaConversion(pilaAux);
                }

                // Se elimina el paréntesis de apertura.
                if (!pilaAux.estaVacia()) {

                    char eliminado = (char) pilaAux.pop();

                    System.out.println(Color.AMARILLO + "  pop() -> " + eliminado + Color.RESET);
                }
            }

            // Caso 4: el símbolo es un operador.
            else if (esOperador(simbolo)) {

                Color.info("Se encontró un operador.");

                System.out.println(Color.CIAN + "  Operador actual: " + Color.RESET + simbolo);

                while (!pilaAux.estaVacia() && (char) pilaAux.peek() != '(') {

                    operadorTope = (char) pilaAux.peek();

                    int prioridadTope = precedencia(operadorTope);
                    int prioridadActual = precedencia(simbolo);

                    System.out.println();

                    Color.titulo("COMPARACIÓN DE PRECEDENCIA");

                    System.out.println(Color.CIAN + "  Operador en la cima: " + Color.RESET + operadorTope);

                    System.out.println(Color.CIAN + "  Operador actual: " + Color.RESET + simbolo);

                    System.out.println(Color.CIAN + "  precedencia(tope): " + Color.RESET + prioridadTope);

                    System.out.println(Color.CIAN + "  precedencia(actual): " + Color.RESET + prioridadActual);

                    /*
                     * Para postfija desapilamos cuando el operador
                     * de la cima tiene mayor precedencia o cuando
                     * tienen igual precedencia y el operador actual
                     * es asociativo por la izquierda.
                     *
                     * Para prefija se invierte la asociatividad,
                     * debido al recorrido de la expresión invertida.
                     */
                    boolean desapilar;

                    if (convertirPrefija) {

                        desapilar = prioridadTope > prioridadActual || (prioridadTope == prioridadActual &&
                                simbolo == '^');

                    } else {

                        desapilar = prioridadTope > prioridadActual || (prioridadTope == prioridadActual &&
                                simbolo != '^');
                    }

                    System.out.println(Color.CIAN + "  ¿Desapilar?: " + Color.RESET + desapilar);

                    if (!desapilar) {
                        break;
                    }

                    char eliminado = (char) pilaAux.pop();

                    resultado.append(eliminado);

                    Color.exito("Se desapila el operador: " + eliminado);

                    System.out.println(Color.VERDE + "  Resultado: " + resultado + Color.RESET);

                    mostrarPilaConversion(pilaAux);
                }

                // Se inserta el operador actual en la pila.
                pilaAux.push(simbolo);

                Color.exito("Se apila el operador: " + simbolo);
            }

            System.out.println();

            Color.titulo("ESTADO DESPUÉS DEL PASO " + (i + 1));

            System.out.println(Color.VERDE + "  Resultado parcial: " + resultado + Color.RESET);

            mostrarPilaConversion(pilaAux);
        }

        // Se extraen los operadores que permanecen en la pila.
        System.out.println();

        Color.titulo("PASO FINAL: VACIAR LA PILA AUXILIAR");

        while (!pilaAux.estaVacia()) {

            char eliminado = (char) pilaAux.pop();

            resultado.append(eliminado);

            System.out.println(Color.AMARILLO + "  pop() -> " + eliminado + Color.RESET);

            System.out.println(Color.VERDE + "  Resultado parcial: " + resultado + Color.RESET);

            mostrarPilaConversion(pilaAux);
        }

        // Se invierte el resultado cuando se solicita notación prefija.
        String resultadoFinal;

        if (convertirPrefija) {

            resultadoFinal = resultado.reverse().toString();

        } else {

            resultadoFinal = resultado.toString();
        }

        System.out.println();

        Color.titulo("========== ESTADO FINAL ==========");

        System.out.println(Color.CIAN + "  Expresión infija: " + Color.RESET + expresion);

        System.out.println(Color.CIAN + "  Resultado: " + Color.VERDE + Color.NEGRITA + resultadoFinal + Color.RESET);

        mostrarPilaConversion(pilaAux);

        Color.exito("La conversión se realizó correctamente.");

        System.out.println();
    }

    /**
     * Ejecuta el menú de operaciones de la pila.
     * <p>Inicializa la estructura si todavía no ha sido creada y le permite
     * al usuario seleccionar las operaciones disponibles hasta que
     * elija regresar el menú principal.</p>
     *
     * @param scanner Objeto ({@code Scanner}) compartido para leer
     *                las opciones y los datos ingresados por el usuario.
     */
    public void ejecutaMenu(Scanner scanner){

        // La pila se crea solamente una vez.
        if (pila == null){
            iniciarPila(scanner);
        }

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Ingrese una opción: ");

            try{
                switch (opcion){
                    case 1:
                        ejecutarPush(scanner);
                        break;

                    case 2:
                        ejecutarPop();
                        break;

                    case 3:
                        ejecutarPeek();
                        break;

                    case 4:
                        ejecutarEstaVacia();
                        break;

                    case 5:
                        ejecutarEstaLlena();
                        break;

                    case 6:
                        ejecutarSize();
                        break;

                    case 7:
                        ejecutarMostrarContenido();
                        break;
                    case 8:
                        ejecutarConversionExpresiones(scanner);
                        break;

                    case 0:
                        System.out.println();
                        Color.info("Regresando al menú principal...");
                        break;

                    default:
                        Color.advertencia("Opción inválida. Seleccione una opción del menú.");
                        break;

                }

            } catch(IllegalStateException illegalStateException){
                Color.error("No se pudo realizar la operación: " + illegalStateException.getMessage());
            }

        } while (opcion != 0);
    }
}
