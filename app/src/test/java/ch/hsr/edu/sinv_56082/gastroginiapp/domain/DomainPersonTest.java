package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainPersonTest {

    Person test1, test2;

    @Before
    public void setUp(){
        test1 = new Person("Test1", "Test1");
        test2 = new Person("Test2", "Test2ü");
        test1.save();
        test2.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        Person query = new Select().from(Person.class).where("firstName=?", test1.firstName).executeSingle();
        assertEquals(query, test1);
    }

    @Test
    public void testCreate() {
        Person person = new Person("John", "Silver");
        person.save();

        Person query = Person.load(Person.class, person.getId());
        assertEquals(person, query);
    }

    @Test
    public void testUpdate(){
        test2.firstName = "asdf";
        test2.lastName = "wef";
        test2.save();

        Person query = Person.load(Person.class, test2.getId());
        assertEquals(test2, query);
    }

    @Test
    public void testFullName(){
        assertEquals(test2.getFullName(), "Test2 Test2ü");
    }

    @Test
    public void testCreateWithUUID() {
        Person person = new Person(UUID.randomUUID(), "John", "Silver");
        person.save();

        Person query = Person.load(Person.class, person.getId());
        assertEquals(person, query);
    }

    @Test
    public void testGet(){
        Person query = Person.get(test1.getUuid());
        assertEquals(test1, query);
    }
}
