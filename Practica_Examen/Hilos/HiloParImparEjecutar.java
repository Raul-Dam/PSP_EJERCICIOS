package PSP_EJERCICIOS.Practica_Examen.Hilos;

public class HiloParImparEjecutar {
    public static void main(String[] args) {
        Thread xx = new Thread(new HiloParImpar("xx", 1));
        Thread yy = new Thread(new HiloParImpar("yy", 2));
        
        yy.start();

        try {
            yy.join(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        xx.start();
    }
    
}

class HiloParImpar implements Runnable{
    
    private int tipo;
    private String nombre;

    HiloParImpar(String nombre, int tipo){
        this.nombre= nombre;
        this.tipo = tipo;
    }

    public void run(){
        switch (tipo) {
            case 1:
                for(int i=1;i<=100;i++){
                    if (i%2==0) {
                        System.out.println("HILO "+nombre+ " generando numero par "+i);
                    }
                }
                break;

            case 2:
                for(int i=101;i<=200;i++){
                    if (i%2!=0) {
                        System.out.println("HILO "+nombre+ " generando numero impar "+i);
                    }
                }
                break;
        
            default:
                break;
        }
    }

}
