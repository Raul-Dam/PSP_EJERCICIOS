import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketPrestamo {
    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        int numeroPuerto = 6000;
        ServerSocket servidor = new ServerSocket(numeroPuerto);
        
        
        Socket cliente = servidor.accept();
        System.out.println("Servidor Cuotas preparado ...");
        

        ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
        ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());

        Prestamo prestamoRecibido = (Prestamo) inObjeto.readObject();

        double c = prestamoRecibido.getImporte();
        double t =(prestamoRecibido.getInteresAnual())/(12*100);
        double n = prestamoRecibido.getAños()*12;

        double cuota=(c*t*Math.pow(1+t,n))/(Math.pow(1+t,n)-1);


        prestamoRecibido.setInteresMensual(t);
        prestamoRecibido.setCuota(cuota);

        outObjeto.writeObject(prestamoRecibido);

        outObjeto.close();
        inObjeto.close();
        cliente.close();
        servidor.close();
    }
}
