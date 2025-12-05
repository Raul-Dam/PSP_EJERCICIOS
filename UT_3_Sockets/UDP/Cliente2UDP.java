package PSP_EJERCICIOS.UT_3_Sockets.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente2UDP {
    public static void main(String[] arg) throws IOException,ClassNotFoundException {
        Scanner teclado = new Scanner(System.in);
        byte[] bufer = new byte[1024];
        DatagramSocket cliente2 = new DatagramSocket(6001);

        DatagramPacket recibenumero = new DatagramPacket(bufer, bufer.length);

        cliente2.receive(recibenumero);
        String numeroString = new String(recibenumero.getData(), 0, recibenumero.getLength());
        int resultado = factorial(Integer.parseInt(numeroString));

        

        DatagramPacket enviarresultado = new DatagramPacket(String.valueOf(resultado).getBytes(), String.valueOf(resultado).getBytes().length,recibenumero.getAddress(),recibenumero.getPort());

        cliente2.send(enviarresultado);

            
    }

    public static int factorial(int n) {
        int resultado = 1; 
        for (int i = 1; i <= n; i++) {
            resultado = resultado * i; 
        }
        return resultado;
    }
}
