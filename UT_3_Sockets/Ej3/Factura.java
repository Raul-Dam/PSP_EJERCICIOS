package PSP_EJERCICIOS.UT_3_Sockets.Ej3;

import java.io.Serializable;

public class Factura implements Serializable {
    private String numeroFactura;
    private String fechaFactura;
    private double importe;
    private String tipoIva;
    private double iva;
    private double importeTotal;

    public Factura(String numeroFactura, String fechaFactura, double importe, String tipoIva) {
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.importe = importe;
        this.tipoIva = tipoIva.toUpperCase();
    }


    public String getTipoIva() { return tipoIva; }
    public double getImporte() { return importe; }
    
    public void setIva(double iva) { this.iva = iva; }
    public double getIva() { return iva; }
    
    public void setImporteTotal(double importeTotal) { this.importeTotal = importeTotal; }
    public double getImporteTotal() { return importeTotal; }

    @Override
    public String toString() {
        return "\nFACTURA\n" +
               "Número: " + numeroFactura + "\n" +
               "Fecha: " + fechaFactura + "\n" +
               "Importe Base: " + importe + " €\n" +
               "Tipo IVA: " + tipoIva + "\n" +
               "IVA Calculado: " + iva + " €\n" +
               "TOTAL: " + importeTotal + " €";
    }


}
