%{
    #include <stdio.h>
    int vowels = 0;
    int consonants = 0;
%}


%%
[aeiouAEIOU] {
    vowels++;
}
[a-zA-Z] {
    consonants++;
}
\n {
    printf("vowels : %d, consonants : %d\n", vowels, consonants);
    vowels = 0, consonants = 0;
}
. {
}
%%


int main(){
    printf("Enter: \n");
    yylex();
    return 0;
}