

//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 19 "sintac.y"
import nodosAST.*;
import java.util.*;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short OR=257;
public final static short AND=258;
public final static short IGUALIGUAL=259;
public final static short DISTINTO=260;
public final static short MAYOROIGUAL=261;
public final static short MENOROIGUAL=262;
public final static short NUM=263;
public final static short CARACTER=264;
public final static short IDENT=265;
public final static short WHILE=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short WRITE=269;
public final static short READ=270;
public final static short RETURN=271;
public final static short RECORD=272;
public final static short INT=273;
public final static short REAL=274;
public final static short DOUBLE=275;
public final static short CHAR=276;
public final static short BYTE=277;
public final static short VAR=278;
public final static short TYPE=279;
public final static short CAST=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    4,    7,    7,
    5,    8,    8,    9,    9,    9,    9,    9,    9,    9,
   11,   12,   12,   10,   13,   13,    6,   16,   16,   17,
   17,   15,   15,   19,   19,   18,   18,   20,   20,   20,
   20,   20,   20,   20,   21,   24,   25,   26,   22,   23,
   29,   29,   28,   28,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   30,   27,   33,   33,   31,   31,
   31,   31,   31,   31,   31,   31,   31,   31,   31,   31,
   31,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,    0,    2,    1,    1,    4,    1,    3,
    5,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    5,    0,    2,    4,    3,    9,    2,    0,    3,
    0,    1,    0,    3,    5,    2,    0,    2,    1,    1,
    2,    2,    2,    2,    3,    2,    2,    2,    7,    8,
    4,    0,    1,    3,    1,    1,    1,    1,    3,    4,
    3,    1,    1,    1,    7,    4,    1,    0,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,
};
final static short yydefred[] = {                         4,
    0,    1,    0,    0,    0,    0,    3,    0,    6,    7,
    0,    9,    0,    0,    5,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   20,    0,   14,   15,   16,
   17,   18,    8,   12,   13,   19,    0,   10,    0,   34,
    0,    0,    0,    0,    0,   55,   56,    0,    0,    0,
    0,   58,   62,   63,   64,   23,    0,   24,   11,   28,
   31,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   26,    0,    0,    0,   35,    0,    0,    0,
    0,   59,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   61,    0,   21,   25,
    0,    0,    0,   66,    0,   60,    0,   30,    0,    0,
    0,    0,    0,   27,    0,   36,    0,   39,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   38,   41,   42,   43,   44,    0,   22,    0,
    0,    0,   65,    0,    0,   37,   37,    0,    0,   49,
    0,    0,   50,   37,    0,   51,
};
final static short yydgoto[] = {                          1,
    2,    3,    7,    8,    9,   10,   13,   33,   34,   35,
   36,   84,   37,  125,   17,   42,   86,  112,   18,  126,
  127,  128,  129,  130,  131,  132,   52,   89,  163,   53,
   54,   55,   90,
};
final static short yysindex[] = {                         0,
    0,    0, -245,  -37, -260, -257,    0,  -20,    0,    0,
 -221,    0,    1,   -8,    0,   -7,   11,   13,  374, -210,
  374,  374,    4, -200,  -23,    0,  -56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  483,    0,   16,    0,
  374,  -41,   25,  -23,  -23,    0,    0,   45,   27,  -23,
  252,    0,    0,    0,    0,    0,  -23,    0,    0,    0,
    0,  374,   31,  -44,  -23,  374,  336,  -23,  -23,  -23,
  -23,  -23,  -23,  -23,  -23,  -23,  -23,  -23,  -23,  -23,
  -23, -174,    0, -112,  368, -185,    0,  485,   50,   54,
   34,    0,  511,  518,  635,  635,   -5,   -5,   -5,   -5,
   31,   31,  -44,  -44,  -44,  375,    0,   40,    0,    0,
   41,  -33,  -23,    0,   59,    0,  374,    0,   61,   63,
  -23,  -23,  -23,    0,  216,    0,   46,    0,    0,   47,
   48,   49,   53,  485,  -23,   62,  -23,  -23,   50,   50,
  485,  -23,    0,    0,    0,    0,    0,  402,    0,  409,
  442,  485,    0,  -19,   -3,    0,    0,  -15,    3,    0,
 -145,    2,    0,    0,   21,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  124,    0,    0,    0,    0,    0,    0,    0,
   85,    0,    0,    0,    0,    0,    0,   86,    0,    0,
    0,    0,    6,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   72,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  545,   98,   89,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,    5,   95,    0,
    0,    0,  365,  -30,  -40,  -35,  559,  749,  771,  784,
  648,  670,  107,  133,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  478,   30,    0,    0,    0,    0,   78,   79,
   88,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   96,    0,    0,    0,    0,    0,    0,    0,    0,
   57,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   70,    0,    0,    0,   -6,  125,    0,
    0,    0,    0,  813,    0,    0,    0,  -76,    0,    0,
    0,    0,    0,    0,    0,    0,  -89,  -94,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=1046;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
   78,   82,   11,   78,   12,   79,   50,   14,   79,   45,
   76,   44,  109,   76,   39,   40,   50,   45,   78,    4,
   78,   44,  133,   79,   50,   79,  139,  140,   76,   44,
   76,   80,    5,    6,   60,   45,   78,   76,   15,   77,
   82,   79,   50,   16,   20,   53,   81,   44,   53,   21,
   22,   23,   78,   45,   38,   87,   24,   79,   19,   91,
   50,   41,   76,   53,   43,   44,   56,   80,  133,  133,
   54,   37,   78,   54,   59,  133,   82,   79,   37,  158,
  159,   61,   62,   37,   65,   81,   66,  165,   54,   52,
  107,  124,    5,  113,  114,  115,   52,  117,  135,  118,
  137,   52,  138,  156,  143,  144,  145,  146,   57,  160,
  136,  147,   57,   57,   57,   57,   57,   57,   57,  157,
  149,   81,  162,    2,  164,   33,   32,  161,   29,   68,
   57,   57,   57,   57,   82,   67,   48,   47,   82,   82,
   82,   82,   82,   71,   82,  166,   46,   71,   71,   71,
   71,   71,  108,   71,   45,  111,   82,   82,   82,   82,
    0,   58,   57,   37,   57,   71,   71,   71,   71,   72,
    0,    0,    0,   72,   72,   72,   72,   72,   73,   72,
    0,   52,   73,   73,   73,   73,   73,    0,   73,    0,
   82,   72,   72,   72,   72,    0,    0,    0,    0,   71,
   73,   73,   73,   73,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   78,   78,   78,   78,
    0,   79,   79,   79,   79,   72,   76,   76,    0,   46,
   47,   48,  119,  120,   73,  121,  122,  123,    0,   46,
   47,   48,    0,    0,    0,    0,   49,   46,   47,   48,
  119,  120,   80,  121,  122,  123,   49,   78,   76,    0,
   77,   82,   79,    0,   49,   46,   47,   48,  119,  120,
    0,  121,  122,  123,    0,   73,  142,   72,    0,    0,
    0,    0,   49,   46,   47,   48,  119,  120,   80,  121,
  122,  123,    0,   78,   76,    0,   77,   82,   79,    0,
   49,   37,   37,   37,   37,   37,   81,   37,   37,   37,
    0,   73,    0,   72,    0,    0,    0,    0,   37,   52,
   52,   52,   52,   52,    0,   52,   52,   52,   57,   57,
   57,   57,   57,   57,    0,    0,   52,    0,    0,    0,
    0,    0,   81,    0,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   82,   82,   82,   82,   82,   82,
    0,    0,    0,   71,   71,   71,   71,   71,   71,    0,
    0,    0,   80,    0,    0,    0,   92,   78,   76,    0,
   77,   82,   79,    0,    0,    0,    0,    0,    0,   72,
   72,   72,   72,   72,   72,   73,    0,   72,   73,   73,
   73,   73,   73,   73,   80,   77,    0,    0,   77,   78,
   76,   80,   77,   82,   79,    0,   78,   76,    0,   77,
   82,   79,    0,   77,    0,   77,   81,   73,    0,   72,
    0,    0,    0,    0,   73,    0,   72,    0,   80,    0,
    0,    0,  153,   78,   76,   80,   77,   82,   79,  154,
   78,   76,    0,   77,   82,   79,    0,   77,   81,    0,
  110,   73,    0,   72,   25,   81,    0,  116,   73,    0,
   72,    0,   68,   69,   70,   71,   74,   75,   80,    0,
    0,    0,  155,   78,   76,    0,   77,   82,   79,    0,
    0,    0,   81,    0,    0,    0,    0,    0,    0,   81,
    0,   73,    0,   72,    0,    0,    0,    0,   68,   69,
   70,   71,   74,   75,   58,    0,    0,    0,    0,   58,
   58,   80,   58,   58,   58,    0,   78,   76,    0,   77,
   82,   79,   81,    0,    0,    0,    0,   58,   58,   58,
    0,    0,    0,    0,   73,    0,   72,   80,    0,    0,
    0,    0,   78,   76,   80,   77,   82,   79,    0,   78,
   76,    0,   77,   82,   79,    0,    0,    0,   58,    0,
   73,    0,   72,   57,    0,   81,    0,   73,    0,   72,
    0,    0,    0,    0,    0,   83,    0,   83,   83,   83,
    0,    0,   68,   69,   70,   71,   74,   75,    0,   75,
    0,   81,   75,   83,   83,   83,   83,    0,   81,    0,
    0,    0,    0,    0,    0,    0,    0,   75,   75,   75,
   75,   77,    0,    0,   68,   69,   70,   71,   74,   75,
    0,   68,   69,   70,   71,   74,   75,   83,   26,    0,
    0,    0,    0,    0,    0,   27,   28,   29,   30,   31,
   32,   75,    0,    0,    0,    0,    0,    0,   68,   69,
   70,   71,   74,   75,    0,   68,   69,   70,   71,   74,
   75,   80,    0,    0,    0,    0,   78,   76,    0,   77,
   82,   79,    0,    0,    0,    0,    0,    0,   69,    0,
   69,   69,   69,    0,   73,    0,   72,    0,   68,   69,
   70,   71,   74,   75,    0,    0,   69,   69,   69,   69,
   70,    0,   70,   70,   70,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   81,    0,    0,   70,   70,
   70,   70,    0,    0,   58,   58,   58,   58,   58,   58,
   69,   68,   69,   70,   71,   74,   75,   26,    0,    0,
    0,    0,    0,    0,   27,   28,   29,   30,   31,   32,
    0,    0,   70,    0,    0,    0,    0,    0,   69,   70,
   71,   74,   75,    0,    0,    0,   70,   71,   74,   75,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   74,
    0,    0,   74,    0,    0,    0,    0,    0,    0,    0,
    0,   83,   83,   83,   83,   83,   83,   74,   74,   74,
   74,   80,    0,    0,   80,   75,   75,   75,   75,   75,
   75,    0,    0,    0,   81,    0,    0,   81,    0,   80,
   80,   80,   80,    0,    0,    0,    0,   51,    0,    0,
    0,   74,   81,   81,   81,   81,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   63,   64,    0,    0,
    0,    0,   67,   80,    0,    0,    0,    0,    0,   85,
    0,    0,    0,    0,    0,    0,   81,   88,    0,    0,
   93,   94,   95,   96,   97,   98,   99,  100,  101,  102,
  103,  104,  105,  106,    0,   74,   75,    0,    0,    0,
    0,    0,    0,    0,   69,   69,   69,   69,   69,   69,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  134,   70,   70,   70,   70,
   70,   70,    0,   88,   88,  141,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  148,    0,  150,
  151,    0,    0,    0,  152,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   74,   74,   74,   74,   74,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,   80,   80,
   80,   80,   80,    0,    0,    0,    0,    0,    0,    0,
   81,   81,   81,   81,   81,   81,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   41,   46,   40,   44,  265,   41,   40,  265,   44,   33,
   41,   45,  125,   44,   21,   22,   40,   33,   59,  265,
   61,   45,  112,   59,   40,   61,  121,  122,   59,   45,
   61,   37,  278,  279,   41,   33,   42,   43,   59,   45,
   46,   47,   40,  265,   44,   41,   91,   45,   44,   58,
   58,   41,   93,   33,  265,   62,   44,   93,   58,   66,
   40,   58,   93,   59,  265,   45,  123,   37,  158,  159,
   41,   33,   42,   44,   59,  165,   46,   47,   40,  156,
  157,  123,   58,   45,   40,   91,   60,  164,   59,   33,
  265,  125,  278,   44,   41,   62,   40,   58,   40,   59,
   40,   45,   40,  123,   59,   59,   59,   59,   37,  125,
  117,   59,   41,   42,   43,   44,   45,   46,   47,  123,
   59,   91,  268,    0,  123,   41,   41,  125,  123,   41,
   59,   60,   61,   62,   37,   41,   59,   59,   41,   42,
   43,   44,   45,   37,   47,  125,   59,   41,   42,   43,
   44,   45,  265,   47,   59,   86,   59,   60,   61,   62,
   -1,   37,   91,  125,   93,   59,   60,   61,   62,   37,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,   47,
   -1,  125,   41,   42,   43,   44,   45,   -1,   47,   -1,
   93,   59,   60,   61,   62,   -1,   -1,   -1,   -1,   93,
   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
   -1,  257,  258,  259,  260,   93,  257,  258,   -1,  263,
  264,  265,  266,  267,   93,  269,  270,  271,   -1,  263,
  264,  265,   -1,   -1,   -1,   -1,  280,  263,  264,  265,
  266,  267,   37,  269,  270,  271,  280,   42,   43,   -1,
   45,   46,   47,   -1,  280,  263,  264,  265,  266,  267,
   -1,  269,  270,  271,   -1,   60,   61,   62,   -1,   -1,
   -1,   -1,  280,  263,  264,  265,  266,  267,   37,  269,
  270,  271,   -1,   42,   43,   -1,   45,   46,   47,   -1,
  280,  263,  264,  265,  266,  267,   91,  269,  270,  271,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,  280,  263,
  264,  265,  266,  267,   -1,  269,  270,  271,  257,  258,
  259,  260,  261,  262,   -1,   -1,  280,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   60,   -1,   62,  257,  258,
  259,  260,  261,  262,   37,   41,   -1,   -1,   44,   42,
   43,   37,   45,   46,   47,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   59,   -1,   61,   91,   60,   -1,   62,
   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,   37,   -1,
   -1,   -1,   41,   42,   43,   37,   45,   46,   47,   41,
   42,   43,   -1,   45,   46,   47,   -1,   93,   91,   -1,
   93,   60,   -1,   62,   91,   91,   -1,   93,   60,   -1,
   62,   -1,  257,  258,  259,  260,  261,  262,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   37,   -1,   -1,   -1,   -1,   42,
   43,   37,   45,   46,   47,   -1,   42,   43,   -1,   45,
   46,   47,   91,   -1,   -1,   -1,   -1,   60,   61,   62,
   -1,   -1,   -1,   -1,   60,   -1,   62,   37,   -1,   -1,
   -1,   -1,   42,   43,   37,   45,   46,   47,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   91,   -1,
   60,   -1,   62,   91,   -1,   91,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   41,   -1,   43,   44,   45,
   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,   41,
   -1,   91,   44,   59,   60,   61,   62,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   61,
   62,  257,   -1,   -1,  257,  258,  259,  260,  261,  262,
   -1,  257,  258,  259,  260,  261,  262,   93,  265,   -1,
   -1,   -1,   -1,   -1,   -1,  272,  273,  274,  275,  276,
  277,   93,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,  257,  258,  259,  260,  261,
  262,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,
   43,   44,   45,   -1,   60,   -1,   62,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,   59,   60,   61,   62,
   41,   -1,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   59,   60,
   61,   62,   -1,   -1,  257,  258,  259,  260,  261,  262,
   93,  257,  258,  259,  260,  261,  262,  265,   -1,   -1,
   -1,   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,
   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,  258,  259,
  260,  261,  262,   -1,   -1,   -1,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   59,   60,   61,
   62,   41,   -1,   -1,   44,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,   25,   -1,   -1,
   -1,   93,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   44,   45,   -1,   -1,
   -1,   -1,   50,   93,   -1,   -1,   -1,   -1,   -1,   57,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   65,   -1,   -1,
   68,   69,   70,   71,   72,   73,   74,   75,   76,   77,
   78,   79,   80,   81,   -1,  261,  262,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  113,  257,  258,  259,  260,
  261,  262,   -1,  121,  122,  123,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  135,   -1,  137,
  138,   -1,   -1,   -1,  142,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"OR","AND","IGUALIGUAL","DISTINTO",
"MAYOROIGUAL","MENOROIGUAL","NUM","CARACTER","IDENT","WHILE","IF","ELSE",
"WRITE","READ","RETURN","RECORD","INT","REAL","DOUBLE","CHAR","BYTE","VAR",
"TYPE","CAST",
};
final static String yyrule[] = {
"$accept : s",
"s : programa",
"programa : declaraciones",
"declaraciones : declaraciones declaracion",
"declaraciones :",
"declaracion : defVar ';'",
"declaracion : defTipo",
"declaracion : defFuncion",
"defVar : VAR listaIdents ':' tipo",
"listaIdents : IDENT",
"listaIdents : listaIdents ',' IDENT",
"defTipo : TYPE IDENT ':' tipo ';'",
"tipo : tipoPrimitivo",
"tipo : array",
"tipoPrimitivo : INT",
"tipoPrimitivo : REAL",
"tipoPrimitivo : DOUBLE",
"tipoPrimitivo : CHAR",
"tipoPrimitivo : BYTE",
"tipoPrimitivo : registro",
"tipoPrimitivo : IDENT",
"registro : RECORD '{' atributos '}'",
"atributos : atributos IDENT ':' tipo ';'",
"atributos :",
"array : dimension tipoPrimitivo",
"dimension : dimension '[' expr ']'",
"dimension : '[' expr ']'",
"defFuncion : IDENT '(' params ')' tipoRetorno '{' defVarLocal sentencias '}'",
"tipoRetorno : ':' tipo",
"tipoRetorno :",
"defVarLocal : defVarLocal defVar ';'",
"defVarLocal :",
"params : listaParams",
"params :",
"listaParams : IDENT ':' tipo",
"listaParams : listaParams ',' IDENT ':' tipo",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : asignacion ';'",
"sentencia : while",
"sentencia : if",
"sentencia : retorno ';'",
"sentencia : lectura ';'",
"sentencia : escritura ';'",
"sentencia : funcion ';'",
"asignacion : expr '=' expr",
"retorno : RETURN expr",
"lectura : READ listaValores",
"escritura : WRITE listaValores",
"while : WHILE '(' expr ')' '{' sentencias '}'",
"if : IF '(' expr ')' '{' sentencias '}' else",
"else : ELSE '{' sentencias '}'",
"else :",
"listaValores : expr",
"listaValores : listaValores ',' expr",
"expr : NUM",
"expr : CARACTER",
"expr : IDENT",
"expr : funcion",
"expr : '(' expr ')'",
"expr : expr '[' expr ']'",
"expr : expr '.' IDENT",
"expr : cast",
"expr : exprBinaria",
"expr : exprUnaria",
"cast : CAST '<' tipo '>' '(' expr ')'",
"funcion : IDENT '(' listaValoresOpt ')'",
"listaValoresOpt : listaValores",
"listaValoresOpt :",
"exprBinaria : expr '+' expr",
"exprBinaria : expr '-' expr",
"exprBinaria : expr '*' expr",
"exprBinaria : expr '/' expr",
"exprBinaria : expr '%' expr",
"exprBinaria : expr '<' expr",
"exprBinaria : expr '>' expr",
"exprBinaria : expr AND expr",
"exprBinaria : expr OR expr",
"exprBinaria : expr IGUALIGUAL expr",
"exprBinaria : expr DISTINTO expr",
"exprBinaria : expr MAYOROIGUAL expr",
"exprBinaria : expr MENOROIGUAL expr",
"exprUnaria : '!' expr",
"exprUnaria : '-' expr",
};

//#line 197 "sintac.y"


Yylex lex;
AST root;
int token;

Parser(Yylex lex, boolean debug) {
  this(debug);
  this.lex = lex;
}

// Funciones requeridas por el parser

void yyerror(String s)
{
    System.out.println("Error sintactico en " + lex.line() + ":" + lex.column() + " Token = " + token + " lexema = \"" + lex.lexeme()+"\"");
}

int yylex() {
  try {
	token = lex.yylex();
	yylval = lex.lexeme();
	return token;
  } catch (Exception e) {
    return -1;
  }
}

AST getAST() {
	return root;
}



//#line 574 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 36 "sintac.y"
{ root = (AST) val_peek(0);}
break;
case 2:
//#line 39 "sintac.y"
{ yyval = new Programa ((List) val_peek(0)); }
break;
case 3:
//#line 42 "sintac.y"
{ yyval = val_peek(1);
                                                for (int i=0; i < ((List) val_peek(0)).size(); i++)
                                                    ((List) yyval).add(((List) val_peek(0)).get(i));    }
break;
case 4:
//#line 45 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 5:
//#line 48 "sintac.y"
{ yyval = (List) val_peek(1); }
break;
case 6:
//#line 49 "sintac.y"
{ yyval = new ArrayList(); ((List) yyval).add((DefTipo) val_peek(0)); }
break;
case 7:
//#line 50 "sintac.y"
{ yyval = new ArrayList(); ((List) yyval).add((DefFuncion) val_peek(0)); }
break;
case 8:
//#line 53 "sintac.y"
{ yyval = new ArrayList();
                                           for (int i=0; i < ((List) val_peek(2)).size(); i++)
                                                ((List) yyval).add(new DefVar(((List) val_peek(2)).get(i), (Tipo) val_peek(0))); }
break;
case 9:
//#line 58 "sintac.y"
{ yyval = new ArrayList(); ((List) yyval).add(val_peek(0)); }
break;
case 10:
//#line 59 "sintac.y"
{ yyval = val_peek(2); ((List) yyval).add(val_peek(0)); }
break;
case 11:
//#line 62 "sintac.y"
{ yyval = new DefTipo(val_peek(3), (Tipo) val_peek(1)); }
break;
case 14:
//#line 69 "sintac.y"
{ yyval = new TipoInt(); }
break;
case 15:
//#line 70 "sintac.y"
{ yyval = new TipoReal(); }
break;
case 16:
//#line 71 "sintac.y"
{ yyval = new TipoReal(); }
break;
case 17:
//#line 72 "sintac.y"
{ yyval = new TipoChar(); }
break;
case 18:
//#line 73 "sintac.y"
{ yyval = new TipoChar(); }
break;
case 20:
//#line 75 "sintac.y"
{ yyval = new TipoUsuario(val_peek(0)); }
break;
case 21:
//#line 78 "sintac.y"
{ yyval = new TipoRegistro ((List) val_peek(1)); }
break;
case 22:
//#line 80 "sintac.y"
{ yyval = val_peek(4); ((List) yyval).add(new Atributo(val_peek(3), (Tipo) val_peek(1))); }
break;
case 23:
//#line 81 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 24:
//#line 84 "sintac.y"
{ TipoArray aux = new TipoArray ( ((List) val_peek(1)).get( ((List) val_peek(1)).size() - 1), (Tipo) val_peek(0) );
                                      for (int i=((List) val_peek(1)).size()-2; i>=0; i--)
                                           aux = new TipoArray ( ((List) val_peek(1)).get(i), (Tipo) aux);
                                      yyval = aux;                                                     }
break;
case 25:
//#line 90 "sintac.y"
{ yyval = val_peek(3); ((List) yyval).add(val_peek(1)); }
break;
case 26:
//#line 91 "sintac.y"
{ yyval = new ArrayList(); ((List) yyval).add(val_peek(1)); }
break;
case 27:
//#line 95 "sintac.y"
{ yyval = new DefFuncion(val_peek(8), (List) val_peek(6), (Tipo) val_peek(4), (List) val_peek(2), (List) val_peek(1)); }
break;
case 28:
//#line 98 "sintac.y"
{ yyval = val_peek(0); }
break;
case 29:
//#line 99 "sintac.y"
{ yyval = new TipoVoid(); }
break;
case 30:
//#line 102 "sintac.y"
{ yyval = val_peek(2);
                                          for(int i=0; i < ((List) val_peek(1)).size(); i++)
                                              ((List) yyval).add ( ((List) val_peek(1)).get(i));  }
break;
case 31:
//#line 105 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 33:
//#line 109 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 34:
//#line 112 "sintac.y"
{ yyval = new ArrayList(); ((List) yyval).add (new Parametro (val_peek(2), (Tipo) val_peek(0))); }
break;
case 35:
//#line 113 "sintac.y"
{ yyval = val_peek(4); ((List) yyval).add (new Parametro (val_peek(2), (Tipo) val_peek(0))); }
break;
case 36:
//#line 116 "sintac.y"
{ yyval = val_peek(1); ((List) yyval).add(val_peek(0)); }
break;
case 37:
//#line 117 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 45:
//#line 129 "sintac.y"
{ yyval = new Asignacion (val_peek(2),val_peek(0)); }
break;
case 46:
//#line 132 "sintac.y"
{ yyval = new Retorno (val_peek(0)); }
break;
case 47:
//#line 135 "sintac.y"
{ yyval = new Lectura ((List) val_peek(0)); }
break;
case 48:
//#line 138 "sintac.y"
{ yyval = new Escritura ((List) val_peek(0)); }
break;
case 49:
//#line 141 "sintac.y"
{ yyval = new While (val_peek(4), (List) val_peek(1)); }
break;
case 50:
//#line 144 "sintac.y"
{ yyval = new If (val_peek(5), (List) val_peek(2), (Else) val_peek(0)); }
break;
case 51:
//#line 147 "sintac.y"
{ yyval = new Else ((List) val_peek(1)); }
break;
case 52:
//#line 148 "sintac.y"
{ yyval = new Else (new ArrayList()); }
break;
case 53:
//#line 151 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); }
break;
case 54:
//#line 152 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
case 55:
//#line 155 "sintac.y"
{ yyval = new Num (val_peek(0)); }
break;
case 56:
//#line 156 "sintac.y"
{ yyval = new Caracter (val_peek(0)); }
break;
case 57:
//#line 157 "sintac.y"
{ yyval = new VarRef (val_peek(0)); }
break;
case 59:
//#line 159 "sintac.y"
{ yyval = val_peek(1); }
break;
case 60:
//#line 160 "sintac.y"
{ yyval = new AccesoArray (val_peek(3),val_peek(1)); }
break;
case 61:
//#line 161 "sintac.y"
{ yyval = new AccesoAtrib (val_peek(2),val_peek(0)); }
break;
case 65:
//#line 167 "sintac.y"
{ yyval = new Casting ((Tipo) val_peek(4), val_peek(1)); }
break;
case 66:
//#line 170 "sintac.y"
{ yyval = new Funcion (val_peek(3), (List) val_peek(1)); }
break;
case 68:
//#line 174 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 69:
//#line 177 "sintac.y"
{ yyval = new Suma (val_peek(2),val_peek(0)); }
break;
case 70:
//#line 178 "sintac.y"
{ yyval = new Resta (val_peek(2),val_peek(0)); }
break;
case 71:
//#line 179 "sintac.y"
{ yyval = new Producto (val_peek(2),val_peek(0)); }
break;
case 72:
//#line 180 "sintac.y"
{ yyval = new Cociente (val_peek(2),val_peek(0)); }
break;
case 73:
//#line 181 "sintac.y"
{ yyval = new Modulo (val_peek(2),val_peek(0)); }
break;
case 74:
//#line 182 "sintac.y"
{ yyval = new Menor (val_peek(2),val_peek(0)); }
break;
case 75:
//#line 183 "sintac.y"
{ yyval = new Mayor (val_peek(2),val_peek(0)); }
break;
case 76:
//#line 184 "sintac.y"
{ yyval = new And (val_peek(2),val_peek(0)); }
break;
case 77:
//#line 185 "sintac.y"
{ yyval = new Or (val_peek(2),val_peek(0)); }
break;
case 78:
//#line 186 "sintac.y"
{ yyval = new IgualIgual (val_peek(2),val_peek(0)); }
break;
case 79:
//#line 187 "sintac.y"
{ yyval = new Distinto (val_peek(2),val_peek(0)); }
break;
case 80:
//#line 188 "sintac.y"
{ yyval = new MayorOIgual (val_peek(2),val_peek(0)); }
break;
case 81:
//#line 189 "sintac.y"
{ yyval = new MenorOIgual (val_peek(2),val_peek(0)); }
break;
case 82:
//#line 192 "sintac.y"
{ yyval = new Not(val_peek(0)); }
break;
case 83:
//#line 193 "sintac.y"
{ yyval = new MenosUnario(val_peek(0)); }
break;
//#line 999 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
