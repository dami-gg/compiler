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
public class JMP extends Instruccion {

    protected String etiq;
    
    public JMP (String e) {
        
        etiq = e;
    }

    public void exec (Contexto c) {

        // Saltar a la dirección de la etiqueta

        int ip = c.ObtenerDirEtiqueta(etiq);

        c.IP = ip - 1;
    }

    public String getID() {

        return "JMP " + etiq + "\n";
    }
}
