package PSP_EJERCICIOS.UT_2_ProgramacionMultihilo;

class HiloParImpar implements Runnable{
 
    //Atributos
    private int tipo;
 
    //Constructor
    public HiloParImpar(int tipo) {
        this.tipo = tipo;
    }
     
    public void run() {
         
            switch (tipo) {
                case 1:
                    //numeros pares
                    for (int i = 1; i <=100; i++) {
                        if (i%2==0)
                        System.out.println("HILO "+Thread.currentThread().getId()+" generando número par "+i);
                    }
                    break;
                case 2:
                    //numeros impares
                    for (int i = 101; i <=200; i++) {
                        if (i%2!=0)
                        System.out.println("HILO "+Thread.currentThread().getId()+" generando número impar "+i);

                    }
                    break;
                     
            }
 
        }
         
        
}

public class HiloParImparEjecutar {
 
    public static void main(String[] args) {
                
        Thread t1 = new Thread(new HiloParImpar(1));
        Thread t2 = new Thread(new HiloParImpar(2));
         
        t1.start();
        t2.start();
        
         
    }
 
}
