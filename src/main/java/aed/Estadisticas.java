package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Estadisticas {
    private MaxHeap<Ciudad> heapSuperavit;
    private ArrayList<Integer> ciudadesMayorGanancia;
    private ArrayList<Integer> ciudadesMayorPerdida;
    private int maxGanancia;
    private int maxPerdida;
    private int gananciaTotal;
    private int trasladosTotales;
    
    public Estadisticas(int cantidadCiudadaes){

        // inicializamos el heap para el superavit
        // O(|cantidadCiudades|)
        this.heapSuperavit = null; //todo crear el heap
        
        // inicializamos los valores de las estadisticas
        this.maxGanancia = 0;
        this.maxPerdida = 0;
        this.gananciaTotal = 0;
        this.trasladosTotales = 0;
        
        // inicializamos las listas de ciudades con mayor ganancia y perdida
        // O(|cantidadCiudades|)
        this.ciudadesMayorGanancia = new ArrayList<Integer>();
        for (int i = 0; i < cantidadCiudadaes; i++) {this.ciudadesMayorGanancia.add(i);
        }
        this.ciudadesMayorPerdida = new ArrayList<Integer>();
        for (int i = 0; i < cantidadCiudadaes; i++) {this.ciudadesMayorPerdida.add(i);
        }
    }
    public void registrarTraslados(Traslado[] traslados){
        // Por cada traslado, actualizamos las estadisticas
        // O(|traslados|*log|C|)
        for (Traslado traslado : traslados) {
            // Recalculamos las estadisticas de las ciudades del traslado
            // O(1) (ya que las funciones del arraylist add y clear son O(1))
            Ciudad ciudadOrigen = null; //todo buscar en heap
            Ciudad ciudadDestino = null; //todo buscar en heap
            ciudadOrigen.agregarGanancia(traslado.gananciaNeta);
            ciudadDestino.agregarPerdida(traslado.gananciaNeta);
            this.gananciaTotal += traslado.gananciaNeta;
            this.trasladosTotales++;

            if (ciudadOrigen.obtenerGanancia() == this.maxGanancia && traslado.gananciaNeta > 0){
                this.ciudadesMayorGanancia.add(ciudadOrigen.obtenerId());
            } else if (ciudadOrigen.obtenerGanancia() > this.maxGanancia) {
                this.maxGanancia = ciudadOrigen.obtenerGanancia();
                this.ciudadesMayorGanancia.clear();
                this.ciudadesMayorGanancia.add(ciudadOrigen.obtenerId());
            }

            if (ciudadDestino.obtenerPerdida() == this.maxPerdida && traslado.gananciaNeta > 0){
                this.ciudadesMayorPerdida.add(ciudadDestino.obtenerId());
            } else if (ciudadDestino.obtenerPerdida() > this.maxPerdida) {
                this.maxPerdida = ciudadDestino.obtenerPerdida();
                this.ciudadesMayorPerdida.clear();
                this.ciudadesMayorPerdida.add(ciudadDestino.obtenerId());
            }

            // actualizamos el heap superavit
            // O(log|C|)
            //todo actualizar heap superavit
        }
    }
    public int ciudadConMayorSuperavit(){
        // Devolvemos el maximo del heap superavit
        // O(1)
        return 0; //todo devolver maximo del heap
    }
    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Devolvemos la lista de ciudades con mayor ganancia
        // O(1)
        return this.ciudadesMayorGanancia;
    }
    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Devolvemos la lista de ciudades con mayor perdida
        // O(1)
        return this.ciudadesMayorPerdida;
    }
    public int gananciaPromedioPorTraslado(){
        // Devolvemos la división de la ganancia total por la cantidad de traslados
        // O(1)
        return this.gananciaTotal / this.trasladosTotales;
    }
}
