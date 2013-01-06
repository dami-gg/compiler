/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dami
 */


import GeneracionCodigo.FaseSeleccionMemoria;
import GeneracionCodigo.FaseGeneracionCodigo;
import Semantico.FaseIdentificacionTipos;
import Semantico.FaseInferenciaTipos;
import java.io.*;
import nodosAST.*;
import Interprete.*;

public class Main {

    public static void main(String[] args) {

        try {

            System.out.print ("\n--- Introduce el fichero que contiene el programa a ejecutar\n(Introducir ProgramaDemostracion.txt para ver una demostración): ");
            
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader buffer = new BufferedReader (input);
            
            String programa = buffer.readLine();
            
            File file = new File ("src/Entrada/" + programa); // Luego cambiar / por \\ pa entregar
        
            if (! (file.exists())) {
            
                System.out.println("\n\nEl fichero indicado no existe en la ruta /src/Entrada");
                return;
            }

            Yylex lex = new Yylex (new FileReader("src/Entrada/" + programa));

            Parser parser = new Parser (lex, false);

            /* System.out.println("--- ANÁLISIS LÉXICO ---");

            int token;

            while ((token = lex.yylex()) != 0)

                System.out.println("[" +  lex.line()+ "]" + "Token: " + token + " Lexema: " + lex.lexeme());
            */
            
            System.out.println("\n\n--- ANÁLISIS SINTÁCTICO ---");

            if (parser.yyparse() != 0) {

                System.out.println("\nPrograma no válido");

                return;
            }

            else

                System.out.println("\nPrograma correcto sintácticamente");

            AST root = parser.getAST();

            // showTree (root);

            System.out.println("\n--- ANÁLISIS SEMÁNTICO - FASE DE IDENTIFICACIÓN DE TIPOS ---");

            FaseIdentificacionTipos fIdent = new FaseIdentificacionTipos();
            fIdent.visit(root);

            System.out.println("\n--- ANÁLISIS SEMÁNTICO - FASE DE INFERENCIA DE TIPOS ---");

            FaseInferenciaTipos fInfer = new FaseInferenciaTipos();
            fInfer.visit(root);

            System.out.println("\n--- GENERACIÓN DE CÓDIGO - FASE DE SELECCIÓN DE MEMORIA ---");

            FaseSeleccionMemoria fAsig = new FaseSeleccionMemoria();
            fAsig.visit(root);

            Interprete interprete = new Interprete ("src/Salida/" + programa);

            System.out.println("\n--- GENERACIÓN DE CÓDIGO - FASE DE GENERACIÓN DE CÓDIGO ---");

            FaseGeneracionCodigo fGen = new FaseGeneracionCodigo (interprete);
            fGen.visit("run", root);

            System.out.println("\n--- INTÉRPRETE ---\n");
            interprete.Interpretar();
            interprete.Depurar();

            System.out.println("\n");
        }

        catch (IOException e) {

            System.out.println(e);
        }
    }

    /*
    public static void showTree (Object root) {

        IntrospectorModel modelo = new IntrospectorModel("Raíz", root);

        new IntrospectorTree("Introspector", modelo);
    }
    */
}

