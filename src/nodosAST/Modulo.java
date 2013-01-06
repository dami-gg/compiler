/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Modulo extends Expr {

    public Expr izq;

    public Expr der;

    public Modulo (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Modulo (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Modulo: " + izq + "%" + der;
    }

}