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
public class POPF extends Instruccion {

    public void exec (Contexto c) {

        c.mem.popFloat();
    }

    public String getID() {

        return "POPF\n";
    }
}
