/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class AccesoArray extends Expr {

    public Expr izq;

    public Expr der;

    public AccesoArray (Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public AccesoArray (Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }

    public String toString () {

        return "AccesoArray: " + izq +"-"+ der;
    }

}
