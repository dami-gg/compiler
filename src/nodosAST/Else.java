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
public class Else extends Sentencia {

    public List cuerpo;

    public DefFuncion definicion; // Fase de generación de código

    public Else (List c) {

        cuerpo = c;
    }

    public String toString () {

        return "Else " + cuerpo;
    }

}
