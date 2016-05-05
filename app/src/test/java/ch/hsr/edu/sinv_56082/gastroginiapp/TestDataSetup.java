package ch.hsr.edu.sinv_56082.gastroginiapp;


import java.util.Date;

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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.WorkAssignment;

public class TestDataSetup {

    public WorkAssignment work;
    public ProductCategory cat;
    public Person pers;
    public ProductList pList;
    public ProductDescription pDesc;
    public Product prod;
    public Event event;
    public EventTable table;
    public EventOrder order;
    public OrderPosition orderPos;
    public OrderState state;

    public TestDataSetup(){
        cat = new ProductCategory("Kategorie 1");
        cat.save();

        pers = new Person("Tom", "What");
        pers.save();
        new Person("Jim", "wjhfe").save();
        new Person("Marry", "Sims").save();
        new Person("Joanne", "Filde").save();

        pList = new ProductList("ProductList 1");
        pList.save();
        new ProductList("ProductList 2").save();
        new ProductList("ProductList 3").save();
        new ProductList("ProductList 4").save();
        new ProductList("ProductList 5").save();


        pDesc = new ProductDescription("Cola", "Getränk", cat);
        pDesc.save();

        prod = new Product(pDesc, pList, 3.5, "3dl");
        prod.save();
        new Product(pDesc, pList, 4.5, "4dl").save();
        new Product(pDesc, pList, 5.5, "5dl").save();
        new Product(pDesc, pList, 6.5, "6dl").save();

        event = new Event(pList, "Test Event", new Date(), new Date(), pers);
        event.save();

        work = new WorkAssignment(new Date(), new Date(), pers, event);
        work.save();

        table = new EventTable(1, "Tisch 1", event);
        table.save();

        new EventTable(2, "Tisch 2", event).save();
        new EventTable(3, "Tisch 3", event).save();
        new EventTable(4, "Tisch 4", event).save();
        new EventTable(5, "Tisch 5", event).save();

        order = new EventOrder(table, new Date(), pers);
        order.save();

        state = OrderState.STATE_OPEN;

        orderPos = new OrderPosition(new Date(), state, prod, order);
        orderPos.save();
    }
}
