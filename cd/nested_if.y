%{
#include <stdio.h>
#include <stdlib.h>

int nesting_level = 0;      // Current nesting level
int max_nesting_level = 0;  // Maximum nesting level found

int yylex(void);
void yyerror(const char *s);
%}

%token IF ELSE OPEN_PAREN CLOSE_PAREN OPEN_BRACE CLOSE_BRACE ID NUM OP SEMICOLON

%%
program : stmt { printf("Maximum nesting level: %d\n", max_nesting_level); }
        ;

stmt : if_stmt
     | compound_stmt
     | expr SEMICOLON
     | /* empty */
     ;

if_stmt : IF OPEN_PAREN expr CLOSE_PAREN {
            nesting_level++;
            if (nesting_level > max_nesting_level)
                max_nesting_level = nesting_level;
          } 
          stmt 
          optional_else
          {
            nesting_level--;
          }
        ;

optional_else : /* empty */
              | ELSE stmt
              ;

compound_stmt : OPEN_BRACE stmt_list CLOSE_BRACE
              ;

stmt_list : stmt
          | stmt_list stmt
          ;

expr : ID
     | NUM
     | ID OP expr
     | NUM OP expr
     ;

%%

int main() {
    printf("Enter code with nested IF statements (end with Ctrl+D):\n");
    yyparse();
    return 0;
}

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
}
