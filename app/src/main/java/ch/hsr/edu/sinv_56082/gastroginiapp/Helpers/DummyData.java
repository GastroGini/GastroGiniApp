package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

import com.activeandroid.query.Select;

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

public class DummyData {
    public DummyData(){
        if (new Select().from(ProductList.class).execute().size() == 0) {
            ProductCategory cat = new ProductCategory("Kalte Getränke");
            cat.save();
            ProductCategory cat2 = new ProductCategory("Warme Getränke");
            cat2.save();
            new ProductCategory("Aloholische Getränke").save();
            new ProductCategory("Biere").save();
            new ProductCategory("Weine").save();
            ProductCategory cat3 = new ProductCategory("Speisen");
            cat3.save();
            new ProductCategory("Vorspeisen").save();
            new ProductCategory("Salate").save();
            new ProductCategory("Dessert").save();

            Person pers = new Person("Tom", "What");
            pers.save();
            new Person("Jim", "wjhfe").save();
            new Person("Marry", "Sims").save();
            new Person("Joanne", "Filde").save();

            ProductList pList2 = new ProductList("ProductList 1");
            pList2.save();
            ProductList pList = new ProductList("ProductList 2");
            pList.save();
            new ProductList("ProductList 3").save();
            new ProductList("ProductList 4").save();
            new ProductList("ProductList 5").save();

            ProductDescription pDesc = new ProductDescription("Cola", "Getränk", cat);
            pDesc.save();
            ProductDescription pDesc2 = new ProductDescription("Ice Tea","Gezuckerter schwarzer Tee mit Limonade.",cat);
            pDesc2.save();
            new ProductDescription("Fanta","Erfrischungsgetränk aus Limonade und Orange mit Mineralwasser",cat).save();
            new ProductDescription("Sprite","Gezuckertes Etwas, das Menschen trinken",cat).save();
            new ProductDescription("Coca Cola Zero","Wie Coca Cola, einfach ohne Zucker.",cat).save();
            ProductDescription pDesc3 = new ProductDescription("Red Bull","Süsse Bullenpisse, auch Taurin genannt, mit sehr viel Koffein.",cat);
            pDesc3.save();
            new ProductDescription("Cuke","Eine neue Variante von Coca Cola, definitiv geniessbar.",cat).save();
            new ProductDescription("Ovomaltine","Warme Milch mit Schokolade von der Marke Ovomaltine.",cat2).save();
            new ProductDescription("Nesquick warm","Warme Milch mit Nesquick-Schokoladenpulver.",cat2).save();
            new ProductDescription("Schwarzer Tee","Klassisch türkischer Tee.",cat2).save();
            new ProductDescription("Döner Kebab","Ein türkisches Gericht. Fleisch, Gemüse etc. mit Sauce in Brot.",cat3).save();
            new ProductDescription("Dürüm","Aehnlich wie Kebab, einfach in Fladenbrot gewickelt.",cat3).save();

            Product prod = new Product(pDesc, pList, 4.5, "4dl");
            prod.save();
            new Product(pDesc, pList, 5.5, "5dl").save();
            new Product(pDesc2, pList, 6.5, "6dl").save();
            new Product(pDesc2, pList, 6.5, "6dl").save();
            new Product(pDesc3, pList, 6.5, "6dl").save();
            new Product(pDesc2, pList2, 5.5, "5dl").save();
            new Product(pDesc2, pList2, 6.5, "6dl").save();
            new Product(pDesc2, pList2, 6.5, "6dl").save();
            new Product(pDesc3, pList2, 6.5, "6dl").save();


            Event event = new Event(pList, "Test Event", new Date(), new Date(), pers);
            event.save();

            EventTable table = new EventTable(1, "Tisch 1", event);
            table.save();

            new EventTable(2, "Tisch 2", event).save();
            new EventTable(3, "Tisch 3", event).save();
            new EventTable(4, "Tisch 4", event).save();
            new EventTable(5, "Tisch 5", event).save();

            EventOrder order = new EventOrder(table, new Date(), pers);
            order.save();

            OrderPosition orderPos = new OrderPosition(new Date(), OrderState.STATE_OPEN, prod, order);
            orderPos.save();
        }
    }
}
