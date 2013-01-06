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
public class PUSHB extends Instruccion {

    public byte valor;

    public PUSHB (byte v) {
        
        valor = v;
    }

    public void exec (Contexto c) {

        c.mem.pushByte(valor);
    }

    public String getID() {

        return "PUSHB " + valor + "\n";
    }
}
