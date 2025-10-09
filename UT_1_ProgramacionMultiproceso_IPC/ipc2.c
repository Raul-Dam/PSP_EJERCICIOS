// ipc2.c
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

int main() {
    int fd[2];
    pid_t pid;
    char buffer[50];
    pipe(fd);
    pid = fork();

    if (pid == 0) {
        // Hijo
        close(fd[1]);
        int suma = 0;
        while (1) {
            read(fd[0], buffer, sizeof(buffer));
            if (strcmp(buffer, "+") == 0) {
                printf("Recibido carácter +\n");
                printf("La suma total es igual a: %d\n", suma);
                break;
            } else {
                int num = atoi(buffer);
                printf("Numero a sumar: %d\n", num);
                suma += num;
            }
        }
        close(fd[0]);
    } else {
        // Padre
        close(fd[0]);
        char entrada[50];
        while (1) {
            printf("Introduce un número o '+' para terminar: ");
            scanf("%s", entrada);
            write(fd[1], entrada, strlen(entrada) + 1);
            if (strcmp(entrada, "+") == 0)
                break;
        }
        close(fd[1]);
    }

    return 0;
}
