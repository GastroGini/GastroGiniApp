package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ViewControllerTest {

    private TestDataSetup tds;

    @Before
    public void setup(){
        tds = new TestDataSetup();
    }

    @Test
    public void testGetModelList(){

    }
    @Test
    public void testGetUUID(){
        String productUUID;
        ViewController<Product> vc=new ViewController(Product.class);
        //productUUID = vc.get(tds.prod.getUuid());
    }
    @Test
    public void testGetString(){

    }
    @Test
    public void testCreate(){

    }
    @Test
    public void testPrepare(){

    }
    @Test
    public void testUpdate(){

    }
    @Test
    public void testDelete(){

    }
}
