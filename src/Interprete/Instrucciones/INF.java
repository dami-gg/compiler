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
public class INF extends Instruccion {

    public void exec (Contexto c) {

        float numero = 0;

        // Leer real desde teclado

        try {

            System.out.print ("\nIntroduce un valor de tipo real: ");

            BufferedReader in = new BufferedReader (new InputStreamReader(System.in));

            numero = Float.parseFloat (in.readLine());

            System.out.print ("\n");
		}
        
        catch (Exception e) {

            throw new RuntimeException ("El valor introducido no es de tipo real");
		}

        c.mem.pushFloat (numero);
    }

    public String getID() {

        return "INF\n";
    }
}
