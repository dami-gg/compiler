/*
Para generar el Parser.java:

	c:\>yacc -Jsemantic=Object -v sintac.y

Esta versión hace, mediante la opción -Jsemantic, que el tipo de los $n y de yylval sea Object en vez de ParserVal ya que es más cómodo hacer
$$ = new Suma(...)

que tener que hacer
$$ = new ParserVal(new Suma(...)) 

ParserVal es más cómodo cuando lo que se va a usar fundamentalmente son tipos primitivos (int o double). 
Pero si la mayoría de los valores que se propagen mediante $$ van a ser objetos es mucho más cómodo cambiar ParserVal por Object 
(como es el caso cuando se crea un AST).

*/

%{
import nodosAST.*;
import java.util.*;
%}


%left OR
%left AND
%left IGUALIGUAL DISTINTO
%left '>' '<' MAYOROIGUAL MENOROIGUAL
%left '+' '-'
%left '*' '/' '%'
%right '!'
%left '[' '.'

%token NUM CARACTER IDENT WHILE IF ELSE WRITE READ RETURN RECORD INT REAL DOUBLE CHAR BYTE VAR TYPE CAST AND OR IGUALIGUAL DISTINTO MAYOROIGUAL MENOROIGUAL
%%

s: programa     { root = (AST) $1;}
 ;

programa: declaraciones    { $$ = new Programa ((List) $1); }
        ;

declaraciones: declaraciones declaracion      { $$ = $1;
                                                for (int i=0; i < ((List) $2).size(); i++)
                                                    ((List) $$).add(((List) $2).get(i));    }
             |                                { $$ = new ArrayList(); }
             ;

declaracion: defVar ";"     { $$ = (List) $1; }
           | defTipo        { $$ = new ArrayList(); ((List) $$).add((DefTipo) $1); }
           | defFuncion     { $$ = new ArrayList(); ((List) $$).add((DefFuncion) $1); }
           ;

defVar: VAR listaIdents ":" tipo     { $$ = new ArrayList();
                                           for (int i=0; i < ((List) $2).size(); i++)
                                                ((List) $$).add(new DefVar(((List) $2).get(i), (Tipo) $4)); }
      ;

listaIdents: IDENT                      { $$ = new ArrayList(); ((List) $$).add($1); }
           | listaIdents "," IDENT      { $$ = $1; ((List) $$).add($3); }
           ;

defTipo: TYPE IDENT ":" tipo ";"        { $$ = new DefTipo($2, (Tipo) $4); }
       ;

tipo: tipoPrimitivo
    | array
    ;

tipoPrimitivo: INT          { $$ = new TipoInt(); }
             | REAL         { $$ = new TipoReal(); }
             | DOUBLE       { $$ = new TipoReal(); }
             | CHAR         { $$ = new TipoChar(); }
             | BYTE         { $$ = new TipoChar(); }
             | registro
             | IDENT        { $$ = new TipoUsuario($1); }
             ;

registro: RECORD "{" atributos "}"  { $$ = new TipoRegistro ((List) $3); }

atributos: atributos IDENT ":" tipo ";"     { $$ = $1; ((List) $$).add(new Atributo($2, (Tipo) $4)); }
         |                                  { $$ = new ArrayList(); }
         ;

array: dimension tipoPrimitivo      { TipoArray aux = new TipoArray ( ((List) $1).get( ((List) $1).size() - 1), (Tipo) $2 );
                                      for (int i=((List) $1).size()-2; i>=0; i--)
                                           aux = new TipoArray ( ((List) $1).get(i), (Tipo) aux);
                                      $$ = aux;                                                     }
     ;

dimension: dimension "[" expr "]"   { $$ = $1; ((List) $$).add($3); }
         | "[" expr "]"             { $$ = new ArrayList(); ((List) $$).add($2); }
         ;

defFuncion: IDENT "(" params ")" tipoRetorno "{" defVarLocal sentencias "}"
          { $$ = new DefFuncion($1, (List) $3, (Tipo) $5, (List) $7, (List) $8); }
          ;

tipoRetorno: ":" tipo { $$ = $2; }
           |          { $$ = new TipoVoid(); }
           ;

defVarLocal: defVarLocal defVar ";"     { $$ = $1;
                                          for(int i=0; i < ((List) $2).size(); i++)
                                              ((List) $$).add ( ((List) $2).get(i));  }
           |                            { $$ = new ArrayList(); }
           ;

params: listaParams
      |                 { $$ = new ArrayList(); }
      ;

listaParams: IDENT ":" tipo                     { $$ = new ArrayList(); ((List) $$).add (new Parametro ($1, (Tipo) $3)); }
           | listaParams "," IDENT ":" tipo     { $$ = $1; ((List) $$).add (new Parametro ($3, (Tipo) $5)); }
           ;

sentencias: sentencias sentencia    { $$ = $1; ((List) $$).add($2); }
          |                                 { $$ = new ArrayList(); }
          ;

sentencia: asignacion ";"
           | while
           | if
           | retorno ";"
           | lectura ";"
           | escritura ";"
           | funcion ";"
           ;

asignacion: expr "=" expr { $$ = new Asignacion ($1,$3); }
          ;

retorno: RETURN expr { $$ = new Retorno ($2); }
       ;

lectura: READ listaValores  { $$ = new Lectura ((List) $2); }
       ;

escritura: WRITE listaValores   { $$ = new Escritura ((List) $2); }
         ;

while: WHILE "(" expr ")" "{" sentencias "}" { $$ = new While ($3, (List) $6); }
     ;

if: IF "(" expr ")" "{" sentencias "}" else  { $$ = new If ($3, (List) $6, (Else) $8); }
  ;

else: ELSE "{" sentencias "}"    { $$ = new Else ((List) $3); }
    |                               { $$ = new Else (new ArrayList()); }
    ;

listaValores: expr                      { $$ = new ArrayList(); ((List)$$).add($1); }
            | listaValores "," expr     { $$ = $1; ((List)$$).add($3); }
            ;

expr: NUM                   { $$ = new Num ($1); }
    | CARACTER              { $$ = new Caracter ($1); }
    | IDENT                 { $$ = new VarRef ($1); }
    | funcion
    | "(" expr ")"          { $$ = $2; }
    | expr "[" expr "]"     { $$ = new AccesoArray ($1,$3); }
    | expr "." IDENT         { $$ = new AccesoAtrib ($1,$3); }
    | cast
    | exprBinaria
    | exprUnaria
    ;

cast: CAST "<" tipo ">" "(" expr ")" { $$ = new Casting ((Tipo) $3, $6); }
    ;

funcion: IDENT "(" listaValoresOpt ")"  { $$ = new Funcion ($1, (List) $3); }
       ;

listaValoresOpt: listaValores
               |                { $$ = new ArrayList(); }
               ;

exprBinaria: expr "+" expr              { $$ = new Suma ($1,$3); }
           | expr "-" expr              { $$ = new Resta ($1,$3); }
           | expr "*" expr              { $$ = new Producto ($1,$3); }
           | expr "/" expr              { $$ = new Cociente ($1,$3); }
           | expr "%" expr              { $$ = new Modulo ($1,$3); }
           | expr "<" expr              { $$ = new Menor ($1,$3); }
           | expr ">" expr              { $$ = new Mayor ($1,$3); }
           | expr AND expr              { $$ = new And ($1,$3); }
           | expr OR expr               { $$ = new Or ($1,$3); }
           | expr IGUALIGUAL expr       { $$ = new IgualIgual ($1,$3); }
           | expr DISTINTO expr         { $$ = new Distinto ($1,$3); }
           | expr MAYOROIGUAL expr      { $$ = new MayorOIgual ($1,$3); }
           | expr MENOROIGUAL expr      { $$ = new MenorOIgual ($1,$3); }
           ;

exprUnaria: "!" expr            { $$ = new Not($2); }
          | "-" expr     { $$ = new MenosUnario($2); } /* Tenía puesto MENOSUNARIO y fallaba al retornar un -1  y esto en lexico.l    "-"?[0-9]+ { return Parser.MENOSUNARIO; }*/
          ;

%%


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



