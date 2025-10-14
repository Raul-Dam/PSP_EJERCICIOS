#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main() {
    int fd[2];
    char buffer[30];
    pid_t pid;

    int numeros[] = {25, 18, 67};
    int num_count = sizeof(numeros) / sizeof(numeros[0]);

    pipe(fd);
    pid = fork();

    if (pid == 0) { // Proceso Hijo
        close(fd[1]); // Cierra el descriptor de escritura

        int suma = 0;
        while (1) {
            read(fd[0], buffer, sizeof(buffer));

            // Compara si la cadena es "+"
            if (strcmp(buffer, "+") == 0) {
                printf("Recibido carácter +\n");
                break;
            }

            // Convierte la cadena a entero
            int numero = atoi(buffer);
            printf("Numero a sumar: %d\n", numero);
            suma += numero;
        }

        printf("La suma total es igual a: %d\n", suma);
        close(fd[0]);

    } else { // Proceso Padre
        close(fd[0]); // Cierra el descriptor de lectura

        for (int i = 0; i < num_count; i++) {
            // Convierte el entero a cadena para enviarlo
            sprintf(buffer, "%d", numeros[i]);
            write(fd[1], buffer, strlen(buffer) + 1);
        }

        close(fd[1]);
        wait(NULL);
    }

    return 0;
}