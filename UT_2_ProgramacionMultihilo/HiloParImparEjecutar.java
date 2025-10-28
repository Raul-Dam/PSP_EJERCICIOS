package PSP_EJERCICIOS.UT_2_ProgramacionMultihilo;

class HiloParImparEjecutar {

    public static void main(String[] args){
        HiloParImpar hiloPar =new HiloParImpar(1);
        HiloParImpar hiloImpar =new HiloParImpar(2);

        Thread hilo1 = new Thread(hiloPar);
        hilo1.setName("01");

        Thread hilo2 = new Thread(hiloImpar);
        hilo2.setName("02");

        hilo1.start();
        hilo2.start();


    }
    
}