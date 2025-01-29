%{
    #include <stdio.h>
%}

KEYWORD (auto|break|case|char|const|continue|default|do|double|else|enum|extern|float|for|goto|if|int|long|register|return|short|signed|sizeof|static|struct|switch|typedef|union|unsigned|void|volatile|while)

%%

{KEYWORD} { 
    printf("%s is a keyword \n", yytext); 
}
[a-zA-Z_][a-zA-Z0-9_]* {
    printf("%s is a valid identifier \n", yytext);
}
.+ {
    printf("%s is not a valid identifier or keyword\n", yytext);
}
%%

int yywrap() { return 1; }

int main(){
    printf("Enter a string:\n");
    yylex();
    return 0;
}