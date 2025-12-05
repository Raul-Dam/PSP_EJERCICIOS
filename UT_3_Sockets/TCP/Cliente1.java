package PSP_EJERCICIOS.UT_3_Sockets.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente1 {
    public static void main(String[] arg) throws IOException,ClassNotFoundException {
        Scanner teclado = new Scanner(System.in);
        Socket cliente1 = new Socket("localhost",6000);

        // Leer
        DataInputStream entrada1 = new DataInputStream( cliente1.getInputStream() );
        //Enviar
        DataOutputStream salida1 = new DataOutputStream(cliente1.getOutputStream());


        //Enviar numero
        int numero=teclado.nextInt();
        salida1.writeInt(numero);

        //Leer resultado
        int factorial = entrada1.readInt();

        System.out.println("El factorial de "+numero+" es "+factorial);
    }
}
