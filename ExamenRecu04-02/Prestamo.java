import java.io.Serializable;

public class Prestamo implements Serializable{
    double importe,interesAnual,años,interesMensual,cuota;

    public Prestamo(double importe,double interesAnual,double años){
        this.importe=importe;
        this.interesAnual=interesAnual;
        this.años=años;
    }

    

    public double getImporte() {
        return importe;
    }

    public double getInteresAnual() {
        return interesAnual;
    }

    public double getAños() {
        return años;
    }

    public void setInteresMensual(double interesMensual){
        this.interesMensual=interesMensual;
    }

    public double getInteresMensual(){
        return interesMensual;
    }

    public void setCuota(double cuota){
        this.cuota=cuota;
    }

    public double getCuota(){
        return cuota;
    }


    
}
