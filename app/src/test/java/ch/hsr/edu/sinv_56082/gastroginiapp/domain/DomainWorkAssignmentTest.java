package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.WorkAssignment;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainWorkAssignmentTest {

    ProductCategory cat;
    ProductDescription description;
    ProductList list;
    Product product;
    Person person;
    Event event;

    WorkAssignment test1, test2;

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

        test1 = new WorkAssignment(new Date(), new Date(), person, event);test1.save();
        test2 = new WorkAssignment(new Date(), new Date(), person, event);test2.save();
    }

    @Test
    public void testCreate() {
        WorkAssignment workAssignment = new WorkAssignment(new Date(), new Date(), person, event);
        workAssignment.save();

        WorkAssignment query = WorkAssignment.load(WorkAssignment.class, workAssignment.getId());
        assertEquals(workAssignment, query);
    }

    @Test
    public void testUpdate(){
        test2.endTime = new Date();
        test2.startTime = new Date();
        test2.person = new Person("asdf","wef");
        test2.person.save();
        test2.event = new Event(list, "adsf", new Date(), new Date(), person);
        test2.event.save();
        test2.save();

        WorkAssignment query = WorkAssignment.load(WorkAssignment.class, test2.getId());
        assertEquals(test2, query);
    }

}
