package PSP_EJERCICIOS.Practica_Examen.Hilos;

public class HiloSumasRestasEjecutar {

    public static void main(String[] args) {
        Thread HiloSuma1 = new Thread(new HiloSumasRestas(100,"+"));
        Thread HiloResta2 = new Thread(new HiloSumasRestas(100,"-"));
        Thread HiloSuma3 = new Thread(new HiloSumasRestas(1,"+"));
        Thread HiloResta4 = new Thread(new HiloSumasRestas(1,"-"));

        HiloSuma1.start();
        HiloResta2.start();
        HiloSuma3.start();
        HiloResta4.start();
    }
    


}


class HiloSumasRestas implements Runnable{

    private static int numero=1000;
    private int numveces;
    private String operacion;


    HiloSumasRestas (int numveces, String operacion){
        this.numveces=numveces;
        this.operacion=operacion;
    }

    public int incrementar (int numveces)
    {
        return numero+numveces;
    }

    int decrementar (int numveces)
    {
        return numero-numveces;
    }

    public void run() {

        switch (operacion) {
            case "+":
                numero = incrementar(numveces);
                System.out.println(numero);
                
                break;
        
            case "-":
                numero = decrementar(numveces);
                System.out.println(numero);
                break;
        
            default:
                break;
        }
    }

}
