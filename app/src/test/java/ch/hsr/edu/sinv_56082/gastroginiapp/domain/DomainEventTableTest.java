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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainEventTableTest {

    ProductCategory cat;
    ProductDescription description;
    ProductList list;
    Product product;
    Person person;
    Event event;

    EventTable test1, test2;


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
        event = new Event(list, "event1", new Date(), new Date(), person);
        event.save();


        test1 = new EventTable(1, "Tisch 1", event);test1.save();
        test2 = new EventTable(2, "Tisch 2", event);test2.save();
    }



    @Test
    public void testQueryWithSelectByName(){
        EventTable query = new Select().from(EventTable.class).where("name=?", test1.name).executeSingle();
        assertEquals(query, test1);
    }

    @Test
    public void testCreate() {
        EventTable eventTable = new EventTable(4, "testit", event);
        eventTable.save();

        EventTable query = EventTable.load(EventTable.class, eventTable.getId());
        assertEquals(eventTable, query);
    }

    @Test
    public void testUpdate(){
        test2.name = "asdf";
        test2.number = 123;
        test2.event = new Event("test", new Date(), new Date(), person, list);
        test2.event.save();
        test2.save();

        EventTable query = EventTable.load(EventTable.class, test2.getId());
        assertEquals(test2, query);
    }

}
