package org.innopolis.kuzymvas;

import org.junit.Assert;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testConstructors() {
        try {
            new Person(Person.Sex.MAN, 101, "Test");
            Assert.fail("Constructor was able to violate upper boundary of Person age value");
        } catch (IllegalArgumentException ignored) {

        }
        try {
            new Person(Person.Sex.MAN, -1, "Test");
            Assert.fail("Constructor was able to violate lower boundary of Person age value");
        } catch (IllegalArgumentException ignored) {

        }
    }

    @Test
    public void testGetters() {
        final Person person = new Person(Person.Sex.MAN, 50, "Test");
        Assert.assertEquals("Person failed to return proper sex", Person.Sex.MAN, person.getSex());
        Assert.assertEquals("Person failed to return proper age", 50, person.getAge());
        Assert.assertEquals("Person failed to return proper name", "Test", person.getName());
        final Person otherPerson = new Person(Person.Sex.WOMAN, 42, null);
        Assert.assertEquals("Person failed to return proper sex", Person.Sex.WOMAN, otherPerson.getSex());
        Assert.assertEquals("Person failed to return proper age", 42, otherPerson.getAge());
        Assert.assertEquals("Person failed to return proper name", null, otherPerson.getName());
    }

    @Test
    public void testEqualsAndHash() {
        final Person samePerson1 = new Person(Person.Sex.MAN, 50, "Test");
        final Person samePerson2 = new Person(Person.Sex.MAN, 50, "Test");
        final Person differentSexPerson = new Person(Person.Sex.WOMAN, 50, "Test");
        final Person differentAgePerson = new Person(Person.Sex.MAN, 40, "Test");
        final Person differentNamePerson = new Person(Person.Sex.MAN, 50, "XYZ");

        Assert.assertEquals("Person is no equal to itself",samePerson1,samePerson1);
        Assert.assertEquals("Person hash code changes without change of its field",samePerson1.hashCode(),samePerson1.hashCode());
        Assert.assertEquals("Persons with same attributes are not equal",samePerson1,samePerson2);
        Assert.assertEquals("Equal persons have different hashes",samePerson1.hashCode(),samePerson2.hashCode());
        Assert.assertNotEquals("Persons of different sex are equal",samePerson1,differentSexPerson);
        Assert.assertNotEquals("Different persons have same hash (possible weak has function)",samePerson1.hashCode(),differentSexPerson.hashCode());
        Assert.assertNotEquals("Persons of different age are equal",samePerson1,differentAgePerson);
        Assert.assertNotEquals("Different persons have same hash (possible weak has function)",samePerson1.hashCode(),differentAgePerson.hashCode());
        Assert.assertNotEquals("Persons with different names are equal",samePerson1,differentNamePerson);
        Assert.assertNotEquals("Different persons have same hash (possible weak has function)",samePerson1.hashCode(),differentNamePerson.hashCode());

    }

}