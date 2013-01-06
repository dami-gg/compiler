package Introspeccion;

import java.lang.reflect.*;
import java.util.*;

public class DefaultVisitor {
    
    private Class nodesType;
    
    /**
     * Si se le pasa el tipo base de los nodos (normalmente llamado AST) ofrece
     * una visita por defecto para aquellos que no tengan un visit expl�cito
     * consistente en visitar todos sus hijos que sean nodos o List.
     * En caso de visitar un objeto que no tiene visit expl�cito y adem�s
     * no es un nodo (o bien no se ha indicado el tipo de los nodos por haber
     * usado el constructor sin par�metros) entonces se produce un mensaje de
     * error avisando de que falta un visit explicito
     */
    
    public DefaultVisitor() {
	nodesType = null;
    }
    
    public DefaultVisitor(String nodesTypeName) throws ClassNotFoundException {
	this.nodesType = Class.forName(nodesTypeName);
    }
    
    public DefaultVisitor(Class nodesType) {
	this.nodesType = nodesType;
    }
    
    // ----------------------------------
    
    public Object visit(Object node) {
	return visit("visit", node);
    }
    
    public void visit(ArrayList list) {
	visit("visit", list);
    }
    
    public void visitChildren(Object node) {
	visitChildren("visit", node);
    }
    
    public Object visit(String methodName, Object node) {
	try {
	    Method method = getClass().getMethod(methodName, new Class[] { node.getClass() });
	    return method.invoke(this, new Object[] { node });
	} catch (Exception e) {
	    if (nodesType != null && nodesType.isInstance(node))
		visitChildren(methodName, node);
	    else
		System.out.println(getClass().getName() +  ". Error al buscar un m�todo visit para " + node.getClass() +'\n' +e);
	    return null;
	}
    }
    
    public void visit(String methodName, List l) {
	for (int i =0; i<l.size(); i++)
	    visit(methodName, l.get(i));
    }
    
    /**
     * visitChildren visita cada uno de los hijos del nodo que sea AST o List.
     *  Se utiliza cuando para un nodo no se ha definido un visit explicito.
     *  Lo puede utilizar tambi�n un m�todo visit expl�cito que, adem�s de
     *  hacer otra cosa, quiera visitar cada uno de sus hijos sin tener que
     *  repetir este c�digo.
     *  Si se ha usado un constructor con par�metro (visita por defecto) pero
     *  para un nodo no se quiere visitar sus hijos basta con crear un m�todo
     *  visit vacio para dicho nodo
     */
    public void visitChildren(String methodName, Object node) {
	Field[] fields = node.getClass().getDeclaredFields();
	for (int i =0; i < fields.length; i++) {
	    try {
		if (Modifier.isStatic(fields[i].getModifiers()))
		    continue;
		fields[i].setAccessible(true);
		Object child = fields[i].get(node);
		if (nodesType.isInstance(child) || List.class.isInstance(child))
		    visit(methodName, child);
	    } catch(IllegalAccessException e) {
		System.out.println("Visitor "+ getClass().getName() + ": " + e);
	    }
	}
    }
    
}