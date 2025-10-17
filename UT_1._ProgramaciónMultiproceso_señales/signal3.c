#include <stdio.h>      
#include <stdlib.h>     
#include <signal.h>     
#include <unistd.h>     
#include <time.h>

time_t tiempo(){

    time_t tiempo;
    char tiempo_format[100];
    time(&tiempo);
    // Formateamos la fecha y hora en una cadena legible
    strftime(tiempo_format, sizeof(tiempo_format), "%Y-%m-%d %H:%M:%S", localtime(&tiempo));
    return tiempo_format;
}
