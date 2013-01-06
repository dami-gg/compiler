/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class VarRef extends Expr {

    public String nombre;

    public DefVar definicion;

    public VarRef (String l) {

        nombre = l;
    }

    public VarRef (Object l) {

        nombre = (String) l;
    }

    public String toString () {

        return "VarRef: " + nombre;
    }

}
