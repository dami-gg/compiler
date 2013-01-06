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
public class PUSHBP extends Instruccion {

    public void exec (Contexto c) {

        c.mem.pushInt (c.BP);
    }

    public String getID() {

        return "PUSH BP\n";
    }
}
