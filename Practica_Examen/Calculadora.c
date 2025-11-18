#include <stdio.h>      // Para printf, scanf, perror, sprintf
#include <stdlib.h>     // Para exit, rand, srand, atoi, EXIT_FAILURE
#include <unistd.h>     // Para fork, pipe, read, write, close, getpid, getppid
#include <sys/wait.h>   // Para wait, waitpid
#include <sys/types.h>  // Para pid_t (aunque unistd.h a veces lo incluye)
#include <stdbool.h>    // Para bool, true, false
#include <string.h>     // Para strcmp, strlen
#include <time.h>       // Para time() (usado en srand)
#include <signal.h>     // Para signal(), kill(), SIGINT, SIGALRM, etc.



int main(){
    pid_t pid;

    int fd[2];

    pipe(fd);

    pid=fork();

    if (pid==-1){
        printf("Error");
        exit(0);
    }

    if (pid==0){

        pid_t pid2;

        int fd2[2];
        pipe(fd2);

        pid2=fork();

        if (pid2==-1){
        printf("Error");
        exit(0);
        }

        if(pid2==0){

            close(fd2[1]);

            int resultado;
            read(fd2[0], &resultado, sizeof(int));
            printf("Proceso P2 pid=%d  - Proceso padre pid=%d\n",getpid(),getppid());

            if(resultado%2==0){
                printf("El resultado de la operacion %d es par",resultado);
            }else{
                printf("El resultado de la operacion %d es impar",resultado);
            }

            printf("Termina proceso P3");
            close(fd2[0]);
            exit(0);

        }else{
            close(fd[1]);
            close(fd2[0]);

            int n1,n2,oper,resul;

            read(fd[0], &oper, sizeof(int));
            read(fd[0], &n1, sizeof(int));
            read(fd[0], &n2, sizeof(int));

            printf("Proceso P2 pid=%d  - Proceso padre pid=%d\n",getpid(),getppid());

            if(oper==1){
                resul=n1+n2;
                printf("Operacion: %d + %d = %d\n",n1,n2,resul);
            }else if(oper==2){
                resul=n1-n2;
                printf("Operacion: %d - %d = %d\n",n1,n2,resul);
            }else if(oper==3){
                resul=n1*n2;
                printf("Operacion: %d * %d = %d\n",n1,n2,resul);
            }else {
                resul=n1/n2;
                printf("Operacion: %d / %d = %d\n",n1,n2,resul);
            }

            write(fd2[1], &resul, sizeof(int));


            wait(NULL);
            printf("Termina proceso P2");
            close(fd2[1]);
            close(fd[0]);
            exit(0);

        }
    



    }else{
        close(fd[0]);
        int opcion,op1,op2;

        bool validar=true;

        printf("Proceso P1 pid=%d\n",getpid());
        while(validar){
        printf("**CALCULADORA**\n");
        printf("1. Sumar\n");
        printf("2. Restar\n" );
        printf("3. Multiplicar\n");
        printf("4. Division\n");
        printf("Seleccione la opcion deseada: ");
        scanf("%d", &opcion); // %d es para un número entero

        if (opcion<=4){
            validar=false;
        }else{
            printf("Opcion no valida");
        }
        
        }

        printf("Introduce el primer operando: ");
        scanf("%d", &op1);

        printf("Introduce el segundo operando: ");
        scanf("%d", &op2);

        write(fd[1], &opcion, sizeof(int));
        write(fd[1], &op1, sizeof(int));
        write(fd[1], &op2, sizeof(int));

        wait(NULL);
        printf("Termina proceso P1");
        close(fd[1]);
        exit(0);
    }
    

    return 0;


    
}