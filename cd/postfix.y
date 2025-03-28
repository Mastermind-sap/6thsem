%{
#include <stdio.h>
#include <stdlib.h>

int valid = 1;  // Flag to track if expression is valid
int count = 0;  // Count of operands on stack

int yylex(void);
void yyerror(const char *s);
%}

%union {
    int ival;
}

%token <ival> NUMBER
%token OPERATOR

%%

start:
    /* empty */
    | start line
    ;

line:
    '\n'           { /* Skip empty lines */ }
    | expr '\n'    { 
        if(valid && count == 1) 
            printf("Valid postfix expression\n");
        else 
            printf("Invalid postfix expression\n");
        // Reset for next expression
        valid = 1;
        count = 0;
    }
    ;

/* A postfix expression is one or more terms */
expr: 
    term
    | expr term
    ;

term: 
    NUMBER { count++; }
    | OPERATOR {
        if(count < 2) {
            valid = 0;
        } else {
            count--;  // Two operands consumed, one result pushed
        }
      }
    ;

%%

int main(void) {
    printf("Enter postfix expression: ");
    yyparse();
    return 0;
}

void yyerror(const char *s) {
    valid = 0;  // Mark expression as invalid
}