/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Suma extends Expr {

    public Expr izq;

    public Expr der;

    public Suma (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Suma (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Suma: " + izq + "+" + der;
    }
}