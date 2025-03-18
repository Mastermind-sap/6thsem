%{
#include "y.tab.h"
int yval;
extern int yval;
%}

%option noyywrap

%%
[0-9]+ {yval=atoi(yytext); return NUMBER;}
[a-zA-Z]+ {return ID;}
[\t]+ ;
\n {return 0;}
. {return yytext[0];}

%%