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
public class OUTB extends Instruccion {

    public void exec (Contexto c) {

        char valor = ((char) c.mem.popByte());

        if (valor == '\n')  System.out.print ("\n");

        else System.out.print (valor + " ");
    }

    public String getID() {

        return "OUTB\n";
    }
}
