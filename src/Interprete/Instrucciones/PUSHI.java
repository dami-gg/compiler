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
public class PUSHI extends Instruccion {

    public int valor;

    public PUSHI (int v) {
        
        valor = v;
    }

    public void exec (Contexto c) {

        c.mem.pushInt(valor);
    }

    public String getID() {

        return "PUSHI " + valor + "\n";
    }
}
