%{
    #include <stdio.h>
%}

KEYWORD (auto|break|case|char|const|continue|default|do|double|else|enum|extern|float|for|goto|if|int|long|register|return|short|signed|sizeof|static|struct|switch|typedef|union|unsigned|void|volatile|while)
OPERATOR (">"|"<"|">="|"<="|"=="|"*"|"%"|"^"|"&"|"-"|"+"|"&&"|"++"|"--"|"/"|"="|"+="|"-="|"*="|"&="|"%="|"^="|"/="|"!"|"\?:"|"~")
SYMBOL (\$|@|#|\.|\?|,|;|`|\\)

%%
{KEYWORD} {
    printf("%s is a keyword \n", yytext);
}
{OPERATOR} {
    printf("%s is a operator \n", yytext);
}
{SYMBOL} {
    printf("%s is a symbol \n", yytext);
}
.+ {
    printf("%s is not a keyword, operator or a symbol\n", yytext);
}
%%


int main(){
    printf("Enter: \n");
    yylex();
    return 0;
}