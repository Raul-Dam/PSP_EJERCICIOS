#include <stdio.h>
#include <unistd.h> 
#include <signal.h> 

int segundos = 0;


void alarma(int signum) {
    segundos += 5;
    printf("Han transcurrido %d segundos\n", segundos);
    alarm(5);
}

int main() {
    signal(SIGALRM, alarma);
    alarm(5);
    while (1) {
        pause();
    }
    return 0;
}