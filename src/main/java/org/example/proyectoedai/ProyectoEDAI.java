package org.example.proyectoedai;

import java.util.Scanner;

public class ProyectoEDAI {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GestorEstructuras gestor = new GestorEstructuras(scanner);
            gestor.ejecutar();
        }
    }
}