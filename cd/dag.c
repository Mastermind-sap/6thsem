#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAXTOK 256
#define MAXSTK 256

typedef struct Node {
    int id;
    char op;     
    struct Node *left, *right;
    char *name;   
    struct Node *next;
} Node;

static Node *nodeList = NULL;  
static int nodeCnt = 0;

// Create or reuse a leaf node
Node *getLeaf(const char *name) {
    for (Node *n = nodeList; n; n = n->next)
        if (n->op == 0 && strcmp(n->name, name) == 0)
            return n;
    
    Node *n = malloc(sizeof *n);
    n->id = nodeCnt++;
    n->op = 0;
    n->left = n->right = NULL;
    n->name = strdup(name);
    n->next = nodeList;
    return nodeList = n;
}

// Create or reuse an operator node
Node *getOp(char op, Node *l, Node *r) {
    for (Node *n = nodeList; n; n = n->next)
        if (n->op == op && n->left == l && n->right == r)
            return n;
    
    Node *n = malloc(sizeof *n);
    n->id = nodeCnt++;
    n->op = op;
    n->left = l;
    n->right = r;
    n->name = NULL;
    n->next = nodeList;
    return nodeList = n;
}

// Operator precedence
int prec(char op) {
    if (op=='+' || op=='-') return 1;
    if (op=='*' || op=='/') return 2;
    return 0;
}

// Apply operator
void applyOp(char *opStk, int *opSp, Node **valStk, int *valSp) {
    char op = opStk[--*opSp];
    Node *r = valStk[--*valSp];
    Node *l = valStk[--*valSp];
    valStk[(*valSp)++] = getOp(op, l, r);
}

// Build DAG from expression
Node *buildDag(const char *expr) {
    char opStk[MAXSTK];
    int opSp = 0;
    Node *valStk[MAXSTK];
    int valSp = 0;
    int i = 0, len = strlen(expr);

    while (i < len) {
        if (isspace(expr[i])) {
            i++;
        }
        else if (isalnum(expr[i])) {
            char tok[MAXTOK];
            int t = 0;
            while (i < len && isalnum(expr[i]))
                tok[t++] = expr[i++];
            tok[t] = 0;
            valStk[valSp++] = getLeaf(tok);
        }
        else if (expr[i] == '(') {
            opStk[opSp++] = '(';
            i++;
        }
        else if (expr[i] == ')') {
            while (opSp > 0 && opStk[opSp-1] != '(')
                applyOp(opStk, &opSp, valStk, &valSp);
            if (opSp > 0) opSp--;
            i++;
        }
        else if (strchr("+-*/", expr[i])) {
            char curOp = expr[i++];
            while (opSp > 0 && opStk[opSp-1] != '(' && 
                   prec(opStk[opSp-1]) >= prec(curOp)) {
                applyOp(opStk, &opSp, valStk, &valSp);
            }
            opStk[opSp++] = curOp;
        }
        else {
            fprintf(stderr, "Unexpected char '%c'\n", expr[i]);
            exit(1);
        }
    }
    
    while (opSp > 0)
        applyOp(opStk, &opSp, valStk, &valSp);

    return valStk[0];
}

// Print DAG
void printDag() {
    printf("DAG has %d nodes:\n", nodeCnt);
    Node **arr = malloc(nodeCnt * sizeof *arr);
    for (Node *n = nodeList; n; n = n->next)
        arr[n->id] = n;
    
    for (int i = 0; i < nodeCnt; i++) {
        Node *n = arr[i];
        if (n->op == 0) {
            printf("  [%2d] LEAF: %s\n", n->id, n->name);
        } else {
            printf("  [%2d] OP: %c   l=%2d r=%2d\n", 
                   n->id, n->op, n->left->id, n->right->id);
        }
    }
    free(arr);
}

int main() {
    char expr[1024];
    printf("Enter an expression: ");
    if (!fgets(expr, sizeof expr, stdin)) return 0;
    expr[strcspn(expr, "\n")] = 0;

    Node *root = buildDag(expr);
    printf("Root node ID: %d\n", root->id);
    printDag();
    return 0;
}