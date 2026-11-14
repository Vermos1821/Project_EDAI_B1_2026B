
package org.example.cola;

import org.example.util.Color;

import java.util.Scanner;

/**
 * Gestiona los submenús de operaciones de las estructuras de datos
 * Cola Simple y Cola Circular.
 * Permite insertar, eliminar, consultar el frente, verificar el estado
 * de las colas y mostrar el procedimiento de cada operación.
 */
public class ColaMenu {

    /**
     * Objeto que representa la cola simple.
     */
    private ColaSimple colaSimple;

    /**
     * Objeto que representa la cola circular.
     */
    private ColaCircular colaCircular;

    /**
     * Muestra el menú principal de las estructuras de colas.
     */
    private void mostrarMenu() {

        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║               ESTRUCTURA DE COLAS            ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        System.out.println();

        System.out.println(Color.AZUL + Color.NEGRITA
                + "       SELECCIONE EL TIPO DE COLA:"
                + Color.RESET);

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET
                + "Cola simple");

        System.out.println(Color.CIAN + "       [2] " + Color.RESET
                + "Cola circular");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Regresar al menú principal"
                + Color.RESET);

        System.out.println();
    }

    /**
     * Lee un número entero ingresado por el usuario y valida la entrada.
     * @param scanner objeto Scanner utilizado para leer los datos.
     * @param mensaje texto que se muestra al solicitar el número.
     * @return número entero ingresado por el usuario.
     */
    private int leerEntero(Scanner scanner, String mensaje) {

        while (true) {

            System.out.print(Color.AMARILLO + Color.NEGRITA
                    + "  ➤ " + Color.RESET + Color.BLANCO
                    + mensaje + Color.RESET);

            String entrada = scanner.nextLine();

            try {

                // Convierte el texto ingresado en un número entero.
                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {

                // Informa que el dato ingresado no es válido.
                Color.error("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    /**
     * Solicita la capacidad de las colas y crea ambas estructuras.
     * @param scanner objeto Scanner utilizado para leer la capacidad.
     */
    private void iniciarColas(Scanner scanner) {

        int capacidad;

        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║               CONFIGURAR COLAS               ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        // Solicita una capacidad mayor que cero.
        do {

            capacidad = leerEntero(
                    scanner,
                    "Ingrese la capacidad de las colas: "
            );

            if (capacidad <= 0) {
                Color.advertencia(
                        "La capacidad debe ser mayor que cero."
                );
            }

        } while (capacidad <= 0);

        // Crea las dos estructuras con la misma capacidad.
        colaSimple = new ColaSimple(capacidad);
        colaCircular = new ColaCircular(capacidad);

        Color.exito("Las colas fueron creadas correctamente.");

        Color.info("Capacidad establecida: " + capacidad + " elementos.");
    }

    /**
     * Muestra detalladamente el estado actual de la cola simple.
     * Representa las posiciones del arreglo, los elementos almacenados,
     * el frente, el final, la cantidad y los espacios disponibles.
     */
    private void mostrarEstadoSimple() {

        System.out.println();

        Color.titulo("  ┌──────────── ESTADO ACTUAL: COLA SIMPLE ────────────┐");

        System.out.print(Color.CIAN + Color.NEGRITA
                + "  Arreglo: " + Color.RESET + "[");

        // Recorre todas las posiciones del arreglo.
        for (int i = 0; i < colaSimple.getCapacidad(); i++) {

            // Comprueba si la posición pertenece a la cola.
            if (!colaSimple.estaVacia()
                    && i >= colaSimple.getFrente()
                    && i <= colaSimple.getFin()) {

                // Muestra los elementos almacenados.
                System.out.print(Color.VERDE + Color.NEGRITA
                        + colaSimple.getDato(i) + Color.RESET);

            } else {

                // Muestra las posiciones que no pertenecen a la cola.
                System.out.print(Color.GRIS + "_" + Color.RESET);
            }

            // Separa los elementos del arreglo.
            if (i < colaSimple.getCapacidad() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");

        System.out.println(Color.CIAN + "  Frente: " + Color.RESET
                + Color.AMARILLO + colaSimple.getFrente() + Color.RESET);

        System.out.println(Color.CIAN + "  Final: " + Color.RESET
                + Color.AMARILLO + colaSimple.getFin() + Color.RESET);

        System.out.println(Color.CIAN + "  Elementos: " + Color.RESET
                + colaSimple.size());

        System.out.println(Color.CIAN + "  Capacidad: " + Color.RESET
                + colaSimple.getCapacidad());

        System.out.println(Color.CIAN + "  Espacios disponibles: " + Color.RESET
                + (colaSimple.getCapacidad() - colaSimple.size()));

        System.out.println(Color.CIAN + "  Está vacía: " + Color.RESET
                + colaSimple.estaVacia());

        System.out.println(Color.CIAN + "  Está llena: " + Color.RESET
                + colaSimple.estaLlena());

        Color.titulo("  └──────────────────────────────────────────────────┘");

        System.out.println();
    }

    /**
     * Muestra detalladamente el estado actual de la cola circular.
     * Representa todas las posiciones del arreglo, identifica los
     * elementos almacenados y muestra los índices del frente y final.
     */
    private void mostrarEstadoCircular() {

        System.out.println();

        Color.titulo("  ┌────────── ESTADO ACTUAL: COLA CIRCULAR ──────────┐");

        System.out.print(Color.CIAN + Color.NEGRITA
                + "  Arreglo: " + Color.RESET + "[");

        // Recorre todas las posiciones físicas del arreglo.
        for (int i = 0; i < colaCircular.getCapacidad(); i++) {

            boolean ocupado = false;

            // Comprueba si la posición actual contiene un elemento de la cola.
            for (int j = 0; j < colaCircular.size(); j++) {

                int indice = (colaCircular.getFrente() + j)
                        % colaCircular.getCapacidad();

                if (indice == i) {
                    ocupado = true;
                    break;
                }
            }

            if (ocupado) {

                // Muestra el elemento almacenado.
                System.out.print(Color.VERDE + Color.NEGRITA
                        + colaCircular.getDato(i) + Color.RESET);

            } else {

                // Representa un espacio disponible.
                System.out.print(Color.GRIS + "_" + Color.RESET);
            }

            if (i < colaCircular.getCapacidad() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");

        System.out.println(Color.CIAN + "  Frente: " + Color.RESET
                + Color.AMARILLO + colaCircular.getFrente() + Color.RESET);

        System.out.println(Color.CIAN + "  Final: " + Color.RESET
                + Color.AMARILLO + colaCircular.getFin() + Color.RESET);

        System.out.println(Color.CIAN + "  Elementos: " + Color.RESET
                + colaCircular.size());

        System.out.println(Color.CIAN + "  Capacidad: " + Color.RESET
                + colaCircular.getCapacidad());

        System.out.println(Color.CIAN + "  Espacios disponibles: " + Color.RESET
                + (colaCircular.getCapacidad() - colaCircular.size()));

        System.out.println(Color.CIAN + "  Está vacía: " + Color.RESET
                + colaCircular.estaVacia());

        System.out.println(Color.CIAN + "  Está llena: " + Color.RESET
                + colaCircular.estaLlena());

        Color.titulo("  └────────────────────────────────────────────────┘");

        System.out.println();
    }

    /**
     * Muestra las opciones disponibles para operar con la cola simple.
     */
    private void mostrarMenuSimple() {

        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║                 COLA SIMPLE                   ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET
                + "Enqueue - Insertar elemento");

        System.out.println(Color.CIAN + "       [2] " + Color.RESET
                + "Dequeue - Eliminar elemento");

        System.out.println(Color.CIAN + "       [3] " + Color.RESET
                + "Peek - Consultar frente");

        System.out.println(Color.CIAN + "       [4] " + Color.RESET
                + "Verificar si está vacía");

        System.out.println(Color.CIAN + "       [5] " + Color.RESET
                + "Verificar si está llena");

        System.out.println(Color.CIAN + "       [6] " + Color.RESET
                + "Consultar tamaño");

        System.out.println(Color.CIAN + "       [7] " + Color.RESET
                + "Mostrar contenido de la cola");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Regresar al menú anterior"
                + Color.RESET);
    }

    /**
     * Muestra las opciones disponibles para operar con la cola circular.
     */
    private void mostrarMenuCircular() {

        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║                COLA CIRCULAR                  ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET
                + "Enqueue - Insertar elemento");

        System.out.println(Color.CIAN + "       [2] " + Color.RESET
                + "Dequeue - Eliminar elemento");

        System.out.println(Color.CIAN + "       [3] " + Color.RESET
                + "Peek - Consultar frente");

        System.out.println(Color.CIAN + "       [4] " + Color.RESET
                + "Verificar si está vacía");

        System.out.println(Color.CIAN + "       [5] " + Color.RESET
                + "Verificar si está llena");

        System.out.println(Color.CIAN + "       [6] " + Color.RESET
                + "Consultar tamaño");

        System.out.println(Color.CIAN + "       [7] " + Color.RESET
                + "Mostrar contenido de la cola");

        Color.separador();

        System.out.println(Color.AMARILLO + Color.NEGRITA
                + "       [0] Regresar al menú anterior"
                + Color.RESET);
    }

    /**
     * Ejecuta la inserción de un elemento en la cola simple.
     * Muestra el estado inicial, comprueba si existe espacio disponible,
     * actualiza el índice final e inserta el nuevo elemento.
     * @param scanner objeto Scanner utilizado para leer el elemento.
     */
    private void ejecutarEnqueueSimple(Scanner scanner) {

        Color.titulo("\n========== OPERACIÓN ENQUEUE: COLA SIMPLE ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoSimple();

        int dato = leerEntero(scanner, "Ingrese el elemento que desea insertar: ");

        System.out.println();

        Color.titulo("PASO 1: Verificar si la cola está llena.");

        System.out.println("Condición: fin == capacidad - 1");

        System.out.println(
                colaSimple.getFin() + " == "
                        + (colaSimple.getCapacidad() - 1)
                        + " -> " + colaSimple.estaLlena()
        );

        if (colaSimple.estaLlena()) {

            Color.error("Desbordamiento (OVERFLOW).");
            Color.error("No se puede insertar porque la cola está llena.");

            Color.info("ESTADO FINAL");
            mostrarEstadoSimple();

            return;
        }

        Color.exito("Existe espacio disponible.");

        System.out.println();

        if (colaSimple.estaVacia()) {

            Color.titulo("PASO 2: Establecer el frente.");

            System.out.println("La cola está vacía.");
            System.out.println("frente = 0");
        }

        Color.titulo("PASO 3: Incrementar el índice final.");

        System.out.println("fin = " + colaSimple.getFin() + " + 1");

        System.out.println("Nuevo fin = " + (colaSimple.getFin() + 1));

        Color.titulo("PASO 4: Insertar el elemento.");

        System.out.println(
                "datos[" + (colaSimple.getFin() + 1) + "] = " + dato
        );

        // Ejecuta la inserción.
        colaSimple.enqueue(dato);

        Color.info("ESTADO FINAL");
        mostrarEstadoSimple();

        Color.exito("Elemento insertado correctamente.");
    }

    /**
     * Ejecuta la eliminación de un elemento de la cola simple.
     * Comprueba si existen elementos, obtiene el dato del frente
     * y actualiza el índice correspondiente.
     */
    private void ejecutarDequeueSimple() {

        Color.titulo("\n========== OPERACIÓN DEQUEUE: COLA SIMPLE ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoSimple();

        Color.titulo("PASO 1: Verificar si la cola está vacía.");

        System.out.println("Condición: fin == -1");

        System.out.println(
                colaSimple.getFin() + " == -1 -> "
                        + colaSimple.estaVacia()
        );

        if (colaSimple.estaVacia()) {

            Color.error("Subdesbordamiento (UNDERFLOW).");
            Color.error("No se puede eliminar porque la cola está vacía.");

            Color.info("ESTADO FINAL");
            mostrarEstadoSimple();

            return;
        }

        Color.titulo("PASO 2: Obtener el elemento del frente.");

        System.out.println(
                "dato = datos[" + colaSimple.getFrente() + "]"
        );

        System.out.println("Elemento: " + colaSimple.peek());

        int dato = colaSimple.peek();

        if (colaSimple.getFrente() == colaSimple.getFin()) {

            Color.titulo("PASO 3: Reiniciar los índices.");

            System.out.println("Era el último elemento de la cola.");
            System.out.println("frente = 0");
            System.out.println("fin = -1");

        } else {

            Color.titulo("PASO 3: Avanzar el frente.");

            System.out.println(
                    "frente = " + colaSimple.getFrente() + " + 1"
            );

            System.out.println(
                    "Nuevo frente = " + (colaSimple.getFrente() + 1)
            );
        }

        // Ejecuta la eliminación.
        colaSimple.dequeue();

        Color.info("ESTADO FINAL");
        mostrarEstadoSimple();

        Color.exito("Elemento eliminado: " + dato);
    }

    /**
     * Ejecuta la inserción de un elemento en la cola circular.
     * Comprueba la capacidad, calcula la nueva posición final mediante
     * el operador módulo e inserta el elemento.
     * @param scanner objeto Scanner utilizado para leer el elemento.
     */
    private void ejecutarEnqueueCircular(Scanner scanner) {

        Color.titulo("\n========== OPERACIÓN ENQUEUE: COLA CIRCULAR ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoCircular();

        int dato = leerEntero(scanner, "Ingrese el elemento que desea insertar: ");

        System.out.println();

        Color.titulo("PASO 1: Verificar si la cola está llena.");

        System.out.println("Condición: cantidad == capacidad");

        System.out.println(
                colaCircular.size() + " == "
                        + colaCircular.getCapacidad()
                        + " -> " + colaCircular.estaLlena()
        );

        if (colaCircular.estaLlena()) {

            Color.error("Desbordamiento (OVERFLOW).");
            Color.error("La cola circular está llena.");

            Color.info("ESTADO FINAL");
            mostrarEstadoCircular();

            return;
        }

        if (colaCircular.estaVacia()) {

            Color.titulo("PASO 2: Establecer el frente.");

            System.out.println("La cola está vacía.");
            System.out.println("frente = 0");
        }

        Color.titulo("PASO 3: Calcular la nueva posición final.");

        System.out.println(
                "fin = (fin + 1) % capacidad"
        );

        System.out.println(
                "fin = (" + colaCircular.getFin() + " + 1) % "
                        + colaCircular.getCapacidad()
        );

        int nuevoFin = (colaCircular.getFin() + 1)
                % colaCircular.getCapacidad();

        System.out.println("Nuevo fin = " + nuevoFin);

        Color.titulo("PASO 4: Insertar el elemento.");

        System.out.println("datos[" + nuevoFin + "] = " + dato);

        // Ejecuta la inserción.
        colaCircular.enqueue(dato);

        Color.titulo("PASO 5: Actualizar la cantidad.");

        System.out.println(
                "Nueva cantidad = " + colaCircular.size()
        );

        Color.info("ESTADO FINAL");
        mostrarEstadoCircular();

        Color.exito("Elemento insertado correctamente.");
    }

    /**
     * Ejecuta la eliminación de un elemento de la cola circular.
     * Comprueba si está vacía, obtiene el elemento del frente,
     * disminuye la cantidad y actualiza el índice de forma circular.
     */
    private void ejecutarDequeueCircular() {

        Color.titulo("\n========== OPERACIÓN DEQUEUE: COLA CIRCULAR ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoCircular();

        Color.titulo("PASO 1: Verificar si la cola está vacía.");

        System.out.println("Condición: cantidad == 0");

        System.out.println(
                colaCircular.size() + " == 0 -> "
                        + colaCircular.estaVacia()
        );

        if (colaCircular.estaVacia()) {

            Color.error("Subdesbordamiento (UNDERFLOW).");
            Color.error("La cola circular está vacía.");

            Color.info("ESTADO FINAL");
            mostrarEstadoCircular();

            return;
        }

        Color.titulo("PASO 2: Obtener el elemento del frente.");

        System.out.println(
                "dato = datos[" + colaCircular.getFrente() + "]"
        );

        System.out.println("Elemento: " + colaCircular.peek());

        int dato = colaCircular.peek();

        Color.titulo("PASO 3: Disminuir la cantidad de elementos.");

        System.out.println(
                "cantidad = " + colaCircular.size() + " - 1"
        );

        if (colaCircular.size() == 1) {

            Color.titulo("PASO 4: Reiniciar los índices.");

            System.out.println("La cola quedará vacía.");
            System.out.println("frente = 0");
            System.out.println("fin = -1");

        } else {

            Color.titulo("PASO 4: Avanzar el frente circularmente.");

            System.out.println("frente = (frente + 1) % capacidad");

            System.out.println(
                    "frente = (" + colaCircular.getFrente() + " + 1) % "
                            + colaCircular.getCapacidad()
            );

            int nuevoFrente = (colaCircular.getFrente() + 1)
                    % colaCircular.getCapacidad();

            System.out.println("Nuevo frente = " + nuevoFrente);
        }

        // Ejecuta la eliminación.
        colaCircular.dequeue();

        Color.info("ESTADO FINAL");
        mostrarEstadoCircular();

        Color.exito("Elemento eliminado: " + dato);
    }

    /**
     * Ejecuta la consulta del frente de la cola simple.
     */
    private void ejecutarPeekSimple() {

        Color.titulo("\n========== CONSULTAR FRENTE: COLA SIMPLE ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoSimple();

        Color.titulo("PASO 1: Verificar si la cola está vacía.");

        System.out.println("Resultado: " + colaSimple.estaVacia());

        if (colaSimple.estaVacia()) {

            Color.error("No existen elementos para consultar.");

        } else {

            Color.titulo("PASO 2: Consultar el elemento del frente.");

            System.out.println(
                    "datos[" + colaSimple.getFrente() + "] = "
                            + colaSimple.peek()
            );

            Color.exito("Elemento del frente: " + colaSimple.peek());
        }

        Color.info("ESTADO FINAL");
        mostrarEstadoSimple();

        Color.info("La consulta no modifica la cola.");
    }

    /**
     * Ejecuta la consulta del frente de la cola circular.
     */
    private void ejecutarPeekCircular() {

        Color.titulo("\n========== CONSULTAR FRENTE: COLA CIRCULAR ==========");

        Color.info("ESTADO INICIAL");
        mostrarEstadoCircular();

        Color.titulo("PASO 1: Verificar si la cola está vacía.");

        System.out.println("Resultado: " + colaCircular.estaVacia());

        if (colaCircular.estaVacia()) {

            Color.error("No existen elementos para consultar.");

        } else {

            Color.titulo("PASO 2: Consultar el elemento del frente.");

            System.out.println(
                    "datos[" + colaCircular.getFrente() + "] = "
                            + colaCircular.peek()
            );

            Color.exito("Elemento del frente: " + colaCircular.peek());
        }

        Color.info("ESTADO FINAL");
        mostrarEstadoCircular();

        Color.info("La consulta no modifica la cola.");
    }

    /**
     * Ejecuta la verificación de si la cola simple está vacía.
     */
    private void ejecutarEstaVaciaSimple() {

        Color.titulo("\n========== VERIFICAR COLA SIMPLE VACÍA ==========");

        mostrarEstadoSimple();

        Color.titulo("PASO 1: Consultar el índice final.");

        System.out.println("fin = " + colaSimple.getFin());

        Color.titulo("PASO 2: Evaluar la condición fin == -1.");

        System.out.println(
                "Resultado: " + colaSimple.estaVacia()
        );

        if (colaSimple.estaVacia()) {
            Color.info("La cola simple está vacía.");
        } else {
            Color.exito("La cola simple contiene elementos.");
        }

        Color.info("La verificación no modifica la cola.");
    }

    /**
     * Ejecuta la verificación de si la cola simple está llena.
     */
    private void ejecutarEstaLlenaSimple() {

        Color.titulo("\n========== VERIFICAR COLA SIMPLE LLENA ==========");

        mostrarEstadoSimple();

        Color.titulo("PASO 1: Consultar el índice final y la capacidad.");

        System.out.println("fin = " + colaSimple.getFin());

        System.out.println(
                "Última posición = " + (colaSimple.getCapacidad() - 1)
        );

        Color.titulo("PASO 2: Evaluar fin == capacidad - 1.");

        System.out.println(
                "Resultado: " + colaSimple.estaLlena()
        );

        if (colaSimple.estaLlena()) {
            Color.advertencia("La cola simple está llena.");
        } else {
            Color.exito("La cola simple tiene espacio para insertar.");
        }

        Color.info("La verificación no modifica la cola.");
    }

    /**
     * Ejecuta la verificación de si la cola circular está vacía.
     */
    private void ejecutarEstaVaciaCircular() {

        Color.titulo("\n========== VERIFICAR COLA CIRCULAR VACÍA ==========");

        mostrarEstadoCircular();

        Color.titulo("PASO 1: Consultar la cantidad de elementos.");

        System.out.println("cantidad = " + colaCircular.size());

        Color.titulo("PASO 2: Evaluar cantidad == 0.");

        System.out.println(
                "Resultado: " + colaCircular.estaVacia()
        );

        if (colaCircular.estaVacia()) {
            Color.info("La cola circular está vacía.");
        } else {
            Color.exito("La cola circular contiene elementos.");
        }

        Color.info("La verificación no modifica la cola.");
    }

    /**
     * Ejecuta la verificación de si la cola circular está llena.
     */
    private void ejecutarEstaLlenaCircular() {

        Color.titulo("\n========== VERIFICAR COLA CIRCULAR LLENA ==========");

        mostrarEstadoCircular();

        Color.titulo("PASO 1: Consultar la cantidad y la capacidad.");

        System.out.println("cantidad = " + colaCircular.size());

        System.out.println(
                "capacidad = " + colaCircular.getCapacidad()
        );

        Color.titulo("PASO 2: Evaluar cantidad == capacidad.");

        System.out.println(
                "Resultado: " + colaCircular.estaLlena()
        );

        if (colaCircular.estaLlena()) {
            Color.advertencia("La cola circular está llena.");
        } else {
            Color.exito("La cola circular tiene espacios disponibles.");
        }

        Color.info("La verificación no modifica la cola.");
    }

    /**
     * Muestra el contenido de la cola simple desde el frente hasta el final.
     */
    private void ejecutarMostrarSimple() {

        Color.titulo("\n========== CONTENIDO DE LA COLA SIMPLE ==========");

        mostrarEstadoSimple();

        if (colaSimple.estaVacia()) {

            Color.info("La cola no contiene elementos.");

        } else {

            Color.titulo("PASO 1: Recorrer desde el frente hasta el final.");

            for (int i = colaSimple.getFrente();
                 i <= colaSimple.getFin(); i++) {

                System.out.println(
                        "i = " + i + " -> datos[" + i + "] = "
                                + colaSimple.getDato(i)
                );
            }
        }

        Color.info("ESTADO FINAL");
        mostrarEstadoSimple();
    }

    /**
     * Muestra los elementos de la cola circular siguiendo su recorrido lógico.
     */
    private void ejecutarMostrarCircular() {

        Color.titulo("\n========== CONTENIDO DE LA COLA CIRCULAR ==========");

        mostrarEstadoCircular();

        if (colaCircular.estaVacia()) {

            Color.info("La cola circular no contiene elementos.");

        } else {

            Color.titulo("PASO 1: Recorrer los elementos de forma circular.");

            for (int i = 0; i < colaCircular.size(); i++) {

                int indice = (colaCircular.getFrente() + i)
                        % colaCircular.getCapacidad();

                System.out.println(
                        "i = " + i + " -> datos[" + indice + "] = "
                                + colaCircular.getDato(indice)
                );
            }
        }

        Color.info("ESTADO FINAL");
        mostrarEstadoCircular();
    }

    /**
     * Ejecuta el menú de operaciones de la cola simple.
     *
     * @param scanner objeto Scanner compartido para leer opciones y datos.
     */
    private void ejecutarMenuSimple(Scanner scanner) {

        int opcion;

        do {

            mostrarMenuSimple();

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {

                case 1:
                    ejecutarEnqueueSimple(scanner);
                    break;

                case 2:
                    ejecutarDequeueSimple();
                    break;

                case 3:
                    ejecutarPeekSimple();
                    break;

                case 4:
                    ejecutarEstaVaciaSimple();
                    break;

                case 5:
                    ejecutarEstaLlenaSimple();
                    break;

                case 6:
                    Color.info("Cantidad de elementos: " + colaSimple.size());
                    mostrarEstadoSimple();
                    break;

                case 7:
                    ejecutarMostrarSimple();
                    break;

                case 0:
                    Color.info("Regresando al menú de colas...");
                    break;

                default:
                    Color.advertencia("Opción inválida.");
            }

        } while (opcion != 0);
    }

    /**
     * Ejecuta el menú de operaciones de la cola circular.
     *
     * @param scanner objeto Scanner compartido para leer opciones y datos.
     */
    private void ejecutarMenuCircular(Scanner scanner) {

        int opcion;

        do {

            mostrarMenuCircular();

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {

                case 1:
                    ejecutarEnqueueCircular(scanner);
                    break;

                case 2:
                    ejecutarDequeueCircular();
                    break;

                case 3:
                    ejecutarPeekCircular();
                    break;

                case 4:
                    ejecutarEstaVaciaCircular();
                    break;

                case 5:
                    ejecutarEstaLlenaCircular();
                    break;

                case 6:
                    Color.info("Cantidad de elementos: " + colaCircular.size());
                    mostrarEstadoCircular();
                    break;

                case 7:
                    ejecutarMostrarCircular();
                    break;

                case 0:
                    Color.info("Regresando al menú de colas...");
                    break;

                default:
                    Color.advertencia("Opción inválida.");
            }

        } while (opcion != 0);
    }

    /**
     * Ejecuta el menú general de las estructuras de colas.
     *
     * Inicializa las dos estructuras y permite seleccionar entre
     * la cola simple y la cola circular.
     *
     * @param scanner objeto Scanner compartido para leer las opciones.
     */
    public void ejecutaMenu(Scanner scanner) {

        // Las estructuras se crean una sola vez.
        if (colaSimple == null || colaCircular == null) {
            iniciarColas(scanner);
        }

        int opcion;

        do {

            mostrarMenu();

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {

                case 1:
                    ejecutarMenuSimple(scanner);
                    break;

                case 2:
                    ejecutarMenuCircular(scanner);
                    break;

                case 0:
                    Color.info("Regresando al menú principal...");
                    break;

                default:
                    Color.advertencia(
                            "Opción inválida. Seleccione una opción del menú."
                    );
                    break;
            }

        } while (opcion != 0);
    }
}
