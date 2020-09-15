package org.innopolis.kuzymvas;

import org.innopolis.kuzymvas.exceptions.SameAgeNamePersonsException;
import org.junit.Assert;
import org.junit.Test;

public class PersonComparatorTest {

    @Test
    public void testException() {
        final Person originalPerson = new Person(Person.Sex.WOMAN, 42, "Danny");
        final Person differentAgePerson = new Person(Person.Sex.WOMAN, 24, "Danny");
        final Person differentNamePerson = new Person(Person.Sex.WOMAN, 42, "Anny");
        final Person differentSexPerson = new Person(Person.Sex.MAN, 42, "Danny");

        final PersonComparator comparatorExceptionsDisallowed = new PersonComparator(false);
        try {
            comparatorExceptionsDisallowed.compare(originalPerson, differentAgePerson);
            comparatorExceptionsDisallowed.compare(originalPerson, differentNamePerson);
        } catch (SameAgeNamePersonsException e) {
            Assert.fail("Comparator threw an exception on comparisons, which shouldn't generate an exception");
        }
        try {
            comparatorExceptionsDisallowed.compare(originalPerson, differentSexPerson);
        } catch (SameAgeNamePersonsException e) {
            Assert.fail(
                    "Comparator threw an exception on comparison, which should generate an exception, when was forbidden from throwing");
        }
        final PersonComparator comparatorExceptionsAllowed = new PersonComparator(true);
        try {
            comparatorExceptionsAllowed.compare(originalPerson, differentAgePerson);
            comparatorExceptionsAllowed.compare(originalPerson, differentNamePerson);
        } catch (SameAgeNamePersonsException e) {
            Assert.fail("Comparator threw an exception on comparisons, which shouldn't generate an exception");
        }
        try {
            comparatorExceptionsAllowed.compare(originalPerson, differentSexPerson);
            Assert.fail("Comparator didn't throw an exception on comparison, which should generate an exception");
        } catch (SameAgeNamePersonsException ignore) {
        }
    }

    @Test
    public void testCompare() {
        final Person personLesserSex = new Person(Person.Sex.MAN, 40, "Beth");
        final Person personGreaterSex = new Person(Person.Sex.WOMAN, 50, "Aaron");
        final Person personLesserAge = new Person(Person.Sex.WOMAN, 50, "Beth");
        final Person personGreaterAge = new Person(Person.Sex.WOMAN, 40, "Aaron");
        final Person personLesserName = new Person(Person.Sex.WOMAN, 40, "Aaron");
        final Person personGreaterName = new Person(Person.Sex.WOMAN, 40, "Beth");
        final Person samePerson1 = new Person(Person.Sex.WOMAN, 40, "Aaron");
        final Person samePerson2 = new Person(Person.Sex.WOMAN, 40, "Aaron");
        final PersonComparator comparator = new PersonComparator(false);
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