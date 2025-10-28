#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <time.h>


void fun_signal(int signum) {

    FILE *fichero;
    time_t tiempo_actual;
    char buffer_hora[100];

    fichero = fopen("salidas.txt", "a");
    if (fichero == NULL) {
        perror("Error al abrir el fichero salidas.txt");
        return;
    }

    time(&tiempo_actual);

    strftime(buffer_hora, sizeof(buffer_hora), "%H:%M:%S", localtime(&tiempo_actual));


    fprintf(fichero, "Señal SIGINT recibida a las %s\n", buffer_hora);


    fclose(fichero);

}

int main() {

    signal(SIGINT, fun_signal);
        


    while (1) {
        pause();
    }

    return 0; 
}