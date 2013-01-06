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
public class While extends Sentencia {

    public Expr condiciones;

    public List cuerpo;

    public DefFuncion definicion; // Fase de generación de código

    public While (Expr c, List cu) {

        condiciones = c;

        cuerpo = cu;
    }

    public While (Object c, Object cu) {

        condiciones = (Expr) c;

        cuerpo = (List) cu;
    }

    public String toString () {

        return "While " + condiciones + cuerpo;
    }


}
