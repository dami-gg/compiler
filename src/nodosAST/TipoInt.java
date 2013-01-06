/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public class TipoInt extends TipoPrimitivo {

    // Fase de generación de código

    @Override
    public String sufijo() {
        
        return "I";
    }

    @Override
    public Tipo Suma (Tipo t) {
        
        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 
            
            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador +");
        
        return this.mayorTipo(t);
    }

    @Override
    public Tipo Resta (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal))

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador -");

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Producto (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador *");

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Cociente (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador /");

        return this.mayorTipo(t);
    }

    @Override
    public Tipo Modulo (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar)) 

            return new TipoError ("Error en la fase de inferencia de tipos: al tipo " + t + " de la expresión derecha no se le puede aplicar el operador %");

        return new TipoInt();
    }

    @Override
    public Tipo Menor (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador <");

        return new TipoInt();
    }

    @Override
    public Tipo Mayor (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador >");

        return new TipoInt();
    }

    @Override
    public Tipo MenorOIgual (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador <=");

        return new TipoInt();
    }

    @Override
    public Tipo MayorOIgual (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador >=");

        return new TipoInt();
    }

    @Override
    public Tipo IgualIgual (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador ==");

        return new TipoInt();
    }

    @Override
    public Tipo Distinto (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables con el operador !=");

        return new TipoInt();
    }

    @Override
    public Tipo Or (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador || sobre los tipos " + this + " y " + t);

        return new TipoInt();
    }

    @Override
    public Tipo And (Tipo t) {

        if (! (t instanceof TipoInt || t instanceof TipoChar))

            return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador && sobre los tipos " + this + " y " + t);

        return new TipoInt();
    }

    public boolean EsPromocionableA (Tipo t) {

        if (t instanceof TipoReal || t instanceof TipoChar || t instanceof TipoInt)

            return true;

        return false;
    }

    @Override
    public int getTam() {

        return 2;
    }
    
    @Override
    public String toString() {

        return "int";
    }    
}
