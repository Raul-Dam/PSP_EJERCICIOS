#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

void main() {
    pid_t pid2, pid3;

    // P1 crea P2
    pid2 = fork();

    if (pid2 < 0) {
        printf("ERROR !!! No se ha podido crear el proceso hijo...");
        exit(-1);
    }

    if (pid2 == 0) {
        // Proceso P2
        sleep(10);
        printf("Despierto\n");
        exit(0);
    } else {
        // P1 crea P3
        pid3 = fork();

        if (pid3 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...");
            exit(-1);
        }

        if (pid3 == 0) {
            // Proceso P3
            printf("Proceso P3: PID=%d, PID del padre=%d\n", getpid(), getppid());
            exit(0);
        } else {
            // P1 espera a P2 y P3
            wait(NULL);
            wait(NULL);
        }
    }

    exit(0);
}
