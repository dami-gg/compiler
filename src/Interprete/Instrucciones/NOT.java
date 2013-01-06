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
public class NOT extends Instruccion {

    public void exec (Contexto c) {

        int valor = c.mem.popInt();

        if (valor == 0)

            c.mem.pushInt(1);

        else if (valor == 1)

            c.mem.pushInt(0);

        else

            throw new RuntimeException ("El valor a negar no es válido");
    }

    public String getID() {

        return "NOT\n";
    }
}
