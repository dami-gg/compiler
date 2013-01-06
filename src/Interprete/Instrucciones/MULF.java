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
public class MULF extends Instruccion {

    public void exec (Contexto c) {

        float mult = c.mem.popFloat() * c.mem.popFloat();

        c.mem.pushFloat(mult);
    }

    public String getID() {

        return "MULF\n";
    }
}
