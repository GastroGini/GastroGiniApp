package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


import com.activeandroid.query.Select;

import java.util.Date;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.WorkAssignment;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;



public class DummyData {
    public DummyData(){

        if (new Select().from(ProductList.class).execute().size() == 0) {

            ProductCategory cat = new ProductCategory("Kategorie 1");
            cat.save();

            Person pers = new Person("Tom", "What");
            pers.save();
            new Person("Jim", "wjhfe").save();
            new Person("Marry", "Sims").save();
            new Person("Joanne", "Filde").save();

            ProductList pList = new ProductList("ProductList 1");
            pList.save();
            new ProductList("ProductList 2").save();
            new ProductList("ProductList 3").save();
            new ProductList("ProductList 4").save();
            new ProductList("ProductList 5").save();


            ProductDescription pDesc = new ProductDescription("Cola", "Getränk", cat);
            pDesc.save();

            Product prod = new Product(pDesc, pList, 3.5, "3dl");
            prod.save();
            new Product(pDesc, pList, 4.5, "4dl").save();
            new Product(pDesc, pList, 5.5, "5dl").save();
            new Product(pDesc, pList, 6.5, "6dl").save();

            Event event = new Event(pList, "Test Event", new Date(), new Date(), pers);
            event.save();

            EventTable table = new EventTable(1, "Tisch 1", event);
            table.save();

            new EventTable(2, "Tisch 2", event).save();
            new EventTable(3, "Tisch 3", event).save();
            new EventTable(4, "Tisch 4", event).save();
            new EventTable(5, "Tisch 5", event).save();

            EventOrder order = new EventOrder(table, new Date());
            order.save();

            OrderState state = new OrderState("Unbezahlt", "");
            state.save();

            OrderPosition orderPos = new OrderPosition(new Date(), state, prod, order);
            orderPos.save();
        }
    }
}
