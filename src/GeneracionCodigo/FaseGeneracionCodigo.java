package GeneracionCodigo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import nodosAST.*;
import Introspeccion.*;
import Interprete.*;

/**
 * Clase que implementa la fase de generación de código
 * @author Dami
 */
public class FaseGeneracionCodigo extends DefaultVisitor {

    private RepresentacionIntermedia ri;

    /**
     * Contador de sentencias while dentro del programa para la generación de etiquetas
     */
    private int contaWhile = 0;

    /**
     * Contador de sentencias if dentro del programa para la generación de etiquetas
     */
    private int contaIf = 0;

    public FaseGeneracionCodigo (Interprete i) {
        
        try {

            ri = new RepresentacionIntermedia (i);
        }

        catch (Exception e) {

            System.out.println("\nError en constructor (Fase de generación de código): " + e.getMessage());
        }
    }

    public void run (Programa p) {

        try {

            ri.call ("main");
            ri.halt();

            visit ("declara", p.declaraciones);

            /* codigo.close(); */
        }

        catch (Exception e) {

            System.out.println("\nError en run Programa (Fase de generación de código): " + e.getMessage());
        }
    }

    public void declara (DefFuncion defun) {

        try {

            ri.comentario ("\n\n#func " + defun.nombre);
            
            if (! (defun.retorno instanceof TipoVoid))

                ri.comentario ("\n#ret " + defun.retorno.toString());
            
            for (int i = 0; i < defun.params.size(); i++) {
                
                if (((Parametro) defun.params.get(i)).tipo instanceof TipoArray || ((Parametro) defun.params.get(i)).tipo instanceof TipoRegistro)

                        ri.comentario ("\n#param " + ((Parametro) defun.params.get(i)).nombre + ":address");

                else
                        ri.comentario ("\n#param " + ((Parametro) defun.params.get(i)).nombre + ":" + ((Parametro) defun.params.get(i)).tipo.toString());
            }

            for (int i = 0; i < defun.vbles.size(); i++) {

                ri.comentario ("\n#local " + ((DefVar) defun.vbles.get(i)).nombre + ": ");

                if (((DefVar) defun.vbles.get(i)).tipo instanceof TipoArray)

                    ri.comentario (((Num) ((TipoArray) ((DefVar) defun.vbles.get(i)).tipo).dimension).lexema + "*" +  (((TipoArray) ((DefVar) defun.vbles.get(i)).tipo).tipo.toString()));

                else

                    ri.comentario (((DefVar) defun.vbles.get(i)).tipo.toString());
            }

            // Escribir etiqueta con el nombre y dos puntos

            ri.etiqueta (defun.nombre);

            // Reservo el tamaño de las variables locales

            ri.enter (defun.tamVblesLocales);

            for (int i = 0; i < defun.cuerpo.size(); i++) {

                // Si la sentencia es un While, un If o un Retorno se ejecuta por separado

                if (defun.cuerpo.get(i) instanceof While) 

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del While, se retornará el tipo que tiene la función */

                    ((While) defun.cuerpo.get(i)).definicion = defun;
                
                else if (defun.cuerpo.get(i) instanceof If) 

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del If, se retornará el tipo que tiene la función */

                    ((If) defun.cuerpo.get(i)).definicion = defun;

                else if (defun.cuerpo.get(i) instanceof Retorno) 

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Retornará el tipo que tiene la función */

                    ((Retorno) defun.cuerpo.get(i)).definicion = defun;

                visit ("ejecuta", defun.cuerpo.get(i));
            }

            if (!(defun.retorno instanceof TipoVoid))

                ri.push (defun.retorno, 0); // El 0 es el valor neutro
            
            // Se coloca un retorno al fin para evitar que quede sin retorno por mala programación

            ri.ret (defun.tamRetorno, defun.tamVblesLocales, defun.tamParametros);

        }

        catch (Exception e) {

            System.out.println("\nError en declara DefFuncion (Fase de generación de código): " + e.getMessage());
        }
    }

    // Las declaraciones de tipos y de variables no generan código pero incluiré metadatos que faciliten la depuración con el intérprete
    
    public void declara (DefTipo defTipo) {

        try {

            ri.comentario ("\n\n#type " + defTipo.nombre + ": " + defTipo.tipo.toString());
        }

        catch (Exception e) {

            System.out.println("\nError en declara DefTipo (Fase de generación de código): " + e.getMessage());
        }
    }

    public void declara (DefVar defVar) {

        try {

            ri.comentario ("\n\n#data " + defVar.nombre + ": " + defVar.tipo.toString());

            if (defVar.esGlobal)

                ri.variableglobal (defVar);
        }

        catch (Exception e) {

            System.out.println("\nError en declara DefVar (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (Retorno ret) {

        visit ("valor", ret.valor);

        try {

            // Debe forzarse el retorno del tipo de la función que engloba al return aunque sea distinto al tipo de la propia sentencia retorno

            conversionTipos (ret.tipo, ret.definicion.retorno);

            ri.ret (ret.definicion.tamRetorno, ret.definicion.tamVblesLocales, ret.definicion.tamParametros);
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta Retorno (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (While w) {

        String etiqWhile = getEtiqueta("While");

        String etiqFinWhile = getEtiqueta("FinWhile");

        contaWhile++;

        try {

            ri.etiqueta (etiqWhile);

            visit ("valor", w.condiciones);

            ri.jz (etiqFinWhile);

            for (int i = 0; i < w.cuerpo.size(); i++) {

                // Si la sentencia es un While, un If o un Retorno se ejecuta por separado

                if (w.cuerpo.get(i) instanceof While)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del While, se retornará el tipo que tiene la función */

                    ((While) w.cuerpo.get(i)).definicion = w.definicion;

                else if (w.cuerpo.get(i) instanceof If)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del If, se retornará el tipo que tiene la función */

                    ((If) w.cuerpo.get(i)).definicion = w.definicion;

                else if (w.cuerpo.get(i) instanceof Retorno)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Retornará el tipo que tiene la función */

                    ((Retorno) w.cuerpo.get(i)).definicion = w.definicion;

                visit ("ejecuta", w.cuerpo.get(i));
            }

            ri.jmp (etiqWhile);

            ri.etiqueta (etiqFinWhile);
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta While (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (If si) {

        String etiqElse = "";

        // Hay que comprobar si existe la parte Else para generar el código de una manera u otra
        
        if (si.sino.cuerpo.size() > 0) {
            
            etiqElse = getEtiqueta("Else");
            
            /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
               Y en caso de retornar dentro del Else, se retornará el tipo que tiene la función */
            
            si.sino.definicion = si.definicion;
        }

        String etiqFinIf = getEtiqueta("FinIf");

        contaIf++;

        try {

            visit ("valor", si.condiciones);

            if (etiqElse.equals(""))
                
                ri.jz (etiqFinIf);

            else

                ri.jz (etiqElse);

            // Bloque verdadero (cuerpo del if)

            for (int i = 0; i < si.cuerpo.size(); i++) {

                // Si la sentencia es un While, un If o un Retorno se ejecuta por separado

                if (si.cuerpo.get(i) instanceof While)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del While, se retornará el tipo que tiene la función */

                    ((While) si.cuerpo.get(i)).definicion = si.definicion;

                else if (si.cuerpo.get(i) instanceof If)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del If, se retornará el tipo que tiene la función */

                    ((If) si.cuerpo.get(i)).definicion = si.definicion;

                else if (si.cuerpo.get(i) instanceof Retorno)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Retornará el tipo que tiene la función */

                    ((Retorno) si.cuerpo.get(i)).definicion = si.definicion;

                visit ("ejecuta", si.cuerpo.get(i));
            }

            if (! etiqElse.equals("")) {

                // Bloque falso

                ri.jmp (etiqFinIf);

                ri.etiqueta (etiqElse);

                visit ("ejecuta", si.sino);
            }

            ri.etiqueta (etiqFinIf);
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta If (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (Else sino)
    {
        try {

            for (int i = 0; i < sino.cuerpo.size(); i++) {

                // Si la sentencia es un While, un If o un Retorno se ejecuta por separado

                if (sino.cuerpo.get(i) instanceof While)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del While, se retornará el tipo que tiene la función */

                    ((While) sino.cuerpo.get(i)).definicion = sino.definicion;

                else if (sino.cuerpo.get(i) instanceof If)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Y en caso de retornar dentro del If, se retornará el tipo que tiene la función */

                    ((If) sino.cuerpo.get(i)).definicion = sino.definicion;

                else if (sino.cuerpo.get(i) instanceof Retorno)

                    /* Dispondrá de las variables locales y de los parámetros de la función en la que está englobado
                       Retornará el tipo que tiene la función */

                    ((Retorno) sino.cuerpo.get(i)).definicion = sino.definicion;

                visit ("ejecuta", sino.cuerpo.get(i));
            }
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta Else (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (Asignacion asig) {

        visit ("direccion", asig.izq);

        visit ("valor", asig.der);

        try {

            conversionTipos (asig.der.getTipo(), asig.izq.getTipo());

            ri.store (asig.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta Asignacion (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (Escritura esc) {

        try {

            for (int i = 0; i < esc.expresiones.size(); i++) {

                visit ("valor", esc.expresiones.get(i));

                ri.out (((Expr) esc.expresiones.get(i)).getTipo());

                /* // Incluyo un espacio (ASCII 32) para que no se muestren todos los resultados seguidos sin separar

                TipoChar t = new TipoChar();

                ri.push (t, 32);
                ri.out (t);        */
            }
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta Escritura (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (Lectura lect) {

        try {

            for (int i = 0; i < lect.expresiones.size(); i++) {

                visit ("direccion", lect.expresiones.get(i));

                ri.in (((Expr) lect.expresiones.get(i)).getTipo());
                ri.store (((Expr) lect.expresiones.get(i)).getTipo());
            }
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta Lectura (Fase de generación de código): " + e.getMessage());
        }
    }

    public void ejecuta (Funcion f) {

        try {

            visit ("valor", f);

            /* Si la función se "ejecuta" es que se está tratando como Sentencia
             *
             * Por tanto, si retorna un valor (no es TipoVoid) habrá que sacarlo de la pila (POP)
             *
             * Cuando se trata como Expr (en una Asignacion) no se ejecuta, simplemente se obtiene el "valor"
             * y la propia "ejecución" de la Asignacion lo quita de la pila 
             *
             * Esto es así porque el lenguaje permite llamar a una función que retorne un valor como si fuera una Sentencia */

            if (! (f.getTipo() instanceof TipoVoid))

                ri.pop (f.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en ejecuta Funcion (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Suma exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.add (exprBin.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Suma (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Resta exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.sub (exprBin.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Resta (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Producto exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.mul (exprBin.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Producto (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Cociente exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.div (exprBin.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Cociente (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Modulo exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.mod(exprBin.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Modulo (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Menor exprBin) {

        visit ("valor", exprBin.izq);
        visit ("valor", exprBin.der);

        try {

            // Hay que igualar los tipos de los operandos para operar con ellos
            // Cambio siempre el menor de los dos tipos para no perder información

            comparacionTipos comp = comparaTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            if (comp == comparacionTipos.MAYOR)

                conversionTipos (exprBin.der.getTipo(), exprBin.izq.getTipo());

            else if (comp == comparacionTipos.MENOR)

                conversionTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            ri.lt (exprBin.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Menor (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Mayor exprBin) {

        visit ("valor", exprBin.izq);
        visit ("valor", exprBin.der);

        try {

            // Hay que igualar los tipos de los operandos para operar con ellos
            // Cambio siempre el menor de los dos tipos para no perder información
            
            comparacionTipos comp = comparaTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            if (comp == comparacionTipos.MAYOR)

                conversionTipos (exprBin.der.getTipo(), exprBin.izq.getTipo());

            else if (comp == comparacionTipos.MENOR)

                conversionTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            ri.gt (exprBin.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Mayor (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (And exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.and();
        }

        catch (Exception e) {

            System.out.println("\nError en valor And (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Or exprBin) {

        try {

            // Convierto los tipos de los operandos al tipo de la expresión binaria para asegurar que se opera con valores del mismo tipo

            visit ("valor", exprBin.izq);
            conversionTipos (exprBin.izq.getTipo(), exprBin.getTipo());

            visit ("valor", exprBin.der);
            conversionTipos (exprBin.der.getTipo(), exprBin.getTipo());

            ri.or();
        }

        catch (Exception e) {

            System.out.println("\nError en valor Or (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (IgualIgual exprBin) {

        visit ("valor", exprBin.izq);
        visit ("valor", exprBin.der);

        try {

            // Hay que igualar los tipos de los operandos para operar con ellos
            // Cambio siempre el menor de los dos tipos para no perder información

            comparacionTipos comp = comparaTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            if (comp == comparacionTipos.MAYOR)

                conversionTipos (exprBin.der.getTipo(), exprBin.izq.getTipo());

            else if (comp == comparacionTipos.MENOR)

                conversionTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            ri.eq (exprBin.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor IgualIgual (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Distinto exprBin) {

        visit ("valor", exprBin.izq);
        visit ("valor", exprBin.der);

        try {

            // Hay que igualar los tipos de los operandos para operar con ellos
            // Cambio siempre el menor de los dos tipos para no perder información

            comparacionTipos comp = comparaTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            if (comp == comparacionTipos.MAYOR)

                conversionTipos (exprBin.der.getTipo(), exprBin.izq.getTipo());

            else if (comp == comparacionTipos.MENOR)

                conversionTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            ri.ne (exprBin.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Distinto (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (MayorOIgual exprBin) {

        visit ("valor", exprBin.izq);
        visit ("valor", exprBin.der);

        try {

            // Hay que igualar los tipos de los operandos para operar con ellos
            // Cambio siempre el menor de los dos tipos para no perder información

            comparacionTipos comp = comparaTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            if (comp == comparacionTipos.MAYOR)

                conversionTipos (exprBin.der.getTipo(), exprBin.izq.getTipo());

            else if (comp == comparacionTipos.MENOR)

                conversionTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            ri.ge (exprBin.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor MayorOIgual (Fase de generación de código): " + e.getMessage());
        }
    }
    public void valor (MenorOIgual exprBin) {

        visit ("valor", exprBin.izq);
        visit ("valor", exprBin.der);

        try {

            // Hay que igualar los tipos de los operandos para operar con ellos
            // Cambio siempre el menor de los dos tipos para no perder información

            comparacionTipos comp = comparaTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            if (comp == comparacionTipos.MAYOR)

                conversionTipos (exprBin.der.getTipo(), exprBin.izq.getTipo());

            else if (comp == comparacionTipos.MENOR)

                conversionTipos (exprBin.izq.getTipo(), exprBin.der.getTipo());

            ri.le (exprBin.izq.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor MenorOIgual (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Not not) {

        visit ("valor", not.valor);

        try {

            ri.not();
        }

        catch (Exception e) {

            System.out.println("\nError en valor Not (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (MenosUnario menos) {

        visit ("valor", menos.valor);

        try {

            // Hay que modificar el valor de la variable sobre la que se utiliza

            ri.push (menos.valor.getTipo(), -1);
            ri.mul (menos.valor.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor MenosUnario (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Num num) {

        try {

            ri.push (num.getTipo(), Float.parseFloat(num.lexema));
        }

        catch (Exception e) {

            System.out.println("\nError en valor Num (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Caracter car) {

        try {

            TipoChar t = new TipoChar();
            
            ri.push (t, Float.parseFloat(car.getCaracter())); 
        }

        catch (Exception e) {

            System.out.println("\nError en valor Caracter (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Funcion f) {

        try {

            Tipo tParam, tDef;

            for (int i = 0; i < f.params.size(); i++) {

                tParam = ((Expr) f.params.get(i)).getTipo();

                if (tParam instanceof TipoRegistro || tParam instanceof TipoArray)

                    visit ("direccion", f.params.get(i));

                else

                    visit ("valor", f.params.get(i));

               // Hay que asegurarse que el tipo de los parámetros es el indicado en la definición de la función

               tDef = ((Parametro) f.definicion.params.get(i)).tipo;

               conversionTipos (tParam, tDef); // Si son registros o arrays no hay que convertir nada pues estaremos manejando direcciones
            }

            ri.call (f.nombre);
        }

        catch (Exception e) {

            System.out.println("\nError en valor Funcion (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (VarRef refVar) {

        visit ("direccion", refVar);

        try {

            if (! (refVar.getTipo() instanceof TipoArray || refVar.getTipo() instanceof TipoRegistro))

                ri.load (refVar.definicion.tipo);
        }

        catch (Exception e) {

            System.out.println("\nError en valor VarRef (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (AccesoArray array) {

        visit ("direccion", array);

        try {

            ri.load (array.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor AccesoArray (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (AccesoAtrib acc) {

        visit ("direccion", acc);

        try {

            ri.load (acc.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor AccesoAtrib (Fase de generación de código): " + e.getMessage());
        }
    }

    public void valor (Casting cast) {

        visit ("valor", cast.valor);
        
        try {

            // Convierto el tipo del valor del casting al del propio casting

            conversionTipos (cast.valor.getTipo(), cast.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en valor Casting (Fase de generación de código): " + e.getMessage());
        }
    }

    public void direccion (Suma exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Suma");
    }

    public void direccion (Resta exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Resta");
    }

    public void direccion (Producto exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Producto");
    }

    public void direccion (Cociente exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Cociente");
    }

    public void direccion (Modulo exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Modulo");
    }

    public void direccion (Menor exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Menor");
    }

    public void direccion (Mayor exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Mayor");
    }

    public void direccion (And exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo And");
    }

    public void direccion (Or exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Or");
    }

    public void direccion (IgualIgual exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo IgualIgual");
    }

    public void direccion (Distinto exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Distinto");
    }

    public void direccion (MayorOIgual exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo MayorOIgual");
    }

    public void direccion (MenorOIgual exprBin) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo MenorOIgual");
    }

    public void direccion (Not not) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Not");
    }

    public void direccion (MenosUnario menos) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo MenosUnario");
    }

    public void direccion (Num n) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Num");
    }

    public void direccion (Caracter c) {

         System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Caracter");
    }

    public void direccion (VarRef refVar) {

        try {

            if (refVar.definicion.esGlobal)
                
                ri.pusha (String.valueOf (refVar.definicion.getDireccion()));

            else {

                // Variable local (mirar desplazamiento)

                ri.pushbp();

                TipoInt t = new TipoInt();
                ri.push (t, refVar.definicion.getOffset());

                ri.add(t);
            }
        }

        catch (Exception e) {

            System.out.println("\nError en direccion VarRef (Fase de generación de código): " + e.getMessage());
        }
    }

    public void direccion (AccesoArray array) {

        try {

            visit ("direccion", array.izq);

            TipoInt t = new TipoInt();

            if (array.izq instanceof VarRef) 

                if (((VarRef) array.izq).definicion instanceof Parametro)

                    ri.load (t);
            
            visit ("valor", array.der);

            /* Hay que comprobar que no se accede a una posición fuera del rango del array
             * Por tanto, el valor que en este momento hay en la cima de la pila debe ser
             * menor que la dimensión del array: Array [5] tiene posiciones de 0 a 4 */

            int dimension = Integer.parseInt (((Num) ((TipoArray) ((VarRef) array.izq).getTipo()).dimension).lexema);

            ri.compruebaRangoArray (dimension);
                        
            ri.push (t, array.getTipo().size());

            ri.mul (t);
            
            ri.add (t);
        }

        catch (Exception e) {

            System.out.println("\nError en direccion AccesoArray (Fase de generación de código): " + e.getMessage());
        }
    }

    public void direccion (AccesoAtrib acc) {

        try {

            visit ("direccion", acc.izq);

            TipoInt t = new TipoInt();

            if (acc.izq instanceof VarRef)

                if (((VarRef) acc.izq).definicion instanceof Parametro)

                    ri.load (t);

            ri.push (t, acc.atrib.getOffset());

            ri.add (t);
        }

        catch (Exception e) {

            System.out.println("\nError en direccion AccesoAtrib (Fase de generación de código): " + e.getMessage());
        }
    }

    public void direccion (Casting cast) {

        System.out.println ("\nError en la fase de generación de código: no se puede obtener la dirección de un nodo Casting");
    }
    
    /**
     * Genera el código necesario para realizar la conversión de tipo ini a tipo fin
     * @param ini Tipo que se desea convertir
     * @param fin Tipo hacia el que se desea convertir
     */
    protected void conversionTipos (Tipo ini, Tipo fin)
    {
        try {

            if (fin instanceof TipoInt) {

                if (ini instanceof TipoInt) return;

                else if (ini instanceof TipoReal) ri.f2i();

                else if (ini instanceof TipoChar) ri.b2i();

                else System.out.println ("\nError en la fase de generación de código: no existe ninguna instrucción de conversión de " + ini + " a " + fin);
            }

            else if (fin instanceof TipoReal) {
            
                if (ini instanceof TipoReal) return;

                else if (ini instanceof TipoInt) ri.i2f();

                else System.out.println ("\nError en la fase de generación de código: no existe ninguna instrucción de conversión de " + ini + " a " + fin);
            }

            else if(fin instanceof TipoChar) {
            
                if (ini instanceof TipoChar) return;

                else if (ini instanceof TipoInt) ri.i2b();

                else System.out.println ("\nError en la fase de generación de código: no existe ninguna instrucción de conversión de " + ini + " a " + fin);
            }

            // Si no se trata de tipos básicos, pero el inicial y el final son del mismo tipo se continúa la ejecución, si son distintos se muestra un error
            
            else if ((ini instanceof TipoRegistro && fin instanceof TipoRegistro)
                      || (ini instanceof TipoArray && fin instanceof TipoArray)) return;
            
            
            else System.out.println ("\nError en la fase de generación de código: no existe ninguna instrucción de conversión de " + ini + " a " + fin);
        }
        
        catch(Exception e) {
        
            System.out.println("\nError en conversionTipos (Fase de generación de código): " + e.getMessage());
        }
    }

    /**
     * Genera una etiqueta de la forma 'EtiqNNN' donde 'NNN' es un ordinal y 'Etiq' puede tomar los valores: While, FinWhile, Else y FinIf
     * @return
     */
    protected String getEtiqueta (String opcion) {

        String etiqueta = "";
        
        if (opcion.equals("While") || opcion.equals("FinWhile"))

            etiqueta = opcion + contaWhile;

        else

            // opcion.equals("Else") || opcion.equals("FinIf")

            etiqueta = opcion + contaIf;

        return etiqueta;
    }

    public enum comparacionTipos { MENOR, IGUAL, MAYOR, NO_COMPARABLE };

    private comparacionTipos comparaTipos (Tipo tIzq, Tipo tDer) {

        // Si alguno de los tipos es TipoArray, obtener su tipo básico para tratarlo

        if (tIzq instanceof TipoArray)

            tIzq = ((TipoArray) tIzq).tipo;

        if (tDer instanceof TipoArray)

            tDer = ((TipoArray) tDer).tipo;

        // REAL > INT > CHAR y el resto de tipos no se pueden comparar

        if (tDer instanceof TipoInt) {

            if (tIzq instanceof TipoInt) return comparacionTipos.IGUAL;

            if (tIzq instanceof TipoReal) return comparacionTipos.MAYOR;

            if (tIzq instanceof TipoChar) return comparacionTipos.MENOR;

            return comparacionTipos.NO_COMPARABLE;
        }

        if (tDer instanceof TipoReal) {

            if (tIzq instanceof TipoInt) return comparacionTipos.MENOR;

            if (tIzq instanceof TipoReal) return comparacionTipos.IGUAL;

            if (tIzq instanceof TipoChar) return comparacionTipos.MENOR;

            return comparacionTipos.NO_COMPARABLE;
        }

        if (tDer instanceof TipoChar) {

            if (tIzq instanceof TipoInt) return comparacionTipos.MAYOR;

            if (tIzq instanceof TipoReal) return comparacionTipos.MAYOR;

            if (tIzq instanceof TipoChar) return comparacionTipos.IGUAL;

            return comparacionTipos.NO_COMPARABLE;
        }

        return comparacionTipos.NO_COMPARABLE;
    }
}
