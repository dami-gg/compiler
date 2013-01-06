package Interprete;

import Interprete.Instrucciones.*;
import nodosAST.*;

public class RepresentacionIntermedia {

    protected Interprete programa;

    public RepresentacionIntermedia (Interprete p) {

        programa = p;
    }

    public void add (Tipo t) {

        if (t instanceof TipoInt)

            addi();

        else

            addf();
    }
    
    private void addf() {
        
        programa.anadir (new ADDF());
    }
    
    private void addi() {
        
        programa.anadir (new ADDI());
    }
    
    public void and() {
        
        programa.anadir (new AND());
    }

    public void b2i() {

        programa.anadir (new B2I());
    }

    public void call (String etiq) {

        programa.anadir (new CALL(etiq));
    }

    public void comentario (String t) {

        programa.anadir (new Comentario(t));
    }

    public void div (Tipo t) {

        if (t instanceof TipoInt)

            divi();

        else

            divf();
    }
    
    private void divf() {

        programa.anadir (new DIVF());
    }

    private void divi() {

        programa.anadir (new DIVI());
    }

    public void dupb() {

        programa.anadir (new DUPB());
    }

    public void dupf() {

        programa.anadir (new DUPF());
    }

    public void dupi() {

        programa.anadir (new DUPI());
    }

    public void enter (int cte) {

        programa.anadir (new ENTER(cte));
    }

    public void eq (Tipo t) {

        if (t instanceof TipoInt)

            eqi();

        else

            eqf();
    }
    
    private void eqf() {

        programa.anadir (new EQF());
    }

    private void eqi() {

        programa.anadir (new EQI());
    }

    public void etiqueta (String e) {

        programa.anadir (new Etiqueta(e));
    }

    public void f2i() {

        programa.anadir (new F2I());
    }

    public void ge (Tipo t) {

        if (t instanceof TipoInt)

            gei();

        else

            gef();
    }

    private void gef() {

        programa.anadir (new GEF());
    }

    private void gei() {

        programa.anadir (new GEI());
    }

    public void gt (Tipo t) {

        if (t instanceof TipoInt)

            gti();

        else

            gtf();
    }

    private void gtf() {

        programa.anadir (new GTF());
    }

    private void gti() {

        programa.anadir (new GTI());
    }

    public void halt() {

        programa.anadir (new HALT());
    }

    public void i2b() {

        programa.anadir (new I2B());
    }

    public void i2f() {

        programa.anadir (new I2F());
    }

    public void in (Tipo t) {

        if (t instanceof TipoChar)

            inb();

        else if (t instanceof TipoInt)

            ini();

        else

            inf();
    }

    private void inb() {

        programa.anadir (new INB());
    }

    private void inf() {

        programa.anadir (new INF());
    }

    private void ini() {

        programa.anadir (new INI());
    }

    public void jmp (String etiq) {

        programa.anadir (new JMP (etiq));
    }

    public void jz (String etiq) {

        programa.anadir (new JZ (etiq));
    }

    public void jnz (String etiq) {

        programa.anadir (new JNZ (etiq));
    }

    public void le (Tipo t) {

        if (t instanceof TipoInt)

            lei();

        else

            lef();
    }
    
    private void lef() {

        programa.anadir (new LEF());
    }

    private void lei() {

        programa.anadir (new LEI());
    }

    public void load (Tipo t) {

        if (t instanceof TipoChar)

            loadb();

        else if (t instanceof TipoInt)

            loadi();

        else

            loadf();
    }
    
    private void loadb() {

        programa.anadir (new LOADB());
    }

    private void loadf() {

        programa.anadir (new LOADF());
    }

    private void loadi() {

        programa.anadir (new LOADI());
    }

    public void lt (Tipo t) {

        if (t instanceof TipoInt)

            lti();

        else

            ltf();
    }
    
    private void ltf() {

        programa.anadir (new LTF());
    }

    private void lti() {

        programa.anadir (new LTI());
    }

    public void ne (Tipo t) {

        if (t instanceof TipoInt)

            nei();

        else

            nef();
    }

    private void nef() {

        programa.anadir (new NEF());
    }

    private void nei() {

        programa.anadir (new NEI());
    }

    public void not() {

        programa.anadir (new NOT());
    }

    public void or() {

        programa.anadir (new OR());
    }

    public void mod (Tipo t) {

        if (t instanceof TipoInt)

            modi();

        else

            modf();
    }

    private void modf() {

        programa.anadir (new MODF());
    }

    private void modi() {

        programa.anadir (new MODI());
    }

    public void mul (Tipo t) {

        if (t instanceof TipoInt)

            muli();

        else

            mulf();
    }
    
    private void mulf() {

        programa.anadir (new MULF());
    }

    private void muli() {

        programa.anadir (new MULI());
    }

    public void pop (Tipo t) {

        if (t instanceof TipoChar)

            popb();

        else if (t instanceof TipoInt)

            popi();

        else

            popf();
    }

    private void popb () {

        programa.anadir (new POPB());
    }

    public void popbp () {

        programa.anadir (new POPBP());
    }

    private void popf () {

        programa.anadir (new POPF());
    }

    private void popi () {

        programa.anadir (new POPI());
    }

    public void popsp () {

        programa.anadir (new POPSP());
    }

    public void push (Tipo t, float valor) {

        if (t instanceof TipoChar)

            pushb ((char) valor);

        else if (t instanceof TipoInt)

            pushi ((int) valor);

        else

            pushf (valor);
    }

    public void pusha (String dir) {

        programa.anadir (new PUSHA (dir));
    }

    private void pushb (char valor) {

        programa.anadir (new PUSHB ((byte) valor));
    }

    public void pushbp () {

        programa.anadir (new PUSHBP ());
    }

    private void pushf (float valor) {

        programa.anadir (new PUSHF (valor));
    }

    private void pushi (int valor) {

        programa.anadir (new PUSHI (valor));
    }

    public void pushsp () {

        programa.anadir (new PUSHSP ());
    }

    public void ret (int c1, int c2, int c3) {

        programa.anadir (new RET (c1, c2, c3));
    }

    public void sub (Tipo t) {

        if (t instanceof TipoInt)

            subi();

        else

            subf();
    }

    private void subf() {

        programa.anadir (new SUBF());
    }

    private void subi() {

        programa.anadir (new SUBI());
    }

    public void out (Tipo t) {

        if (t instanceof TipoChar)

            outb();

        else if (t instanceof TipoInt)

            outi();

        else

            outf();
    }

    private void outb() {

        programa.anadir (new OUTB());
    }
    
    private void outf() {

        programa.anadir (new OUTF());
    }

    private void outi() {

        programa.anadir (new OUTI());
    }

    public void store (Tipo t) {

        if (t instanceof TipoChar)

            storeb();

        else if (t instanceof TipoInt)

            storei();

        else

            storef();
    }

    private void storeb() {

        programa.anadir (new STOREB());
    }

    private void storef() {

        programa.anadir (new STOREF());
    }

    private void storei() {

        programa.anadir (new STOREI());
    }

    public void variableglobal (DefVar def) {

        programa.anadir (new VariableGlobal (def));
    }

    public void compruebaRangoArray (int tam) {

        programa.anadir (new CompruebaRangoArray (tam));
    }
}