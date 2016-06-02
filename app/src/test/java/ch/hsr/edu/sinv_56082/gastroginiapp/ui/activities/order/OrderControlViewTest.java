package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OrderControlViewTest {

    private Intent intent;
    private TestDataSetup testData;
    ArrayList<String> newOrderPositionUUID = new ArrayList<>();

    @Before
    public void setUp(){
        testData = new TestDataSetup();

        intent = new Intent(Intent.ACTION_VIEW);
        newOrderPositionUUID.add(testData.prod.getUuid().toString());
        intent.putStringArrayListExtra("newOrderPositionsUUID", newOrderPositionUUID);
        intent.putExtra("eventTable-uuid", testData.table.getUuid().toString());
    }

    @Test
    public void testSetup(){
        OrderControlView activity  = Robolectric.buildActivity(OrderControlView.class).withIntent(intent).create().get();
        assertEquals(activity.productList.size(),1);
    }
}
