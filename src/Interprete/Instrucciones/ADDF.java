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
public class ADDF extends Instruccion {

    public void exec (Contexto c) {

        float suma = c.mem.popFloat() + c.mem.popFloat();

        c.mem.pushFloat(suma);
    }

    public String getID() {

        return "ADDF\n";
    }
}
