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
public class DIVI extends Instruccion {

    public void exec (Contexto c) {

        // Lo primero que se saca es el divisor
        
        int divisor = c.mem.popInt();

        if (divisor == 0)
            
            throw new RuntimeException ("División por 0");
        
        else {
            
            int dividendo = c.mem.popInt();
            
            int cociente = dividendo / divisor;

            c.mem.pushInt(cociente);
        }
    }

    public String getID() {

        return "DIVI\n";
    }
}
