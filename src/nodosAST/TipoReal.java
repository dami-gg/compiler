/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class TipoReal extends TipoPrimitivo {

    // Fase de generación de código

    @Override
    public String sufijo() {

        return "F";
    }

    @Override
    public Tipo Suma (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador +");
        }

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Resta (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador -");
        }

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Producto (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\n\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador *");
        }

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Cociente (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador /");
        }

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Modulo (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar))

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador %");

        return new TipoInt();
    }

    @Override
    public Tipo Menor (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador <");
        }

        return new TipoInt();
    }

    @Override
    public Tipo Mayor (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador >");
        }

        return new TipoInt();
    }

    @Override
    public Tipo MenorOIgual (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador <=");
        }

        return new TipoInt();
    }

    @Override
    public Tipo MayorOIgual (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador >=");
        }

        return new TipoInt();
    }

    @Override
    public Tipo IgualIgual (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador ==");
        }

        return new TipoInt();
    }

    @Override
    public Tipo Distinto (Tipo t) {

        
        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) {

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador !=");
        }

        return new TipoInt();
    }

    @Override
    public boolean EsPromocionableA (Tipo t) {

        if (t instanceof TipoReal)

            return true;

        return false;
    }

    /* Métodos de la Fase de selección de memoria */

    @Override
    public int getTam() {

        return 4;
    }

    @Override
    public String toString() {

        return "real";
    }
}
