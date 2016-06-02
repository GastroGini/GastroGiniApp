package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.app.Activity;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;
import static org.robolectric.Shadows.shadowOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventListActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonViewHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventViewHolder;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EventListActivityTest {

    private TestDataSetup testData;
    private EventListActivity activity;

    @Before
    public void setUp(){
        testData = new TestDataSetup();

    }

    @Test
    public void testSetup(){
        activity = Robolectric.setupActivity(EventListActivity.class);
        assertEquals(1, activity.eventListMyEventsRecyclerView.getAdapter().getItemCount());
    }

    @Test
    public void myEventsRecyclerViewIsCollapsedOnEnteringActivity(){
        activity = Robolectric.setupActivity(EventListActivity.class);
        activity.myEventsExpandCollapseIcon.callOnClick();
        assertEquals(View.VISIBLE, activity.eventListMyEventsRecyclerView.getVisibility());
    }

    @Test
    public void fabOnClickStartsNewActivityTest(){
        activity = Robolectric.setupActivity(EventListActivity.class);
        activity.fab.performClick();
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }

}
