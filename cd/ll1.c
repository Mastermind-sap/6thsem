#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX 10

char productions[MAX][20];
char first[MAX][20], follow[MAX][20], table[MAX][MAX][20];
char nonterminals[MAX], terminals[MAX];
int n, ntCount = 0, tCount = 0;

int isTerminal(char ch) {
    return !isupper(ch) && ch != '#';
}

int indexOf(char arr[], int count, char ch) {
    for (int i = 0; i < count; i++)
        if (arr[i] == ch) return i;
    return -1;
}

void addToSet(char *set, char ch) {
    if (!strchr(set, ch)) {
        int len = strlen(set);
        set[len] = ch;
        set[len + 1] = '\0';
    }
}

void computeFirst(char ch, char *res) {
    if (isTerminal(ch)) {
        addToSet(res, ch);
        return;
    }

    int idx = indexOf(nonterminals, ntCount, ch);
    if (strlen(first[idx]) > 0) {
        strcpy(res, first[idx]);
        return;
    }

    for (int i = 0; i < n; i++) {
        if (productions[i][0] == ch) {
            char *rhs = strchr(productions[i], '=') + 1;
            if (strcmp(rhs, "#") == 0) {
                addToSet(res, '#');
            } else {
                char temp[20] = "";
                computeFirst(rhs[0], temp);
                for (int k = 0; temp[k]; k++)
                    addToSet(res, temp[k]);
            }
        }
    }

    strcpy(first[idx], res);
}

void computeFollow(char ch) {
    int idx = indexOf(nonterminals, ntCount, ch);
    if (strlen(follow[idx]) > 0) return;

    if (ch == productions[0][0]) addToSet(follow[idx], '$');

    for (int i = 0; i < n; i++) {
        char *rhs = strchr(productions[i], '=') + 1;
        for (int j = 0; rhs[j]; j++) {
            if (rhs[j] == ch) {
                if (rhs[j+1] != '\0') {
                    char temp[20] = "";
                    computeFirst(rhs[j+1], temp);
                    for (int k = 0; temp[k]; k++)
                        if (temp[k] != '#') addToSet(follow[idx], temp[k]);

                    if (strchr(temp, '#')) {
                        computeFollow(productions[i][0]);
                        int head = indexOf(nonterminals, ntCount, productions[i][0]);
                        for (int k = 0; follow[head][k]; k++)
                            addToSet(follow[idx], follow[head][k]);
                    }
                } else {
                    if (productions[i][0] != ch) {
                        computeFollow(productions[i][0]);
                        int head = indexOf(nonterminals, ntCount, productions[i][0]);
                        for (int k = 0; follow[head][k]; k++)
                            addToSet(follow[idx], follow[head][k]);
                    }
                }
            }
        }
    }
}

void buildParsingTable() {
    for (int i = 0; i < n; i++) {
        char head = productions[i][0];
        char *rhs = strchr(productions[i], '=') + 1;
        int row = indexOf(nonterminals, ntCount, head);

        if (strcmp(rhs, "#") == 0) {
            // Epsilon production — use FOLLOW(head)
            computeFollow(head);
            int fIdx = indexOf(nonterminals, ntCount, head);
            for (int k = 0; follow[fIdx][k]; k++) {
                int col = indexOf(terminals, tCount, follow[fIdx][k]);
                strcpy(table[row][col], productions[i]);
            }
        } else {
            char fSet[20] = "";
            computeFirst(rhs[0], fSet);
            for (int j = 0; fSet[j]; j++) {
                if (fSet[j] != '#') {
                    int col = indexOf(terminals, tCount, fSet[j]);
                    strcpy(table[row][col], productions[i]);
                }
            }

            if (strchr(fSet, '#')) {
                computeFollow(head);
                int fIdx = indexOf(nonterminals, ntCount, head);
                for (int k = 0; follow[fIdx][k]; k++) {
                    int col = indexOf(terminals, tCount, follow[fIdx][k]);
                    strcpy(table[row][col], productions[i]);
                }
            }
        }
    }
}


void displayTable() {
    printf("\nLL(1) Parsing Table:\n\n\t");
    for (int i = 0; i < tCount; i++)
        printf("%c\t", terminals[i]);
    printf("\n");

    for (int i = 0; i < ntCount; i++) {
        printf("%c\t", nonterminals[i]);
        for (int j = 0; j < tCount; j++) {
            if (strlen(table[i][j]) > 0)
                printf("%s\t", table[i][j]);
            else
                printf("-\t");
        }
        printf("\n");
    }
}

int main() {
    printf("Enter number of productions: ");
    scanf("%d", &n);
    getchar();

    printf("Enter productions (e.g., E=TR):\n");
    for (int i = 0; i < n; i++) {
        fgets(productions[i], sizeof(productions[i]), stdin);
        productions[i][strcspn(productions[i], "\n")] = '\0';

        char head = productions[i][0];
        if (indexOf(nonterminals, ntCount, head) == -1)
            nonterminals[ntCount++] = head;

        char *rhs = strchr(productions[i], '=') + 1;
        for (int j = 0; rhs[j]; j++) {
            if (isTerminal(rhs[j]) && indexOf(terminals, tCount, rhs[j]) == -1 && rhs[j] != '#')
                terminals[tCount++] = rhs[j];
        }
    }

    terminals[tCount++] = '$';

    for (int i = 0; i < ntCount; i++) {
        char tmp[20] = "";
        computeFirst(nonterminals[i], tmp);
    }

    for (int i = 0; i < ntCount; i++)
        computeFollow(nonterminals[i]);

    printf("\nFOLLOW Sets:\n");
    for (int i = 0; i < ntCount; i++)
        printf("FOLLOW(%c) = %s\n", nonterminals[i], follow[i]);

    buildParsingTable();
    displayTable();

    return 0;
}