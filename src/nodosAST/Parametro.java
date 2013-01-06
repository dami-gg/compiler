/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Parametro extends DefVar {

    // public String nombre;

    // public Tipo tipo;

    public Parametro (String n, Tipo t) {

        super (n, t);
    }

    public Parametro (Object n, Object t) {

        super (n, t);
    }

    public String toString () {

        return "Parámetro " + nombre + tipo;
    }

}
