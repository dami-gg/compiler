package GeneracionCodigo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import nodosAST.*;
import Introspeccion.*;

/**
 *
 * @author Dami
 */
public class FaseSeleccionMemoria extends DefaultVisitor {

    private int direccionEstatica = 0; // Para las variables globales
    
    private int direccionRelativa = 0; // Para las variables locales
    
    private int direccionParametros = 0; // Para los parámetros de las funciones

    public FaseSeleccionMemoria() {
        
        super(AST.class);   // Recorrido implícito
    }

    // No hace falta un visit(Programa) ya que hay recorrido
    // implícito (si no se pone se recorren sus hijos)
    
    public void visit(AccesoArray array) {

        visit(array.izq);
        visit(array.der);

        // Hay que visitar todos los tipos para asignarles tamaño

        visit(array.getTipo());
    }

    public void visit(AccesoAtrib atrib) {

        visit(atrib.izq);

        visit(atrib.getTipo());
    }

    public void visit(And and) {

        visit(and.izq);
        visit(and.der);
    }

    public void visit(Asignacion asig) {

        visit(asig.izq);
        visit(asig.der);
    }

    public void visit(Atributo atrib) {}

    public void visit(Caracter car) {}

    public void visit(Casting cast) {

        visit(cast.valor);
    }

    public void visit(Cociente div) {

        visit(div.izq);
        visit(div.der);
    }

    public void visit(DefFuncion defun) {

        // Reseteo la dirección relativa ya que se va a introducir una nueva función

        direccionRelativa = 0;

        /* Los parámetros se almacenarán a partir de la dirección relativa -4 ya que las 2 primeras posiciones son para almacenar la dirección
         * de retorno y las 2 siguientes para almacenar el BP anterior */
        
        direccionParametros = 4;
        
        // Los parámetros se introducen en la pila en orden inverso, luego los visitaremos ya en este orden para insertarlos

        for (int i = defun.params.size() -1; i > -1; i--)

            visit(defun.params.get(i));


        Tipo aux;

        int tam = 0;
        
        // Calculo el tamaño total de todos los parámetros de la función para reservar el espacio necesario al meterla
        // posteriormente en la pila

        for (int i = 0; i < defun.params.size(); i++) {

            // Si el parámetro es de tipo simple ocupará tantas posiciones como tamaño tenga su tipo

            aux = ((Parametro) defun.params.get(i)).tipo; // Sin redeclarar la variable i en el for no dejaba utilizar la función get

            if (aux instanceof TipoInt || aux instanceof TipoReal || aux instanceof TipoChar
                || aux instanceof TipoVoid || aux instanceof TipoError)

                tam += aux.getTam();

            // En caso contrario, se almacenará su dirección que ocupa 2 posiciones de memoria

            else

                tam += 2;
        }

        // Almaceno el tamaño calculado

        defun.tamParametros = tam;


        // Hago lo mismo con las variables locales

        // Primero las visito para asignarles tamaño
        
        for (int i = 0; i < defun.vbles.size(); i++)
            
            visit (defun.vbles.get(i));

        tam = 0;

        for (int i = 0; i < defun.vbles.size(); i++)

            tam += ((DefVar) defun.vbles.get(i)).tipo.getTam();

        defun.tamVblesLocales = tam;

        visit (defun.cuerpo);
        
        // Y por último lo mismo con el retorno de la función (tras visitarlo)

        visit (defun.retorno);

        defun.tamRetorno = defun.retorno.getTam();
    }

    public void visit(DefTipo defTipo) {

        visit (defTipo.tipo);
    }

    public void visit(DefVar defVar) {

        visit (defVar.tipo);

        try {

            /* Las variables globales se almacenan en memoria estática empezando en la dirección 0000h, por tanto al ir almacenándolas, 
             * se irá aumentando el puntero de memoria */

            if (defVar.esGlobal) {

                defVar.setDireccion(direccionEstatica);

                direccionEstatica += defVar.tipo.getTam();
            }

            /* Las variables locales, irán asociadas a una función. Éstas se apilan en la dirección más alta posible de la pila y sus 
             * parámetros se van introduciendo a continuación, reduciendo para la inserción de cada uno de ellos el puntero de pila */

            else {

                direccionRelativa -= defVar.tipo.getTam();

                defVar.setOffset(direccionRelativa);
            }
       }

       catch (Exception e) {

            System.out.println("\nError en visit DefVar (Fase de selección de memoria): " + e.getMessage());
       }
    }

    public void visit(Distinto dist) {

        visit(dist.izq);
        visit(dist.der);
    }

    public void visit(Else sino) {

        for (int i = 0; i < sino.cuerpo.size(); i++)

            visit(sino.cuerpo.get(i));
    }

    public void visit(Escritura esc) {

        for (int i = 0; i < esc.expresiones.size(); i++) 

            visit(esc.expresiones.get(i));
    }

    public void visit(Funcion f) {}

    public void visit(If si) {

        visit(si.condiciones);

        try {

            for (int i = 0; i < si.cuerpo.size(); i++)

                visit(si.cuerpo.get(i));
        }

        catch (Exception e) {

            System.out.println("\nError en visit If (Fase de selección de memoria): " + e.getMessage());
        }
    }

    public void visit(IgualIgual igig) {

        visit(igig.izq);
        visit(igig.der);
    }

    public void visit(Lectura lect) {

        for (int i = 0; i < lect.expresiones.size(); i++) 

            visit(lect.expresiones.get(i));
    }

    public void visit(Mayor may) {

        visit(may.izq);
        visit(may.der);
    }

    public void visit(MayorOIgual Mayig) {

        visit(Mayig.izq);
        visit(Mayig.der);
    }

    public void visit(Menor men) {

        visit(men.izq);
        visit(men.der);
    }

    public void visit(MenorOIgual Menig) {

        visit(Menig.izq);
        visit(Menig.der);
    }

    public void visit(MenosUnario mm) {

        visit(mm.valor);
    }

    public void visit(Modulo mod) {

        visit(mod.izq);
        visit(mod.der);
    }

    public void visit(Not not) {

        visit(not.valor);
    }

    public void visit(Num num) {}

    public void visit(Or or) {

        visit(or.izq);
        visit(or.der);
    }

    public void visit(Parametro param) {

        visit(param.tipo);

        param.setOffset(direccionParametros);

        // Si el parámetro es de tipo simple ocupará tantas posiciones como tamaño tenga su tipo

        if (param.tipo instanceof TipoInt || param.tipo instanceof TipoReal || param.tipo instanceof TipoChar
            || param.tipo instanceof TipoVoid || param.tipo instanceof TipoError)

            direccionParametros += param.tipo.getTam();

        // En caso contrario, se almacenará su dirección que ocupa 2 posiciones de memoria

        else

            direccionParametros += 2;
    }

    public void visit(Producto prod) {

        visit(prod.izq);
        visit(prod.der);
    }

    public void visit(Resta resta) {

        visit(resta.izq);
        visit(resta.der);
    }

    public void visit(Retorno ret) {

        visit(ret.valor);
    }

    public void visit(Suma suma) {

        visit(suma.izq);
        visit(suma.der);
    }

    public void visit(TipoArray t) {

        visit (t.tipo);

        // Ocupará en memoria lo que ocupe su tipo multiplicado por el número de elementos contenidos en el array

        t.setTam(t.tipo.getTam() * Integer.parseInt(((Num) t.dimension).lexema));
    }

    public void visit(TipoChar t) {

        t.setTam(1);
    }

    public void visit(TipoInt t) { 

        t.setTam(2);
    }

    public void visit(TipoReal t) {

        t.setTam(4);
    }

    public void visit(TipoRegistro t) {

        try {

            int acumulado = 0;

            for (int i = 0; i < t.atribs.size(); i++) {

              visit(t.atribs.get(i));

                // Asigno a los atributos su desplazamiento dentro del espacio de memoria que ocupa el registro

                ((Atributo) t.atribs.get(i)).setOffset(acumulado);

                acumulado += ((Atributo) t.atribs.get(i)).tipo.getTam();
            }

            // Asignamos al registro su tamaño, que será lo acumulado de los tamaños de sus atributos

            t.setTam(acumulado);
        }

        catch (Exception e) {

            System.out.println("\nError en visit TipoRegistro (Fase de selección de memoria): " + e.getMessage());
        }
    }

    public void visit(TipoUsuario t) {

        t.setTam(t.declaracion.tipo.getTam());
    }

    public void visit(TipoVoid t) {

        // Hay que asignarle valor al atributo

        t.setTam(0);
    }
    
    public void visit(VarRef ref) {}

    public void visit(While w) {

        visit(w.condiciones);

        try {

            for (int i = 0; i < w.cuerpo.size(); i++)

                visit(w.cuerpo.get(i));
        }

        catch (Exception e) {

            System.out.println("\nError en visit While (Fase de selección de memoria): " + e.getMessage());
        }
    }
}
