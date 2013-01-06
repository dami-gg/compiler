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
public class CompruebaRangoArray extends Instruccion {

    public int tam;

    public CompruebaRangoArray (int t) {
        
        tam = t;
    }

    public void exec (Contexto c) {

        int pos = c.mem.popInt();

        c.mem.pushInt (pos);

        if (pos >= tam)

            throw new ArrayIndexOutOfBoundsException("acceso a una posición fuera del rango del array");
    }

    public String getID() {

        return "";
    }
}
