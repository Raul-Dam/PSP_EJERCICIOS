#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

void main(){
	pid_t pid;
	
	pid = fork();
	
	if (pid == -1 ) //Ha ocurrido un error 
	{
		printf("ERROR !!! No se ha podido crear el proceso hijo...");
		exit(-1);       
	}
	if (pid == 0 )  //Nos encontramos en Proceso hijo 
	{        
		printf("El nombre del alumno es Raul\n"); 
	}
	else    //Nos encontramos en Proceso padre 
	{ 
		wait(NULL); //espera la finalización del proceso hijo

		printf("Proceso padre: PID=%d, PID del hijo=%d\n", getpid(), pid);       
	}
   	exit(0);
}
