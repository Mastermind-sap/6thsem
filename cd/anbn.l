%{
#include "y.tab.h"
#include <stdio.h>
%}

%%
a       { return A; }
b       { return B; }
\n      { return '\n'; }
[ \t]+  {}
.       {
          fprintf(stderr, "Error: Invalid character '%s'\n", yytext);
          return INVALID_CHAR;
        }
%%

int yywrap() {
    return 1;
}