package org.example;

import org.example.GUI.MainGUI;

import java.util.Scanner;


public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainGUI mainGUI = new MainGUI(scanner);
        mainGUI.menu();



    }


}
