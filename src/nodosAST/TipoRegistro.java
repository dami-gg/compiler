/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

import java.util.*;

/**
 *
 * @author Dami
 */
public class TipoRegistro extends Tipo {

    public List atribs;

    public TipoRegistro (List a) {

        atribs = a;
    }

    public TipoRegistro (Object a) {

        atribs = (List) a;
    }

    public String toString() {
        
        String retorno = "{ ";

        for (int i = 0; i < this.atribs.size(); i++) {

            retorno += "\n\t" + ((Atributo) atribs.get(i)).nombre + ": " + ((Atributo) atribs.get(i)).tipo.toString();
        }

        retorno += "\n}";

        return retorno;
    }

    /* Métodos de la Fase de selección de memoria */

    @Override
    public int getTam() {

        int total = 0;

        for (int i = 0; i < this.atribs.size(); i++) {

            total += ((Atributo) this.atribs.get(i)).tipo.getTam();
        }

        return total;
    }
}
