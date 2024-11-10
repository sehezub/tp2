package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class MaxHeap<T> {
    protected ArrayList<T> heap;
    protected Comparator<T> comparator;

    public MaxHeap(Comparator<T> comparator, ArrayList<T> elementos) {
        this.heap = elementos;
        this.comparator = comparator;
    }

    public void apilar(T elemento) {
        heap.add(elemento);
        siftUp(heap.size() - 1);
    }

    public T desapilarMax() {
        if (heap.isEmpty()) {
            return null;
        }
        T elementoEliminado = heap.get(0);
        T ultimoElemento = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, ultimoElemento);
            siftDown(0);
        }
        return elementoEliminado;
    }

    public T maximo() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    private void siftUp(int index) {
        T elemento = heap.get(index);
        while (index > 0) {
            int padreIndex = (index - 1) / 2;
            T padre = heap.get(padreIndex);
            if (comparator.compare(elemento, padre) >= 0) {
                break;
            }
            heap.set(index, padre);
            index = padreIndex;
        }
        heap.set(index, elemento);
    }

    private void siftDown(int index) {
        T elemento = heap.get(index);
        while (index < heap.size() / 2) {
            int hijoIzquierdoIndex = 2 * index + 1;
            int hijoDerechoIndex = hijoIzquierdoIndex + 1;
            int hijoMenorIndex = hijoIzquierdoIndex;
            T hijoMenor = heap.get(hijoIzquierdoIndex);
            if (hijoDerechoIndex < heap.size() && comparator.compare(heap.get(hijoDerechoIndex), hijoMenor) < 0) {
                hijoMenorIndex = hijoDerechoIndex;
                hijoMenor = heap.get(hijoDerechoIndex);
            }
            if (comparator.compare(elemento, hijoMenor) <= 0) {
                break;
            }
            heap.set(index, hijoMenor);
            index = hijoMenorIndex;
        }
        heap.set(index, elemento);
    }
}
