%{
#include <stdio.h>
#include <stdlib.h>
%}

%%

"http"("s"?)"://"([a-zA-Z0-9.-]+)\.([a-zA-Z0-9./?=_-]+) {
    printf("URL found: %s\n", yytext);
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
