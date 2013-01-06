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
public class STOREF extends Instruccion {

    /*
     * Situación de la pila antes de la llamada:
     * 
     *      ----------------
     *   -->
     *      ----------------
     *           valor
     *      ----------------
     *         dirección
     *      ----------------
     * 
     */

    public void exec (Contexto c) {

        float valor = c.mem.popFloat();

        int direccion = c.mem.popInt();

        c.mem.writeFloat(direccion, valor);
    }

    public String getID() {

        return "STOREF\n";
    }
}
