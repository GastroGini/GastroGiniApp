package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainEventOrderTest {

    ProductCategory cat;
    ProductDescription description;
    ProductList list;
    Product product;
    Person person;
    Event event;
    EventTable table;


    EventOrder test1, test2;

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
        table = new EventTable(1, "Tisch 1", event);
        table.save();

        test1 = new EventOrder(table, new Date());test1.save();
        test2 = new EventOrder(table, new Date());test2.save();
    }

    @Test
    public void testCreate() {
        EventOrder eventOrder = new EventOrder(table, new Date());
        eventOrder.save();

        EventOrder query = EventOrder.load(EventOrder.class, eventOrder.getId());
        assertEquals(eventOrder, query);
    }

    @Test
    public void testUpdate(){
        test2.orderTime = new Date();
        test2.eventTable = new EventTable(1, "adsf", event);
        test2.eventTable.save();
        test2.save();

        EventOrder query = EventOrder.load(EventOrder.class, test2.getId());
        assertEquals(test2, query);
    }

}
