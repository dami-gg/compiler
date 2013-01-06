/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Not extends Expr {

    public Expr valor;

    public Not (Expr v) {

        valor = v;
    }

    public Not (Object v) {

        valor = (Expr) v;
    }

    public String toString () {

        return "Not: !" + valor;
    }

}
