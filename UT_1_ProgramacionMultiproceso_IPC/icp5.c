// ipc5.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>

int main() {
    int pipe1[2], pipe2[2];
    pid_t pid;
    char dni_str[20];
    int dni;
    char letra[] = "TRWAGMYFPDXBNJZSQVHLCKE";
    char letra_final;

    pipe(pipe1);
    pipe(pipe2);
    pid = fork();

    if (pid == 0) {
        // Hijo
        close(pipe1[0]);
        close(pipe2[1]);
        printf("Introduce el número de tu DNI: ");
        scanf("%s", dni_str);
        write(pipe1[1], dni_str, strlen(dni_str) + 1);
        read(pipe2[0], &letra_final, sizeof(letra_final));
        printf("La letra del NIF es %c\n", letra_final);
        close(pipe1[1]);
        close(pipe2[0]);
    } else {
        // Padre
        close(pipe1[1]);
        close(pipe2[0]);
        read(pipe1[0], dni_str, sizeof(dni_str));
        dni = atoi(dni_str);
        letra_final = letra[dni % 23];
        write(pipe2[1], &letra_final, sizeof(letra_final));
        close(pipe1[0]);
        close(pipe2[1]);
    }

    return 0;
}
