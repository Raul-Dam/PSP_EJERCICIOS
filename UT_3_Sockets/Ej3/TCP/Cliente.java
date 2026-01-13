package PSP_EJERCICIOS.UT_3_Sockets.Ej3.TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import PSP_EJERCICIOS.UT_3_Sockets.Ej3.Factura;

public class Cliente {
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

        Socket cliente = new Socket("localhost",6000);
        ObjectOutputStream facSal = new ObjectOutputStream(cliente.getOutputStream());
        ObjectInputStream facEnt = new ObjectInputStream(cliente.getInputStream());


        facSal.writeObject(facturaEnvio);
        System.out.println("Factura enviada al servidor");

        Factura facturaRecibida = (Factura) facEnt.readObject();

        System.out.println(facturaRecibida.toString());


        facEnt.close();
        facSal.close();
        cliente.close();
        teclado.close();
    }
    
}
