package Interprete;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Introspeccion.*;
import java.util.*;
import Interprete.Instrucciones.*;
import java.io.*;

/**
 * Clase que implementa la fase de generación de código
 * @author Dami
 */
public class Interprete extends DefaultVisitor {

    private Contexto cntxt;

    private ArrayList codigo;

    private FileWriter debug;

    public Interprete (String fichero) {
        
        try {

            cntxt = new Contexto();

            codigo = new ArrayList();

            debug = new FileWriter (fichero);
        }

        catch (Exception e) {

            System.out.println("\nError en constructor (Intérprete): " + e.getMessage());
        }
    }

    public Interprete (Contexto c, String fichero) {
        
        try {

            cntxt = new Contexto (c);

            codigo = new ArrayList();

            debug = new FileWriter (fichero);
        }

        catch (Exception e) {

            System.out.println("\nError en constructor (Intérprete): " + e.getMessage());
        }
    }

    /**
     * Inicia el intérprete
     */
    public void Interpretar () {

        try {

            Instruccion inst = (Instruccion) codigo.get(cntxt.IP);

            while (! (inst instanceof HALT)) {

                inst.exec(cntxt);
                cntxt.IP++;

                inst = (Instruccion) codigo.get(cntxt.IP);
            }
        }

        catch (Exception e) {

            System.out.println("\n\nError en Interpretar (Intérprete): " + e.getMessage());
        }
    }

    /**
     * Añade una nueva instrucción al codigo que deberá interpretarse posteriormente
     * @param inst
     */
    public void anadir (Instruccion inst) {

        codigo.add (inst);

        // Si es una etiqueta se almacena en la tabla de símbolos

        if (inst instanceof Etiqueta)

            cntxt.AnadirEtiqueta(((Etiqueta) inst).verEtiqueta(), codigo.size()-1);

        // Y si es una variable global se almacena en memoria

        else if (inst instanceof VariableGlobal)

            inst.exec (cntxt);
    }

    /**
     * Almacena el código máquina en un fichero con el fin de facilitar la depuración del intérprete
     */
    public void Depurar () {

        try {

            for (int i = 0; i < codigo.size(); i++)

                debug.write (((Instruccion) codigo.get(i)).getID());

            debug.close();
        }

        catch (Exception e) {

            System.out.println("\nError en Depurar (Intérprete): " + e.getMessage());
        }
    }
}
