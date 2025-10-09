#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main() {
    pid_t pid2, pid3, pid4, pid5;
    int acumulado;

    // Proceso P1
    acumulado = getpid();  // inicializa acumulado con su PID

    // Crear P2
    pid2 = fork();
    if (pid2 < 0) {
        printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
        exit(-1);
    }

    if (pid2 == 0) {
        // P2
        int valor_p2 = (getpid() % 2 == 0) ? acumulado + 10 : acumulado - 100;

        // Crear P5
        pid5 = fork();
        if (pid5 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
            exit(-1);
        }

        if (pid5 == 0) {
            // P5
            int valor_p5 = (getpid() % 2 == 0) ? acumulado + 10 : acumulado - 100;
            printf("Soy P5. PID: %d, Valor: %d\n", getpid(), valor_p5);
            exit(0);
        } else {
            wait(NULL);  // P2 espera a P5
            printf("Soy P2. PID: %d, Valor: %d\n", getpid(), valor_p2);
            exit(0);
        }

    } else {
        // Crear P3
        pid3 = fork();
        if (pid3 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
            exit(-1);
        }

        if (pid3 == 0) {
            // P3
            int valor_p3 = (getpid() % 2 == 0) ? acumulado + 10 : acumulado - 100;

            // Crear P4
            pid4 = fork();
            if (pid4 < 0) {
                printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
                exit(-1);
            }

            if (pid4 == 0) {
                // P4
                int valor_p4 = (getpid() % 2 == 0) ? acumulado + 10 : acumulado - 100;
                printf("Soy P4. PID: %d, Valor: %d\n", getpid(), valor_p4);
                exit(0);
            } else {
                wait(NULL);  // P3 espera a P4
                printf("Soy P3. PID: %d, Valor: %d\n", getpid(), valor_p3);
                exit(0);
            }

        } else {
            // P1 espera a P2 y P3
            wait(NULL);
            wait(NULL);
            printf("Soy P1. PID: %d, Acumulado inicial: %d\n", getpid(), acumulado);
        }
    }

}
