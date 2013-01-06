/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interprete.Instrucciones;

import Interprete.Contexto;
import Interprete.Instruccion;
import nodosAST.*;

/**
 *
 * @author Dami
 */
public class VariableGlobal extends Instruccion {

    private DefVar def;
    
    public VariableGlobal (DefVar d) {
        
        def = d;
    }
    
    public void exec (Contexto c) {

        // Las variables globales empezarán a almacenarse en la posición 0

        if (def.tipo instanceof TipoChar)

            ReservarEspacioChar (c);

        else if (def.tipo instanceof TipoInt)

            ReservarEspacioInt (c);

        else if (def.tipo instanceof TipoReal)

            ReservarEspacioReal (c);

        else if (def.tipo instanceof TipoArray)

            ReservarEspacioArray (c, def.tipo);

        else if (def.tipo instanceof TipoRegistro) 

            ReservarEspacioRegistro (c, def.tipo);

        // Almaceno el identificador y la dirección base

        c.AnadirEtiqueta (def.nombre, def.getDireccion());
    }

    private void ReservarEspacioChar (Contexto c) {

        c.mem.writeByte(c.VG, 0);

        // Actualizo el índice de variables globales

        c.VG++;
    }

    private void ReservarEspacioInt (Contexto c) {

        c.mem.writeInt(c.VG, 0);

        // Actualizo el índice de variables globales

        c.VG += 2;
    }

    private void ReservarEspacioReal (Contexto c) {

        c.mem.writeFloat(c.VG, 0);

        // Actualizo el índice de variables globales

        c.VG += 4;
    }

    private void ReservarEspacioArray (Contexto c, Tipo t) {

        // Todos los elementos son del mismo tipo

        int elementos;

        // Ocupará en memoria lo que ocupe su tipo multiplicado por el número de elementos contenidos en el array

        // Primero averiguo el tipo

        Tipo aux = t;

        while (aux instanceof TipoArray) {

            aux = ((TipoArray) aux).tipo;
        }

        if (aux instanceof TipoChar) {

            elementos = t.getTam();

            for (int i = 0; i < elementos; i++)

                c.mem.writeByte (c.VG + i, 0);

            c.VG += t.getTam();
        }

        else if (aux instanceof TipoInt) {

            elementos = t.getTam() / 2;

            for (int i = 0; i < elementos; i++)

                c.mem.writeInt (c.VG + i, 0);

            c.VG += t.getTam();
        }

        else if (aux instanceof TipoReal) {

            elementos = t.getTam() / 4;

            for (int i = 0; i < elementos; i++)

                c.mem.writeFloat (c.VG + i, 0);

            c.VG += t.getTam();
        }
    }

    private void ReservarEspacioRegistro (Contexto c, Tipo t) {

        // Cada elemento puede ser de un tipo distinto

        Tipo aux;

        for (int i = 0; i < ((TipoRegistro) t).atribs.size(); i++) {

            aux = ((Atributo) ((TipoRegistro) t).atribs.get(i)).tipo;

            if (aux instanceof TipoChar)

                ReservarEspacioChar (c);

            else if (aux instanceof TipoInt)

                ReservarEspacioInt (c);

            else if (aux instanceof TipoReal)

                ReservarEspacioReal (c);

            else if (aux instanceof TipoArray)

                ReservarEspacioArray (c, aux);
        }
    }

    public String getID() {

        return "";
    }
}
