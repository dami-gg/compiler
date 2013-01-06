/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interprete.Instrucciones;

import Interprete.Contexto;
import Interprete.Instruccion;

/**
 *
 * @author Dami
 */
public class DIVF extends Instruccion {

    public void exec (Contexto c) {

        // Lo primero que se saca es el divisor

        float divisor = c.mem.popFloat();

        if (divisor == 0)

            throw new RuntimeException ("División por 0");

        else {

            float dividendo = c.mem.popFloat();

            float cociente = dividendo / divisor;

            c.mem.pushFloat(cociente);
        }
    }

    public String getID() {

        return "DIVF\n";
    }
}
