%{
#include<stdio.h>
#include <stdlib.h>
int yylex();
void yyerror(char *); 
%}

%token NUMBER ID
%left '+' '-'
%left '*' '/'



%%
expr:expr '+' expr 
|expr '-' expr 
|expr '*' expr 
|expr '/' expr 
|'('expr')'
| NUMBER
| ID
| '-' NUMBER
| '+' NUMBER
| '+' ID
| '-' ID;  

%%

int main(){
    printf("Enter the expression\n");
    yyparse();
    printf("Expression is valid\n");
    return 0;
}

void yyerror(char *s){
    printf("Expression is invalid\n");
    exit(1);
}