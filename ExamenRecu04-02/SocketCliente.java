import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import PSP_EJERCICIOS.UT_3_Sockets.Ej3.Factura;

public class SocketCliente {
    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Importe prestamo: ");
        double prestamo = teclado.nextDouble();
        teclado.nextLine();
        System.out.print("Tipo de Interés Anual: ");
        double interesAnual = teclado.nextDouble();
        teclado.nextLine();
        System.out.print("Periodo Amortizacion: ");
        double años = teclado.nextDouble();
        teclado.nextLine();
        
        Prestamo prest1 = new Prestamo(prestamo,interesAnual,años);


        Socket cliente = new Socket("localhost",6000);
        ObjectOutputStream sal = new ObjectOutputStream(cliente.getOutputStream());
        ObjectInputStream ent = new ObjectInputStream(cliente.getInputStream());

        sal.writeObject(prest1);

        Prestamo prestamoActualizado = (Prestamo) ent.readObject();

        System.out.println("\nTipo Interes Mensual: "+prestamoActualizado.getInteresMensual());
        System.out.println("Cuota: "+prestamoActualizado.getCuota());


        ent.close();
        sal.close();
        cliente.close();
        teclado.close();
    }
}
