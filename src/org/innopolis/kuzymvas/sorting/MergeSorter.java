package org.innopolis.kuzymvas.sorting;

import org.innopolis.kuzymvas.exceptions.EqualElementsDuringSortException;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSorter<T> implements Sorter<T> {

    // Флаг, регулирующий выбрасывание исключений
    private boolean throwExceptionOnEqual;
    // Текущий компаратор
    private Comparator<T> currentComparator;

    public MergeSorter(boolean throwExceptionOnEqual) {
        this.throwExceptionOnEqual = throwExceptionOnEqual;
    }

    @Override
    public void sort(T[] array, Comparator<T> comparator) throws EqualElementsDuringSortException {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null");
        }
        if (array == null) {
            return;
        }
        currentComparator = comparator;
        final T[] copy = Arrays.copyOf(array, array.length);
        topDownSplitMerge(copy, array, 0, array.length);
    }

    /**
     * Сравнивает два объекта с помощью текущего компаратора
     *
     * @param o1 - первый объект
     * @param o2 - второй объект
     * @return - true, если первый объект меньше или равен второму, false иначе
     * @throws EqualElementsDuringSortException - будет выброшено, если установлен флаг и объекты равны
     */
    private boolean lessOrEquals(T o1, T o2) throws EqualElementsDuringSortException {
        if (throwExceptionOnEqual && currentComparator.compare(o1, o2) == 0) {
            throw new EqualElementsDuringSortException("Merge sorter encountered equal elements during the sorting");
        }
        return currentComparator.compare(o1, o2) <= 0;
    }

    /**
     * Разбивает исходный массив на две части и сортирует каждую отдельно.
     * Затем выполняет слияние отсортированных частей в целевой массив, получая единый отсортированный массив
     *
     * @param source - исходный массив
     * @param dest   - целевой массив, который будет заполнен рузльтатом сортировки
     * @param start  - индекс начального элемента области сортировки
     * @param end    - индекс конечного (не включительно) элемента области сортировки
     * @throws EqualElementsDuringSortException - будет выброшено, если установлен флаг и алгоритм обнаружит равные элементы
     */
    private void topDownSplitMerge(T[] source, T[] dest, int start, int end) throws EqualElementsDuringSortException {
        if ((end - start) <= 1) { // Массивы из одного элемента считаются отсортированными
            return;
        }
        final int middle = (start + end) / 2;
        // Целевой и рабочий массив меняются местами на каждом уровне рекурсии
        topDownSplitMerge(dest, source, start, middle);
        topDownSplitMerge(dest, source, middle, end);
        topDownMerge(source, dest, start, middle, end);
    }

    /**
     * Выполняет слияние двух отсортированных  частей исходного массива в один отсортированный целевой массив
     *
     * @param source - массив, содержащий две отсортированные части
     * @param dest   - целевой массив, в которой будет помещен результат их слияния
     * @param start  - индекс начального элемента области сортировким
     * @param middle - индекс точки разделения двух отсортированных частей (конец первой (не включительно) и начало второй)
     * @param end-   индекс конечного (не включительно) элемента области сортировки
     * @throws EqualElementsDuringSortException - будет выброшено, если установлен флаг и алгоритм обнаружит равные элементы
     */
    private void topDownMerge(
            T[] source, T[] dest, int start, int middle, int end) throws EqualElementsDuringSortException {
        int leftPos = start;
        int rightPos = middle;
        for (int i = start; i < end; i++) {
            if ((leftPos < middle)
                    && (rightPos >= end || lessOrEquals(source[leftPos], source[rightPos]))) {
                dest[i] = source[leftPos];
                leftPos++;
            } else {
                dest[i] = source[rightPos];
                rightPos++;
            }
        }
    }

    @Override
    public void setThrowExceptionOnEqual(boolean throwExceptionOnEqual) {
        this.throwExceptionOnEqual = throwExceptionOnEqual;
    }
}
