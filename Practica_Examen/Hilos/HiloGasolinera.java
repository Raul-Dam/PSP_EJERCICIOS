package PSP_EJERCICIOS.Practica_Examen.Hilos;

import java.util.concurrent.ArrayBlockingQueue;

public class HiloGasolinera {

    public static void main(String[] args) {
        Deposito deposito = new Deposito();

        ProductoGasolina pro=new ProductoGasolina(deposito);
        Thread[] camiones = new Thread[3];

        for (int i = 0; i < camiones.length; i++) {
            camiones[i]=new Camion(deposito);
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


    }
    
}



class ProductoGasolina extends Thread{
    Deposito deposito;

    public ProductoGasolina(Deposito deposito){
        this.deposito=deposito;
    }

    @Override
    public void run() {

        for (int i = 0; i < 15; i++) {
            deposito.cargar();
        }
    }

}

class Camion extends Thread{

    Deposito deposito;
    int capacidad=0;

    public Camion(Deposito deposito){
        this.deposito=deposito;
    }

    @Override
    public void run() {
        while(capacidad<5){
            capacidad +=deposito.rellenar();
        }
    }

}

class Deposito{

    ArrayBlockingQueue<Integer> deposito;

    public Deposito(){
        deposito = new ArrayBlockingQueue<>(1);
    }


    public void cargar(){
        try {
            deposito.put((int)(Math.random()*1000));
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Deposito: "+deposito.size());
    }


    public int rellenar(){
        try {
            deposito.take();
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

}