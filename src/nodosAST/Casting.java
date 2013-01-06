/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Casting extends Expr {

    // public Tipo tipoConversion;

    public Expr valor;

    public Casting (Tipo t, Expr v) {

        tipo = t;

        valor = v;
    }

    public Casting (Object t, Object v) {

        tipo = (Tipo) t;

        valor = (Expr) v;
    }

    public String toString() {

        return "Casting: " + tipo + "-" + valor;
    }

    

}
