package PSP_EJERCICIOS.UT_2_ProgramacionMultihilo;

public class HiloParImpar implements Runnable {

    private int tipo;

    public HiloParImpar(int tipo){
        this.tipo=tipo;
    }

    @Override
    public void run() {
        if(this.tipo == 1){
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0) {
                    System.out.println("HILO " + Thread.currentThread().getName() + " generando número par " + i);
                }
            }
        }else if (this.tipo == 2) {
            
            for (int i = 101; i <= 200; i++) {
                if (i % 2 != 0) {
                    System.out.println("HILO " + Thread.currentThread().getName() + " generando número impar " + i);
                }
            }
        }
        
    }


    
}
