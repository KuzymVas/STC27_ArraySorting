package org.innopolis.kuzymvas;

import org.innopolis.kuzymvas.exceptions.SameAgeNamePersonsException;

import java.util.Comparator;

/**
 * Минимальная реализация компаратора для класса Person
 */
public class PersonComparator implements Comparator<Person> {

    // Флаг, регулирующий выбрасывание исключений
    private final boolean allowExceptions;

    public PersonComparator(boolean allowExceptions) {
        this.allowExceptions = allowExceptions;
    }

    /**
     * Сравнивает два объекта класса Person и возвращает -1, если p1 меньше(первее) p2,
     * 1, если больше, и 0, если они равны. Правила сравнения в порядке значимости:
     * 1) MAN меньше, чем WOMAN
     * 2) Больший возраст меньше младшего
     * 3) Имена сортируются лексиографически
     *
     * @param p1 - первый объект Person для сравнения
     * @param p2 - второй объект Person для сравнения
     * @return - -1, если p1 меньше(первее) p2, 1, если больше, и 0, если они равны.
     */
    @Override
    public int compare(Person p1, Person p2) {
        if (allowExceptions) {
            if ((p1.getAge() == p2.getAge())
                    && (p1.getName().equals(p2.getName()))) {
                throw new SameAgeNamePersonsException("Comparator encountered two persons with same age and name");
            }
        }
        if (p1 == p2) {
            return 0;
        }
        if (p1.getSex() != p2.getSex()) {
            return (p1.getSex() == Person.Sex.MAN) ? -1 : 1;
        }
        if (p1.getAge() != p2.getAge()) {
            return (p1.getAge() > p2.getAge()) ? -1 : 1;
        }
        return p1.getName().compareTo(p2.getName());
    }
}
