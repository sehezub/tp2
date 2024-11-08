package aed;

public class Ciudades {
    private int[] gananciasCiudad;
    private int[] perdidasCiudad;
    public Ciudades(int cantidad){
        gananciasCiudad = new int[cantidad];
        perdidasCiudad = new int[cantidad];
    }
    public void agregarGanancia(int ciudad, int ganancia){
        gananciasCiudad[ciudad] += ganancia;
    }
    public void agregarPerdida(int ciudad, int perdida){
        perdidasCiudad[ciudad] += perdida;
    }
    public int obtenerGanancia(int ciudad){
        return gananciasCiudad[ciudad];
    }
    public int obtenerPerdida(int ciudad){
        return perdidasCiudad[ciudad];
    }

}
