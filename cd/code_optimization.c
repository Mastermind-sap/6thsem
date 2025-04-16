#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 100

typedef struct {
    char var;
    int value;
    int isConstant;
} Symbol;

Symbol table[MAX];
int symCount = 0;

int isOperator(char ch) {
    return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';
}

int eval(int a, int b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        case '*': return a * b;
        case '/': return b != 0 ? a / b : 0;
        case '%': return b != 0 ? a % b : 0;
    }
    return 0;
}

int getValue(char var) {
    for (int i = 0; i < symCount; i++) {
        if (table[i].var == var && table[i].isConstant) {
            return table[i].value;
        }
    }
    return -1;
}

void addSymbol(char var, int value) {
    for (int i = 0; i < symCount; i++) {
        if (table[i].var == var) {
            table[i].value = value;
            table[i].isConstant = 1;
            return;
        }
    }
    table[symCount++] = (Symbol){var, value, 1};
}

void optimize(char *line) {
    char var, op, left, right;
    int val1, val2;

    sscanf(line, "%c = %c %c %c", &var, &left, &op, &right);

    if (isOperator(op)) {
        val1 = (left >= '0' && left <= '9') ? left - '0' : getValue(left);
        val2 = (right >= '0' && right <= '9') ? right - '0' : getValue(right);

        if (val1 != -1 && val2 != -1) {
            int result = eval(val1, val2, op);
            addSymbol(var, result);
            printf("%c = %d\n", var, result);
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
        line[strcspn(line, "\n")] = '\0';
        optimize(line);
    }

    return 0;
}