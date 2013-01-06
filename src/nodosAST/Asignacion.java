package nodosAST;

public class Asignacion extends Sentencia {
    
    public Expr izq;

    public Expr der;
    
    public Asignacion(Expr i, Expr d) {

        izq = i;

        der = d;
    }

    public Asignacion(Object i, Object d) {

        izq = (Expr) i;

        der = (Expr) d;
    }
    
    public String toString() {
	
        return izq + " = " + der;
    }
}

