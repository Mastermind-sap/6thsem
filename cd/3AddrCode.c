#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

#define MAX 100

char stack[MAX][10];
int top = -1;
int tempCount = 1;

int precedence(char op) {
    if (op == '*' || op == '/') return 2;
    if (op == '+' || op == '-') return 1;
    return 0;
}

char opStack[MAX];
int opTop = -1;

void pushOp(char c) {
    opStack[++opTop] = c;
}

char popOp() {
    return opStack[opTop--];
}

char peekOp() {
    return opStack[opTop];
}

void infixToPostfix(char* infix, char* postfix) {
    int i, k = 0;
    for (i = 0; infix[i]; i++) {
        char ch = infix[i];
        if (isspace(ch) || ch == ';') continue;

        if (isalnum(ch)) {
            postfix[k++] = ch;
        } else if (ch == '=') {
            continue;
        } else {
            while (opTop != -1 && precedence(peekOp()) >= precedence(ch)) {
                postfix[k++] = popOp();
            }
            pushOp(ch);
        }
    }

    while (opTop != -1) {
        postfix[k++] = popOp();
    }

    postfix[k] = '\0';
}

void push(char* str) {
    strcpy(stack[++top], str);
}

char* pop() {
    return stack[top--];
}

void generateTAC(char* postfix, char* lhs) {
    for (int i = 0; postfix[i]; i++) {
        char ch = postfix[i];
        if (isalnum(ch)) {
            char operand[2] = {ch, '\0'};
            push(operand);
        } else {
            char* op2 = pop();
            char* op1 = pop();
            char temp[10];
            sprintf(temp, "t%d", tempCount++);
            printf("%s = %s %c %s\n", temp, op1, ch, op2);
            push(temp);
        }
    }

    char* result = pop();
    printf("%s = %s\n", lhs, result);
}

int main() {
    char infix[100], postfix[100];

    printf("Enter the expression: ");
    scanf("%s", infix);

    char lhs[10];
    int i = 0;

    while (infix[i] && infix[i] != '=') {
        lhs[i] = infix[i];
        i++;
    }
    lhs[i] = '\0';

    infixToPostfix(infix, postfix);

    generateTAC(postfix, lhs);

    return 0;
}