package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventListActivity;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ProductDescriptionListActivityTest {

    private TestDataSetup testData;

    @Before
    public void setUp(){

        testData = new TestDataSetup();
    }

    @Test
    public void testSetup(){
        ProductDescriptionListActivity activity = Robolectric.setupActivity(ProductDescriptionListActivity.class);
        assertEquals(activity.s_recycler.getAdapter().getItemCount(), 1);
    }


}
