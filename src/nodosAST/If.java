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
public class If extends Sentencia {

    public Expr condiciones;

    public List cuerpo;

    public Else sino;

    public DefFuncion definicion; // Fase de generación de código

    public If (Expr c, List cu, Else s) {

        condiciones = c;

        cuerpo = cu;

        sino = s;
    }

    public If (Object c, Object cu, Object s) {

        condiciones = (Expr) c;

        cuerpo = (List) cu;

        sino = (Else) s;
    }

    public String toString () {

        return "If " + condiciones + cuerpo + sino;
    }


}
