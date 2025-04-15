%{
#include <stdio.h>
#include "y.tab.h"
%}

%%
[a-zA-Z][a-zA-Z0-9]*    { return VARIABLE; }
[ \t\n]                 {}
.+                     { return INVALID; }
%%

int yywrap() {
    return 1;
}