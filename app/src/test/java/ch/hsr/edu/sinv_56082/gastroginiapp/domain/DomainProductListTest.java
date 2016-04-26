package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainProductListTest {

    ProductList test1, test2;

    @Before
    public void setUp(){
        test1 = new ProductList("test1");
        test2 = new ProductList("test2");
        test2.save(); test1.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        ProductList query = new Select().from(ProductList.class).where("name=?", test1.name).executeSingle();
        assertEquals(query, test1);
    }

    @Test
    public void testCreate() {
        ProductList list = new ProductList("TestList");
        list.save();

        ProductList query = ProductList.load(ProductList.class, list.getId());
        assertEquals(list, query);
    }

    @Test
    public void testUpdate(){
        test2.name = "asdf";
        test2.save();

        ProductList query = ProductList.load(ProductList.class, test2.getId());
        assertEquals(test2, query);
    }

    @Test
    public void testToString(){
        assertEquals(test2.toString(), test2.name);
    }
}
