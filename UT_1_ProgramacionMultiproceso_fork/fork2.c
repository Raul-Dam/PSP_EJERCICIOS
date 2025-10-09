#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main() {
    pid_t pid2, pid3, pid4;

    // Proceso P1
    pid2 = fork();
    if (pid2 < 0) {
        printf("ERROR !!! No se ha podido crear el proceso hijo...");
        exit(-1);
    }

    if (pid2 == 0) {  
        // Estamos en P2
        pid3 = fork();
        if (pid3 < 0) {
            printf("ERROR !!! No se ha podido crear el proceso hijo...");
            exit(-1);
        }

        if (pid3 == 0) {  
            // Estamos en P3
            pid4 = fork();
            if (pid4 < 0) {
                printf("ERROR !!! No se ha podido crear el proceso hijo...");
                exit(-1);
            }

            if (pid4 == 0) {  
                // Estamos en P4
                printf("PID: %d, PPID: %d, Suma: %d\n", getpid(), getppid(), getpid() + getppid());
                exit(0);
            } else {
                // P3 espera a P4
                wait(NULL);
                printf("PID: %d, PPID: %d, Suma: %d\n", getpid(), getppid(), getpid() + getppid());
                exit(0);
            }

        } else {
            // P2 espera a P3
            wait(NULL);
            printf("PID: %d, PPID: %d, Suma: %d\n", getpid(), getppid(), getpid() + getppid());
            exit(0);
        }

    } else {
        // P1 espera a P2
        wait(NULL);
        printf("PID: %d, PPID: %d, Suma: %d\n", getpid(), getppid(), getpid() + getppid());
    }

}
