package org.innopolis.kuzymvas.sorting;

import org.innopolis.kuzymvas.exceptions.EqualElementsDuringSortException;

import java.util.Comparator;

public interface  Sorter<T> {

    /**
     *  Сортирует данный массив по возрастанию, используя данный компаратор для сравнения элементов
     * @param array - массив для сортировки. БУДЕТ изменен в процессе сортировки
     * @param comparator - компаратор для сравнения элементов массива
     * @throws EqualElementsDuringSortException - будет выброшено при обнаружении равных элементов
     * в процессе сортировки, если сортировщику указано выбрасывать исключения в этом случае
     */
    void sort(T[] array, Comparator<T> comparator)  throws EqualElementsDuringSortException;

    /**
     * Определяет поведение сортировщика при обнаружении в процессе сортировки равных элементов.
     * При передаче false, равные элементы обрабатываются, как обычно
     * При передаче true, при обнаружении равных элементов генерируется исключение
     * @param throwExceptionOnEqual - флаг, определяющий поведение при обнаружении равных элементов
     */
    void setThrowExceptionOnEqual(boolean throwExceptionOnEqual);
}
