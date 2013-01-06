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
public class MODI extends Instruccion {

    public void exec (Contexto c) {

        // Lo primero que se saca es el divisor
        
        int divisor = c.mem.popInt();

        int dividendo = c.mem.popInt();
            
        int modulo = dividendo % divisor;

        c.mem.pushInt(modulo);
    }

    public String getID() {

        return "MODI\n";
    }
}
