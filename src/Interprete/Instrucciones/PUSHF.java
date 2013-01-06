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
public class PUSHF extends Instruccion {

    public float valor;

    public PUSHF (float v) {
        
        valor = v;
    }

    public void exec (Contexto c) {

        c.mem.pushFloat(valor);
    }

    public String getID() {

        return "PUSHF " + valor + "\n";
    }
}
