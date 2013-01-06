/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Resta extends Expr {

    public Expr izq;

    public Expr der;

    public Resta (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Resta (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Resta: " + izq + "-" + der;
    }

}