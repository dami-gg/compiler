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
public class PUSHA extends Instruccion {

    private String dir;
    
    public PUSHA (String d) {
        
        dir = d;
    }

    public void exec (Contexto c) {

        // Puede ser una dirección expresada por un número, o el registro BP

        try {

            int num = Integer.parseInt(dir);

            c.mem.pushInt (num);
        }

        catch (NumberFormatException e) {

            // Si llega aquí entonces se trataba del registro BP y lo apilo

            c.mem.pushInt (c.BP);
        }
    }

    public String getID() {

        return "PUSHA " + dir + "\n";
    }
}
