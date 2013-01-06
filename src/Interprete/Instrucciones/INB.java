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
public class INB extends Instruccion {

    public void exec (Contexto c) {

        byte numero = 0;

        // Leer entero desde teclado

        try {

            System.out.print ("\nIntroduce un valor de tipo byte: ");

            BufferedReader in = new BufferedReader (new InputStreamReader(System.in));

            String aux = in.readLine();

            if (aux.compareTo("") != 0)

                numero = (byte) aux.charAt(0);

            else

                numero = 10;

            System.out.print ("\n");
		}
        
        catch (Exception e) {

            throw new RuntimeException ("El valor introducido no es de tipo caracter");
		}

        c.mem.pushByte (numero);
    }

    public String getID() {

        return "INB\n";
    }
}
