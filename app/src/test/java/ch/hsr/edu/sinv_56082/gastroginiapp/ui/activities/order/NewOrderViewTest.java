package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class NewOrderViewTest {

    private Intent intent;
    private TestDataSetup testData;

    @Before
    public void setUp(){
        testData = new TestDataSetup();

        intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("eventTable-uuid", testData.table.getUuid().toString());
    }

    @Test
    public void testSetup(){
        NewOrderView activity  = Robolectric.buildActivity(NewOrderView.class).withIntent(intent).create().get();

        activity.newOrderPositionUUID.add(testData.prod.getUuid().toString());

        activity.finishButton.performClick();

        assertEquals(activity.finishButton.getText().toString(), "Bestellungsübersicht");

    }
}
