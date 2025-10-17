#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>



int main() {
    int fd[2];
    pid_t pid;

    pipe(fd);

    pid = fork();

    if (pid == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) { 
        close(fd[0]); 


        srand((unsigned)time(NULL));


        int num1 = rand() % 50 + 1;
        int num2 = rand() % 50 + 1;
        
        printf("El hijo ha generado los números: %d y %d\n", num1, num2);


        write(fd[1], &num1, sizeof(int));
        write(fd[1], &num2, sizeof(int));

        close(fd[1]); 
        exit(0);

    } else { 
        close(fd[1]); 

        int n1, n2;


        read(fd[0], &n1, sizeof(int));
        read(fd[0], &n2, sizeof(int));


        wait(NULL);

        printf("%d + %d = %d\n", n1, n2, n1 + n2);
        printf("%d - %d = %d\n", n1, n2, n1 - n2);
        printf("%d * %d = %d\n", n1, n2, n1 * n2);
        
        if (n2 != 0) {
            printf("%d / %d = %d\n", n1, n2, n1 / n2);
        } else {
            printf("No es posible dividir por cero.\n");
        }

        close(fd[0]); 
    }

    return 0;
}