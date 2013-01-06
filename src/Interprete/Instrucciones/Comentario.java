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
public class Comentario extends Instruccion {

    protected String texto;
    
    public Comentario (String t) {
        
        texto = t;
    }
    
    public void exec (Contexto c) {}

    public String getID() {

        return texto;
    }
}
