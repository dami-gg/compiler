/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Or extends Expr {

    public Expr izq;

    public Expr der;

    public Or (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Or (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Or: " + izq + "||" + der;
    }

}