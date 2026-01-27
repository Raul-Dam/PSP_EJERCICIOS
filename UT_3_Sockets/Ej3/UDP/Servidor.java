import java.io.*;
import java.net.*;

import PSP_EJERCICIOS.UT_3_Sockets.Ej3.Factura;

public class Servidor {
    private static final int PUERTO = 6000;
    private static final int MAX_DATAGRAMA = 1024;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Esperando al cliente.....");

        try (DatagramSocket servidor = new DatagramSocket(PUERTO)) {

            byte[] buffer = new byte[MAX_DATAGRAMA];
            DatagramPacket pEntrada = new DatagramPacket(buffer, buffer.length);
            servidor.receive(pEntrada);

            InetAddress ipCliente = pEntrada.getAddress();
            int puertoCliente = pEntrada.getPort();

            Factura factura = (Factura) deserializar(buffer, buffer.length);

            double porcentaje = 0;
            String tipo = factura.getTipoIva();

            switch (tipo) {
                case "IGC": porcentaje = 0.07; break;

                case "ESP": porcentaje = 0.21; break;

                case "EUR": porcentaje = 0.15; break;

                default: porcentaje = 0; break; 
            }

            double calculoIva = factura.getImporte() * porcentaje;
            double calculoTotal = factura.getImporte() + calculoIva;

            factura.setIva(calculoIva);
            factura.setImporteTotal(calculoTotal);

            System.out.println("Enviando paquete");
            buffer = serializar(factura);
            DatagramPacket pSalida = new DatagramPacket(buffer, buffer.length,ipCliente,puertoCliente);
            servidor.send(pSalida);
        }
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

    
