/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Mayor extends Expr {

    public Expr izq;

    public Expr der;

    public Mayor (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Mayor (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Mayor: " + izq + ">" + der;
    }

}
