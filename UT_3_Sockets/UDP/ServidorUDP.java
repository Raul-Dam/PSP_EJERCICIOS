package PSP_EJERCICIOS.UT_3_Sockets.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP {
   public static void main(String[] arg) throws IOException,ClassNotFoundException {
      byte[] bufer = new byte[1024];
      DatagramSocket servidor = new DatagramSocket(6000);

      DatagramPacket recibo = new DatagramPacket(bufer, bufer.length);

      servidor.receive(recibo);
      //Logica recibir numero
      //String transformacion = new String(recibo.getData(),0,recibo.getLength());
      //int numero = Integer.parseInt(transformacion);


      InetAddress ip = InetAddress.getByName("localhost");
      byte[] numeroEnviar = recibo.getData();
      DatagramPacket envionum = new DatagramPacket(numeroEnviar,recibo.getLength(),ip,6001);
      servidor.send(envionum);

      DatagramPacket reciboresultado = new DatagramPacket(bufer, bufer.length);
      servidor.receive(reciboresultado);
      numeroEnviar= reciboresultado.getData();
      DatagramPacket envioresultado = new DatagramPacket(numeroEnviar,reciboresultado.getLength(),recibo.getAddress(),recibo.getPort());
      servidor.send(envioresultado);
   }
}
