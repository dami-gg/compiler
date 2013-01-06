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
public class DUPB extends Instruccion {

    public void exec (Contexto c) {

        byte valor = c.mem.popByte();

        c.mem.pushByte(valor);
        c.mem.pushByte(valor);
    }

    public String getID() {

        return "DUPB\n";
    }
}
