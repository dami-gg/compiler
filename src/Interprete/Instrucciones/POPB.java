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
public class POPB extends Instruccion {

    public void exec (Contexto c) {

        c.mem.popByte();
    }

    public String getID() {

        return "POPB\n";
    }
}
