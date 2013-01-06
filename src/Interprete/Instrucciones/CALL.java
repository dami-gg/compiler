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
public class CALL extends Instruccion {

    public String etiq;
    
    public CALL (String e) {
        
        etiq = e;
    }

    public void exec (Contexto c) {

        // Apilar la dirección de retorno (la de la siguiente instrucción al call)

        c.mem.pushInt(c.IP + 1);

        // Apilar BP

        c.mem.pushInt(c.BP);

        // Saltar al procedimiento que indica la etiqueta

        int ip = c.ObtenerDirEtiqueta (etiq);

        c.IP = ip - 1; // Resto uno para ejecutarla porque si no la trataría como ejecutada

        // Actualizar el valor de BP al tope de la pila (SP)

        c.BP = c.mem.getSP();
    }

    public String getID() {

        return "CALL " + etiq + "\n";
    }
}
