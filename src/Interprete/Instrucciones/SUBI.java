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
public class SUBI extends Instruccion {

    public void exec (Contexto c) {

        // Lo primero que se saca es el sustraendo
        
        int num = c.mem.popInt();

        int resta = c.mem.popInt() - num;

        c.mem.pushInt(resta);
    }

    public String getID() {

        return "SUBI\n";
    }
}
