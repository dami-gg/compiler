/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class DefVar extends Declaracion {

    public String nombre;

    public Tipo tipo;

    public boolean esGlobal;

    /* Atributos de la fase de selección de memoria */
    
    protected int direccion; // Se utiliza si es global

    protected int offset; // Se utiliza si es local

    public DefVar (String nom, Tipo t) {

        nombre = nom;

        tipo = t;
    }

    public DefVar (Object nom, Object t) {

        nombre = (String) nom;

        tipo = (Tipo) t;
    }

    public String toString() {

        return "Variable " + nombre + tipo;
    }

    /* Métodos de la Fase de selección de memoria */

    public int getDireccion() {

        return direccion;
    }

    public void setDireccion (int d) {

        direccion = d;
    }

    public int getOffset() {

        return offset;
    }

    public void setOffset (int o) {

        offset = o;
    }
}
