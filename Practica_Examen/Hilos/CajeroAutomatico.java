package PSP_EJERCICIOS.Practica_Examen.Hilos;

public class CajeroAutomatico {
    public static void main(String[] args){

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

class CuentaBancaria {
    int saldo = 1000;

    synchronized void sacarDinero (String nombre, int importe)
    {
        System.out.println("Sacar "+importe+" Total: "+saldo);
        saldo-=importe;
        System.out.println("Sacado "+importe+" Total: "+saldo);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    synchronized void ingresarDinero (String nombre, int importe)
    {

        System.out.println("Ingresar "+importe+" Total: "+saldo);
        saldo+=importe;
        System.out.println("Ingrasado "+importe+" Total: "+saldo);

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class HiloSacarDinero extends Thread {

    private CuentaBancaria cuenta;
    private String nombre;
    private int cantidad;

    // Constructor de la clase
    HiloSacarDinero(CuentaBancaria micuenta, String nombre, int cantidad)
    {
        this.cuenta=micuenta;
        this.nombre=nombre;
        this.cantidad=cantidad;
    }

    public void run() {
        cuenta.sacarDinero(nombre,cantidad);
    }
}


class HiloIngresarDinero extends Thread {

    private CuentaBancaria cuenta;
    private String nombre;
    private int cantidad;
    // Constructor de la clase
    HiloIngresarDinero (CuentaBancaria micuenta, String nombre, int cantidad)
    {
        this.cuenta=micuenta;
        this.nombre=nombre;
        this.cantidad=cantidad;
    }
    public void run() {
        cuenta.ingresarDinero(nombre, cantidad);
    }
}
