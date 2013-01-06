/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Cociente extends Expr {

    public Expr izq;

    public Expr der;

    public Cociente (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Cociente (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString () {

        return "Cociente: " + izq + "/" + der;
    }

}