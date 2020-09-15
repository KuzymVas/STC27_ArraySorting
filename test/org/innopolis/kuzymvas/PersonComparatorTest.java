package org.innopolis.kuzymvas;

import org.junit.Assert;
import org.junit.Test;

public class PersonComparatorTest {

    @Test
    public void testCompare() {
        final Person personLesserSex = new Person(Person.Sex.MAN, 40, "Bbbb");
        final Person personGreaterSex = new Person(Person.Sex.WOMAN, 50, "Aaaa");
        final Person personLesserAge = new Person(Person.Sex.WOMAN, 50, "Bbbb");
        final Person personGreaterAge = new Person(Person.Sex.WOMAN, 40, "Aaaa");
        final Person personLesserName = new Person(Person.Sex.WOMAN, 40, "Aaaa");
        final Person personGreaterName = new Person(Person.Sex.WOMAN, 40, "Bbbb");
        final Person samePerson1 = new Person(Person.Sex.WOMAN, 40, "Aaaa");
        final Person samePerson2 = new Person(Person.Sex.WOMAN, 40, "Aaaa");
        final PersonComparator comparator = new PersonComparator();
        Assert.assertEquals("Person c полем пол MAN не меньше Person с полем пол WOMAN", -1,
                            comparator.compare(personLesserSex, personGreaterSex));
        Assert.assertEquals("Person c полем пол WOMAN не больше Person с полем пол MAN", 1,
                            comparator.compare(personGreaterSex, personLesserSex));
        Assert.assertEquals("Person с большим возрастом не меньше Person с меньшим возрастом", -1,
                            comparator.compare(personLesserAge, personGreaterAge));
        Assert.assertEquals("Person с меньшим возрастом не больше Person с большим возрастом", 1,
                            comparator.compare(personGreaterAge, personLesserAge));
        Assert.assertEquals("Person с именем впереди по алфавиту не меньше Person с именем дальше по алфавиту", -1,
                            comparator.compare(personLesserName, personGreaterName));
        Assert.assertEquals("Person с именем дальше по алфавиту не больше Person с именем впереди по алфавиту", 1,
                            comparator.compare(personGreaterName, personLesserName));
        Assert.assertEquals("Person не равен сам себе", 0, comparator.compare(samePerson1, samePerson1));
        Assert.assertEquals("Два идентичных Person не равны", 0, comparator.compare(samePerson1, samePerson2));
    }
}