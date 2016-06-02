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

public class UnsavedTestDataSetup {

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

    public UnsavedTestDataSetup(){
        cat = new ProductCategory("Kategorie 1");

        pers = new Person("Tom", "What");
        new Person("Jim", "wjhfe");
        new Person("Marry", "Sims");
        new Person("Joanne", "Filde");

        pList = new ProductList("ProductList 1");
        new ProductList("ProductList 2");
        new ProductList("ProductList 3");
        new ProductList("ProductList 4");
        new ProductList("ProductList 5");


        pDesc = new ProductDescription("Cola", "Getränk", cat);

        prod = new Product(pDesc, pList, 3.5, "3dl");
        new Product(pDesc, pList, 4.5, "4dl");
        new Product(pDesc, pList, 5.5, "5dl");
        new Product(pDesc, pList, 6.5, "6dl");

        event = new Event(pList, "Test Event", new Date(), new Date(), pers);


        work = new WorkAssignment(new Date(), new Date(), pers, event);


        table = new EventTable(1, "Tisch 1", event);

        new EventTable(2, "Tisch 2", event);
        new EventTable(3, "Tisch 3", event);
        new EventTable(4, "Tisch 4", event);
        new EventTable(5, "Tisch 5", event);

        order = new EventOrder(table, new Date(), pers);

        state = OrderState.STATE_OPEN;

        orderPos = new OrderPosition(new Date(), state, prod, order);

    }
}
