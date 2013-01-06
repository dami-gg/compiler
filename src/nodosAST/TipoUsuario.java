/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class TipoUsuario extends Tipo {

    public String nombre;

    public DefTipo declaracion;

    public TipoUsuario (String n) {

        nombre = n;
    }

    public TipoUsuario (Object n) {

        nombre = (String) n;
    }

    @Override
    public String toString() {

        return "'"+ nombre + "'";
    }

    @Override
    public int getTam() {

        return declaracion.tipo.getTam();
    }

}
