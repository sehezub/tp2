package aed;

public class Estadisticas {
    private Ciudades ciudades;
    private int maxGanancia;
    private int maxPerdida;
    private int maxSuperavit;
    private int gananciaTotal;
    private int trasladosTotales;
    public Estadisticas(int cantidad){
        this.ciudades = new Ciudades(cantidad);
        this.maxGanancia = 0;
        this.maxPerdida = 0;
        this.maxSuperavit = 0;
        this.gananciaTotal = 0;
        this.trasladosTotales = 0;
    }
}
