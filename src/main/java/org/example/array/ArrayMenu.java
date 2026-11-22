package org.example.array;

import org.example.util.Color;
import java.util.Scanner;

/**
 * Gestiona la interacción del usuario y la visualización detallada de las
 * operaciones sobre el Array Unidimensional.
 *
 * <p>Muestra paso a paso el estado inicial, la evolución del algoritmo,
 * el seguimiento de las variables auxiliares y el estado final de la estructura.</p>
 */
public class ArrayMenu {

    /**
     * Instancia de la estructura de datos Array.
     */
    private Array array;

    /**
     * Despliega el menú principal de operaciones de la estructura Array Unidimensional.
     */
    private void mostrarMenu() {
        System.out.println();

        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║             ARRAYS UNIDIMENSIONALES          ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        System.out.println();
        System.out.println(Color.AZUL + Color.NEGRITA + "       SELECCIONE UNA OPERACIÓN:" + Color.RESET);
        Color.separador();

        System.out.println(Color.CIAN + "       [1] " + Color.RESET + "Crear / Reinicializar Array");
        System.out.println(Color.CIAN + "       [2] " + Color.RESET + "Recorrer y mostrar elementos");
        System.out.println(Color.CIAN + "       [3] " + Color.RESET + "Insertar elemento en posición");
        System.out.println(Color.CIAN + "       [4] " + Color.RESET + "Eliminar elemento de posición");
        System.out.println(Color.CIAN + "       [5] " + Color.RESET + "Buscar un valor");
        System.out.println(Color.CIAN + "       [6] " + Color.RESET + "Verificar estado (Vacío / Lleno)");

        Color.separador();
        System.out.println(Color.AMARILLO + Color.NEGRITA + "       [0] Regresar al menú principal" + Color.RESET);
        System.out.println();
    }

    /**
     * Lee un entero por consola validando que no ocurra NumberFormatException.
     */
    private int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(Color.AMARILLO + Color.NEGRITA + "  ➤ " + Color.RESET + Color.BLANCO + mensaje + Color.RESET);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                Color.error("Entrada inválida. Por favor ingrese un número entero.");
            }
        }
    }

    /**
     * Muestra la representación gráfica del Array en formato horizontal con índices y estado.
     */
    private void mostrarEstadoDetallado() {
        System.out.println();
        Color.titulo("  ┌──────────── ESTADO ACTUAL DEL ARRAY ────────────┐");
        System.out.println(Color.CIAN + Color.NEGRITA + "  Representación gráfica unidimensional:" + Color.RESET);
        System.out.println();

        if (array == null) {
            Color.advertencia("  El array no ha sido inicializado.");
            return;
        }

        int cap = array.getCapacidad();
        int tam = array.getTamanio();


        // Fila de Índices
        System.out.print("  Índ.  ");
        for (int i = 0; i < cap; i++) {
            System.out.printf("   [%d]    ", i);
        }
        System.out.println();

        // Borde Superior
        System.out.print("       ┌");
        for (int i = 0; i < cap; i++) {
            System.out.print("─────────" + (i < cap - 1 ? "┬" : "┐"));
        }
        System.out.println();

        // Fila de Valores
        System.out.print("  Val. │");
        for (int i = 0; i < cap; i++) {
            if (i < tam) {
                System.out.print(Color.VERDE + Color.NEGRITA + String.format("%6d   ", array.getDato(i)) + Color.RESET + "│");
            } else {
                System.out.print(Color.GRIS + "    _    " + Color.RESET + "│");
            }
        }
        System.out.println();

        // Borde inferior
        System.out.print("       └");
        for (int i = 0; i < cap; i++) {
            System.out.print("─────────" + (i < cap - 1 ? "┴" : "┘"));
        }
        System.out.println();

        // Fila de Estado (Ocupado / Libre)
        System.out.print("  Est.  ");
        for (int i = 0; i < cap; i++) {
            if (i < tam) {
                System.out.print(Color.CIAN + " Ocupado " + Color.RESET + " ");
            } else {
                System.out.print(Color.GRIS + " Dispon. " + Color.RESET + " ");
            }
        }
        System.out.println();
        System.out.println();

        System.out.println(Color.CIAN + "  Tamaño actual (elementos): " + Color.RESET + Color.VERDE + Color.NEGRITA + tam + Color.RESET);
        System.out.println(Color.CIAN + "  Capacidad total: " + Color.RESET + Color.BLANCO + cap + Color.RESET);
        System.out.println(Color.CIAN + "  Posiciones disponibles: " + Color.RESET + Color.BLANCO + (cap - tam) + Color.RESET);
        Color.titulo("  └────────────────────────────────────────────────┘");
        System.out.println();
    }

    /**
     * Muestra la Etapa 1 de una operación: el estado inicial del Array.
     */
    private void mostrarEtapaInicial() {
        Color.info("ETAPA 1: ESTADO INICIAL");
        mostrarEstadoDetallado();
    }

    /**
     * Muestra el encabezado de las Etapas 2 y 3 con la descripción estándar
     * (ejecución paso a paso y variables auxiliares).
     */
    private void mostrarEncabezadoEjecucion() {
        mostrarEncabezadoEjecucion("EJECUCIÓN PASO A PASO Y VARIABLES AUXILIARES");
    }

    /**
     * Muestra el encabezado de las Etapas 2 y 3 con una descripción personalizada.
     *
     * @param descripcion Texto que acompaña al encabezado "ETAPA 2 & 3".
     */
    private void mostrarEncabezadoEjecucion(String descripcion) {
        Color.titulo("ETAPA 2 & 3: " + descripcion);
    }

    /**
     * Muestra la Etapa 4 de una operación: el estado final del Array.
     */
    private void mostrarEtapaFinal() {
        Color.info("ETAPA 4: ESTADO FINAL");
        mostrarEstadoDetallado();
    }

    /**
     * Inicializa el array especificando su capacidad y elementos iniciales opcionales.
     */
    private void inicializarArray(Scanner scanner) {
        System.out.println();
        Color.titulo("  ╔══════════════════════════════════════════════╗");
        Color.titulo("  ║               CONFIGURAR ARRAY               ║");
        Color.titulo("  ╚══════════════════════════════════════════════╝");

        int capacidad;
        do {
            capacidad = leerEntero(scanner, "Ingrese la capacidad máxima del array: ");
            if (capacidad <= 0) {
                Color.advertencia("La capacidad debe ser un número entero positivo mayor a cero.");
            }
        } while (capacidad <= 0);

        array = new Array(capacidad);
        Color.exito("Array creado con capacidad de " + capacidad + " elementos.");

        int n;
        do {
            n = leerEntero(scanner, "Ingrese el número de elementos iniciales (0 a " + capacidad + "): ");
            if (n < 0 || n > capacidad) {
                Color.advertencia("La cantidad debe estar entre 0 y " + capacidad + ".");
            }
        } while (n < 0 || n > capacidad);

        for (int i = 0; i < n; i++) {
            int val = leerEntero(scanner, "Elemento en la posición [" + i + "]: ");
            array.insertar(i, val);
        }

        Color.exito("Inicialización completada.");
        mostrarEstadoDetallado();
    }

    /**
     * Recorre y muestra los elementos almacenados en el Array (Operación 2.2 #2).
     */
    private void ejecutarRecorrido() {
        Color.titulo("\n========== OPERACIÓN: RECORRER Y MOSTRAR ==========");

        // ETAPA 1: ESTADO INICIAL
        mostrarEtapaInicial();

        // ETAPA 2 & 3: PASO A PASO Y VARIABLES AUXILIARES
        mostrarEncabezadoEjecucion();
        if (array.estaVacio()) {
            Color.advertencia("El array está vacío. No hay elementos ocupados para recorrer.");
        } else {
            System.out.println(Color.CIAN + "Iniciando bucle de recorrido desde i = 0 hasta i < tamano (" + array.getTamanio() + ")..." + Color.RESET);
            System.out.println();

            for (int i = 0; i < array.getCapacidad(); i++) {
                // Variables Auxiliares
                String estado = (i < array.getTamanio()) ? "OCUPADO" : "DISPONIBLE";
                String valorStr = (i < array.getTamanio()) ? String.valueOf(array.getDato(i)) : "N/A (Libre)";

                System.out.println(Color.BLANCO + "  Paso " + (i + 1) + ":" + Color.RESET +
                        " | Variable Auxiliar " + Color.AMARILLO + "i = " + i + Color.RESET +
                        " | " + Color.CIAN + "Estado = " + estado + Color.RESET +
                        " | " + Color.VERDE + "Valor datos[" + i + "] = " + valorStr + Color.RESET);
            }
        }

        System.out.println();

        // ETAPA 4: ESTADO FINAL
        mostrarEtapaFinal();
        Color.exito("Recorrido finalizado exitosamente.");
    }

    /**
     * Inserta un elemento en una posición indicada con desplazamiento a la derecha (Operación 2.2 #3).
     */
    private void ejecutarInsercion(Scanner scanner) {
        Color.titulo("\n========== OPERACIÓN: INSERTAR EN POSICIÓN ==========");

        // ETAPA 1: ESTADO INICIAL
        mostrarEtapaInicial();

        // Comprobación de límites (Array Lleno)
        if (array.estaLleno()) {
            Color.error("No se puede insertar. El array está LLENO (Desbordamiento).");
            mostrarEtapaFinal();
            return;
        }

        int elemento = leerEntero(scanner, "Ingrese el valor entero a insertar: ");
        int pos = leerEntero(scanner, "Ingrese la posición donde insertar (0 a " + array.getTamanio() + "): ");

        // Comprobación de posición inválida
        if (pos < 0 || pos > array.getTamanio()) {
            Color.error("Posición inválida. No se puede insertar en el índice " + pos + ".");
            Color.info("Rango válido para inserción actual: [0, " + array.getTamanio() + "].");
            mostrarEtapaFinal();
            return;
        }

        System.out.println();
        // ETAPA 2 & 3: PASO A PASO Y VARIABLES AUXILIARES
        mostrarEncabezadoEjecucion();
        System.out.println(Color.CIAN + "Variables Auxiliares Iniciales: " + Color.RESET +
                "elemento = " + elemento + ", posicion = " + pos + ", tamano_inicial = " + array.getTamanio());

        int tamanoAnterior = array.getTamanio();

        if (pos < tamanoAnterior) {
            Color.info("Iniciando desplazamientos hacia la derecha para hacer espacio en el índice " + pos + "...");
            for (int j = tamanoAnterior; j > pos; j--) {
                System.out.println(Color.AMARILLO + "  [Desplazamiento] " + Color.RESET +
                        "Variable Auxiliar " + Color.CIAN + "j = " + j + Color.RESET +
                        " -> Copiando datos[" + (j - 1) + "] (" + array.getDato(j - 1) + ") a datos[" + j + "]");
            }
        } else {
            Color.info("La inserción es al final del array. No se requieren desplazamientos.");
        }

        // Realizar inserción efectiva
        array.insertar(pos, elemento);

        System.out.println(Color.VERDE + "  [Asignación] " + Color.RESET +
                "datos[" + pos + "] = " + elemento + " | Nuevo tamaño = " + array.getTamanio());
        System.out.println();

        // ETAPA 4: ESTADO FINAL
        mostrarEtapaFinal();
        Color.exito("Elemento " + elemento + " insertado correctamente en la posición " + pos + ".");
    }

    /**
     * Elimina el elemento de una posición indicada con desplazamiento a la izquierda (Operación 2.2 #4).
     */
    private void ejecutarEliminacion(Scanner scanner) {
        Color.titulo("\n========== OPERACIÓN: ELIMINAR DE POSICIÓN ==========");

        // ETAPA 1: ESTADO INICIAL
        mostrarEtapaInicial();

        // Comprobación de Array Vacío
        if (array.estaVacio()) {
            Color.error("No se puede eliminar. El array está VACÍO (Subdesbordamiento).");
            mostrarEtapaFinal();
            return;
        }

        int pos = leerEntero(scanner, "Ingrese la posición a eliminar (0 a " + (array.getTamanio() - 1) + "): ");

        // Comprobación de posición inválida
        if (pos < 0 || pos >= array.getTamanio()) {
            Color.error("Posición inválida. El índice " + pos + " está fuera de los elementos ocupados.");
            Color.info("Rango válido para eliminación: [0, " + (array.getTamanio() - 1) + "].");
            mostrarEtapaFinal();
            return;
        }

        System.out.println();
        // ETAPA 2 & 3: PASO A PASO Y VARIABLES AUXILIARES
        mostrarEncabezadoEjecucion();

        int valorAEliminar = array.getDato(pos);
        System.out.println(Color.CIAN + "Variables Auxiliares Iniciales: " + Color.RESET +
                "posicion = " + pos + ", elementoEliminado = " + valorAEliminar + ", tamano_inicial = " + array.getTamanio());

        int tamanoAnterior = array.getTamanio();

        if (pos < tamanoAnterior - 1) {
            Color.info("Iniciando desplazamientos hacia la izquierda para rellenar la posición " + pos + "...");
            for (int j = pos; j < tamanoAnterior - 1; j++) {
                System.out.println(Color.AMARILLO + "  [Desplazamiento] " + Color.RESET +
                        "Variable Auxiliar " + Color.CIAN + "j = " + j + Color.RESET +
                        " -> Moviendo datos[" + (j + 1) + "] (" + array.getDato(j + 1) + ") a datos[" + j + "]");
            }
        } else {
            Color.info("Eliminación del último elemento. No se requieren desplazamientos de subsecuentes.");
        }

        // Ejecutar eliminación efectiva
        int eliminado = array.eliminar(pos);

        System.out.println(Color.VERDE + "  [Actualización] " + Color.RESET +
                "Posición " + pos + " eliminada. Nuevo tamaño = " + array.getTamanio());
        System.out.println();

        // ETAPA 4: ESTADO FINAL
        mostrarEtapaFinal();
        Color.exito("Se eliminó exitosamente el elemento " + eliminado + " de la posición " + pos + ".");
    }

    /**
     * Busca un valor e informa si existe y las posiciones donde se encuentra (Operación 2.2 #5).
     */
    private void ejecutarBusqueda(Scanner scanner) {
        Color.titulo("\n========== OPERACIÓN: BUSCAR UN VALOR ==========");

        // ETAPA 1: ESTADO INICIAL
        mostrarEtapaInicial();

        if (array.estaVacio()) {
            Color.advertencia("El array está vacío. El valor buscado no existe.");
            mostrarEtapaFinal();
            return;
        }

        int valorBuscado = leerEntero(scanner, "Ingrese el valor a buscar: ");

        System.out.println();
        // ETAPA 2 & 3: PASO A PASO Y VARIABLES AUXILIARES
        mostrarEncabezadoEjecucion();

        int coincidencias = 0;

        System.out.println(Color.CIAN + "Variables Auxiliares Iniciales: " + Color.RESET +
                "valorBuscado = " + valorBuscado + ", coincidencias = 0, posicionesEncontradas = \"\"");
        System.out.println();

        for (int i = 0; i < array.getTamanio(); i++) {
            int actual = array.getDato(i);
            boolean esIgual = (actual == valorBuscado);

            if (esIgual) {
                coincidencias++;
            }

            System.out.println(Color.BLANCO + "  Paso " + (i + 1) + ":" + Color.RESET +
                    " Variable Auxiliar " + Color.AMARILLO + "i = " + i + Color.RESET +
                    " | Comparando datos[" + i + "] (" + actual + ") == " + valorBuscado + " -> " +
                    (esIgual ? Color.VERDE + Color.NEGRITA + "¡COINCIDENCIA!" + Color.RESET : Color.GRIS + "No coincide" + Color.RESET) +
                    " | " + Color.CIAN + "coincidencias = " + coincidencias + Color.RESET);
        }

        System.out.println();

        // Resultado de la búsqueda
        int[] posiciones = array.buscar(valorBuscado);

        // ETAPA 4: ESTADO FINAL
        mostrarEtapaFinal();

        if (posiciones.length > 0) {
            Color.exito("El valor " + valorBuscado + " FUE ENCONTRADO.");
            Color.info("Total de coincidencias: " + posiciones.length);
            Color.info("Posición(es) en el array: [" + formatearPosiciones(posiciones) + "]");
        } else {
            Color.advertencia("El valor " + valorBuscado + " NO se encuentra en el array.");
        }
    }

    /**
     * Da formato a una lista de posiciones separadas por coma (por ejemplo: {@code 0, 3, 5}).
     *
     * @param posiciones Posiciones a mostrar.
     * @return Texto con las posiciones separadas por coma.
     */
    private String formatearPosiciones(int[] posiciones) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < posiciones.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(posiciones[i]);
        }
        return sb.toString();
    }

    /**
     * Verifica los estados del array (Vacio / Lleno / Capacidad).
     */
    private void ejecutarVerificarEstado() {
        Color.titulo("\n========== VERIFICAR ESTADOS DEL ARRAY ==========");

        // ETAPA 1: ESTADO INICIAL
        mostrarEtapaInicial();

        // ETAPA 2 & 3: PASO A PASO Y VARIABLES AUXILIARES
        mostrarEncabezadoEjecucion("EVALUACIÓN DE CONDICIONES Y VARIABLES AUXILIARES");

        boolean vacio = array.estaVacio();
        boolean lleno = array.estaLleno();
        int tam = array.getTamanio();
        int cap = array.getCapacidad();

        System.out.println(Color.CIAN + "  Variable Auxiliar tamano: " + Color.RESET + tam);
        System.out.println(Color.CIAN + "  Variable Auxiliar capacidad: " + Color.RESET + cap);
        System.out.println();

        System.out.println(Color.BLANCO + "  1. Evaluando condición Array Vacío (tamano == 0): " + Color.RESET +
                tam + " == 0 -> " + (vacio ? Color.VERDE + "TRUE" : Color.ROJO + "FALSE") + Color.RESET);

        System.out.println(Color.BLANCO + "  2. Evaluando condición Array Lleno (tamano == capacidad): " + Color.RESET +
                tam + " == " + cap + " -> " + (lleno ? Color.VERDE + "TRUE" : Color.ROJO + "FALSE") + Color.RESET);

        System.out.println();

        // ETAPA 4: ESTADO FINAL
        mostrarEtapaFinal();

        if (vacio) {
            Color.advertencia("Resultado: El array está actualmente VACÍO.");
        } else if (lleno) {
            Color.advertencia("Resultado: El array está actualmente LLENO.");
        } else {
            Color.exito("Resultado: El array contiene " + tam + " elemento(s) y aún tiene espacio disponible.");
        }
    }

    /**
     * Ejecuta el menú de interacción del array unidimensional.
     *
     * @param scanner Objeto {@code Scanner} compartido para lectura por consola.
     */
    public void ejecutarMenu(Scanner scanner) {
        if (array == null) {
            inicializarArray(scanner);
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Ingrese una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        inicializarArray(scanner);
                        break;
                    case 2:
                        ejecutarRecorrido();
                        break;
                    case 3:
                        ejecutarInsercion(scanner);
                        break;
                    case 4:
                        ejecutarEliminacion(scanner);
                        break;
                    case 5:
                        ejecutarBusqueda(scanner);
                        break;
                    case 6:
                        ejecutarVerificarEstado();
                        break;
                    case 0:
                        System.out.println();
                        Color.info("Regresando al menú principal...");
                        break;
                    default:
                        Color.advertencia("Opción inválida. Seleccione una opción del menú.");
                        break;
                }
            } catch (Exception e) {
                Color.error("Error durante la ejecución: " + e.getMessage());
            }

        } while (opcion != 0);
    }
}