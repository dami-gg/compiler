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
public class Etiqueta extends Instruccion {

    protected String texto;
    
    public Etiqueta (String t) {
        
        texto = t;
    }
    
    public void exec (Contexto c) {}

    public String verEtiqueta () {

        return texto;
    }

    public String getID() {

        return ("\n" + texto + ":\n");
    }
}
