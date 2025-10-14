#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h> 
#include <string.h>

int main() {
    int fd[2];
    pid_t pid;
    char buffer[30];

    pipe(fd);
    pid = fork();

    if (pid == 0) { // Proceso Hijo
        close(fd[0]); // Cierra lectura

        // Genera dos números aleatorios entre 1 y 50 
        int num1 = rand() % 50 + 1;
        int num2 = rand() % 50 + 1;

        // Escribe los dos números en el pipe
        write(fd[1], &num1, sizeof(int));
        write(fd[1], &num2, sizeof(int));

        close(fd[1]);

    } else { // Proceso Padre
        close(fd[1]); // Cierra escritura
        wait(NULL); // Espera a que el hijo termine de escribir

        int n1, n2;

        // Lee el primer número
        read(fd[0], buffer, sizeof(buffer));
        n1 = atoi(buffer); // Convierte a entero

        // Lee el segundo número
        read(fd[0], buffer, sizeof(buffer));
        n2 = atoi(buffer); // Convierte a entero

        // Realiza y muestra las operaciones
        printf("%d + %d = %d\n", n1, n2, n1 + n2);
        printf("%d - %d = %d\n", n1, n2, n1 - n2);
        printf("%d * %d = %d\n", n1, n2, n1 * n2);
        if (n2 != 0) {
            printf("%d / %d = %d\n", n1, n2, n1 / n2);
        } else {
            printf("No se puede dividir por cero.\n");
        }

        close(fd[0]);
    }
    return 0;
}