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
public class SUBF extends Instruccion {

    public void exec (Contexto c) {

        // Lo primero que se saca es el sustraendo
        
        float num = c.mem.popFloat();

        float resta = c.mem.popFloat() - num;

        c.mem.pushFloat(resta);
    }

    public String getID() {

        return "SUBF\n";
    }
}
