/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Producto extends Expr {

    public Expr izq;

    public Expr der;

    public Producto (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Producto (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Producto: " + izq + "*" + der;
    }

}