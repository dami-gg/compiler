/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Distinto extends Expr {

    public Expr izq;

    public Expr der;

    public Distinto (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Distinto (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Distinto: " + izq + "!=" + der;
    }

}