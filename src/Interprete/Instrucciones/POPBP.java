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
public class POPBP extends Instruccion {

    public void exec (Contexto c) {

        c.BP = c.mem.popInt();
    }

    public String getID() {

        return "POP BP\n";
    }
}
