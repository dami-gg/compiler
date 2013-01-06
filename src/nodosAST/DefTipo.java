/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class DefTipo extends Declaracion {

    public String nombre;

    public Tipo tipo;

    public boolean esGlobal;

    public DefTipo (String nom, Tipo t) {

        nombre = nom;

        tipo = t;
    }

    public DefTipo (Object nom, Object t) {

        nombre = (String) nom;

        tipo = (Tipo) t;
    }

    public String toString() {

        return "Tipo definido " + nombre + " " + tipo;
    }

}
