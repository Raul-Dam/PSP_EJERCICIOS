// ipc4.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <fcntl.h>

int factorial(int n) {
    if (n == 0) return 1;
    int f = 1;
    for (int i = 1; i <= n; i++) f *= i;
    return f;
}

int main() {
    int pipe1[2], pipe2[2];
    pid_t pid;
    int numero, resultado;

    pipe(pipe1);
    pipe(pipe2);
    pid = fork();

    if (pid == 0) {
        // Hijo
        close(pipe1[1]);
        close(pipe2[0]);
        read(pipe1[0], &numero, sizeof(numero));
        resultado = factorial(numero);
        write(pipe2[1], &resultado, sizeof(resultado));
        close(pipe1[0]);
        close(pipe2[1]);
    } else {
        // Padre
        close(pipe1[0]);
        close(pipe2[1]);
        srand(time(NULL));
        numero = rand() % 11; // entre 0 y 10
        printf("El proceso padre genera el numero %d en el pipe1\n", numero);
        write(pipe1[1], &numero, sizeof(numero));
        read(pipe2[0], &resultado, sizeof(resultado));
        printf("El factorial calculado por el proceso hijo: %d != %d\n", numero, resultado);
        close(pipe1[1]);
        close(pipe2[0]);
    }

    return 0;
}
