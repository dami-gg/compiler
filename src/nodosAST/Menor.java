/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Menor extends Expr {

    public Expr izq;

    public Expr der;

    public Menor (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Menor (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Menor: " + izq + "<" + der;
    }

}