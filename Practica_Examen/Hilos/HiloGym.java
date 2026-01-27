package PSP_EJERCICIOS.Practica_Examen.Hilos;

public class HiloGym {
    public static void main(String[] args) {
        Gym gym = new Gym(5);

        Thread[] clientes = new Thread[15];

        for(int i=0;i<clientes.length;i++){
            clientes[i]=new ClienteGym(i+1, gym);
            clientes[i].start();
        } 

        for (Thread c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Gym{
    int aforomax,aforoactual;

    public Gym(int aforomax){
        this.aforomax=aforomax;
        aforoactual=0;
    }

    public void entrenar(int idcliente){

        System.out.println("Cliente "+idcliente+" esta entrenando  (Aforo: "+aforoactual+")");
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Cliente "+idcliente+" se va");
        
        
    }

    public int getAforoActual(){
        return aforoactual;
    }
    public int getAforoMax(){
        return aforomax;
    }

    public synchronized void  entrarGym(){
        while(aforoactual>=aforomax){
            try{
            wait();
            }catch(InterruptedException e){
            e.printStackTrace();
            }
        }
        aforoactual++;
    }

    public synchronized void salirgym(){
        aforoactual--;
        notifyAll();
    }


}

class ClienteGym extends Thread{
    int idcliente;
    Gym gym;

    public ClienteGym(int idcliente,Gym gym){
        this.idcliente=idcliente;
        this.gym=gym;
    }

    @Override
    public void run() {
        gym.entrarGym();
        gym.entrenar(idcliente);
        gym.salirgym();
    }

}



