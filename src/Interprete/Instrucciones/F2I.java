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
public class F2I extends Instruccion {

    public void exec (Contexto c) {

        c.mem.pushInt((int) c.mem.popFloat());
    }

    public String getID() {

        return "F2I\n";
    }
}
