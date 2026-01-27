package PSP_EJERCICIOS.Practica_Examen.Hilos;

import java.util.LinkedList;

public class HiloGasolineraLinked {

    public static void main(String[] args) {
        DepositoLinked deposito = new DepositoLinked();

        ProductoGasolinaLinked pro = new ProductoGasolinaLinked(deposito);
        Thread[] camiones = new Thread[3];

        for (int i = 0; i < camiones.length; i++) {
            // Pasamos ID (i+1) para identificar al camión
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
    private final int CAPACIDAD_MAXIMA = 1; // Capacidad 1 para forzar turno estricto

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

        // 2. Sección Crítica: Añadimos a la lista
        cola.add(litros);
        System.out.println("Refinería: Llenó el depósito con " + litros + " litros.");

        // 3. Avisamos a los camiones que están esperando
        notifyAll();
    }

    // Método para el Consumidor (Camión)
    // Devuelve true si carga, false si hubo error (opcional)
    public synchronized boolean cargarCamion(int idCamion) {
        // 1. MIENTRAS esté vacía, esperamos (wait)
        while (cola.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        // 2. Sección Crítica: Sacamos de la lista
        int litros = cola.removeFirst(); // o cola.poll()
        System.out.println("   🚛 Camión " + idCamion + " cargó " + litros + " litros.");

        // 3. Avisamos al productor que hay sitio libre
        notifyAll();
        return true;
    }
}