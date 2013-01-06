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
public class JNZ extends Instruccion {

    protected String etiq;

    public JNZ (String e) {

        etiq = e;
    }

    public void exec (Contexto c) {

        // Saltar a la dirección de la etiqueta si el valor de la cima de la pila es distinto de 0

        int valor = c.mem.popInt();

        if (valor != 0) {

            int ip = c.ObtenerDirEtiqueta(etiq);

            c.IP = ip - 1;
        }
    }

    public String getID() {

        return "JNZ " + etiq + "\n";
    }
}
