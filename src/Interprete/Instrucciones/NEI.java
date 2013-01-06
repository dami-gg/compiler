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
public class NEI extends Instruccion {

    public void exec (Contexto c) {

        int a = c.mem.popInt();

        int b = c.mem.popInt();

        if (a != b)

            c.mem.pushInt(1);

        else

            c.mem.pushInt(0);
    }

    public String getID() {

        return "NEQI\n";
    }
}
