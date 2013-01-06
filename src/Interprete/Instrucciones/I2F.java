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
public class I2F extends Instruccion {

    public void exec (Contexto c) {

        c.mem.pushFloat(c.mem.popInt());
    }

    public String getID() {

        return "I2F\n";
    }
}
