package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainProductDescriptionTest {

    ProductDescription test1;
    ProductCategory cat;

    @Before
    public void setUp(){
        cat = new ProductCategory("category");
        cat.save();
        test1 = new ProductDescription("name", "description", cat);
        test1.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        ProductDescription desc = new Select().from(ProductDescription.class).where("name = ?", test1.name).executeSingle();

        assertEquals(test1, desc);
    }

    @Test
    public void testCreate() {
        ProductDescription desc = new ProductDescription("name", "desc", cat);
        desc.save();

        ProductDescription query = ProductDescription.load(ProductDescription.class, desc.getId());

        assertEquals(query,desc);
    }

    @Test
    public void testUpdate() {
        test1.description = "new desc";
        test1.name = "kja";
        test1.save();

        ProductDescription desc = ProductDescription.load(ProductDescription.class, test1.getId());
        assertEquals(test1, desc);
    }

    @Test
    public void testToString(){
        assertEquals(test1.toString(), test1.name);
    }

}
