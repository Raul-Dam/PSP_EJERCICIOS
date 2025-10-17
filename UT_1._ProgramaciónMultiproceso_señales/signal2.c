#include <stdio.h>
#include <unistd.h> 
#include <signal.h> 

int segundos = 0;


void manejador_alarma(int signum) {
    segundos += 5;
    printf("Han transcurrido %d segundos\n", segundos);
    alarm(5);
}

int main() {
    signal(SIGALRM, manejador_alarma);
    alarm(5);
    while (1) {
        pause();
    }
    return 0;
}