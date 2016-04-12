package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainProductCategoryTest {

    ProductCategory test1, test2;

    @Before
    public void setUp(){
        test1 = new ProductCategory("Test1");
        test2 = new ProductCategory("Test2");
        test1.save();
        test2.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        ProductCategory query = new Select().from(ProductCategory.class).where("name=?", test1.name).executeSingle();
        assertEquals(query, test1);
    }

    @Test
    public void testCreate() {
        ProductCategory category = new ProductCategory("TestCategory");
        category.save();

        ProductCategory query = ProductCategory.load(ProductCategory.class, category.getId());
        assertEquals(category, query);
    }

    @Test
    public void testGet(){
        ProductCategory query = ProductCategory.get(test1.getUuid());
        assertEquals(test1, query);
    }


}
