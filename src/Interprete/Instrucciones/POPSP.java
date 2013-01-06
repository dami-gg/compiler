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
public class POPSP extends Instruccion {

    public void exec (Contexto c) {

        c.mem.setSP(c.mem.popInt());
    }

    public String getID() {

        return "POP SP\n";
    }
}
