package org.innopolis.kuzymvas;

import org.junit.Assert;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testConstructors() {
        try {
            final Person person = new Person(Person.Sex.MAN, 101, "Test");
            Assert.fail("Constructor was able to violate upper boundary of Person age value");
        } catch (IllegalArgumentException ignored) {

        }
        try {
            final Person person = new Person(Person.Sex.MAN, -1, "Test");
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

}