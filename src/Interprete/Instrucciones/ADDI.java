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
public class ADDI extends Instruccion {

    public void exec (Contexto c) {

        int suma = c.mem.popInt() + c.mem.popInt();

        c.mem.pushInt(suma);
    }

    public String getID() {

        return "ADDI\n";
    }
}
