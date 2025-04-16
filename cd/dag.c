#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAXTOK   256
#define MAXSTACK 256

typedef struct DAGNode {
    int               id;
    char              op;     
    struct DAGNode   *left, *right;
    char             *name;   
    struct DAGNode   *next;
} DAGNode;

static DAGNode *node_list = NULL;  
static int      node_count = 0;

// Create or reuse a leaf node for variable/constant 'name'
DAGNode *get_leaf(const char *name) {
    for (DAGNode *n = node_list; n; n = n->next) {
        if (n->op == 0 && strcmp(n->name, name) == 0)
            return n;
    }
    DAGNode *n = malloc(sizeof *n);
    n->id    = node_count++;
    n->op    = 0;
    n->left  = n->right = NULL;
    n->name  = strdup(name);
    n->next  = node_list;
    return node_list = n;
}

// Create or reuse an operator node (op,left,right)
DAGNode *get_op(char op, DAGNode *l, DAGNode *r) {
    for (DAGNode *n = node_list; n; n = n->next) {
        if (n->op == op && n->left == l && n->right == r)
            return n;
    }
    DAGNode *n = malloc(sizeof *n);
    n->id    = node_count++;
    n->op    = op;
    n->left  = l;
    n->right = r;
    n->name  = NULL;
    n->next  = node_list;
    return node_list = n;
}

// Operator precedence
int prec(char op) {
    if (op=='+'||op=='-') return 1;
    if (op=='*'||op=='/') return 2;
    return 0;
}

// Apply the operator on top of opstack to the top two entries of valstack
void apply_op(char *opstack, int *op_sp,
              DAGNode **valstack, int *val_sp) {
    char   op = opstack[--*op_sp];
    DAGNode *r = valstack[--*val_sp];
    DAGNode *l = valstack[--*val_sp];
    DAGNode *n = get_op(op, l, r);
    valstack[(*val_sp)++] = n;
}

// Build the DAG from the infix expression in 'expr'
DAGNode *build_dag(const char *expr) {
    char      opstack[MAXSTACK];
    int       op_sp = 0;
    DAGNode  *valstack[MAXSTACK];
    int       val_sp = 0;
    int       i = 0, L = strlen(expr);

    while (i < L) {
        if (isspace(expr[i])) {
            i++;
        }
        else if (isalnum(expr[i])) {
            // parse a variable or integer constant
            char tok[MAXTOK];
            int  t = 0;
            while (i < L && isalnum(expr[i]))
                tok[t++] = expr[i++];
            tok[t] = 0;
            valstack[val_sp++] = get_leaf(tok);
        }
        else if (expr[i] == '(') {
            opstack[op_sp++] = '(';
            i++;
        }
        else if (expr[i] == ')') {
            while (op_sp > 0 && opstack[op_sp-1] != '(')
                apply_op(opstack, &op_sp, valstack, &val_sp);
            if (op_sp>0) op_sp--;  // pop '('
            i++;
        }
        else if (strchr("+-*/", expr[i])) {
            char curop = expr[i++];
            // while top of opstack has higher-or-equal precedence, apply it
            while (op_sp > 0 && opstack[op_sp-1] != '('
                   && prec(opstack[op_sp-1]) >= prec(curop)) {
                apply_op(opstack, &op_sp, valstack, &val_sp);
            }
            opstack[op_sp++] = curop;
        }
        else {
            fprintf(stderr, "Unexpected char '%c'\n", expr[i]);
            exit(1);
        }
    }
    // apply remaining ops
    while (op_sp > 0)
        apply_op(opstack, &op_sp, valstack, &val_sp);

    return valstack[0];  // root of the DAG
}

// Print all nodes in the DAG
void print_dag(void) {
    printf("DAG has %d nodes:\n", node_count);
    // Since we built a linked list in reverse-creation order,
    // let's collect pointers and then print sorted by ID
    DAGNode **arr = malloc(node_count * sizeof *arr);
    for (DAGNode *n = node_list; n; n = n->next)
        arr[n->id] = n;
    for (int i = 0; i < node_count; i++) {
        DAGNode *n = arr[i];
        if (n->op == 0) {
            printf("  [%2d] LEAF: %s\n", n->id, n->name);
        } else {
            printf("  [%2d] OP   : %c   left=%2d right=%2d\n",
                   n->id, n->op, n->left->id, n->right->id);
        }
    }
    free(arr);
}

int main(void) {
    char expr[1024];
    printf("Enter an expression: ");
    if (!fgets(expr, sizeof expr, stdin)) return 0;
    expr[strcspn(expr, "\n")] = 0;

    DAGNode *root = build_dag(expr);
    printf("Root node ID: %d\n", root->id);
    print_dag();
    return 0;
}