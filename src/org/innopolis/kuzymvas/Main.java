package org.innopolis.kuzymvas;

import org.innopolis.kuzymvas.exceptions.SameAgeNamePersonsException;
import org.innopolis.kuzymvas.sorting.HeapSorter;
import org.innopolis.kuzymvas.sorting.MergeSorter;
import org.innopolis.kuzymvas.sorting.Sorter;

import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        final String[] names = {
                "Andrew", "Amanda",
                "Blake", "Beatrice",
                "Cedric", "Cathrine",
                "Denis", "Diana",
                "Eric", "Emily",
                "Frederick", "Francisca",
                "George", "Ginni",
                "Howard", "Helen",
                "Ian", "Irene",
                "Jacob", "Jenna",
                "Karl", "Klara",
                "Leon", "Layla",
                "Marcus", "Mathilda",
                "Nicolas", "Nina"
        };
        final PersonGenerator generator = new PersonGenerator(names);
        final Person[] humanSizeArrayOriginal = generator.generatePersons(10);
        final Person[] smallArrayOriginal = generator.generatePersons(10_000);
        final Person[] mediumArrayOriginal = generator.generatePersons(100_000);
        final Person[] largeArrayOriginal = generator.generatePersons(1_000_000);

        final Sorter<Person> mergeSorter = new MergeSorter<>();
        final Sorter<Person> heapSorter = new HeapSorter<>();

        final Comparator<Person> comparatorExceptionsAllowed = new PersonComparator(true);
        final Comparator<Person> comparatorExceptionsDisallowed = new PersonComparator(false);
        System.out.println("Демонстрация работы сортировщиков:");
        System.out.println(" Исходный массив = " + Arrays.toString(humanSizeArrayOriginal));
        final Person[] sortedByMerge = getSortingResults(mergeSorter, comparatorExceptionsAllowed,
                                                         comparatorExceptionsDisallowed, humanSizeArrayOriginal);
        System.out.println(" Массив отсортированный слиянием = " + Arrays.toString(sortedByMerge));
        final Person[] sortedByHeap = getSortingResults(heapSorter, comparatorExceptionsAllowed,
                                                        comparatorExceptionsDisallowed, humanSizeArrayOriginal);
        System.out.println(" Массив отсортированный через кучу = " + Arrays.toString(sortedByHeap));

        performComparison(mergeSorter, heapSorter, smallArrayOriginal, "массива их 10K элементов");
        performComparison(mergeSorter, heapSorter, mediumArrayOriginal, "массива их 10оK элементов");
        performComparison(mergeSorter, heapSorter, largeArrayOriginal, "массива их 1М элементов");
    }

    /**
     * Преобразует суммарное время обработки в наносекундах за заданное число прогонов
     * в среднее время обработки прогона в секундах
     *
     * @param original   - суммарное время обработки в наносекундах
     * @param runsNumber - число прогонов
     * @return - среднее время обработки прогона в секундах
     */
    private static double averageMeasurementsToSeconds(long original, int runsNumber) {
        final double nano = original / (double) runsNumber;
        return nano / Math.pow(10, 9);
    }

    /**
     * Выполняет сравнение работы сортировщика слиянием и сортировщика через куча на заданном массиве
     *
     * @param mergeSorter      - сортировщик слиянием
     * @param heapSorter       - сортировщик через кучу
     * @param arrayOriginal    - оригинал массива для сравнения. Не будет изменен в ходе сортировки
     * @param arrayDescription - описание массива для вывода на экран
     */
    private static void performComparison(
            Sorter<Person> mergeSorter, Sorter<Person> heapSorter,
            Person[] arrayOriginal, String arrayDescription) {
        final int runsNumber = 20;
        System.out.println("Выполняем " + runsNumber + " проходов, что измерить время сортировки для "
                                   + arrayDescription + ":");
        long measurementMerge = getMeasurementOverMultipleRuns(mergeSorter, arrayOriginal, runsNumber);
        System.out.println("  Средняя оценка времени для сортировки слиянием: = "
                                   + averageMeasurementsToSeconds(measurementMerge, runsNumber));
        long measurementHeap = getMeasurementOverMultipleRuns(heapSorter, arrayOriginal, runsNumber);
        System.out.println("  Средняя оценка времени для сортировки через кучу: = "
                                   + averageMeasurementsToSeconds(measurementHeap, runsNumber));
    }

    private static long getMeasurementOverMultipleRuns(
            Sorter<Person> sorter, Person[] arrayOriginal, int runsNumber) {
        Comparator<Person> comparatorExceptionsAllowed = new PersonComparator(true);
        Comparator<Person> comparatorExceptionsDisallowed = new PersonComparator(false);
        long measurementMerge = 0;
        try {
            for (int i = 0; i < runsNumber; i++) {
                measurementMerge += measureSortingSpeed(sorter, comparatorExceptionsAllowed,
                                                        arrayOriginal);
            }
        } catch (SameAgeNamePersonsException e) {
            System.out.println("    Перехвачено исключение, вызванное сравнением людей с одинаковым именем и возрастом: " + e.getMessage());
            System.out.println("    Перезапускаем обработку, подавив выброс исключений.");
            measurementMerge = 0;
            for (int i = 0; i < runsNumber; i++) {
                measurementMerge += measureSortingSpeed(sorter, comparatorExceptionsDisallowed,
                                                        arrayOriginal);
            }
        }
        return measurementMerge;
    }

    /**
     * Измеряет время сортировки данным сортировщиком данного массива в наносекундах
     *
     * @param sorter        - сортировщик
     * @param comparator    - компаратор для сортировки
     * @param arrayOriginal - оригинал массива. Не будет изменен в ходе сортировки
     * @return - время выполнения сортировщиком сортировки массива в наносекундах
     */
    private static long measureSortingSpeed(
            Sorter<Person> sorter, Comparator<Person> comparator,
            Person[] arrayOriginal) throws SameAgeNamePersonsException {
        final Person[] workCopy = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
        final long start = System.nanoTime();
        sorter.sort(workCopy, comparator);
        return System.nanoTime() - start;
    }

    /**
     * Возвращает результат сортировки данным сортировщиком данного массива
     *
     * @param sorter                         - сортировщик
     * @param comparatorExceptionsAllowed    - компаратор для сортировки со включенными исключениями
     * @param comparatorExceptionsDisallowed - компаратор для сортировки с подавленными исключениями
     * @param arrayOriginal                  - оригинал массива. Не будет изменен в ходе сортировки
     * @return - отсортированная копия массива
     */
    private static Person[] getSortingResults(
            Sorter<Person> sorter, Comparator<Person> comparatorExceptionsAllowed,
            Comparator<Person> comparatorExceptionsDisallowed,
            Person[] arrayOriginal) {
        Person[] workCopy;
        try {
            workCopy = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
            sorter.sort(workCopy, comparatorExceptionsAllowed);
        } catch (SameAgeNamePersonsException e) {
            System.out.println("   Исключение во время работы сортировщика с массивом длиной " + arrayOriginal.length
                                       + " : " + e.getMessage() + "\n   Повторяем сортировку с начала с подавленными исключениями.");
            workCopy = Arrays.copyOf(arrayOriginal, arrayOriginal.length);
            try {
                sorter.sort(workCopy, comparatorExceptionsDisallowed);
            } catch (SameAgeNamePersonsException ignored) {
            }
        }
        return workCopy;
    }
}
