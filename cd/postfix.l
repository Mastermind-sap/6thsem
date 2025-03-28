%{
#include "y.tab.h"
#include <stdlib.h>
%}

%%
[-+*/] { return OPERATOR; }
[0-9]+ { yylval.ival = atoi(yytext); return NUMBER; }
\n     { return '\n'; }  // Return '\n' as a token instead of 0
[ \t]  { /* Ignore whitespace */ }
.      { /* Ignore other characters */ }
%%

int yywrap() { return 1; }