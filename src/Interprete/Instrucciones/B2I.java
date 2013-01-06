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
public class B2I extends Instruccion {

    public void exec (Contexto c) {

        c.mem.pushInt(c.mem.popByte());
    }

    public String getID() {

        return "B2I\n";
    }
}
