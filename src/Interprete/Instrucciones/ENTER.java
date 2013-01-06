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
public class ENTER extends Instruccion {

    protected int cte;
    
    public ENTER (int c) {
        
        cte = c;
    }

    public void exec (Contexto c) {

        c.mem.setSP(c.mem.getSP() - cte);
    }

    public String getID() {

        return "ENTER " + cte + "\n";
    }
}
