package PSP_EJERCICIOS.Practica_Examen.Hilos;

import java.util.LinkedList;

public class HiloGasolineraLinked {

    public static void main(String[] args) {
        DepositoLinked deposito = new DepositoLinked();

        ProductoGasolinaLinked pro = new ProductoGasolinaLinked(deposito);
        Thread[] camiones = new Thread[3];

        for (int i = 0; i < camiones.length; i++) {
            camiones[i] = new CamionLinked(deposito, i + 1);
        }

        pro.start();
        for (Thread c : camiones) {
            c.start();
        }

        try {
            pro.join();
            for (Thread c : camiones) {
                c.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("✅ FIN DE LA SIMULACION");
    }
}

class ProductoGasolinaLinked extends Thread {
    DepositoLinked deposito;

    public ProductoGasolinaLinked(DepositoLinked deposito) {
        this.deposito = deposito;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            int litros = (int) (Math.random() * 1000) + 1;
            deposito.llenarDeposito(litros);
            
            try { Thread.sleep(400); } catch (InterruptedException e) {} 
        }
    }
}

class CamionLinked extends Thread {
    DepositoLinked deposito;
    int cargas = 0;
    int id;

    public CamionLinked(DepositoLinked deposito, int id) {
        this.deposito = deposito;
        this.id = id;
    }

    @Override
    public void run() {
        while (cargas < 5) {
            if (deposito.cargarCamion(id)) {
                cargas++;
            }
            try { Thread.sleep(800); } catch (InterruptedException e) {}
        }
        System.out.println("👋 Camion " + id + " TERMINÓ sus cargas.");
    }
}


class DepositoLinked {
    private LinkedList<Integer> cola;
    private final int CAPACIDAD_MAXIMA = 1;

    public DepositoLinked() {
        this.cola = new LinkedList<>();
    }

    public synchronized void llenarDeposito(int litros) {

        while (cola.size() == CAPACIDAD_MAXIMA) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        cola.add(litros);
        System.out.println("Refinería: Llenó el depósito con " + litros + " litros.");
        notifyAll();
    }

    public synchronized boolean cargarCamion(int idCamion) {
        while (cola.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }


        int litros = cola.removeFirst(); 
        System.out.println("   🚛 Camión " + idCamion + " cargó " + litros + " litros.");

        notifyAll();
        return true;
    }
}