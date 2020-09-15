package org.innopolis.kuzymvas.sorting;

import org.innopolis.kuzymvas.exceptions.EqualElementsDuringSortException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

@RunWith(Parameterized.class)
public class SortersTest {

    private final String sorterName;
    private final SortersFactory.SorterType sorterType;
    private static final int significantAmount = 100_000;

    public SortersTest(SortersFactory.SorterType sorterType, String sorterName) {
        this.sorterName = sorterName;
        this.sorterType = sorterType;
    }

    @Parameterized.Parameters
    public static Collection sortersToTest() {
        return Arrays.asList(new Object[][]{
                {SortersFactory.SorterType.MERGE, "Merge Sorter"},
                {SortersFactory.SorterType.HEAP, "Heap Sorter"}
        });
    }

    @Test
    public void testException() {
        final Integer[] noEqualsArr = {1, 2, 3};
        final Integer[] equalsArr = {1, 1, 1};
        final Sorter<Integer> sorter = SortersFactory.getSorter(sorterType, false);
        Assert.assertNotNull(
                "Internal testing set failure: factory returned null instead of sorter. Check test parameters.",
                sorter);
        sorter.setThrowExceptionOnEqual(false);
        try {
            sorter.sort(noEqualsArr,
                        Comparator.comparingInt((Integer i) -> i));
        } catch (EqualElementsDuringSortException e) {
            Assert.fail(sorterName + " threw an exception on array without equals in in");
        }
        try {
            sorter.sort(equalsArr,
                        Comparator.comparingInt((Integer i) -> i));
        } catch (EqualElementsDuringSortException e) {
            Assert.fail(
                    sorterName + " threw an exception on array with equals in in, when was forbidden from throwing");
        }
        sorter.setThrowExceptionOnEqual(true);
        try {
            sorter.sort(noEqualsArr,
                        Comparator.comparingInt((Integer i) -> i));
        } catch (EqualElementsDuringSortException e) {
            Assert.fail(sorterName + " threw an exception on array without equals in in");
        }
        try {
            sorter.sort(equalsArr,
                        Comparator.comparingInt((Integer i) -> i));
            Assert.fail(sorterName + " didn't throw an exception on array with equals in in, when was asked to");
        } catch (EqualElementsDuringSortException ignore) {
        }
    }

    @Test
    public void testSort() {
        final Sorter<Integer> sorter = SortersFactory.getSorter(sorterType, false);
        Assert.assertNotNull(
                "Internal testing set failure: factory returned null instead of sorter. Check test parameters.",
                sorter);
        sorter.setThrowExceptionOnEqual(false);
        try {
            sorter.sort(null, Comparator.comparingInt((Integer i) -> i));
            final Integer[] single = {1};
            sorter.sort(single, Comparator.comparingInt((Integer i) -> i));
            final Integer[] multiple = {1, 10, 2, 9, 3, 8, 4, 7, 5, 6};
            sorter.sort(multiple, Comparator.comparingInt((Integer i) -> i));
            for (int i = 0; i < multiple.length - 1; i++) {
                Assert.assertTrue(sorterName + " returned not fully sorted array", multiple[i] <= multiple[i + 1]);
            }
        } catch (EqualElementsDuringSortException e) {
            Assert.fail(sorterName + " threw an exception on array, when was forbidden from throwing");
        }
        final Sorter<Double> sorterDouble = SortersFactory.getSorter(sorterType, false);
        Assert.assertNotNull(
                "Internal testing set failure: factory returned null instead of sorter. Check test parameters.",
                sorter);
        sorter.setThrowExceptionOnEqual(false);
        try {
            final Double[] array = new Double[significantAmount];
            for (int i = 0; i < significantAmount; i++) {
                array[i] = Math.random();
            }
            sorterDouble.sort(array, Comparator.comparingDouble((Double i) -> i));
            for (int i = 0; i < array.length - 1; i++) {
                Assert.assertTrue(sorterName + " returned not fully sorted array", array[i] <= array[i + 1]);
            }
        } catch (EqualElementsDuringSortException e) {
            Assert.fail(sorterName + " threw an exception on array, when was forbidden from throwing");
        }
    }
}