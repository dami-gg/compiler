/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class And extends Expr {

    public Expr izq;

    public Expr der;

    public And (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public And (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "And: " + izq + "&&" + der;
    }

}