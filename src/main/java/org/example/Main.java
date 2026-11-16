package org.example;


import org.example.pilas.MenuPila;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        MenuPila menu = new MenuPila();
        menu.ejecutaMenu(sc);
    }
}
