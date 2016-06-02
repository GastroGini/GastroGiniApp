package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;


import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order.ServiceHome;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ProductListActivityTest {

    private TestDataSetup testData;
    private Intent intent;

    @Before
    public void setUp(){

        testData = new TestDataSetup();

        intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("menucardRowItem-uuid", testData.pList.getUuid());
    }

    @Test
    public void testSetup(){
        ProductListActivity activity  = Robolectric.buildActivity(ProductListActivity.class).withIntent(intent).create().get();
        assertEquals(activity.productRecyclerView.getAdapter().getItemCount(), 4);
    }


}
