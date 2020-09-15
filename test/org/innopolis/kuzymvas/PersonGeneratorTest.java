package org.innopolis.kuzymvas;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PersonGeneratorTest {

    private static final int testAmount = 42;
    private static final int significantAmount = 100_000;
    private static final String[] names = {
            "Andrew", "Bill", "Cedric", "Evan", "Frank",
            "Amanda", "Beatrice", "Cathrine", "Eva", "Fionna"
    };

    @Test
    public void testConstructorException() {
        try {
            new PersonGenerator(null);
            Assert.fail("Constructor accepted null instead of names array");
        } catch (IllegalArgumentException ignored) {

        }
    }

    @Test
    public void testGeneratePersons() {
        PersonGenerator personGenerator = new PersonGenerator(names);
        Person[] persons = personGenerator.generatePersons(testAmount);
        Assert.assertEquals("Person generator returned array of wrong size", testAmount, persons.length);
    }

    @Test
    public void testGeneratorDistribution() {
        PersonGenerator personGenerator = new PersonGenerator(names);
        Person[] persons = personGenerator.generatePersons(significantAmount);
        int[] sexCounters = new int[2];
        int[] ageCounters = new int[Person.MAX_AGE - Person.MIN_AGE + 1];
        int[] nameCounters = new int[names.length];
        List<String> namesList = Arrays.asList(names); // Для применения contains
        for (Person person : persons) {
            if (person.getSex() == Person.Sex.MAN) {
                sexCounters[0]++;
            } else {
                sexCounters[1]++;
            }
            ageCounters[person.getAge() - Person.MIN_AGE]++;
            nameCounters[namesList.indexOf(person.getName())]++;
        }
        double meanAge = 0., varianceAge = 0.;
        for (int i = 0; i < ageCounters.length; i++) {
            meanAge += ageCounters[i] * (i + Person.MIN_AGE);
        }
        meanAge /= significantAmount;
        for (int i = 0; i < ageCounters.length; i++) {
            varianceAge += ageCounters[i] * Math.pow((i + Person.MIN_AGE) - meanAge, 2);
        }
        varianceAge /= significantAmount - 1;
        System.out.println("Generation size = " + significantAmount);
        System.out.println("Number of MAN: " + sexCounters[0]);
        System.out.println("Number of WOMAN: " + sexCounters[1]);
        System.out.println("Mean value of age = " + meanAge
                                   + " Expected mean value = " + (Person.MAX_AGE - Person.MIN_AGE) / 2);
        System.out.println("Variance of age = " + varianceAge +
                                   " Expected variance = " + Math.pow((Person.MAX_AGE - Person.MIN_AGE), 2) / 12.);
        for (int i = 0; i < names.length; i++) {
            System.out.println("Number of persons named " + names[i] + ": " + nameCounters[i]);
        }
    }
}