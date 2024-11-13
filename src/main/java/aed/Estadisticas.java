package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Estadisticas {
    // array de las ciudades con las mayores ganancias
    private ArrayList<Integer> ciudadesMayorGanancia;
    // array de las ciudades con las mayores perdidas
    private ArrayList<Integer> ciudadesMayorPerdida;
    // array de las ciudades
    private ArrayList<Ciudad> ciudades;
    // comparador que da un orden para el heap de superavit de las ciudades
    private Comparator<Ciudad> comparadorSuperavit;
    // heap de superavit de las ciudades, ordena los id de las ciudadesen base al comprador
    private int[] heapSuperavit;
    // int que contiene la mayor ganancia que se ha registrado de una ciudad
    private int maxGanancia;
    // int que contiene la mayor perdida que se ha registrado de una ciudad
    private int maxPerdida;
    // contiene la ganancia total de todas las ciudades
    private int gananciaTotal;
    // contiene la cantidad de traslados que se han registrado
    private int trasladosTotales;
    
    public Estadisticas(int cantidadCiudadaes){
        // inicializamos el array ciudades que contiene a las ciudades indexadas por el indice de la ciudad
        // inicializamos el array heapSuperavit que representa el heap para el superavit
        // O(|cantidadCiudades|)
        this.heapSuperavit = new int[cantidadCiudadaes];
        this.ciudades = new ArrayList<>();
        for (int i = 0; i < cantidadCiudadaes; i++) {
            this.ciudades.add(new Ciudad(i, i));
            this.heapSuperavit[i] = i;
        }
        
        // inicializamos los valores de las estadisticas
        // O(1)
        this.maxGanancia = 0;
        this.maxPerdida = 0;
        this.gananciaTotal = 0;
        this.trasladosTotales = 0;
        this.comparadorSuperavit = new Comparator<Ciudad>() {
            @Override
            public int compare(Ciudad c1, Ciudad c2) {
                if (c1.getSuperavit() == c2.getSuperavit()) {
                    return c2.obtenerId() - c1.obtenerId();
                }
                return c1.getSuperavit() - c2.getSuperavit();
            }
        };
        
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
        // Por cada traslado, actualizamos las estadisticas y el heap superavit
        // O(|traslados|*log|C|)
        for (Traslado traslado : traslados) {
            // Recalculamos las estadisticas de las ciudades del traslado
            // O(1) (ya que las funciones del arraylist add y clear son O(1))
            Ciudad ciudadOrigen = ciudades.get(traslado.origen);
            Ciudad ciudadDestino = ciudades.get(traslado.destino);
            ciudadOrigen.agregarGanancia(traslado.gananciaNeta);
            ciudadDestino.agregarPerdida(traslado.gananciaNeta);
            this.gananciaTotal += traslado.gananciaNeta;
            this.trasladosTotales++;

            if (ciudadOrigen.getGanancia() == this.maxGanancia && traslado.gananciaNeta > 0){
                this.ciudadesMayorGanancia.add(ciudadOrigen.obtenerId());
            } else if (ciudadOrigen.getGanancia() > this.maxGanancia) {
                this.maxGanancia = ciudadOrigen.getGanancia();
                this.ciudadesMayorGanancia.clear();
                this.ciudadesMayorGanancia.add(ciudadOrigen.obtenerId());
            }

            if (ciudadDestino.getPerdida() == this.maxPerdida && traslado.gananciaNeta > 0){
                this.ciudadesMayorPerdida.add(ciudadDestino.obtenerId());
            } else if (ciudadDestino.getPerdida() > this.maxPerdida) {
                this.maxPerdida = ciudadDestino.getPerdida();
                this.ciudadesMayorPerdida.clear();
                this.ciudadesMayorPerdida.add(ciudadDestino.obtenerId());
            }

            // actualizamos el heap superavit
            // O(log|C|)
            actualizarHeapSuperavit(ciudadOrigen.obtenerId());
            actualizarHeapSuperavit(ciudadDestino.obtenerId());
        }
    }
    private void actualizarHeapSuperavit(int idCiudad){
        // Actualizamos el heap superavit
        // O(log|C|)
        siftDown(ciudades.get(idCiudad).getIndiceSuperavit());
        siftUp(ciudades.get(idCiudad).getIndiceSuperavit());
    }
    private void siftUp(int index) {
        // Vamos subiendo el nodo en el heap hasta que se cumpla la condición de heap
        // con cada swap actualizamos la variable indiceSuperavit de la ciudad
        // O(log|C|)
        Ciudad ciudad = ciudades.get(heapSuperavit[index]);
        while (index > 0) {
            int padreIndex = (index - 1) / 2;
            Ciudad padre = ciudades.get(heapSuperavit[padreIndex]);
            if ( comparadorSuperavit.compare(padre, ciudad) > 0) {
                break;
            }
            heapSuperavit[index] = heapSuperavit[padreIndex];
            padre.setIndiceSuperavit(index);
            index = padreIndex;
        }
        heapSuperavit[index] = ciudad.obtenerId();
        ciudad.setIndiceSuperavit(index);
    }
    private void siftDown(int index) {
        // Vamos bajando el nodo en el heap hasta que se cumpla la condición de heap
        // con cada swap actualizamos la variable indiceSuperavit de la ciudad
        // O(log|C|)
        Ciudad ciudad = ciudades.get(heapSuperavit[index]);
        while (index < heapSuperavit.length / 2) {
            int hijoIzquierdoIndex = 2 * index + 1;
            int hijoDerechoIndex = hijoIzquierdoIndex + 1;
            int hijoMayorIndex = hijoIzquierdoIndex;
            Ciudad hijoMayor = ciudades.get(heapSuperavit[hijoIzquierdoIndex]);
            if (hijoDerechoIndex < heapSuperavit.length &&
                    comparadorSuperavit.compare(ciudades.get(heapSuperavit[hijoDerechoIndex]), hijoMayor) > 0) {
                hijoMayorIndex = hijoDerechoIndex;
                hijoMayor = ciudades.get(heapSuperavit[hijoDerechoIndex]);
            }
            if (comparadorSuperavit.compare(ciudad, hijoMayor) > 0) {
                break;
            }
            heapSuperavit[index] = hijoMayorIndex;
            hijoMayor.setIndiceSuperavit(index);
            index = hijoMayorIndex;
        }
        heapSuperavit[index] = ciudad.obtenerId();
        ciudad.setIndiceSuperavit(index);
    }
    public int ciudadConMayorSuperavit(){
        // Devolvemos el maximo del heap superavit
        // O(1)
        return this.ciudades.get(this.heapSuperavit[0]).obtenerId();
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
