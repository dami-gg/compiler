/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class AccesoAtrib extends Expr {

    public Expr izq;

    public String der;

    public Atributo atrib;

    public AccesoAtrib (Expr i, String d) {

        izq = i;

        der = d;
    }

    public AccesoAtrib (Object i, Object d) {

        izq = (Expr) i;

        der = (String) d;
    }

    public String toString () {

        return "AccesoAtrib: " + izq +"-"+ der;
    }

}
