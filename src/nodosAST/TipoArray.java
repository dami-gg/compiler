/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class TipoArray extends Tipo {

    // Fase de generación de código

    @Override
    public String sufijo() {

        return this.tipo.sufijo();
    }

    public Expr dimension;

    public Tipo tipo;

    public TipoArray (Expr d, Tipo t) {

        dimension = d;

        tipo = t;
    }

    public TipoArray (Object d, Object t) {

        dimension = (Expr) d;

        tipo = (Tipo) t;
    }

    @Override
    public String toString() {

        return ((Num) dimension).lexema + "*" + tipo.toString();
    }

    /* Métodos de la Fase de selección de memoria */

    @Override
    public int getTam() {

        int retorno = Integer.parseInt(((Num) this.dimension).lexema) * this.tipo.getTam();

        return retorno;
    }
}
