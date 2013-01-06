/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interprete.Instrucciones;

import Interprete.Contexto;
import Interprete.Instruccion;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Dami
 */
public class INI extends Instruccion {

    public void exec (Contexto c) {

        int numero = 0;

        // Leer entero desde teclado

        try {

            System.out.print ("\nIntroduce un valor de tipo entero: ");

            BufferedReader in = new BufferedReader (new InputStreamReader(System.in));

            numero = Integer.parseInt (in.readLine());

            System.out.print ("\n");
		}
        
        catch (Exception e) {

            throw new RuntimeException ("El valor introducido no es de tipo entero");
		}

        c.mem.pushInt (numero);
    }

    public String getID() {

        return "INI\n";
    }
}
