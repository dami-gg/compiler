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
public class OR extends Instruccion {

    public void exec (Contexto c) {

        int a = c.mem.popInt();

        int b = c.mem.popInt();

        if (a <= 1 && a >= 0 && b <= 1 && b >= 0) {

            if (a == 1 || b == 1)

                c.mem.pushInt(1);

            else // Los dos valen 0

                c.mem.pushInt(0);
        }

        else

            throw new RuntimeException ("Los valores a evaluar no son válidos");
    }

    public String getID() {

        return "OR\n";
    }
}
