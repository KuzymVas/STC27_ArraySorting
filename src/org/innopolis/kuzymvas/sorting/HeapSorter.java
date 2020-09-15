package org.innopolis.kuzymvas.sorting;

import org.innopolis.kuzymvas.exceptions.SameAgeNamePersonsException;

import java.util.Comparator;

public class HeapSorter<T> implements Sorter<T> {

    // Текущий компаратор
    private Comparator<T> currentComparator;

    @Override
    public void sort(T[] array, Comparator<T> comparator) throws SameAgeNamePersonsException {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null");
        }
        if (array == null) {
            return;
        }
        currentComparator = comparator;
        heapSort(array);
    }

    /**
     * Сравнивает два объекта с помощью текущего компаратора
     *
     * @param o1 - первый объект для сравнения
     * @param o2 - второй объейт для сравнения
     * @return - true, если первый объект строго больше второго, false в противном случае
     */
    private boolean greaterThan(T o1, T o2) {
        return currentComparator.compare(o1, o2) > 0;
    }

    /**
     * Сортировка массива с помощью бинарной кучи
     *
     * @param array - сортируемый массив
     */
    private void heapSort(T[] array) {
        // Строим кучу на весь массив (идем с конца, начиная с последнего элемента, у которого есть потомки)
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapifyRoot(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            // Корень текущей кучи - максимальный несортированный элемент массива
            // Ставим его в конец, увеличивая отсортированную часть и уменьшая кучу.
            final T temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            // Восстанавливаем кучу, у которой заменили корень
            heapifyRoot(array, 0, i);
        }
    }

    /**
     * Преобразует в бинарную кучу часть массива, начинающуюся с заданной позиции корня.
     * Ожидает, что оба поддерева корня уже являются бинарными кучами.
     *
     * @param array   - массив, содержащий бинарную кучу
     * @param rootPos - позиция корня кучи (оба поддерев должны уже быть кучами)
     * @param heapEnd - конец  пространства (не включительно), занимаемого кучей в массиве
     */
    private void heapifyRoot(T[] array, int rootPos, int heapEnd) {
        int largestPos = rootPos;
        final int leftChildPos = 2 * rootPos + 1;
        final int rightChildPos = 2 * rootPos + 2;
        // Находим позицию наибольшего элемента среди корня и его потомков (если они существуют, т. е. влезают в пространство кучи)
        if (leftChildPos < heapEnd && greaterThan(array[leftChildPos], array[largestPos])) {
            largestPos = leftChildPos;
        }
        if (rightChildPos < heapEnd && greaterThan(array[rightChildPos], array[largestPos])) {
            largestPos = rightChildPos;
        }
        if (largestPos != rootPos) {
            // Ставим наибольший элемент в корень
            final T temp = array[rootPos];
            array[rootPos] = array[largestPos];
            array[largestPos] = temp;
            // Рекурсивно восстанавливаем кучу, спускаясь в поддерево, с которым обменяли элемент
            heapifyRoot(array, largestPos, heapEnd);
        }
    }
}
