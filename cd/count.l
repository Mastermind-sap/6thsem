%{
#include <stdio.h>
#include <stdlib.h>

int lines = 0, words = 0, letters = 0, specials = 0;
FILE *fp;
%}

%%

[a-zA-Z]+        { letters += yyleng; words++; }
[^a-zA-Z0-9\n\t]+ { specials += yyleng; }
\n              { lines++; }
[ \t\r]+        { /* ignore */ }
.               { /* ignore other characters */ }

%%

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <filename>\n", argv[0]);
        return 1;
    }

    fp = fopen(argv[1], "r");
    if (!fp) {
        perror("Error opening file");
        return 1;
    }

    yyin = fp; // Set input source to file
    yylex();

    printf("\nLines: %d\nWords: %d\nLetters: %d\nSpecial Characters: %d\n", lines, words, letters, specials);
    
    fclose(fp);
    return 0;
}

int yywrap() {
    return 1;
}
