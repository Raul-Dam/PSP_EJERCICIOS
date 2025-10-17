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


    if (pid == 0) { 
        close(fd[1]); 

        int suma = 0;

        while (read(fd[0], buffer, sizeof(buffer)) > 0) {
            

            if (strcmp(buffer, "+") == 0) {
                printf("Recibido carácter +\n");
                break; 
            }

            int numero = atoi(buffer);
            printf("Numero a sumar: %d\n", numero);
            suma += numero;
        }

        printf("La suma total es igual a: %d\n", suma);
        close(fd[0]);
        exit(0);

    } else { 
        close(fd[0]); 


        for (int i = 0; i < num_count; i++) {
            sprintf(buffer, "%d", numeros[i]);

            write(fd[1], buffer, strlen(buffer) + 1);
        }


        write(fd[1], "+", strlen("+") + 1);

        close(fd[1]); 
        wait(NULL);   

    return 0;
}