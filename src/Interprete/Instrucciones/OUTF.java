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
public class OUTF extends Instruccion {

    public void exec (Contexto c) {

        System.out.print (c.mem.popFloat() + " ");
    }

    public String getID() {

        return "OUTF\n";
    }
}
