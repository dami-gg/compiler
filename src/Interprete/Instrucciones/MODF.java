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
public class MODF extends Instruccion {

    public void exec (Contexto c) {

        // Lo primero que se saca es el divisor

        float divisor = c.mem.popFloat();

        float dividendo = c.mem.popFloat();

        float modulo = dividendo % divisor;

        c.mem.pushFloat(modulo);
    }

    public String getID() {

        return "MODF\n";
    }
}
