package org.innopolis.kuzymvas.sorting;

/**
 * Фабрика для генерации различных сортировщиков для тестов. Не используется в рабочем коде и потому
 * располагается среди тестов
 */
public class SortersFactory {

    /**
     * Возможные типы сортировщиков для тестирования
     */
    public enum SorterType {
        MERGE, // Сортировщик слиянием
        HEAP   // Сортировщик через бинарную кучу
    }

    /**
     * Возвращает сортировщик заданного типа
     *
     * @param type                  - тип сортировщика
     * @param throwExceptionOnEqual - начальное значение флага выброса исключений. Передается в конструктор сортировщика.
     * @param <T>                   - тип сортируемых значений
     * @return - сортировщик заданного типа
     */
    public static <T> Sorter<T> getSorter(SorterType type, boolean throwExceptionOnEqual) {
        switch (type) {
            case MERGE:
                return new MergeSorter<>(throwExceptionOnEqual);
            case HEAP:
                return new HeapSorter<>(throwExceptionOnEqual);
            default:
                return null;
        }
    }
}
