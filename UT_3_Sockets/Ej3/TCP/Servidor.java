package PSP_EJERCICIOS.UT_3_Sockets.Ej3.TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import PSP_EJERCICIOS.UT_3_Sockets.Ej3.Factura;

public class Servidor {
    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        int numeroPuerto = 6000;
        ServerSocket servidor = new ServerSocket(numeroPuerto);
        
        System.out.println("Esperando al cliente");
        Socket cliente = servidor.accept();

        ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
        ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());


        Factura factura = (Factura) inObjeto.readObject();
        System.out.println("Factura recibida con importe: " + factura.getImporte());

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

        outObjeto.writeObject(factura);

        outObjeto.close();
        inObjeto.close();
        cliente.close();
        servidor.close();
    }
    
}
