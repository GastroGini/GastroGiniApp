package ch.hsr.edu.sinv_56082.gastroginiapp;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.products.ProductCategory;

import static org.junit.Assert.*;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ProductCategory cat = new ProductCategory();
        cat.name = "test";
        cat.save();

        ProductCategory cat2 = new Select().from(ProductCategory.class).executeSingle();
        assertEquals(cat.name, cat2.name);
    }
}