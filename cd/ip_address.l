%{
#include <stdio.h>
#include <stdlib.h>
%}

%%

([0-9]{1,3}\.){3}[0-9]{1,3} {
    printf("IPv4 Address found: %s\n", yytext);
}

([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4} {
    printf("IPv6 Address found: %s\n", yytext);
}

.|\n { /* ignore other characters */ }

%%

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <filename>\n", argv[0]);
        return 1;
    }

    FILE *fp = fopen(argv[1], "r");
    if (!fp) {
        perror("Error opening file");
        return 1;
    }

    yyin = fp; // Set input source to file
    yylex();
    
    fclose(fp);
    return 0;
}

int yywrap() {
    return 1;
}
