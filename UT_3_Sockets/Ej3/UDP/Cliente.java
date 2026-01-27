import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import PSP_EJERCICIOS.UT_3_Sockets.Ej3.Factura;

public class Cliente{
    private static final String HOST = "localhost";
    private static final int PUERTO_SERVIDOR = 6000;
    private static final int MAX_DATAGRAMA = 1024;
    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce Número Factura: ");
        String num = teclado.nextLine();
        System.out.print("Introduce Fecha: ");
        String fecha = teclado.nextLine();
        System.out.print("Introduce Importe: ");
        double importe = teclado.nextDouble();
        System.out.print("Introduce Tipo IVA (IGC, ESP, EUR): ");
        String tipo = teclado.next();

        Factura facturaEnvio = new Factura(num, fecha, importe, tipo);

        InetAddress ipServidor = InetAddress.getByName(HOST);

        try (DatagramSocket cliente = new DatagramSocket()) {

            //Envio factura
            byte[] facturaEnviada = serializar(facturaEnvio);
            DatagramPacket pSalida = new DatagramPacket(facturaEnviada, facturaEnviada.length, ipServidor, PUERTO_SERVIDOR);
            cliente.send(pSalida);

            //Recibo factura
            byte[] bufferEntrada = new byte[MAX_DATAGRAMA];
            DatagramPacket pEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            cliente.receive(pEntrada);

            facturaEnvio = (Factura) deserializar(bufferEntrada, bufferEntrada.length);

            System.out.println(facturaEnvio);
        }   
        teclado.close();
    }

    private static byte[] serializar(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();
        
    }

    private static Object deserializar(byte[] data, int length) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data, 0, length);
        ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        
    }
}