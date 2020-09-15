package org.innopolis.kuzymvas.sorting;

import java.util.Comparator;

/**
 * Интерфейс алгоритма сортировки
 * @param <T> - тип сортируемого массива
 */
public interface Sorter<T> {

    /**
     * Сортирует данный массив по возрастанию, используя данный компаратор для сравнения элементов
     *
     * @param array      - массив для сортировки. БУДЕТ изменен в процессе сортировки
     * @param comparator - компаратор для сравнения элементов массива
     */
    void sort(T[] array, Comparator<T> comparator);
}
