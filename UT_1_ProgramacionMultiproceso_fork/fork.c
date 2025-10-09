#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main() {
    pid_t pid2, pid3, pid4;

    // Proceso P1
    pid2 = fork();  // Crear P2
    if (pid2 < 0) {
        printf("ERROR !!! No se ha podido crear el proceso hijo...");
        exit(-1);
    }

    if (pid2 == 0) {
        // Estamos en P2
        if (getpid() % 2 == 0)
            printf("Soy P2. PID: %d, PPID: %d\n", getpid(), getppid());
        else
            printf("Soy P2. PID: %d\n", getpid());
        exit(0);  // P2 termina
    } else {
        // Estamos en P1
        pid3 = fork();  // Crear P3
        if (pid3 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...");
            exit(-1);
        }

        if (pid3 == 0) {
            // Estamos en P3
            pid4 = fork();  // Crear P4
            if (pid4 < 0) {
                printf("ERROR !!! No se ha podido crear el proceso hijo...");
                exit(-1);
            }

            if (pid4 == 0) {
                // Estamos en P4
                if (getpid() % 2 == 0)
                    printf("Soy P4. PID: %d, PPID: %d\n", getpid(), getppid());
                else
                    printf("Soy P4. PID: %d\n", getpid());
                exit(0);
            } else {
                // P3 espera a P4
                wait(NULL);
                if (getpid() % 2 == 0)
                    printf("Soy P3. PID: %d, PPID: %d\n", getpid(), getppid());
                else
                    printf("Soy P3. PID: %d\n", getpid());
                exit(0);
            }
        } else {
            // P1 espera a P2 y P3
            wait(NULL);  // espera P2
            wait(NULL);  // espera P3
            if (getpid() % 2 == 0)
                printf("Soy P1. PID: %d, PPID: %d\n", getpid(), getppid());
            else
                printf("Soy P1. PID: %d\n", getpid());
        }
    }
}
