package PSP_EJERCICIOS.Practica_Examen.Hilos;

import java.util.ArrayList;

class Supermercado {
    private ArrayList<Caja>cajas;

    public Supermercado(int numcajas){
        cajas=new ArrayList<>();
        for(int i=1;i<=numcajas;i++){
            cajas.add(new Caja(i));
        }
    }

    public synchronized Caja asignarCaja(){
        boolean ocupadas=true;
        do{

        for(Caja c : cajas){
            if(c.getocupada()!=true){
                c.setocupada(true);
               return c; 
            }
        }

        try{
            wait();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        }while(ocupadas);
        
        return new Caja(0);
    }

    public synchronized void liberarCaja(Caja caja){
        caja.setocupada(false);
        notifyAll();
    }

    
}


class Caja{
    int id;
    boolean ocupada;

    public Caja(int id){
        this.id=id;
        ocupada=false;
    }

    public synchronized void atender(int idcliente){
        System.out.println("Caja "+id+" atendiendo a cliente "+idcliente);
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public boolean getocupada(){
        return ocupada;
    }
    public void setocupada(boolean ocupada){
        this.ocupada=ocupada;
    }
}

class Cliente extends Thread{
    int id;
    Supermercado supermercado;

    public Cliente(int id, Supermercado supermercado){
        this.id=id;
        this.supermercado=supermercado;
    }

    @Override
    public void run() {
        Caja caja = supermercado.asignarCaja();
        caja.atender(id);
        supermercado.liberarCaja(caja);
    }

}

public class HiloSupermercado{
    public static void main(String[] args) {
        Supermercado dia = new Supermercado(3);
        Thread[] hilosclientes= new Thread[15];

        for(int i=0;i<hilosclientes.length;i++){
            hilosclientes[i]=new Cliente(i+1, dia);
            hilosclientes[i].start();
        }

        for (Thread c : hilosclientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

