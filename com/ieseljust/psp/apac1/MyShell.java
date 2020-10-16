/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ieseljust.psp.apac1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @ivpepo
 */
public class MyShell {

    public static void main(String[] args) throws IOException {
        // para solicitar datos al usuario
        final Scanner keyboard = new Scanner(System.in);
        String comando = "";

        while (!comando.equalsIgnoreCase("QUIT")) {
            System.out.print("# MyShell:> ");
            comando = keyboard.nextLine();

            String colorVerde = "\u001B[32m";
            String colorStandart = "\u001B[0m";
            String colorRojo = "\u001B[31m";

            try {
                // creamos obj ProcessBuilder
                ProcessBuilder pbruta = new ProcessBuilder("bash", "-c", comando);
                // lanzamos procesos
                Process p = pbruta.start();
                // Leemos la salida del procesos con getInputStream
                // bloqueamos por pantalla
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                p.getInputStream()));
                String line;
                //comprobación y mostrar por pantalla
                int comprueba = 0;
                while ((line = br.readLine()) != null) {
                    System.out.println(colorVerde + line + colorStandart);
                    comprueba++;
                }
                // como no se ha ejecutado el while no ha encontrado la orden
                if (!(comprueba >= 1) && (!comando.equalsIgnoreCase("QUIT"))) {
                    System.out.println(colorRojo + comando + " ordre: not found" + colorStandart);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

}
