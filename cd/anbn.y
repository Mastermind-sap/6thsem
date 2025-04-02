%{
#include <stdio.h>
#include <stdlib.h>
extern int yylex();
extern char *yytext;
void yyerror(const char *s);
int error_occurred = 0;
%}

%token A B INVALID_CHAR
%start S

%%
S : A_seq B_seq '\n'    {
                            if (!error_occurred) {
                                printf("Result: Input String Accepted.\n");
                            } else {
                                printf("Result: Input String Rejected due to previous errors.\n");
                            }
                            exit(0);
                        }
  | S error '\n'        {
                            yyerrok;
                        };

A_seq : 
      | A_seq A
      ;

B_seq : 
      | B_seq B
      ;
%%

void yyerror(const char *s) {
    fprintf(stderr, "Syntax Error: %s near token '%s'\n", s, yytext);
    error_occurred = 1;
}

int main() {
    if (yyparse() == 0 && !error_occurred) {
    } else {
        fprintf(stderr, "Result: Input String Rejected.\n");
    }
    return error_occurred;
}