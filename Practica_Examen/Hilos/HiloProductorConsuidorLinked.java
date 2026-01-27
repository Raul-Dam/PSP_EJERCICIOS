package PSP_EJERCICIOS.Practica_Examen.Hilos;

import java.util.LinkedList;

public class HiloProductorConsuidorLinked {

    public static void main(String[] args) {
        Tienda tienda = new Tienda();

        Consumidor con = new Consumidor(tienda);
        Productor pro = new Productor(tienda);

        con.start();
        pro.start();

        try {
            con.join();
            pro.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}

class Consumidor extends Thread{

    Tienda tienda;
    int total=0;


    public Consumidor(Tienda tienda){
        this.tienda=tienda;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            total += tienda.consumir();
        }
        System.out.println("Total: "+total);
    }
    

}


class Productor extends Thread{

    Tienda tienda;


    public Productor(Tienda tienda){
        this.tienda=tienda;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            tienda.producir((int)(Math.random()*100));
        }
    }


}


class Tienda{
    LinkedList<Integer> cola;

    public Tienda(){
        this.cola=new LinkedList<>();
    }

    public synchronized void producir(int producto){
        while(cola.size()==5){
            System.out.println("Cola llena. Productor esperando...");
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        cola.add(producto);
        notifyAll();
    }

    public synchronized int consumir(){
        while(cola.size()==0){
            System.out.println("Cola vacia. Consumidor esperando...");
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        int numero=cola.poll();
        notifyAll();
        return numero;
        
    }

}
