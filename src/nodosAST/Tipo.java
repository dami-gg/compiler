/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nodosAST;

/**
 *
 * @author Dami
 */
public abstract class Tipo extends AST {

    // Fase de generación de código

    public int size() {

        return tam;
    }

    public String sufijo() {
        
        System.out.println ("\nError en la fase de generación de código: el tipo " + this + " no posee un sufijo propio");
        
        return "";
    }

    protected int tam; // Fase de selección de memoria
    
    /* Métodos de la fase de inferencia de tipos */

    public Tipo Suma (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador + a un " + this);
    }

    public Tipo Resta (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador - a un " + this);
    }

    public Tipo Producto (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador * a un " + this);
    }

    public Tipo Cociente (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador / a un " + this);
    }

    public Tipo Modulo (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador % a un " + this);
    }
    
    public Tipo Menor (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador < a un " + this);
    }

    public Tipo MenorOIgual (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador <= a un " + this);
    }

    public Tipo Mayor (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador > a un " + this);
    }

    public Tipo MayorOIgual (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador >= a un " + this);
    }
    
    public Tipo IgualIgual (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador == a un " + this);
    }

    public Tipo Distinto (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador != a un " + this);
    }

    public Tipo Or (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador || a un " + this);
    }

    public Tipo And (Tipo t) {

        return new TipoError ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador && a un " + this);
    }

    /**
     * Compara dos tipos y retorna el mayor de ambos (para promociones de tipos)
     * @param t
     * @return
     */

    public Tipo mayorTipo (Tipo t) {

        // Si no es int, real o char no se pueden comparar

        if (! (t instanceof TipoInt || t instanceof TipoChar || t instanceof TipoReal)) 

            return new TipoError ("\nError en la fase de inferencia de tipos: " + this + " y " + t + " no son comparables");

        // Tipos de mayor a menor: Real > Int > Char (byte)

        if (this instanceof TipoReal || t instanceof TipoReal) return new TipoReal();

        if (this instanceof TipoInt || t instanceof TipoInt) return new TipoInt();

        return new TipoChar();
    }

    /**
     * Comprueba si dos tipos son iguales
     * @param t
     * @return
     */

    public boolean mismoTipo (Tipo t) {

        if (this instanceof TipoInt && t instanceof TipoInt) return true;

        else if (this instanceof TipoReal && t instanceof TipoReal) return true;

        else if (this instanceof TipoChar && t instanceof TipoChar) return true;

        else if (this instanceof TipoArray && t instanceof TipoArray) return true;

        else if (this instanceof TipoRegistro && t instanceof TipoRegistro) return true;

        else return false;
    }

    public boolean EsPromocionableA (Tipo t) {

        return false;
    }

    /* Métodos de la Fase de selección de memoria */

    public int getTam() {

        return tam;
    }

    public void setTam (int t) {

        tam = t;
    }


}
