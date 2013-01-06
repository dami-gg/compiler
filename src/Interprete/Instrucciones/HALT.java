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
public class HALT extends Instruccion {

    public void exec (Contexto c) {}

    public String getID() {

        return "HALT\n";
    }
}
