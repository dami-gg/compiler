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
public class LOADI extends Instruccion {

    /*
     * Situación de la pila antes de la llamada:
     * 
     *      ----------------
     *   -->
     *      ----------------
     *          dirección
     *      ----------------
     *
     *  Situación de la pila después de la llamada:
     *
     *      ----------------
     *   -->
     *      ----------------
     *          valor dir
     *      ----------------
     *
     */

    public void exec (Contexto c) {

        int direccion = c.mem.popInt();

        int valor = c.mem.readInt(direccion);
        
        c.mem.pushInt(valor);
    }

    public String getID() {

        return "LOADI\n";
    }
}
