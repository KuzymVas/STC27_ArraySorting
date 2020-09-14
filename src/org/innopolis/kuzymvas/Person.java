package org.innopolis.kuzymvas;

import java.util.Objects;

/**
 * Иммутабельный класс Person для сортировки
 */
public class Person {

    // Границы поля возраста
    public final static int MAX_AGE = 100;
    public final static int MIN_AGE = 0;

    public enum Sex {
        WOMAN,
        MAN
    }

    /**
     *  Создает новый иммутабельный объект Person
     * @param sex - пол
     * @param age - возраст в интверале [0-100]
     * @param name - имя.
     */
    public Person(Sex sex, int age, String name) {
        this.sex = sex;
        this.name = name;
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("Age must be in between [0-100]");
        }
        this.age = (byte) age;
    }

    private final Sex sex;
    private final String name;
    private final byte age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                sex == person.sex &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sex, name, age);
    }

    @Override
    public String toString() {
        return "{" +
                "Пол:" + sex +
                ", Возраст:" + age +
                ", Имя:" + name  +
                '}';
    }
}
