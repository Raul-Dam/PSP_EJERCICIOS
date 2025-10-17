#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>

// Función para calcular el factorial de un número.
int factorial(int n) {
    if (n < 0) return -1; // Factorial no está definido para negativos.
    if (n == 0) return 1; // El factorial de 0 es 1.
    int fact = 1;
    for (int i = 1; i <= n; i++) {
        fact *= i;
    }
    return fact;
}

int main() {
    int pipe1[2]; // Pipe para la comunicación Padre -> Hijo
    int pipe2[2]; // Pipe para la comunicación Hijo -> Padre
    pid_t pid;

    // Se crean ambos pipes.
    pipe(pipe1);
    pipe(pipe2);

    pid = fork();


    if (pid == 0) { // Proceso Hijo
        // El hijo no escribe en pipe1, así que cierra escritura.
        close(pipe1[1]);
        // El hijo no lee de pipe2, así que cierra lectura.
        close(pipe2[0]);

        int numero_recibido;

        read(pipe1[0], &numero_recibido, sizeof(int));

        // Calcula el factorial del número recibido.
        int resultado = factorial(numero_recibido);

        // Escribe el resultado en pipe2 para que el padre lo lea.
        write(pipe2[1], &resultado, sizeof(int));

        // Cierra los descriptores que quedan abiertos.
        close(pipe1[0]);
        close(pipe2[1]);
        exit(0);

    } else { // Proceso Padre
        // El padre no lee de pipe1, así que cierra lectura.
        close(pipe1[0]);
        // El padre no escribe en pipe2, así que cierra escritura.
        close(pipe2[1]);

        // Inicializa la semilla para generar un número aleatorio.
        srand((unsigned)time(NULL));
        int num_aleatorio = rand() % 11; // Genera número aleatorio entre 0 y 10.

        printf("El proceso padre genera el numero %d en el pipe1\n", num_aleatorio);

        // Escribe el número aleatorio en pipe1 para enviarlo al hijo.
        write(pipe1[1], &num_aleatorio, sizeof(int));

        int resultado_factorial;
        // Lee el resultado del factorial enviado por el hijo desde pipe2.
        read(pipe2[0], &resultado_factorial, sizeof(int));

        // Espera a que el proceso hijo termine su ejecución.
        wait(NULL);

        // Muestra el resultado final por pantalla.
        printf("El factorial calculado por el proceso hijo: %d! = %d\n", num_aleatorio, resultado_factorial);

        // Cierra los descriptores que quedan abiertos.
        close(pipe1[1]);
        close(pipe2[0]);
    }

    return 0;
}