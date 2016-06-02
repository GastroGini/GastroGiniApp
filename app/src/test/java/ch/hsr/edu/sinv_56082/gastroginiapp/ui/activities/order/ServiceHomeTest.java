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

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ServiceHomeTest {

    private Intent intent;
    private TestDataSetup testData;

    @Before
    public void setUp(){
        testData = new TestDataSetup();

        intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("event-uuid", testData.event.getUuid().toString());
        intent.putExtra("userName", "tesst");
        intent.putExtra("eventPassword", "asdf");
    }

    @Test
    public void testSetup(){

        ServiceHome activity = Robolectric.buildActivity(ServiceHome.class).withIntent(intent).create().get();

        assertEquals(activity.eventTablesRecyclerView.getAdapter().getItemCount(), 5);
    }

}
