package aed;

import java.util.ArrayList;

// Clase que se encarga de ordenar los traslados por más antiguos en un heap
public class MinHeapAntiguos {
     ArrayList<Traslado> heap;
    public MinHeapAntiguos(Traslado[] traslados){
        // Al inicializar el sistema, ingresa una lista de traslados en forma desordenada
        // Usamos construirMinHeap para ordenarlos por más antiguos
        // O(|T|)
        this.heap = new ArrayList<>();
        for (Traslado traslado : traslados) {heap.add(traslado);}
        construirMinHeap();
    }

    private void construirMinHeap(){
        // Le asignamos a cada traslado su indice en el heap, al principio sin orden
        // O(|T|)
        for (int i = 0; i < heap.size(); i++) {
            heap.get(i).indiceAntiguos = i;
        }
        // Ordenamos los traslados por más antiguos implementando el algoritmo de floyd
        // O(|T|)
        for (int indice = (heap.size() / 2) - 1; indice >= 0; indice--)
            minHeapify(indice);

    }

    private void  minHeapify(int indice){
        // Hacemos un sift down para mantener la propiedad de heap
        // O(log |T|)
        while (indice < heap.size()) {
            int indiceHijoIzquierdo = 2 * indice + 1;
            int indiceHijoDerecho = 2 * indice + 2;
            int indiceMenor;

            if (indiceHijoIzquierdo < heap.size() && comparar(heap.get(indiceHijoIzquierdo), heap.get(indice)) < 0) {
                indiceMenor = indiceHijoIzquierdo;
            } else {
                indiceMenor = indice;
            }

            if (indiceHijoDerecho < heap.size() && comparar(heap.get(indiceHijoDerecho), heap.get(indiceMenor)) < 0) {
                indiceMenor = indiceHijoDerecho;
            }

            if (indiceMenor == indice) {
                return;
            }
            intercambiarAntiguos(indice, indiceMenor);
            indice = indiceMenor;
        }
    }
    private int comparar(Traslado t1, Traslado t2) {return Integer.compare(t1.timestamp, t2.timestamp);
    }
    private void intercambiarAntiguos(int i, int j){
        // Función auxiliar que sirve para hacer un swap de los indices de dos traslados dentro del heap
        // O(1)
        Traslado traslado = heap.get(i);
        heap.set(i, heap.get(j));
        heap.get(i).indiceAntiguos = i;
        heap.set(j, traslado);
        heap.get(j).indiceAntiguos = j;

    }

    public Traslado extraerMasAntiguo(){
        // Extraemos el traslado más antiguo del heap y lo devolvemos
        // O(log |T|)
        if (heap.isEmpty()) {return null;}
        Traslado antiguo = heap.get(0);
        intercambiarAntiguos(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        minHeapify(0);
        return antiguo;
    }

    public void insertar(Traslado traslado){
        // Insertamos un traslado en el heap y lo ordenamos
        // O(log |T|)
        heap.add(traslado);
        int indice = heap.size() - 1;
        traslado.indiceAntiguos = indice;
        int indicePadre = (indice-1) / 2;

        while (indice > 0 && comparar(heap.get(indicePadre), heap.get(indice)) > 0){
            intercambiarAntiguos(indice, indicePadre);
            indice = indicePadre;
            indicePadre = (indice-1) / 2;
        }
    }

    public void eliminar(Traslado traslado) {
        // Eliminamos un traslado del heap y lo ordenamos
        // O(log |T|)
        int index = traslado.indiceAntiguos;
        intercambiarAntiguos(traslado.indiceAntiguos, heap.size() - 1);
        heap.remove(heap.size() - 1);
        if (index == heap.size()) {return;}
        traslado = heap.get(index);

        while (index > 0) {
            int padreIndex = (index - 1) / 2;
            Traslado padre = heap.get(padreIndex);
            if (comparar(padre, traslado) < 0) {
                break;
            }
            intercambiarAntiguos(index, padreIndex);
            index = padreIndex;
        }
        traslado.indiceAntiguos = index;
        minHeapify(index);
    }
}
