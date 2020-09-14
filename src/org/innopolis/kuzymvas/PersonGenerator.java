package org.innopolis.kuzymvas;

import java.util.Arrays;

public class PersonGenerator {

    private final String[] names;

    /**
     * Создает новый генератор и инициализирует его копией заданного массива имен
     * @param names - массив имен, допустимых при генерации
     */
    public  PersonGenerator(String[] names) {
        if (names == null) {
            throw new IllegalArgumentException("Names array can't be null");
        }
        this.names = Arrays.copyOf(names, names.length);
    }

    /**
     * Возвращает случайное равномерно распределенное целое числое в интвервале [MinLimit,MaxLimit]
     * @param minLimit - нижняя граница интервала распределения
     * @param maxLimit - верхняя граница интервала распределения
     * @return - случайное целое число в интервале [MinLimit,MaxLimit]
     */
    private int getRandomInLimits(int minLimit, int maxLimit) {
        return  (int)(Math.random() * ((maxLimit - minLimit) + 1)) + minLimit;
    }

    /**
     * Генерирует новый объект Person со случайным полом из Person.Sex,
     * возрастом в интервале [Person.MIN_AGE, Person.MAX_AGE] и именем из заданного при создании
     * генератора списка
     * @return - сгенерированный объект  Person
     */
    public Person generatePerson() {
        int nameInd = getRandomInLimits(0, names.length - 1);
        int age = getRandomInLimits(Person.MIN_AGE, Person.MAX_AGE);
        Person.Sex sex = (getRandomInLimits(0, 1) == 0) ? Person.Sex.MAN : Person.Sex.WOMAN;
        return  new Person(sex,age,names[nameInd]);
    }

    /**
     * Генерирует масиив Person заданного размера
     * @param amount - размер массива для генерации
     * @return - сгенерированный массив
     */
    public Person[] generatePersons(int amount) {
        Person[] persons = new Person[amount];
        for (int i = 0; i < amount; i++) {
            persons[i] = generatePerson();
        }
        return  persons;
    }
}
