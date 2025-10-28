#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <time.h>

int total_repeticiones;
int intervalo_segundos;
int contador_alarmas = 0;

void fun_signal(int signum) {

    time_t tiempo_actual;
    char buffer_hora[100];

    time(&tiempo_actual);
    strftime(buffer_hora, sizeof(buffer_hora), "%H:%M:%S", localtime(&tiempo_actual));

    
    printf("Señal de alarma recibida a las %s\n", buffer_hora);
    contador_alarmas++;

    
    if (contador_alarmas < total_repeticiones) {
        
        alarm(intervalo_segundos);
    } else {
        printf("Alarma desactivada\n");
        exit(0);
    }
}

int main() {
   
    printf("¿Cuántas veces sonará la alarma?:\n");
    scanf("%d", &total_repeticiones);

    printf("¿Cada cuántos segundos se repetirá la alarma?:\n");
    scanf("%d", &intervalo_segundos);

    
    signal(SIGALRM, fun_signal);


    printf("Alarma activada\n");
    alarm(intervalo_segundos);


    while (1) {
        pause();
    }

    return 0;
}