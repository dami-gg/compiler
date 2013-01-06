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
public class DUPI extends Instruccion {

    public void exec (Contexto c) {

        int valor = c.mem.popInt();

        c.mem.pushInt(valor);
        c.mem.pushInt(valor);
    }

    public String getID() {

        return "DUPI\n";
    }
}
