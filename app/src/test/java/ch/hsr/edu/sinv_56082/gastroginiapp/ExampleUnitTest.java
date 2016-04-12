package ch.hsr.edu.sinv_56082.gastroginiapp;

import com.activeandroid.query.Select;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;

import static org.junit.Assert.*;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "ch.hsr.edu.sinv_56082.gastroginiapp")
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        ProductCategory cat = new ProductCategory();
        cat.name = "test";
        cat.save();

        ProductCategory cat2 = ProductCategory.get(cat.getUuid());
        assertEquals(cat.name, cat2.name);
    }
}