/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class TipoError extends Tipo {

    private String mensaje;

    public TipoError (String m) {

        mensaje = m;
    }

    public String mostrarMensaje () {

        return mensaje;
    }

    @Override
    public String toString() {

        return "'null'";
    }
}
