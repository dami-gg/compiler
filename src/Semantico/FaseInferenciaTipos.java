package Semantico;

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
public class FaseInferenciaTipos extends DefaultVisitor {

    public FaseInferenciaTipos() {
        
        super(AST.class);   // Recorrido implícito
    }

    // No hace falta un visit(Programa) ya que hay recorrido
    // implícito (si no se pone se recorren sus hijos)
    
    public void visit(AccesoArray array) {

        visit(array.izq);
        visit(array.der);

        try {

            // El tipo de la expresión izquierda debe ser TipoArray

            Tipo tIzq = array.izq.getTipo();

            if (! (tIzq instanceof TipoArray))

                System.out.println ("\nError en la fase de inferencia de tipos: la expresión izquierda es de tipo " + tIzq + " en lugar de un array");

            // El tipo de la expresión derecha sólo puede ser TipoInt, TipoArray o TipoRegistro

            Tipo tDer = array.der.getTipo();

            if (! (tDer instanceof TipoInt || tDer instanceof TipoArray || tDer instanceof TipoRegistro)) {

                System.out.println ("\nError en la fase de inferencia de tipos: la expresión derecha es un " + tDer + " en lugar de entero, array o registro");

                return;
            }

            // El tipo de AccesoArray sera el del TipoArray de la parte izquierda

            array.setTipo (((TipoArray) array.izq.getTipo()).tipo );

            // Y su modificabilidad la misma que la de la expresión izquierda

            array.setLvalue(array.izq.getLvalue());
        }

        catch (Exception e) {

            System.out.println("\nError en visit AccesoArray (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(AccesoAtrib atrib) {

        visit(atrib.izq);

        try {

            // La parte izquierda debe ser una expresión de TipoRegistro

            Tipo tIzq = atrib.izq.getTipo();

            if (! (tIzq instanceof TipoRegistro)) {

                System.out.println ("\nError en la fase de inferencia de tipos: la expresión izquierda es de tipo " + tIzq + " en lugar de un registro");

                return;
            }

            // Almaceno el registro para recorrer sus atributos y buscar al que se intenta acceder
            
            TipoRegistro tReg = (TipoRegistro) tIzq;

            for (int i = 0; i < tReg.atribs.size(); i++) {

                if (((Atributo) tReg.atribs.get(i)).nombre.equals(atrib.der)) {

                    // El tipo del acceso a un atributo será el del atributo al que se desea acceder

                    atrib.setTipo (((Atributo) tReg.atribs.get(i)).tipo);

                    // La modificabilidad será la del registro (expresión izquierda)

                    atrib.setLvalue (atrib.izq.getLvalue());

                    // Enlazamos el atributo del acceso con el del registro
                    
                    atrib.atrib = (Atributo) tReg.atribs.get(i);

                    // Una vez encontrado ya no sigo recorriendo más la lista de atributos

                    return;
                }
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit AccesoAtrib (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(And and) {

        visit(and.izq);
        visit(and.der);

        try {

            Tipo tIzq = and.izq.getTipo();

            Tipo tDer = and.der.getTipo();

            and.setTipo(tIzq.And(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (and.getTipo() instanceof TipoError)

                System.out.println (((TipoError) and.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit And (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Asignacion asig) {

        visit(asig.izq);
        visit(asig.der);

        try {

            // La expresión de la izquierda debe ser lvalue para poder asignarle un valor

            if (!asig.izq.getLvalue()) {

                System.out.println ("\nError en la fase de inferencia de tipos: no se le puede asignar ningún valor a un objeto de tipo " + asig.izq.getTipo());

                return;
            }

            // El tipo de al menos una de las expresiones debe ser promocionable al otro tipo

            if (! (asig.izq.getTipo().EsPromocionableA(asig.der.getTipo()) || (asig.der.getTipo().EsPromocionableA((asig.izq.getTipo())))))

                System.out.println ("\nError en la fase de inferencia de tipos: no se le puede asignar a un " + asig.izq.getTipo() + " un valor de tipo " + asig.der.getTipo());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Asignación (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Atributo atrib) {}

    public void visit(Caracter car) {}

    public void visit(Casting cast) {

        visit(cast.valor);

        try {

            // Suponiendo que se pueda hacer casting entre char, int y real en cualquiera de los sentidos posibles

            // Por tanto, el tipo origen debe ser uno de esos tres

            if (! (cast.valor.getTipo() instanceof TipoChar || cast.valor.getTipo() instanceof TipoInt || cast.valor.getTipo() instanceof TipoReal)) {

                System.out.println ("\nError en la fase de inferencia de tipos: no se le puede realizar un casting a un objeto de tipo " + cast.valor.getTipo());

                return;
            }

            // Y lo mismo para el tipo destino

            if (! (cast.getTipo() instanceof TipoChar || cast.getTipo() instanceof TipoInt || cast.getTipo() instanceof TipoReal)) {

                    System.out.println ("\nError en la fase de inferencia de tipos: no se puede realizar un casting hacia el tipo " + cast.getTipo());

                return;
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit Casting (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Cociente div) {

        visit(div.izq);
        visit(div.der);

        try {

            Tipo tIzq = div.izq.getTipo();

            Tipo tDer = div.der.getTipo();

            div.setTipo(tIzq.Producto(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (div.getTipo() instanceof TipoError)

                System.out.println (((TipoError) div.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Cociente (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(DefFuncion defun) {

        // Visitar las instrucciones que forman la función

        for (int i = 0; i < defun.cuerpo.size(); i++) {

            visit(defun.cuerpo.get(i));

            if (defun.cuerpo.get(i) instanceof Retorno) {

                if (! (((Retorno) defun.cuerpo.get(i)).tipo).mismoTipo(defun.retorno) ) {

                    if (((Retorno) defun.cuerpo.get(i)).tipo instanceof TipoError) return;

                    // Si el retorno es un número, tendrá asignado TipoInt por defecto, pero habrá que adaptarlo al retorno de la definición 
                    // (si es TipoChar, TipoInt o TipoReal y darlo por válido
                    
                    else if (((Retorno) defun.cuerpo.get(i)).valor instanceof Num &&
                            (defun.retorno instanceof TipoChar || defun.retorno instanceof TipoInt || defun.retorno instanceof TipoReal)) {
                        
                        ((Retorno) defun.cuerpo.get(i)).valor.setTipo(defun.retorno);

                        ((Retorno) defun.cuerpo.get(i)).valor.setTipo(defun.retorno);

                        return;
                    }

                    System.out.println ("\nError en la fase de inferencia de tipos: el tipo retornado " + ((Retorno) defun.cuerpo.get(i)).tipo + " no coincide con el tipo de retorno de la función " + defun.retorno);
                }
            }
        }
    }

    public void visit(DefTipo defTipo) {}

    public void visit(DefVar defVar) {}

    public void visit(Distinto dist) {

        visit(dist.izq);
        visit(dist.der);

        try {

            Tipo tIzq = dist.izq.getTipo();

            Tipo tDer = dist.der.getTipo();

            dist.setTipo(tIzq.Distinto(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (dist.getTipo() instanceof TipoError)

                System.out.println (((TipoError) dist.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit IgualIgual (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Else sino) {

        try {

            for (int i = 0; i < sino.cuerpo.size(); i++) {

                visit(sino.cuerpo.get(i));
            
                if (sino.cuerpo.get(i) instanceof Retorno) {

                    if (! (((Retorno) sino.cuerpo.get(i)).tipo).mismoTipo(sino.definicion.retorno) ) {

                        if (((Retorno) sino.cuerpo.get(i)).tipo instanceof TipoError) return;

                        // Si el retorno es un número, tendrá asignado TipoInt por defecto, pero habrá que adaptarlo al retorno de la definición
                        // (si es TipoChar, TipoInt o TipoReal y darlo por válido

                        else if (((Retorno) sino.cuerpo.get(i)).valor instanceof Num &&
                                (sino.definicion.retorno instanceof TipoChar || sino.definicion.retorno instanceof TipoInt || sino.definicion.retorno instanceof TipoReal)) {

                            ((Retorno) sino.cuerpo.get(i)).tipo = sino.definicion.retorno;

                            ((Retorno) sino.cuerpo.get(i)).valor.setTipo(sino.definicion.retorno);

                            return;
                        }

                        System.out.println ("\nError en la fase de inferencia de tipos: el tipo retornado en el else " + ((Retorno) sino.cuerpo.get(i)).tipo + " no coincide con el tipo de retorno de la función en que está contenido " + sino.definicion.retorno);
                    }
                }
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit Else (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Escritura esc) {

        for (int i = 0; i < esc.expresiones.size(); i++) 

            visit(esc.expresiones.get(i));
    }

    public void visit(Funcion f) {

        Tipo tParam, tDefParam;

        // El número de parámetros de la llamada debe ser el mismo que en la definición de la función

        if (f.params.size() != f.definicion.params.size()) {

            System.out.println ("\nError en la fase de inferencia de tipos: en la llamada a la función " + f.nombre + ", el número de parámetros no coincide con el establecido en su definición");

            return;
        }

        // Para tipar los parámetros de la llamada (si no al asignar el tipo a tParam valdría null)

        visit (f.params);

        // Visitar los parámetros uno a uno y comprobar que sus tipos coinciden con los indicados en la definición de la función

        for (int i = 0; i < f.params.size(); i++) {

            tParam = ((Expr) f.params.get(i)).getTipo();

            tDefParam = ((Parametro) f.definicion.params.get(i)).tipo;

            if (! tParam.mismoTipo(tDefParam)) {
                
                // Controlar tipos promocionables (tipos simples)

                // En los errores no pongo return porque si hay 5 parámetros mal tiene que mostrar avisos para los 5
            
                if (! (tDefParam instanceof TipoArray || tDefParam instanceof TipoRegistro)) {

                    if (! tParam.EsPromocionableA(tDefParam))

                        System.out.println("\nError en la fase de inferencia de tipos: el tipo " + tParam + " del parámetro " + ((Parametro) f.params.get(i)).nombre + " en la llamada a la función " + f.nombre + " no es promocionable al tipo " + tDefParam + " indicado en su definición");
                }
                
                else // Los tipos no coinciden
                    
                    System.out.println ("\nError en la fase de inferencia de tipos: el tipo " + tParam + " del parámetro " + ((Parametro) f.params.get(i)).nombre + " en la llamada a la función " + f.nombre + " no coincide con el tipo " + tDefParam + " indicado en su definición");
            }
        }
        
        // El tipo de la llamada a la función será el que retorne dicha función

        f.setTipo(f.definicion.retorno);
    }

    public void visit(If si) {

        visit(si.condiciones);

        try {

            if (! (si.condiciones.getTipo() instanceof TipoInt)) {

                System.out.println ("\nError en la fase de inferencia de tipos: el tipo " + si.condiciones.getTipo() + " de la condición no es evaluable");
            }

            for (int i = 0; i < si.cuerpo.size(); i++) {

                visit(si.cuerpo.get(i));

                if (si.cuerpo.get(i) instanceof Retorno) {

                    if (! (((Retorno) si.cuerpo.get(i)).tipo).mismoTipo(si.definicion.retorno) ) {

                        if (((Retorno) si.cuerpo.get(i)).tipo instanceof TipoError) break;

                        // Si el retorno es un número, tendrá asignado TipoInt por defecto, pero habrá que adaptarlo al retorno de la definición
                        // (si es TipoChar, TipoInt o TipoReal y darlo por válido

                        else if (((Retorno) si.cuerpo.get(i)).valor instanceof Num &&
                            (si.definicion.retorno instanceof TipoChar || si.definicion.retorno instanceof TipoInt || si.definicion.retorno instanceof TipoReal)) {

                            ((Retorno) si.cuerpo.get(i)).tipo = si.definicion.retorno;

                            ((Retorno) si.cuerpo.get(i)).valor.setTipo(si.definicion.retorno);

                            break;
                        }

                        System.out.println ("\nError en la fase de inferencia de tipos: el tipo retornado en el if " + ((Retorno) si.cuerpo.get(i)).tipo + " no coincide con el tipo de retorno de la función en que está contenido " + si.definicion.retorno);
                    }
                }
            }

            visit(si.sino);
        }

        catch (Exception e) {

            System.out.println("\nError en visit If (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(IgualIgual igig) {

        visit(igig.izq);
        visit(igig.der);

        try {

            Tipo tIzq = igig.izq.getTipo();

            Tipo tDer = igig.der.getTipo();

            igig.setTipo(tIzq.IgualIgual(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (igig.getTipo() instanceof TipoError)

                System.out.println (((TipoError) igig.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit IgualIgual (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Lectura lect) {

        for (int i = 0; i < lect.expresiones.size(); i++) 

            visit(lect.expresiones.get(i));
    }

    public void visit(Mayor may) {

        visit(may.izq);
        visit(may.der);

        try {

            Tipo tIzq = may.izq.getTipo();

            Tipo tDer = may.der.getTipo();

            may.setTipo(tIzq.Mayor(tDer));

            // Si el tipo inferido es Error, mostramos su mensaje

            if (may.getTipo() instanceof TipoError)

                System.out.println (((TipoError) may.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Mayor (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(MayorOIgual Mayig) {

        visit(Mayig.izq);
        visit(Mayig.der);

        try {

            Tipo tIzq = Mayig.izq.getTipo();

            Tipo tDer = Mayig.der.getTipo();

            Mayig.setTipo(tIzq.MayorOIgual(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (Mayig.getTipo() instanceof TipoError)

                System.out.println (((TipoError) Mayig.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit MayorOIgual (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Menor men) {

        visit(men.izq);
        visit(men.der);

        try {

            Tipo tIzq = men.izq.getTipo();

            Tipo tDer = men.der.getTipo();

            men.setTipo(tIzq.Menor(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (men.getTipo() instanceof TipoError)

                System.out.println (((TipoError) men.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Menor (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(MenorOIgual Menig) {

        visit(Menig.izq);
        visit(Menig.der);

        try {

            Tipo tIzq = Menig.izq.getTipo();

            Tipo tDer = Menig.der.getTipo();

            Menig.setTipo(tIzq.MenorOIgual(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (Menig.getTipo() instanceof TipoError)

                System.out.println (((TipoError) Menig.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit MenorOIgual (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(MenosUnario mm) {

        visit(mm.valor);

        try {

            if (!(mm.valor.getTipo() instanceof TipoInt || mm.valor.getTipo() instanceof TipoChar || mm.valor.getTipo() instanceof TipoReal || mm.valor.getTipo() instanceof TipoUsuario))

                System.out.println ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador - sobre el tipo " + mm.valor.getTipo());

            else {

                if (mm.valor.getTipo() instanceof TipoUsuario) {

                    Tipo aux = (((TipoUsuario) mm.valor.getTipo()).declaracion).tipo;

                    if (! (aux instanceof TipoInt || aux instanceof TipoChar)) { 

                        System.out.println ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador - sobre el tipo " + mm.valor.getTipo());

                        return;
                    }
                }

                mm.setTipo(mm.valor.getTipo());

                // Si el tipo inferido es TipoError, mostramos su mensaje

                if (mm.valor.getTipo() instanceof TipoError)

                    System.out.println (((TipoError) mm.valor.getTipo()).mostrarMensaje());
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit MenosUnario (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Modulo mod) {

        visit(mod.izq);
        visit(mod.der);

        try {

            Tipo tIzq = mod.izq.getTipo();

            Tipo tDer = mod.der.getTipo();

            mod.setTipo(tIzq.Modulo(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (mod.getTipo() instanceof TipoError)

                System.out.println (((TipoError) mod.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Modulo (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Not not) {

        visit(not.valor);

        try {

            if (!(not.valor.getTipo() instanceof TipoInt || not.valor.getTipo() instanceof TipoChar || not.valor.getTipo() instanceof TipoUsuario)) 

                System.out.println ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador ! sobre el tipo " + not.valor.getTipo());

            else {

                if (not.valor.getTipo() instanceof TipoUsuario) {
                    
                    Tipo aux = (((TipoUsuario) not.valor.getTipo()).declaracion).tipo;
                    
                    if (! (aux instanceof TipoInt || aux instanceof TipoChar)) {
                        
                        System.out.println ("\nError en la fase de inferencia de tipos: no se puede aplicar el operador ! sobre el tipo " + not.valor.getTipo());
                        
                        return;
                    }
                }

                not.setTipo(not.valor.getTipo());

                // Si el tipo inferido es TipoError, mostramos su mensaje

                if (not.valor.getTipo() instanceof TipoError)

                    System.out.println (((TipoError) not.valor.getTipo()).mostrarMensaje());
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit Not (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Num num) {}

    public void visit(Or or) {

        visit(or.izq);
        visit(or.der);

        try {

            Tipo tIzq = or.izq.getTipo();

            Tipo tDer = or.der.getTipo();

            or.setTipo(tIzq.Or(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (or.getTipo() instanceof TipoError)

                System.out.println (((TipoError) or.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Or (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Parametro param) {}

    public void visit(Producto prod) {

        visit(prod.izq);
        visit(prod.der);

        try {

            Tipo tIzq = prod.izq.getTipo();

            Tipo tDer = prod.der.getTipo();

            prod.setTipo(tIzq.Producto(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (prod.getTipo() instanceof TipoError)

                System.out.println (((TipoError) prod.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Producto (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Resta resta) {

        visit(resta.izq);
        visit(resta.der);

        try {

            Tipo tIzq = resta.izq.getTipo();

            Tipo tDer = resta.der.getTipo();

            resta.setTipo(tIzq.Resta(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (resta.getTipo() instanceof TipoError)

                System.out.println (((TipoError) resta.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Resta (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(Retorno ret) {

        visit(ret.valor);

        ret.tipo = ret.valor.getTipo();
    }

    public void visit(Suma suma) {

        visit(suma.izq);
        visit(suma.der);

        try {

            Tipo tIzq = suma.izq.getTipo();

            Tipo tDer = suma.der.getTipo();

            suma.setTipo(tIzq.Suma(tDer));

            // Si el tipo inferido es TipoError, mostramos su mensaje

            if (suma.getTipo() instanceof TipoError)

                System.out.println (((TipoError) suma.getTipo()).mostrarMensaje());
        }

        catch (Exception e) {

            System.out.println("\nError en visit Suma (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(TipoArray t) {

        try {
            
            // La dimensión debe ser un número (NUM) --> no vale var array:[d] int

            if (!(t.dimension instanceof Num)) {

               System.out.println("\nError en la fase de inferencia de tipos: la dimensión de un array debe ser un número");

               return;
            }

            // La dimensión debe ser de TipoInt --> no vale var array:[2.5] int

            if (!(t.dimension.getTipo() instanceof TipoInt)) {

               System.out.println("\nError en la fase de inferencia de tipos: la dimensión de un array debe ser de tipo entero");

               return;
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit TipoArray (Fase de inferencia de tipos): " + e.getMessage());
        }
    }

    public void visit(TipoChar t) {}

    public void visit(TipoInt t) {}

    public void visit(TipoReal t) {}

    public void visit(TipoRegistro t) {}

    public void visit(TipoUsuario t) {}

    public void visit(TipoVoid t) {}

    public void visit(VarRef ref) {

        // Le asignamos el tipo que tenga la definición, y como es una variable la hacemos lvalue

        ref.setTipo(ref.definicion.tipo);

        ref.setLvalue(true);
    }

    public void visit(While w) {

        visit(w.condiciones);

        try {

            if (! (w.condiciones.getTipo() instanceof TipoInt)) {

                System.out.println ("\nError en la fase de inferencia de tipos: el tipo " + w.condiciones.getTipo() + " de la condición no es evaluable");
            }

            for (int i = 0; i < w.cuerpo.size(); i++) {

                visit(w.cuerpo.get(i));
                
                if (w.cuerpo.get(i) instanceof Retorno) {

                    if (! (((Retorno) w.cuerpo.get(i)).tipo).mismoTipo(w.definicion.retorno) ) {

                        if (((Retorno) w.cuerpo.get(i)).tipo instanceof TipoError) return;

                        // Si el retorno es un número, tendrá asignado TipoInt por defecto, pero habrá que adaptarlo al retorno de la definición
                        // (si es TipoChar, TipoInt o TipoReal y darlo por válido

                        else if (((Retorno) w.cuerpo.get(i)).valor instanceof Num &&
                                (w.definicion.retorno instanceof TipoChar || w.definicion.retorno instanceof TipoInt || w.definicion.retorno instanceof TipoReal)) {

                            ((Retorno) w.cuerpo.get(i)).tipo = w.definicion.retorno;

                            ((Retorno) w.cuerpo.get(i)).valor.setTipo(w.definicion.retorno);

                            return;
                        }

                        System.out.println ("\nError en la fase de inferencia de tipos: el tipo retornado en el while " + ((Retorno) w.cuerpo.get(i)).tipo + " no coincide con el tipo de retorno de la función en que está contenido " + w.definicion.retorno);
                    }
                }
            }
        }

        catch (Exception e) {

            System.out.println("\nError en visit While (Fase de inferencia de tipos): " + e.getMessage());
        }
    }
}
