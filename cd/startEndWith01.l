%{
#include "y.tab.h"
%}

%%
0 { return ZERO; }
1 { return ONE; }
. { /* Ignore other characters */ }
\n { return 0; }
%%
