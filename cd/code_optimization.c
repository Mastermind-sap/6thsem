#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 100

typedef struct {
    char var;
    int val;
    int isConst;
} Sym;

Sym tab[MAX];
int symCnt = 0;

int isOp(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
}

int eval(int a, int b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        case '*': return a * b;
        case '/': return b ? a / b : 0;
        case '%': return b ? a % b : 0;
    }
    return 0;
}

int getVal(char var) {
    for (int i = 0; i < symCnt; i++)
        if (tab[i].var == var && tab[i].isConst)
            return tab[i].val;
    return -1;
}

void addSym(char var, int val) {
    for (int i = 0; i < symCnt; i++) {
        if (tab[i].var == var) {
            tab[i].val = val;
            tab[i].isConst = 1;
            return;
        }
    }
    tab[symCnt++] = (Sym){var, val, 1};
}

void opt(char *line) {
    char var, op, l, r;
    int v1, v2;

    sscanf(line, "%c = %c %c %c", &var, &l, &op, &r);

    if (isOp(op)) {
        v1 = (l >= '0' && l <= '9') ? l - '0' : getVal(l);
        v2 = (r >= '0' && r <= '9') ? r - '0' : getVal(r);

        if (v1 != -1 && v2 != -1) {
            int res = eval(v1, v2, op);
            addSym(var, res);
            printf("%c = %d\n", var, res);
        } else {
            printf("%s\n", line); 
        }
    } else {
        printf("%s\n", line); 
    }
}

int main() {
    int n;
    char line[50];

    printf("Enter number of statements: ");
    scanf("%d", &n);
    getchar();

    printf("Enter three address code expressions:\n");

    for (int i = 0; i < n; i++) {
        fgets(line, sizeof(line), stdin);
        line[strcspn(line, "\n")] = 0;
        opt(line);
    }

    return 0;
}