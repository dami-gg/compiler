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
public class EQF extends Instruccion {

    public void exec (Contexto c) {

        float a = c.mem.popFloat();

        float b = c.mem.popFloat();

        if (a == b)

            c.mem.pushInt(1); // Las comparaciones producen un valor entero independientemente de los tipos comparados

        else

            c.mem.pushInt(0); // Las comparaciones producen un valor entero independientemente de los tipos comparados
    }

    public String getID() {

        return "EQF\n";
    }
}
