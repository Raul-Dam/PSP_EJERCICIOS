package PSP_EJERCICIOS.UT_3_Sockets.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente2 {
    public static void main(String[] arg) throws IOException,ClassNotFoundException {
        Socket cliente2 = new Socket("localhost",6000);

        // Leer
        DataInputStream entrada2 = new DataInputStream( cliente2.getInputStream() );
        //Enviar
        DataOutputStream salida2 = new DataOutputStream(cliente2.getOutputStream());

        int numero = entrada2.readInt();

        int factorial = factorial(numero);

        salida2.writeInt(factorial);


    }

    public static int factorial(int n) {
    int resultado = 1; 
    for (int i = 1; i <= n; i++) {
        resultado = resultado * i; 
    }
    return resultado;
}
}
