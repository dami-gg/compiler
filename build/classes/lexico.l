%%
%byaccj
%unicode
%line
%column
%caseless

%{
  public String lexeme() {
   return yytext();
  }

  public int line() {
    return yyline + 1;
  }

  public int column() {
    return yycolumn + 1;
  }
%}

%%
[+\-*/():.;=,%<>!\[\]{}']  { return yycharat(0); }

type|typedef	{ return Parser.TYPE; }
var	{ return Parser.VAR; }
int	{ return Parser.INT; }
real	{ return Parser.REAL; }
double	{ return Parser.DOUBLE; }
char	{ return Parser.CHAR; }
byte	{ return Parser.BYTE; }
record	{ return Parser.RECORD; }
read	{ return Parser.READ; }
write	{ return Parser.WRITE; }
while	{ return Parser.WHILE; }
if	{ return Parser.IF; }
else	{ return Parser.ELSE; }
return	{ return Parser.RETURN; }
cast	{ return Parser.CAST; }

"<="	{ return Parser.MENOROIGUAL; }
">="	{ return Parser.MAYOROIGUAL; }
"=="	{ return Parser.IGUALIGUAL; }
"!="	{ return Parser.DISTINTO; }
"||"	{ return Parser.OR; }
"&&"	{ return Parser.AND; }

[a-zA-Z][a-zA-Z0-9_]*	{ return Parser.IDENT; } 
"-"?[0-9]+[.]*[0-9]*    { return Parser.NUM; }
"'"[a-zA-Z0-9 ]"'" | "'\\n'" | "'\\t'" | "'\\r'" { return Parser.CARACTER; }

"/*"~"*/"	{ }
"//"~\n	{ }
"#"~"#"	{ }

[ \t\n\r]     { }

.	  { System.out.println("Error lexico en " + line() + ":" + column() + ". Lexema = [" + yytext() + "]"); }