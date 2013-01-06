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
public class STOREI extends Instruccion {

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

        int valor = c.mem.popInt();

        int direccion = c.mem.popInt();

        c.mem.writeInt(direccion, valor);
    }

    public String getID() {

        return "STOREI\n";
    }
}
