package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view;

import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ViewControllerTest {

    private TestDataSetup testData;

    @Before
    public void setup(){
        testData = new TestDataSetup();
    }

    @Test
    public void testGetModelList(){
        ViewController<Product> viewController = new ViewController<>(Product.class);
        assertEquals(viewController.getModelList().size(), 4);

    }

    @Test
    public void testGetUUID(){
        ViewController<Product> vc=new ViewController(Product.class);
        Product product = vc.get(testData.prod.getUuid());
        assertEquals(product.price, testData.prod.price, 0.0001);
    }

    @Test
    public void testGetString(){
        ViewController<Product> vc=new ViewController(Product.class);
        Product product = vc.get(testData.prod.getUuid().toString());
        assertEquals(product.price, testData.prod.price, 0.0001);
    }

    @Test
    public void testCreate(){
        ViewController<Product> viewController = new ViewController<>(Product.class);
        Product newProd = viewController.create(new Supplier<Product>() {
            @Override
            public Product supply() {
                return new Product(testData.pDesc, testData.pList, 10.50, "2dl");
            }
        });

        assertEquals(testData.pDesc, newProd.productDescription);
        assertEquals(testData.pList, newProd.productList);
        assertEquals(newProd.price, 10.50, 0.0001);
        assertEquals(newProd.volume, "2dl");
    }

    @Test
    public void testPrepare(){
        ViewController<Product> viewController = new ViewController<>(Product.class);
        Product newProd = viewController.prepare(new Supplier<Product>() {
            @Override
            public Product supply() {
                return new Product(testData.pDesc, testData.pList, 10.50, "2dl");
            }
        });

        viewController.update(newProd, new Consumer<Product>() {
            @Override
            public void consume(Product product) {
                product.volume = "test";
            }
        });

        assertNotNull(new Select().from(Product.class).where("volume==?", "test").executeSingle());
    }

    @Test
    public void testUpdate(){
        ViewController<Product> viewController = new ViewController<>(Product.class);
        viewController.update(testData.prod, new Consumer<Product>() {
            @Override
            public void consume(Product product) {
                product.volume = "test";
            }
        });

        assertEquals(viewController.get(testData.prod.getUuid()).volume, "test");
    }

    @Test
    public void testDelete(){
        ViewController<Product> viewController = new ViewController<>(Product.class);
        viewController.delete(testData.prod);

        assertEquals(3, viewController.getModelList().size());
    }
}
