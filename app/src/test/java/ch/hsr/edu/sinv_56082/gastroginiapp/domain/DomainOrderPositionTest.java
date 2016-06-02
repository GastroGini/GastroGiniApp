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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainOrderPositionTest {

    ProductCategory cat;
    ProductDescription description;
    ProductList list;
    Product product;
    Person person;
    Event event;
    EventTable table;
    EventOrder order;
    OrderState state;

    OrderPosition test1, test2;

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
        order = new EventOrder(table, new Date(), person);order.save();
        state = OrderState.STATE_OPEN;

        test1 = new OrderPosition(new Date(), state, product, order);test1.save();
        test2 = new OrderPosition(new Date(), state, product, order);test2.save();
    }


    @Test
    public void testCreate() {
        OrderPosition orderPosition = new OrderPosition(new Date(), state, product, order);
        orderPosition.save();

        OrderPosition query = OrderPosition.load(OrderPosition.class, orderPosition.getId());
        assertEquals(orderPosition, query);
    }

    @Test
    public void testUpdate(){
        test2.payTime = new Date();
        test2.eventOrder = new EventOrder(table, new Date(), person);
        test2.eventOrder.save();
        test2.orderState = OrderState.STATE_OPEN;
        test2.product = new Product(description, list, 12.0, "324dl");
        test2.product.save();
        test2.save();

        OrderPosition query = OrderPosition.load(OrderPosition.class, test2.getId());
        assertEquals(test2, query);
    }


}
