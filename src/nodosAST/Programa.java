package nodosAST;

import java.util.*;

public class Programa extends AST {

    public List declaraciones;
    
    public Programa (List lista) {

        declaraciones = lista;
    }

    public Programa (Object d) {

        declaraciones = (List) d;
    }

    public String toString() {
	
        return "Programa con " + declaraciones.size() + " declaraciones:\n" + declaraciones.toString();
    }
}

