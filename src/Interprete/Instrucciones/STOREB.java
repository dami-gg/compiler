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
public class STOREB extends Instruccion {

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

        byte valor = c.mem.popByte();

        int direccion = c.mem.popInt();

        c.mem.writeByte(direccion, valor);
    }

    public String getID() {

        return "STOREB\n";
    }
}
