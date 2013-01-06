/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class Caracter extends Expr {

    public String lexema;

    /**
     * Obtiene el valor del caracter asociado al lexema
     * Fase de generación de código
     * @return
     */
    public String getCaracter () {

        // Compruebo si es una letra minúscula

        String abecedario = "abcdefghijklmnopqrstuvwxyz";

        if (abecedario.indexOf(lexema) != -1)

            return (String.valueOf(abecedario.indexOf(lexema) + 97));

        // Compruebo si es una letra mayúscula

        abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (abecedario.indexOf(lexema) != -1)

            return (String.valueOf(abecedario.indexOf(lexema) + 65));

        // Si no es una letra, será un número o un caracter especial

        if (lexema.equals("\n"))

            return String.valueOf(10);

        else if (lexema.equals ("\r"))

            return String.valueOf(13);

        else if (lexema.equals ("\t"))

            return String.valueOf(9);

        else {

            abecedario = "0123456789";

            return (String.valueOf(abecedario.indexOf(lexema) + 48));
        }
    }

    public Caracter (String l) {

        if (l.equals("'\\n'")) lexema = "\n";

        else if (l.equals("'\\r'")) lexema = "\r";

        else if (l.equals("'\\t'")) lexema = "\t";

        else

            // Quito las comillas

            lexema = l.replace("'", "");

        this.setTipo(new TipoChar());
    }

    public Caracter (Object l) {

        lexema = (String) l;

        if (lexema.equals("'\\n'")) lexema = "\n";

        else if (lexema.equals("'\\r'")) lexema = "\r";

        else if (lexema.equals("'\\t'")) lexema = "\t";

        else

            // Quito las comillas

            lexema = lexema.replace("'", "");

        this.setTipo(new TipoChar());
    }

    @Override
    public String toString() {

        return "Carácter: "+ lexema;
    }

}
