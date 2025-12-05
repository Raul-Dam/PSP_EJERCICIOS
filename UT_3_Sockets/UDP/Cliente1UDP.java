package PSP_EJERCICIOS.UT_3_Sockets.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente1UDP {
    public static void main(String[] arg) throws IOException,ClassNotFoundException {
        Scanner teclado = new Scanner(System.in);
        byte[] bufer = new byte[1024];
        DatagramSocket cliente1 = new DatagramSocket();

        int numero = teclado.nextInt();
        InetAddress ip = InetAddress.getByName("localhost");

        DatagramPacket envionum = new DatagramPacket(String.valueOf(numero).getBytes(), String.valueOf(numero).getBytes().length,ip,6000);
        cliente1.send(envionum);

        DatagramPacket reciboresultado = new DatagramPacket(bufer, bufer.length);
        cliente1.receive(reciboresultado);

        String resultaString = new String(reciboresultado.getData(),0,reciboresultado.getLength());
        System.out.println("El factorial de "+numero+" es "+Integer.parseInt(resultaString));     
    }
}
