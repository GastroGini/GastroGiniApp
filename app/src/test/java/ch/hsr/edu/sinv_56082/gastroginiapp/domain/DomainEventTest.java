package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainEventTest {

    ProductCategory cat;
    ProductDescription description;
    ProductList list;
    Product product;
    Person person;

    Event test1, test2;

    @Before
    public void setUp(){
        cat = new ProductCategory("cat");
        description = new ProductDescription("name", "desc", cat);
        list = new ProductList("list");
        product = new Product(description,list,2.0,"2dl");
        person = new Person("John", "Silver");
        cat.save();
        description.save();
        list.save();
        product.save();
        person.save();

        test1 = new Event(list, "event1", new Date(), new Date(), person);test1.save();
        test2 = new Event(list, "event2", new Date(), new Date(), person);test2.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        Event query = new Select().from(Event.class).where("name=?", test1.name).executeSingle();
        assertEquals(query, test1);
    }

    @Test
    public void testCreate() {
        Event event = new Event(list, "testit", new Date(), new Date(), person);
        event.save();

        Event query = Event.load(Event.class, event.getId());
        assertEquals(event, query);
    }

    @Test
    public void testUpdate(){
        test2.name = "asdf";
        test2.productList = new ProductList("adsf");
        test2.host = new Person("asdf","wef");
        test2.productList.save();
        test2.host.save();
        test2.save();

        Event query = Event.load(Event.class, test2.getId());
        assertEquals(test2, query);
    }

    @Test
    public void testGet(){
        Event query = Event.get(test1.getUuid());
        assertEquals(test1, query);
    }
}
