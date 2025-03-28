%{
#include <stdio.h>
#include <string.h>

// Forward declaration for yylex
int yylex(void);

// Ensure yyerror matches the expected signature
void yyerror(const char *s);
%}

%token ZERO ONE

%%
string : start middle end { printf("Valid string\n"); }
       ;

start : ZERO | ONE
      ;

middle : /* empty */
       | middle ZERO
       | middle ONE
       ;

end : ZERO | ONE
    ;

%%
int main() {
    printf("Enter a string: ");
    yyparse();
    return 0;
}

void yyerror(const char *s) {
    printf("Invalid string\n");
}

int yywrap() {
    return 1;
}
