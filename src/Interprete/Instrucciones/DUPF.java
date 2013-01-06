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
public class DUPF extends Instruccion {

    public void exec (Contexto c) {

        float valor = c.mem.popFloat();

        c.mem.pushFloat(valor);
        c.mem.pushFloat(valor);
    }

    public String getID() {

        return "DUPF\n";
    }
}
