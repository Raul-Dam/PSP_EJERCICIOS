// ipc1.c
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include <fcntl.h>


int main() {
    int fd[2];
    pid_t pid;
    char buffer[100];
    time_t hora;
    char *fecha;

    pipe(fd);
    pid = fork();

    if (pid < 0) {
        perror("Error en fork");
        return 1;
    }

    if (pid == 0) { 
        // Proceso hijo
        close(fd[1]); // Cierra extremo de escritura
        read(fd[0], buffer, sizeof(buffer));
        printf("Soy el proceso hijo con pid %d\n", getpid());
        printf("Fecha/hora: %s", buffer);
        close(fd[0]);
    } else {
        // Proceso padre
        close(fd[0]); // Cierra extremo de lectura
        time(&hora);
        fecha = ctime(&hora);
        write(fd[1], fecha, strlen(fecha) + 1);
        close(fd[1]);
    }

    return 0;
}
