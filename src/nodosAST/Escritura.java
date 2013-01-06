package nodosAST;

import java.util.*;

public class Escritura extends Sentencia {
    
    public List expresiones;
    
    public Escritura (List e) {

        expresiones = e;
    }

    public Escritura (Object e) {

        expresiones = (List) e;
    }

    public String toString() {
	
        return "Escritura de " + expresiones;
    }
}

