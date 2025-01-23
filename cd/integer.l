%{
	#include <stdio.h>
%}

%%

[0-9]+ {
	printf("%s is a positive integer",yytext);
}

-[0-9]+ {
	printf("%s is a negative integer",yytext);
}

[-] {
    printf("This is a minus sign\n");
}

[+] {
    printf("This is a positive sign\n");
}
. {
    printf("%s is not an integer",yytext);
}

%%

int main(){

	printf("Enter a string:\n");
	yylex();
	return 0;
}
