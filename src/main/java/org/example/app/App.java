package org.example.app;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GestorEstructuras gestor = new GestorEstructuras(scanner);
            gestor.ejecutar();
        }
    }
}