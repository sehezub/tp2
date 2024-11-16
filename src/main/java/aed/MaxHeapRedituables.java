package aed;

import java.util.ArrayList;

public class MaxHeapRedituables {
     ArrayList<Traslado> heap;

    public MaxHeapRedituables(Traslado[] traslados) {
        this.heap = new ArrayList<>();
        for (Traslado traslado : traslados) {heap.add(traslado);}
        construirMaxHeap();
    }

    private void construirMaxHeap() {
        for (int i = 0; i < heap.size(); i++) {
            heap.get(i).indiceRedituables = i;
        }
        for (int indice = (heap.size() / 2) - 1; indice >= 0; indice--) {
            maxHeapify(indice);
        }
    }

    private void maxHeapify(int indice) {
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
            intercambiarRedituables(indice, indiceMenor);
            indice = indiceMenor;
        }
    }

    private int comparar(Traslado t1, Traslado t2) {
        return Integer.compare(t2.gananciaNeta, t1.gananciaNeta);
    }

    private void intercambiarRedituables(int i, int j) {
        Traslado traslado = heap.get(i);
        heap.set(i, heap.get(j));
        heap.get(i).indiceRedituables = i;
        heap.set(j, traslado);
        heap.get(j).indiceRedituables = j;
    }

    public Traslado extraerMasRedituable() {
        if (heap.isEmpty()) {return null;}
        Traslado redituable = heap.get(0);
        intercambiarRedituables(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        maxHeapify(0);
        return redituable;
    }

    public void insertar(Traslado traslado) {
        heap.add(traslado);
        int indice = heap.size() - 1;
        traslado.indiceRedituables = indice;
        int indicePadre = (indice - 1) / 2;

        while (indice > 0 && comparar(heap.get(indicePadre), heap.get(indice)) < 0) {
            intercambiarRedituables(indice, indicePadre);
            indice = indicePadre;
            indicePadre = (indice - 1) / 2;
        }
    }

    public void eliminar(Traslado traslado) {
        int index = traslado.indiceRedituables;
        intercambiarRedituables(traslado.indiceRedituables, heap.size() - 1);
        heap.remove(heap.size() - 1);
        if (index == heap.size()) {return;}
        traslado = heap.get(index);

        while (index > 0) {
            int padreIndex = (index - 1) / 2;
            Traslado padre = heap.get(padreIndex);
            if (comparar(padre, traslado) > 0) {
                break;
            }
            intercambiarRedituables(index, padreIndex);
            index = padreIndex;
        }
        traslado.indiceRedituables = index;
        maxHeapify(index);
    }
}
