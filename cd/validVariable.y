%{
#include <stdio.h>
#include <stdlib.h>
void yyerror(char *);
int yylex(void);
%}

%token VARIABLE INVALID

%%
program:
        INVALID       { printf("Invalid variable name\n"); exit(1); }
        | variable        { printf("Valid variable name\n"); exit(0); }
        ;

variable:
        VARIABLE
        ;
%%

void yyerror(char *s) {
    fprintf(stderr, "Error: %s\n", s);
}

int main(void) {
    printf("Enter a variable name: ");
    yyparse();
    return 0;
}