package PSP_EJERCICIOS.Practica_Examen.Hilos;


import java.util.concurrent.ArrayBlockingQueue;

public class HiloProductorConsuidorArrayBlock {

    public static void main(String[] args) {

    
        ArrayBlockingQueue<Integer> cola = new ArrayBlockingQueue<>(5);

        Productor1 pro = new Productor1(cola);
        Consumidor1 con = new Consumidor1(cola);

        pro.start();
        con.start();

        try {
            con.join();
            pro.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Productor1 extends Thread{

    ArrayBlockingQueue<Integer> cola;


    public Productor1(ArrayBlockingQueue<Integer> cola){
        this.cola=cola;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                cola.put((int)(Math.random()*100));
                
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
}

class Consumidor1 extends Thread{

    ArrayBlockingQueue<Integer> cola;
    int total=0;

    public Consumidor1(ArrayBlockingQueue<Integer> cola){
        this.cola=cola;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                total+=cola.take();
                try{
                Thread.sleep(2000);
                }catch(InterruptedException e){
                e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total: "+total);

    }
}