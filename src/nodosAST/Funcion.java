/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

import java.util.*;

/**
 *
 * @author Dami
 */
public class Funcion extends Expr {

    public String nombre;

    public List params;

    public DefFuncion definicion;

    public Funcion (String n, List p) {

        nombre = n;

        params = p;
    }

    public Funcion (Object n, Object p) {

        nombre = (String) n;

        params = (List) p;
    }

    public String toString () {

        return "Función " + nombre + params;
    }

}
