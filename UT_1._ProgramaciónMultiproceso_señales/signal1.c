#include<stdio.h>
#include<unistd.h>
#include<signal.h>
#include <time.h>
#include <fcntl.h>
#include <stdlib.h>

time_t tiempo(){

    time_t tiempo;
    char tiempo_format[100];
    time(&tiempo);
    // Formateamos la fecha y hora en una cadena legible
    strftime(tiempo_format, sizeof(tiempo_format), "%Y-%m-%d %H:%M:%S", localtime(&tiempo));
    return tiempo_format;
}

void fun_signal(int signum){
    pid_t pid;
    pid=getpid();


    printf("Fin del proceso %d: %s\n", pid, tiempo());
    exit(0);
}

int main(){
    pid_t pid;
    signal(SIGINT,fun_signal); 
    pid=getpid();


    printf("Inicio del proceso %d: %s\n", pid, tiempo());


    pause();
    return 0;
}