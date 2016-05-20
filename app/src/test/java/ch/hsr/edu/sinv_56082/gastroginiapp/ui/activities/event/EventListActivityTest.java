package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

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
public class EventListActivityTest {

    private TestDataSetup testData;

    @Before
    public void setUp(){

        testData = new TestDataSetup();
    }

    @Test
    public void testSetup(){
        EventListActivity activity = Robolectric.setupActivity(EventListActivity.class);
        assertEquals(activity.eventListMyEventsRecyclerView.getAdapter().getItemCount(), 1);
    }

}
