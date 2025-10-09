// ipc3.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <fcntl.h>

int main() {
    int fd[2];
    pid_t pid;
    int numeros[2];

    pipe(fd);
    pid = fork();

    if (pid == 0) {
        // Hijo
        close(fd[0]);
        srand(time(NULL));
        numeros[0] = rand() % 50 + 1;
        numeros[1] = rand() % 50 + 1;
        write(fd[1], numeros, sizeof(numeros));
        close(fd[1]);
    } else {
        // Padre
        close(fd[1]);
        read(fd[0], numeros, sizeof(numeros));
        int a = numeros[0], b = numeros[1];
        printf("%d + %d = %d\n", a, b, a + b);
        printf("%d - %d = %d\n", a, b, a - b);
        printf("%d * %d = %d\n", a, b, a * b);
        printf("%d / %d = %d\n", a, b, b != 0 ? a / b : 0);
        close(fd[0]);
    }

    return 0;
}
