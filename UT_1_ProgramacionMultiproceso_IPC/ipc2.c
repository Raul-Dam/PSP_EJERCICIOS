#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main() {
    int fd[2];
    char buffer[30]; // Buffer para la comunicación basada en texto
    pid_t pid;

    int numeros[] = {25, 18, 67};
    int num_count = sizeof(numeros) / sizeof(numeros[0]);

    pipe(fd);
    
    pid = fork();


    if (pid == 0) { // Proceso Hijo
        close(fd[1]); // El hijo solo lee

        int suma = 0;
        // Bucle para leer mensajes de texto del pipe
        while (read(fd[0], buffer, sizeof(buffer)) > 0) {
            
            // Comprueba si el mensaje es la señal de finalización "+"
            if (strcmp(buffer, "+") == 0) {
                printf("Recibido carácter +\n");
                break; // Sale del bucle
            }

            // Si no es la señal, convierte el texto a número
            int numero = atoi(buffer);
            printf("Numero a sumar: %d\n", numero);
            suma += numero;
        }

        printf("La suma total es igual a: %d\n", suma);
        close(fd[0]);
        exit(0);

    } else { // Proceso Padre
        close(fd[0]); // El padre solo escribe

        // Envía cada número como una cadena de texto
        for (int i = 0; i < num_count; i++) {
            sprintf(buffer, "%d", numeros[i]);
            // Se escribe la longitud de la cadena + 1 para incluir el carácter nulo '\0'
            write(fd[1], buffer, strlen(buffer) + 1);
        }

        // Envía la señal de finalización "+" como una cadena
        write(fd[1], "+", strlen("+") + 1);

        close(fd[1]); // Cierra el pipe para que el hijo detecte el final
        wait(NULL);   // Espera a que el hijo termine
    }

    return 0;
}