/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interprete;

/**
 *
 * @author Dami
 */
public abstract class Instruccion {

    public abstract void exec (Contexto c);

    public abstract String getID();
}
