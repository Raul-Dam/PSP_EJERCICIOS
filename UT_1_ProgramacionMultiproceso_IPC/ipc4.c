#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>


int factorial(int n) {
    if (n < 0) return -1; 
    if (n == 0) return 1; 
    int fact = 1;
    for (int i = 1; i <= n; i++) {
        fact *= i;
    }
    return fact;
}

int main() {
    int pipe1[2]; 
    int pipe2[2]; 
    pid_t pid;


    pipe(pipe1);
    pipe(pipe2);

    pid = fork();


    if (pid == 0) {
        close(pipe1[1]);

        close(pipe2[0]);

        int numero_recibido;

        read(pipe1[0], &numero_recibido, sizeof(int));


        int resultado = factorial(numero_recibido);


        write(pipe2[1], &resultado, sizeof(int));

        close(pipe1[0]);
        close(pipe2[1]);
        exit(0);

    } else { 

        close(pipe1[0]);

        close(pipe2[1]);

    
        srand((unsigned)time(NULL));
        int num_aleatorio = rand() % 11; 

        printf("El proceso padre genera el numero %d en el pipe1\n", num_aleatorio);


        write(pipe1[1], &num_aleatorio, sizeof(int));

        int resultado_factorial;

        read(pipe2[0], &resultado_factorial, sizeof(int));


        wait(NULL);


        printf("El factorial calculado por el proceso hijo: %d! = %d\n", num_aleatorio, resultado_factorial);


        close(pipe1[1]);
        close(pipe2[0]);
    }

    return 0;
}