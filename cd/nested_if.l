%{
#include "y.tab.h"
%}

%%
"if"        { return IF; }
"else"      { return ELSE; }
"("         { return OPEN_PAREN; }
")"         { return CLOSE_PAREN; }
"{"         { return OPEN_BRACE; }
"}"         { return CLOSE_BRACE; }
";"         { return SEMICOLON; }
[a-zA-Z_][a-zA-Z0-9_]*  { return ID; }
[0-9]+      { return NUM; }
[+\-*/=<>!]+ { return OP; }
[ \t\n]     { /* ignore whitespace */ }
.           { /* ignore other characters */ }
%%

int yywrap() {
    return 1;
}
