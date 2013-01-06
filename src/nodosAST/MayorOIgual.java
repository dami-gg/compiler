/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class MayorOIgual extends Expr {

    public Expr izq;

    public Expr der;

    public MayorOIgual (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public MayorOIgual (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Mayor o igual: " + izq + ">=" + der;
    }

}