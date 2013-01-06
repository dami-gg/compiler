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
public class DefFuncion extends Declaracion {

    public String nombre;

    public List params;

    public Tipo retorno;

    public List vbles;

    public List cuerpo;

    /* Atributos de la fase de selección de memoria */

    public int tamParametros;

    public int tamVblesLocales;

    public int tamRetorno;

    public DefFuncion (String nom, List p, Tipo t, List v, List c) {

        nombre = nom;

        params = p;

        retorno = t;

        vbles = v;

        cuerpo = c;
    }

    public DefFuncion (Object nom, Object p, Object t, Object v, Object c) {

        nombre = (String) nom;

        params = (List) p;

        retorno = (Tipo) t;

        vbles = (List) v;

        cuerpo = (List) c;
    }

    public String toString() {

        return "Funcion " + retorno.toString() + " " + nombre + "-" + params.toString() +
            vbles.toString() + cuerpo.toString();
    }

}
