package org.example.pilas;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Gestiona el submenú de operaciones de la estructura de datos Pila.
 * Permite realizar las operaciones básicas de una pila implementada
 * a través de un arreglo, ilustrando el procedimiento y los estados
 * de la estructura durante cada operación
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

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA + "       [0] Regresar al menú principal" + Color.RESET);

        System.out.println();

    }

    /**
     * Lee un número entero que fue ingresado por el usuario y valida que
     * la entrada sea correcta.
     *
     * @param scanner objeto ({@code Scanner}) utilizado para leer la entrada
     * @param mensaje texto que se muestra al solicitar el número
     * @return número entero que fue ingresado por el usuario
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
     * @param scanner objeto ({@code Scanner}) usado para leer la capacidad
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
     * Muestra de manera detallada el estado actual de la pila.
     * Representa los elementos almacenados, las posiciones disponibles,
     * el índice del tope, la cantidad de elementos y la capacidad máxima.
     */
    private void mostrarEstadoDetallado(){
        System.out.println();
        Color.titulo("  ┌──────────── ESTADO ACTUAL DE LA PILA ────────────┐");

        System.out.print(Color.CIAN + Color.NEGRITA + "  Arreglo: " + Color.RESET + "[");

        for (int i = 0; i < pila.getCapacidad(); i++){
            if (i <= pila.getTope()){
                System.out.print(Color.VERDE + Color.NEGRITA + pila.getDato(i) + Color.RESET);

            } else {
                System.out.print(Color.GRIS + "_" + Color.RESET);
            }

            if (i < pila.getCapacidad() - 1){
                System.out.print(", ");
            }

        }

        System.out.println("]");

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
     * Comprueba que la pila no esté vacía, obtiene el elemento que se eliminó
     * y actualiza el índice del tope.
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
     * Verifica si la pila está vacía.
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
     * Verifica si la pila alcanzó su capacidad máxima.
     * Muestra los valores del tope y la capacidad, y evalúa
     * la condición que determina si se puede insertar elementos.
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
     * Muestra el valor del tope y el cálculo que se realizó a través
     * de la expresión size = tope + 1.
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
     * Recorre el arreglo desde la posición del fondo hasta la posición
     * que se indica por el tope, mostrando el índice y el valor de
     * cada elemento.
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
     * Ejecuta el submenú de operaciones de la pila.
     * Inicializa la estructura si todavía no ha sido creada y le permite
     * al usuario seleccionar las operaciones disponibles hasta que
     * elija regresar el menú principal.
     *
     * @param scanner objeto ({@code Scanner}) compartido para leer
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
