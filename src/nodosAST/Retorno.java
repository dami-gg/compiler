/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Retorno extends Sentencia {

    public Expr valor;

    public Tipo tipo;

    public DefFuncion definicion; // Fase de generación de código

    public Retorno (Expr v) {

        valor = v;
    }

    public Retorno (Object v) {

        valor = (Expr) v;
    }

    public String toString () {

        return "Retorno " + valor;
    }

}
