package nodosAST;

import java.util.*;

public class Lectura extends Sentencia {
    
    public List expresiones;
    
    public Lectura (List e) {

        expresiones = e;
    }

    public Lectura (Object e) {

        expresiones = (List) e;
    }
    
    public String toString() {
	
        return "Lectura de " + expresiones;
    }
}

