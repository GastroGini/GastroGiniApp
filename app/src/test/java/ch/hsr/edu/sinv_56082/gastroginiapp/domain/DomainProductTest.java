package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainProductTest {

    ProductCategory cat;
    ProductDescription description;
    ProductList list;

    Product test1;

    @Before
    public void setUp(){
        cat = new ProductCategory("cat");
        description = new ProductDescription("productDescriptionName", "productDescriptionDesc", cat);
        list = new ProductList("list");
        cat.save();description.save();list.save();

        test1 = new Product(description, list, 1.0, "1dl");
        test1.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        Product desc = new Select().from(Product.class).where("price = ? and volume = ?", test1.price, test1.volume).executeSingle();

        assertEquals(test1, desc);
    }

    @Test
    public void testCreate() {
        Product pro = new Product(description, list, 3.60, "3dl");
        pro.save();

        Product query = Product.load(Product.class, pro.getId());

        assertEquals(query, pro);
    }


    @Test
    public void testUpdate() {
        test1.price = 4.0;
        test1.volume = "kja";
        test1.productList = new ProductList("test2");
        test1.productList.save();
        test1.save();

        Product query = Product.load(Product.class, test1.getId());
        assertEquals(test1,query);
    }

    @Test
    public void testGet(){
        Product query = Product.get(test1.getUuid());
        assertEquals(test1, query);
    }

}
