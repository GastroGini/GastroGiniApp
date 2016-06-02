package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities;


import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import static org.robolectric.Shadows.shadowOf;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HomeScreenActivityTest {
    HomeScreenActivity activity;
    Button eventButton;
    Button menuButton;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(HomeScreenActivity.class);
        eventButton = (Button) activity.findViewById(R.id.eventButton);
        menuButton = (Button) activity.findViewById(R.id.menuButton);

    }

    @Test
    public void testSetup(){
        assertEquals(eventButton.getText().toString(), activity.getResources().getString(R.string.events));
        assertEquals(menuButton.getText().toString(), activity.getResources().getString(R.string.menu));
    }

    @Test
    public void eventButtonCallsNextActivity(){
        eventButton.performClick();
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }

    @Test
    public void menuButtonCallsNextActivity(){
        menuButton.performClick();
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }

}
