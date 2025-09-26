#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

void main() {
    pid_t pid2, pid3;

    pid2 = fork(); // P1 crea P2

    if (pid2 < 0) {
        printf("ERROR !!! No se ha podido crear el proceso hijo...");
        exit(-1);
    }

    if (pid2 == 0) {
        // Proceso P2
        pid3 = fork(); // P2 crea P3

        if (pid3 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...");
            exit(-1);
        }

        if (pid3 == 0) {
            // Proceso P3
            printf("Proceso P3: PID=%d, PID del padre=%d\n", getpid(), getppid());
        } else {
            // P2 espera a P3
            wait(NULL);
            printf("Proceso P2: PID=%d, PID del padre=%d\n", getpid(), getppid());
        }

    } else {
        // P1 espera a P2
        wait(NULL);
        printf("Proceso P1: PID=%d, PID del hijo=%d\n", getpid(), pid2);
    }
    exit(0);
}
