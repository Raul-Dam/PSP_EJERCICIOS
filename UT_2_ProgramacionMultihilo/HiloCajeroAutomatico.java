package PSP_EJERCICIOS.UT_2_ProgramacionMultihilo;

// Clase CuentaBancaria
// Definición de la cuenta con un saldo inicial y de las operaciones para
//ingresar dinero (método ingresarDinero) y sacar dinero (método sacarDinero)de // la cuenta
class CuentaBancaria {
    // Saldo inicial de la cuenta
    int saldo = 1000;

    // método sacarDinero:
    // nombre -> persona que realiza la operación
    // importe -> cantidad a retirar
    synchronized void sacarDinero(String nombre, int importe) {
        System.out.println(nombre + " va a sacar " + importe + " (Saldo actual: " + saldo + ")");

        if (saldo >= importe) {
            saldo -= importe;
            System.out.println(nombre + " HA SACADO " + importe + " (Saldo restante: " + saldo + ")");
        } else {
            
            System.out.println(nombre + " NO puede sacar " + importe + " Saldo insuficiente. (Saldo: " + saldo + ")");
        }

        // Después de la operación dormir el hilo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // método ingresarDinero
    // nombre -> persona que realiza la operación
    // importe -> cantidad a retirar
    synchronized void ingresarDinero(String nombre, int importe) {
        System.out.println( nombre + " va a ingresar " + importe + " (Saldo actual: " + saldo + ")");

        
        saldo += importe;
        System.out.println(nombre + " HA INGRESADO " + importe + " (Saldo nuevo: " + saldo + ")");

        // Después de la operación dormir el hilo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Clase HiloSacarDinero
class HiloSacarDinero extends Thread {

    private CuentaBancaria cuenta;
    private String nombre;
    private int cantidad;

    // Constructor de la clase
    HiloSacarDinero(CuentaBancaria micuenta, String nombre, int cantidad) {
        super(nombre);
        this.cuenta = micuenta;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
    
    public void run() {
        cuenta.sacarDinero(nombre, cantidad);
    }
}

// Clase HiloIngresarDinero
class HiloIngresarDinero extends Thread {

    private CuentaBancaria cuenta;
    private String nombre;
    private int cantidad;

    // Constructor de la clase
    HiloIngresarDinero(CuentaBancaria micuenta, String nombre, int cantidad) {
        super(nombre);
        this.cuenta = micuenta;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public void run() {
        cuenta.ingresarDinero(nombre, cantidad);
    }
}

// Clase Principal
public class HiloCajeroAutomatico {

    public static void main(String[] args) {

        CuentaBancaria cuenta = new CuentaBancaria();

        HiloIngresarDinero padre = new HiloIngresarDinero(cuenta, "Padre", 200);    
        HiloSacarDinero madre = new HiloSacarDinero(cuenta, "Madre", 800); 
        HiloIngresarDinero hijo1 = new HiloIngresarDinero(cuenta, "Hijo1", 300);  
        HiloSacarDinero hijo2 = new HiloSacarDinero(cuenta, "Hijo2", 800);   
        HiloSacarDinero abuelo = new HiloSacarDinero(cuenta, "Abuelo", 600); 

        padre.start();
        madre.start();
        hijo1.start();
        hijo2.start();
        abuelo.start();
        
        
        try {
            padre.join();
            madre.join();
            hijo1.join();
            hijo2.join();
            abuelo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        


    }
}
