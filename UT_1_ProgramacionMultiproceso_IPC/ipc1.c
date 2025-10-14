#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h> 
#include <string.h>

int main() {
    int fd[2]; //pipe
    char buffer[100];
    pid_t pid;

    // Creamos el pipe
    pipe(fd);

    // Se crea un proceso hijo
    pid = fork();

    if (pid == -1) {
        perror("fork");
        return 0;
    }

    if (pid == 0) { // Proceso Hijo
        // el hijo solo leerá.
        close(fd[1]);

        printf("Soy el proceso hijo con pid %d\n", getpid());

        // Lee del pipe
        read(fd[0], buffer, sizeof(buffer));

        // Muestra el mensaje recibido del padre
        printf("Fecha/hora: %s", buffer);

        // Cierra el descriptor de lectura
        close(fd[0]);

    } else { // Proceso Padre
        // Cierra el descriptor de lectura, el padre solo escribirá.
        close(fd[0]);

        // Obtiene la fecha y hora del sistema
        time_t hora;
        char *fecha;
        time(&hora);
        fecha = ctime(&hora);

        // Escribe la fecha en el pipe
        write(fd[1], fecha, strlen(fecha) + 1);

        // Cierra el descriptor de escritura
        close(fd[1]);

        // Espera a que el proceso hijo termine
        wait(NULL);
    }

    return 0;
}