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
public class MULI extends Instruccion {

    public void exec (Contexto c) {

        int mult = c.mem.popInt() * c.mem.popInt();

        c.mem.pushInt(mult);
    }

    public String getID() {

        return "MULI\n";
    }
}
