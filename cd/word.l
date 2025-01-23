%{
	#include <stdio.h>
%}

%%

[a-zA-Z]+ {
	printf("%s is a word",yytext);
}

[0-9a-zA-Z]+ {
	printf("%s is a alphanumeric",yytext);
}


. {
	printf("%s is not a word or alphanumeric",yytext);
}

%%

int main(){

	printf("Enter a string:\n");
	yylex();
	return 0;
}
