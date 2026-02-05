package PSP_EJERCICIOS.Practica_Examen.Hilos;

import java.util.concurrent.ArrayBlockingQueue;

public class HiloGasolineraMultipleDeposito {

    public static void main(String[] args) {
        Deposito deposito = new Deposito();

        ProductoGasolina pro = new ProductoGasolina(deposito);
        Thread[] camiones = new Thread[3];

        for (int i = 0; i < camiones.length; i++) {
            camiones[i] = new Camion(deposito, i + 1);
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
        
        System.out.println("Simulación finalizada.");
    }
}

class ProductoGasolina extends Thread {
    Deposito deposito;

    public ProductoGasolina(Deposito deposito) {
        this.deposito = deposito;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            deposito.cargar();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Camion extends Thread {
    Deposito deposito;
    int capacidad = 0;
    int id;

    public Camion(Deposito deposito, int id) {
        this.deposito = deposito;
        this.id = id;
    }

    @Override
    public void run() {
        while (capacidad < 5) {
            capacidad += deposito.rellenar(id);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("--- Camión " + id + " se va LLENO ---");
    }
}

class Deposito {
    private ArrayBlockingQueue<Integer>[] tanques;

    public Deposito() {
        tanques = new ArrayBlockingQueue[3];
        for (int i = 0; i < tanques.length; i++) {
            tanques[i] = new ArrayBlockingQueue<>(1);
        }
    }

    public void cargar() {
        int indiceTanque = (int) (Math.random() * 3);
        
        try {
            tanques[indiceTanque].put((int) (Math.random() * 1000));
            System.out.println("Productor: Cargó combustible en TANQUE " + indiceTanque);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int rellenar(int idCamion) {
        int indiceTanque = (int) (Math.random() * 3);
        
        try {
            tanques[indiceTanque].take();
            System.out.println("Camión " + idCamion + ": Repostó del TANQUE " + indiceTanque);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 1;
    }
}