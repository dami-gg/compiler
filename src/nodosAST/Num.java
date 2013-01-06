/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Num extends Expr {

    public String lexema;

    public Num (String l) {

        lexema = l;

        if (! lexema.contains(".")) this.setTipo(new TipoInt());

        else this.setTipo(new TipoReal());
    }

    public Num (Object l) {

        lexema = (String) l;

        if (! lexema.contains(".")) this.setTipo(new TipoInt());

        else this.setTipo(new TipoReal());
    }
}
