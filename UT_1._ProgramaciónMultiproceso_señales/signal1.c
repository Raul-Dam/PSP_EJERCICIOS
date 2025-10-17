#include<stdio.h>
#include<unistd.h>
#include<signal.h>
#include <time.h>

void fun_signal(int signum){

    printf("Hola");
    exit(0);
}

int main(){
    signal(SIGINT,fun_signal); 
    time_t tiempo;
    char tiempo_format[100];


    
    // Obtenemos el tiempo actual
    time_format(tiempo);
    // Formateamos la fecha y hora en una cadena legible
    strftime(tiempo_format, sizeof(tiempo_format), "%Y-%m-%d %H:%M:%S", localtime(tiempo));

    printf("Programa iniciado en: %s\n", tiempo_format);
    printf("ID del proceso (PID): %d\n", getpid());


    pause();
    return 0;
}