#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main() {
    pid_t pid2, pid3, pid4, pid5, pid6;

    // Proceso P1

    pid2 = fork();
    if (pid2 < 0) {
        printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
        exit(-1);
    }

    if (pid2 == 0) {
        // P2
        pid_t mi_padre = getppid(); // abuelo para P3 y P4
        pid3 = fork();
        if (pid3 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
            exit(-1);
        }

        if (pid3 == 0) {
            // P3
            pid_t mi_abuelo_p3 = getppid(); // abuelo de P3
            pid5 = fork();
            if (pid5 < 0) {
                printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
                exit(-1);
            }

            if (pid5 == 0) {
                // P5
                printf("Soy P5. PID: %d, Abuelo: %d\n", getpid(), mi_abuelo_p3);
                exit(0);
            } else {
                wait(NULL);
                printf("Soy P3. PID: %d, Abuelo: %d\n", getpid(), mi_padre);
                exit(0);
            }

        } else {
            // P2 también crea P4
            pid4 = fork();
            if (pid4 < 0) {
                printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
                exit(-1);
            }

            if (pid4 == 0) {
                // P4
                pid_t mi_abuelo_p4 = getppid(); // abuelo de P4 = abuelo de P2
                pid6 = fork();
                if (pid6 < 0) {
                    printf("ERROR !!! No se ha podido crear el proceso hijo...\n");
                    exit(-1);
                }

                if (pid6 == 0) {
                    // P6
                    printf("Soy P6. PID: %d, Abuelo: %d\n", getpid(), mi_abuelo_p4);
                    exit(0);
                } else {
                    wait(NULL);
                    printf("Soy P4. PID: %d, Abuelo: %d\n", getpid(), mi_padre);
                    exit(0);
                }

            } else {
                // P2 espera a P3 y P4
                wait(NULL);
                wait(NULL);
                printf("Soy P2. PID: %d\n", getpid());
                exit(0);
            }
        }

    } else {
        // P1 espera a P2
        wait(NULL);
        printf("Soy P1. PID: %d\n",getpid());
    }

}
