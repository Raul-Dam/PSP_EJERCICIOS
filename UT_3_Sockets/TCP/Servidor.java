package PSP_EJERCICIOS.UT_3_Sockets.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] arg) throws IOException,ClassNotFoundException {
	
        int numeroPuerto = 6000;// Puerto
        ServerSocket servidor =  new ServerSocket(numeroPuerto);
        System.out.println("Esperando al cliente.....");
        Socket cliente1 = servidor.accept();
        Socket cliente2 = servidor.accept();

        // Leer cliente 1
        DataInputStream entrada1 = new DataInputStream( cliente1.getInputStream() );

        //Enviar cliente 1
        DataOutputStream salida1 = new DataOutputStream(cliente1.getOutputStream());

        //Enviar cliente 2
        DataOutputStream salida2 = new DataOutputStream(cliente2.getOutputStream());

        // Leer cliente 2
        DataInputStream entrada2 = new DataInputStream( cliente2.getInputStream() );


        //Leer del cliente 1 y guardar el numero
        int numeroclient1 = entrada1.readInt();

        //Enviar numero a cliente 2
        salida2.writeInt(numeroclient1);

        //Leer del cliente 2 y guardar el resultado
        int resultado = entrada2.readInt();

        //Enviar resultado a cliente 1
        salida1.writeInt(resultado);
    }
}
