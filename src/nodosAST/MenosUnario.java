/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class MenosUnario extends Expr {

    public Expr valor;

    public MenosUnario (Expr v) {

        valor = v;
    }

    public MenosUnario (Object v) {

        valor = (Expr) v;
    }

    public String toString () {

        return "-" + valor;
    }

}
