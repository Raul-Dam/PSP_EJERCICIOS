package PSP_EJERCICIOS.UT_2_ProgramacionMultihilo_3;

import java.util.ArrayList;

class Cliente extends Thread{
    private int id;
    private Gimnasio gimnasio;

    public Cliente(int id,Gimnasio gimnasio){
        this.id = id;
        this.gimnasio= gimnasio;
    }

    @Override
    public void run() {
        gimnasio.entrar(id);

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gimnasio.salir(id);
    }
}


class Gimnasio{
    private int aforoActual=0;
    private int aforoMaximo;


    public Gimnasio(int aforoMaximo){
        this.aforoMaximo=aforoMaximo;
    }

    public synchronized void entrar(int idcliente){
        while (aforoActual>=aforoMaximo) {
            try{
                System.out.println("Cliente "+idcliente+" esperando para entrar (Aforo actual: "+aforoActual+" )");
                wait();
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        aforoActual++;
        System.out.println("Cliente "+idcliente+" entra (Aforo actual: "+aforoActual+" )");
    }

    public synchronized void salir (int idcliente){
        aforoActual--;
        System.out.println("Cliente "+idcliente+" sale (Aforo actual: "+aforoActual+" )");
        notifyAll();
    }

}


public class Gym {

    public static void main(String[] args) {
        Gimnasio gym = new Gimnasio(5);

        ArrayList<Cliente> clientes= new ArrayList<>();

        System.out.println("Abre el gimnasio (Aforo maximo 5 personas)");

        for(int i=0;i<20;i++){
            Cliente n = new Cliente(i+1,gym);
            clientes.add(n);
            n.start();

        }

        for (Cliente h : clientes) {
            try {
                h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    
        System.out.println("Cierra el gimnasion (No queda nadie)");
    }
    
    
    

}
