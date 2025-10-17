#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>

int main() {
    int fd[2];
    pid_t pid;
    // El buffer ya no es necesario en el padre
    
    pipe(fd);
    pid = fork();

    if (pid == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) { // Proceso Hijo
        close(fd[0]); // Cierra lectura

        // Inicializa la semilla para que los números sean diferentes cada vez
        srand((unsigned)time(NULL));

        // Genera dos números aleatorios entre 1 y 50 [cite: 25, 94]
        int num1 = rand() % 50 + 1;
        int num2 = rand() % 50 + 1;

        // Escribe los dos números como datos binarios en el pipe
        write(fd[1], &num1, sizeof(int));
        write(fd[1], &num2, sizeof(int));

        close(fd[1]);
        exit(0); // El hijo termina su trabajo

    } else { // Proceso Padre
        close(fd[1]); // Cierra escritura

        int n1, n2;

        // Lee los datos binarios directamente en las variables enteras
        read(fd[0], &n1, sizeof(int));
        read(fd[0], &n2, sizeof(int));
        
        // Espera a que el proceso hijo termine
        wait(NULL);

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