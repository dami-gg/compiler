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
public class RET extends Instruccion {

    protected int c1, c2, c3;
    
    public RET (int a, int b, int c) {
        
        c1 = a;
        c2 = b;
        c3 = c;
    }

    public void exec (Contexto c) {

        // Retirar y guardar c1 bytes

        byte [] retorno = new byte [c1];

        for (int i = 0; i < c1; i++)

            retorno [i] = c.mem.popByte();

        // Desapilar las variables locales (c2)

        c.mem.setSP(c.mem.getSP() + c2);

        // Desapilar y restaurar el BP anterior

        c.BP = c.mem.popInt();

        // Desapilar y restaurar el IP anterior

        int ip = c.mem.popInt();

        c.IP = ip - 1; // Resto uno para ejecutarla porque si no la trataría como ejecutada

        // Desapilar los parámetros (c3)

        c.mem.setSP(c.mem.getSP() + c3);

        // Añadir a la cima los c1 bytes guardados en el mismo orden que estaban

        for (int i = c1 - 1; i >= 0; i--)

            c.mem.pushByte(retorno[i]);
    }

    public String getID() {

        return "RET " + c1 + ", " + c2 + ", " + c3 + "\n";
    }
}
