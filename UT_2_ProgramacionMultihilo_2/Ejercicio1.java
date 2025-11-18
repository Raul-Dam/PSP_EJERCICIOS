package PSP_EJERCICIOS.UT_2_ProgramacionMultihilo_2;

import java.util.ArrayList;

class Caja {
    public int id; 

    public Caja(int id) {
        this.id = id;
    }
    

    
    public synchronized void atenderCliente(int idcliente) {
        System.out.println("Caja "+ id+" atiende a "+ idcliente );
    }
}


class Cliente extends Thread {
    private Supermercado supermercado;
    private int id;

    public Cliente(int id,Supermercado supermercado) {
        this.id = id;
        this.supermercado = supermercado;
    }

    
    @Override
    public void run() {
        Caja cajaAsignada = supermercado.asignarCaja();

        cajaAsignada.atenderCliente(id);
    }
}

class Supermercado {
    private final ArrayList<Caja> cajas;
    private int indiceSiguiente = 0; // reparto circular

    public Supermercado(int numCajas) {
        cajas = new ArrayList<>();
        for (int i = 0; i < numCajas; i++) {
            cajas.add(new Caja(i + 1));
        }
    }

    // Asigna una caja de forma circular
    public synchronized Caja asignarCaja() {
        Caja caja = cajas.get(indiceSiguiente);
        indiceSiguiente = (indiceSiguiente + 1) % cajas.size();
        return caja;
    }
}

public class Ejercicio1{

    public static void main(String[] args) {
        System.out.println("=== Supermercado Abierto ===");

        Supermercado nuevo = new Supermercado(3);
        Thread[] clientes = new Thread[15];
    
        for (int i =0;i<clientes.length;i++){
            clientes[i] = new Cliente(i+1, nuevo);
            clientes[i].start();
        }

        for (Thread c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("=== Supermercado Cerrado ===");
    }
}