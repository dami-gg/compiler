/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class IgualIgual extends Expr {

    public Expr izq;

    public Expr der;

    public IgualIgual (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public IgualIgual (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString() {

        return "Igualdad: " + izq + "==" + der;
    }

}