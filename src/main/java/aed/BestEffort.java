package aed;

import java.util.ArrayList;

public class BestEffort {
    // Estadisticas se encarga de devolver los datos correspondientes a las ciudades
    private Estadisticas estadisticas;
    private MaxHeapRedituables heapRedituables;
    private MinHeapAntiguos heapAntiguos;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        this.estadisticas = new Estadisticas(cantCiudades);
        this.heapRedituables = new MaxHeapRedituables(traslados);
        this.heapAntiguos = new MinHeapAntiguos(traslados);
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados) {
            heapAntiguos.insertar(traslado);
            heapRedituables.insertar(traslado);
        }
    }

    public int[] despacharMasRedituables(int n){
        int [] traslados = new int[n];

        // O(n * (log |T| + log |C|))
        for (int i = 0; i < n; i++) {
            // O(log |T|)
            Traslado traslado = heapRedituables.extraerMasRedituable();

            if (traslado == null) {break;}
            traslados[i] = traslado.id;
            // O(log |T|)
            heapAntiguos.eliminar(traslado);

            // O(log |C|)
            estadisticas.despacharTraslado(traslado);
        }
        return traslados;
    }

    public int[] despacharMasAntiguos(int n){
        int [] traslados = new int[n];
        // O(n * (log |T| + log |C|))
        for (int i = 0; i < n; i++) {
            // O(log |T|)
            Traslado traslado = heapAntiguos.extraerMasAntiguo();

            if (traslado == null) {break;}
            traslados[i] = traslado.id;
            // O(log |T|)
            heapRedituables.eliminar(traslado);

            // O(log |C|)
            estadisticas.despacharTraslado(traslado);
        }
        return traslados;
    }

    public int ciudadConMayorSuperavit(){
        return estadisticas.ciudadConMayorSuperavit();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return estadisticas.ciudadesConMayorGanancia();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return estadisticas.ciudadesConMayorPerdida();
    }

    public int gananciaPromedioPorTraslado(){
        return estadisticas.gananciaPromedioPorTraslado();
    }
    
}
