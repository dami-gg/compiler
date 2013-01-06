package Semantico;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import nodosAST.*;
import java.util.HashMap;
import java.util.Map;
import Introspeccion.*;

/**
 *
 * @author Dami
 */
public class FaseIdentificacionTipos extends DefaultVisitor {

    private enum Ambito {

        GLOBAL, LOCAL
    };

    private Ambito ambito = Ambito.GLOBAL;

    private Map<String, DefVar> tablaVblesGlobal = new HashMap<String, DefVar>();
    private Map<String, Tipo> tablaTiposGlobal = new HashMap<String, Tipo>();
    private Map<String, DefFuncion> tablaFuncsGlobal = new HashMap<String, DefFuncion>();

    // No hay tabla de funciones local porque dentro de una función no se pueden definir otras

    private Map<String, DefVar> tablaVblesLocal;
    private Map<String, Tipo> tablaTiposLocal;

    // Cambia a ámbito local
    
    private void set() {

        ambito = Ambito.LOCAL;

        tablaVblesLocal = new HashMap<String, DefVar>();
        tablaTiposLocal = new HashMap<String, Tipo>();
    }

    // Cambia a ámbito global
    
    private void reset() {

        ambito = Ambito.GLOBAL;

        tablaVblesLocal.clear();
        tablaTiposLocal.clear();
    }

    public FaseIdentificacionTipos() {

        super(AST.class);   // Recorrido implícito
    }

    // No hace falta un visit(Programa) ya que hay recorrido
    // implícito (si no se pone se recorren sus hijos)
    
    public void visit(AccesoArray array) {

        visit(array.izq);
        visit(array.der);
    }

    public void visit(AccesoAtrib atrib) {

        visit(atrib.izq);
    }

    public void visit(And and) {

        visit(and.izq);
        visit(and.der);
    }

    public void visit(Asignacion asig) {

        visit(asig.izq);
        visit(asig.der);
    }

    public void visit(Atributo atrib) {

        atrib.tipo = (Tipo) visit(atrib.tipo);
    }

    public void visit(Caracter car) {}

    public void visit(Casting cast) {

        cast.setTipo((Tipo) visit(cast.getTipo()));
        visit(cast.valor);
    }

    public void visit(Cociente div) {

        visit(div.izq);
        visit(div.der);
    }

    public void visit(DefFuncion defun) {
                
        // Comprobar si la función ya estaba definida previamente
        
        if (tablaFuncsGlobal.get(defun.nombre) != null)
            
            System.out.println("\nFunción ya definida: " + defun.nombre);
        
        else
            
            // La almacenamos en la tabla de funciones
            
            tablaFuncsGlobal.put(defun.nombre, defun);
        
        // Pasar a ámbito local

        set();
    
        // Visitar los parámetros

        visit(defun.params);
        
        // Visitar las definiciones de variables locales de la función
        
        for (int i = 0; i < defun.vbles.size(); i++) {

            visit(defun.vbles.get(i));
        }
        
        // Visitar las instrucciones que forman la función

        for (int i = 0; i < defun.cuerpo.size(); i++) {

            // Asigno a las instrucciones If (y al Else dentro del visit del If) y While la definición de la función a la que pertenecen para facilitar posteriores fases

            if (defun.cuerpo.get(i) instanceof If)

                ((If) defun.cuerpo.get(i)).definicion = defun;
            
            else if (defun.cuerpo.get(i) instanceof While)

                ((While) defun.cuerpo.get(i)).definicion = defun;

            visit(defun.cuerpo.get(i));
        }
        
        // Volver a ámbito global

        reset();

        // Si la función no es void (tiene tipo de retorno) hay que comprobar que tiene una sentencia de Retorno

        if (! (defun.retorno instanceof TipoVoid)) {

            for (int i = 0; i < defun.cuerpo.size(); i++) {

                if (defun.cuerpo.get(i) instanceof Retorno) return;
            }

            System.out.println("\nLa función " + defun.nombre + " no es de tipo void y no tiene sentencia de tipo retorno");
        }
    }

    public void visit(DefTipo defTipo) {

        if (ambito == Ambito.LOCAL) {

            // Comprobar si el tipo ya estaba definido anteriormente

            if (tablaTiposLocal.get(defTipo.nombre) != null)

                System.out.println("\nTipo ya definido: " + defTipo.nombre);

            else {

                defTipo.esGlobal = false;

                defTipo.tipo = (Tipo) visit(defTipo.tipo);

                // Lo almacenamos en la tabla de tipos locales

                tablaTiposLocal.put(defTipo.nombre, defTipo.tipo);
            }
        }

        else {

            // Ámbito global

            // Comprobar si el tipo ya estaba definido anteriormente

            if (tablaVblesGlobal.get(defTipo.nombre) != null)

                System.out.println("\nTipo ya definido: " + defTipo.nombre);

            else {

                defTipo.esGlobal = true;

                defTipo.tipo = (Tipo) visit(defTipo.tipo);

                // Lo almacenamos en la tabla de tipos locales

                tablaTiposGlobal.put(defTipo.nombre, defTipo.tipo);
            }
        }
    }

    public void visit(DefVar defVar) {

        if (ambito == Ambito.LOCAL) {

            // Comprobar si la variable ya estaba definida anteriormente

            if (tablaVblesLocal.get(defVar.nombre) != null)

                System.out.println("\nVariable ya definida: " + defVar.nombre);

            else {

                defVar.esGlobal = false;

                defVar.tipo = (Tipo) visit(defVar.tipo);

                // La almacenamos en la tabla de variables locales

                tablaVblesLocal.put(defVar.nombre, defVar);
            }
        }

        else {

            // Ámbito global

            // Comprobar si la variable ya estaba definida anteriormente

            if (tablaVblesGlobal.get(defVar.nombre) != null)

                System.out.println("\nVariable ya definida: " + defVar.nombre);

            else {

                defVar.esGlobal = true;

                defVar.tipo = (Tipo) visit(defVar.tipo);

                // La almacenamos en la tabla de variables locales

                tablaVblesGlobal.put(defVar.nombre, defVar);
            }
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

    public void visit(Funcion f) {

        DefFuncion def = (DefFuncion) tablaFuncsGlobal.get(f.nombre);

        if (def == null)

            System.out.println("\nFunción no definida: " + f.nombre);

        for (int i = 0; i < f.params.size(); i++) 

            visit(f.params.get(i));

        // Enlazar función con su definición

        f.definicion = def;

    }

    public void visit(If si) {

        visit(si.condiciones);

        for (int i = 0; i < si.cuerpo.size(); i++) 

            visit(si.cuerpo.get(i));

        // Si el If se encuentra dentro de la definición de una función, el Else asociado a él también
        
        if (si.definicion != null) si.sino.definicion = si.definicion;

        visit(si.sino);
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

        // Sólo se puede poner un menos antes de un número, un identificador de variable o una llamada a una función

        if (! (mm.valor instanceof Funcion || mm.valor instanceof VarRef || mm.valor instanceof Num))

            System.out.println ("\nError en la fase de identificación de tipos: no se puede utilizar el operador '-' con un objeto de tipo " + mm.valor.toString());
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

        if (ambito == Ambito.LOCAL) {

            if (tablaVblesLocal.get(param.nombre) != null)

                System.out.println("\nParámetro ya definido: " + param.nombre);

            else {

                // Almacenarlo en la tabla de variables locales

                param.tipo = (Tipo) visit(param.tipo);

                tablaVblesLocal.put(param.nombre, param);
            }
        }

        // No tiene sentido buscar un parámetro propio de una función en la tabla de variables globales
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

    public Tipo visit(TipoArray t) {

        t.tipo = (Tipo) visit(t.tipo);
        
        return t;
    }

    public Tipo visit(TipoChar t) {

        return t;
    }

    public Tipo visit(TipoInt t) {

        return t;
    }

    public Tipo visit(TipoReal t) {

        return t;
    }

    public Tipo visit(TipoRegistro t) {

        for (int i = 0; i < t.atribs.size(); i++) 

            visit(t.atribs.get(i));

        return t;
    }

    public Tipo visit(TipoUsuario t) {

        Tipo tipo;

        // Buscar su definición según en qué ámbito estemos

        if (ambito == Ambito.LOCAL) {

            tipo = tablaTiposLocal.get(t.nombre);

            if(tipo == null)

                // Puede ser un tipo global aunque estemos trabajando en ámbito local

                tipo = tablaTiposGlobal.get(t.nombre);
        }

        else

            // Ámbito global

            tipo = tablaTiposGlobal.get(t.nombre);

        if (tipo == null)

            System.out.println("\nTipo no definido: " + t.nombre);

        return tipo;
    }

    public void visit(VarRef refVar) {

        DefVar def;

        // Buscar su definición según en qué ámbito estemos

        if (ambito == Ambito.LOCAL) {

            def = (DefVar) tablaVblesLocal.get(refVar.nombre);

            if(def == null)

                // Puede ser una variable global aunque estemos trabajando en ámbito local
                
                def = (DefVar) tablaVblesGlobal.get(refVar.nombre);
        }

        else

            // Ámbito global

            def = (DefVar) tablaVblesGlobal.get(refVar.nombre);

        if(def == null)
        
            System.out.println("\nVariable no definida: " + refVar.nombre);

        // Enlazar con su definición
        
        refVar.definicion = def;
    }

    public void visit(While w) {

        visit(w.condiciones);

        for (int i = 0; i < w.cuerpo.size(); i++) 

            visit(w.cuerpo.get(i));
    }
}
