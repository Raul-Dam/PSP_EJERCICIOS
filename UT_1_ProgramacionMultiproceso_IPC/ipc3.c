#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>



int main() {
    int fd[2];
    pid_t pid;

    pipe(fd);

    pid = fork();

    if (pid == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) { // Proceso Hijo
        close(fd[0]); // El hijo no lee, cierra el descriptor de lectura.

        // Inicializa la semilla para la generación de números aleatorios.
        srand((unsigned)time(NULL));

        // Genera dos números aleatorios entre 1 y 50.
        int num1 = rand() % 50 + 1;
        int num2 = rand() % 50 + 1;
        
        printf("El hijo ha generado los números: %d y %d\n", num1, num2);

        // Escribe los dos números en el pipe
        write(fd[1], &num1, sizeof(int));
        write(fd[1], &num2, sizeof(int));

        close(fd[1]); // Cierra el descriptor de escritura.
        exit(0);

    } else { // Proceso Padre
        close(fd[1]); // El padre no escribe, cierra el descriptor de escritura.

        int n1, n2;

        // Lee los dos números del pipe directamente en las variables enteras.
        read(fd[0], &n1, sizeof(int));
        read(fd[0], &n2, sizeof(int));

        // Espera a que el proceso hijo termine.
        wait(NULL);

        // Muestra por pantalla la suma, diferencia, producto y división[cite: 26].
        printf("%d + %d = %d\n", n1, n2, n1 + n2);
        printf("%d - %d = %d\n", n1, n2, n1 - n2);
        printf("%d * %d = %d\n", n1, n2, n1 * n2);
        
        if (n2 != 0) {
            printf("%d / %d = %d\n", n1, n2, n1 / n2);
        } else {
            printf("No es posible dividir por cero.\n");
        }

        close(fd[0]); // Cierra el descriptor de lectura.
    }

    return 0;
}