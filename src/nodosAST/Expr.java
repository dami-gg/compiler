/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public abstract class Expr extends AST {

    protected Tipo tipo;

    private boolean lvalue = false; // Por defecto no son modificables
    
    public Tipo getTipo() {

        return tipo;
    }

    public void setTipo (Tipo t) {

        tipo = t;
    }

    public boolean getLvalue() {

        return lvalue;
    }

    public void setLvalue(boolean l) {

        lvalue = l;
    }

}
