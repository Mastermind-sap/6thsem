%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>

    extern FILE *yyin;
    extern FILE *yyout;
    int comments = 0;
%}

%%
"//".* { comments++; }
"/*"([^*]|\*+[^*/])*\*+"/"  { comments++; }
.|\n { fprintf(yyout, "%s", yytext); }
%%

int yywrap() { return 1; }

int main(int argc, char **argv) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <filename>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    yyin = fopen(argv[1], "r");
    if (!yyin) {
        perror(argv[1]);
        exit(EXIT_FAILURE);
    }

    yyout = tmpfile();
    if (!yyout) {
        perror("tmpfile");
        fclose(yyin);
        exit(EXIT_FAILURE);
    }

    yylex();
    rewind(yyout);

    FILE *fp = fopen(argv[1], "w");
    if (!fp) {
        perror("fopen for writing");
        fclose(yyin);
        fclose(yyout);
        exit(EXIT_FAILURE);
    }
    
    int c;
    while ((c = fgetc(yyout)) != EOF) {
        fputc(c, fp);
    }
    
    fclose(fp);
    fclose(yyin);
    fclose(yyout);
    
    printf("Comments removed: %d\n", comments);
    return 0;
}