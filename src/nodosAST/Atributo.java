/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Atributo extends AST {

    public String nombre;

    public Tipo tipo;

    /**
     * Almacena el desplazamiento del atributo dentro del espacio de memoria asignado al registro
     */

    protected int offset; // Fase de selección de memoria

    public Atributo (String n, Tipo t) {

        nombre = n;

        tipo = t;
    }

    public Atributo (Object n, Object t) {

        nombre = (String) n;

        tipo = (Tipo) t;
    }

    public String toString() {

        return "Atributo: " + nombre +"-"+ tipo;
    }

    /* Métodos de la Fase de selección de memoria */

    public int getOffset() {

        return offset;
    }

    public void setOffset (int o) {

        offset = o;
    }

}
